"""
Headless GUI smoke test: builds the real window, loads a real JSON + PNG,
exercises the texture picker and folder warning, and runs a generation.
"""
import os
import sys
import json
import tempfile
import shutil

os.environ.setdefault("QT_QPA_PLATFORM", "offscreen")
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from PyQt6.QtWidgets import QApplication, QMessageBox
from qfluentwidgets import setTheme, Theme
from woodcreator import WoodCreator

WS = r"C:\Users\minec\MCreatorWorkspaces\voxelized_furniture"
SRC = os.path.join(WS, "src", "main", "resources", "assets", "voxelized_furniture")
REAL_JSON = os.path.join(SRC, "models", "block", "acacia_drawer.json")
REAL_PNG = os.path.join(SRC, "textures", "block", "acaciasprinkler.png")

failures = []


def check(label, cond, detail=""):
    print("[%s] %s%s" % ("PASS" if cond else "FAIL", label,
                         ("  -> " + detail) if detail else ""))
    if not cond:
        failures.append(label)


app = QApplication(sys.argv)
setTheme(Theme.DARK)
win = WoodCreator()
win.show()
app.processEvents()

check("window constructed", win is not None)
check("9 wood checkboxes present", len(win.wood_boxes) == 9, str(len(win.wood_boxes)))
check("cherry + bamboo checkboxes exist",
      "cherry" in win.wood_boxes and "bamboo" in win.wood_boxes)
check("all woods default-checked",
      all(cb.isChecked() for cb in win.wood_boxes.values()))
check("source fields exist",
      win.json_edit is not None and win.png_edit is not None
      and win.json_out_edit is not None and win.png_out_edit is not None)

# Load real sources
win.set_json(REAL_JSON)
win.set_png(REAL_PNG)
app.processEvents()

check("4 texture checkboxes built", len(win.texture_boxes) == 4,
      str(len(win.texture_boxes)))
check("textures default selected", len(win.selected_textures()) == 4)
check("all 9 woods selected", len(win.selected_woods()) == 9)
check("png preview loaded", not win.preview.pixmap().isNull())
check("wood_ prefix checkbox exists by default OFF",
      win.wood_prefix_cb.isChecked() is False)
check("overwrite checkbox exists by default OFF",
      win.overwrite_cb.isChecked() is False)

# Deselect one texture, then verify only it is excluded
win.texture_boxes["minecraft:block/light_blue_concrete"].setChecked(False)
check("deselect works", len(win.selected_textures()) == 3)

# Select All / None
win._set_all_textures(True)
check("Select All re-checks everything", len(win.selected_textures()) == 4)
win._set_all_textures(False)
check("Select None clears", len(win.selected_textures()) == 0)

# Leave only the two wood textures selected for the generation step below,
# leaving both concrete refs out so we can prove they pass through untouched.
win.texture_boxes["minecraft:block/stripped_acacia_log"].setChecked(True)
win.texture_boxes["minecraft:block/stripped_oak_log"].setChecked(True)
check("2 textures selected for generation",
      len(win.selected_textures()) == 2, str(len(win.selected_textures())))

win._set_all_woods(False)
check("wood None clears", len(win.selected_woods()) == 0)
win._set_all_woods(True)

# Folder warning behaviour
tmp = tempfile.mkdtemp(prefix="woodcreator_gui_")
good = os.path.join(tmp, "block")
bad = os.path.join(tmp, "wrongname")
models_dir = os.path.join(tmp, "models")
for d in (good, bad, models_dir):
    os.makedirs(d)

win.json_out_edit.setText(models_dir)
win.png_out_edit.setText(good)
win._check_folder_match()
check("no warning when PNG folder matches ref folder",
      not win.folder_warning.isVisible())

win.png_out_edit.setText(bad)
win._check_folder_match()
check("warning shown for mismatched folder",
      win.folder_warning.isVisible(), win.folder_warning.text()[:55])
win.png_out_edit.setText(good)

# JSON output field -> models dir, PNG output field -> block dir
win.json_out_edit.setText(models_dir)
win.png_out_edit.setText(good)
win._check_folder_match()

win.generate()
app.processEvents()

# Pump the event loop until the worker reports back (wait() would block the
# queued signal delivery the worker depends on, so spin events instead).
from PyQt6.QtCore import QElapsedTimer
timer = QElapsedTimer()
timer.start()
while win.worker is not None and win.worker.isRunning() and timer.elapsed() < 8000:
    app.processEvents()
app.processEvents()
while timer.elapsed() < 8000:
    app.processEvents()
    if not win.generate_btn.isEnabled():
        break
app.processEvents()

produced = sorted(os.listdir(models_dir))
produced_pngs = sorted(os.listdir(good))
print("\nJSONs:", produced)
print("PNGs:", produced_pngs)
check("9 JSONs written by GUI", len(produced) == 9, str(len(produced)))
check("9 PNGs written by GUI", len(produced_pngs) == 9, str(len(produced_pngs)))
check("all generated names lowercase",
      all(n == n.lower() for n in produced + produced_pngs))
check("generate button re-enabled after run", win.generate_btn.isEnabled())

# A deselected texture must survive in the generated JSON
d = json.load(open(os.path.join(models_dir, "oak_acacia_drawer.json"),
                   encoding="utf-8"))
check("GUI output: concrete preserved",
      d["textures"]["1"] == "minecraft:block/light_blue_concrete")
check("GUI output: acacia rewritten to oak",
      d["textures"]["all"] == "minecraft:block/oak_acaciasprinkler")

# Modal dialogs would block forever headless - stub them out.
_called = {"critical": 0}
QMessageBox.critical = staticmethod(
    lambda *a, **k: _called.__setitem__("critical", _called["critical"] + 1))

# Invalid JSON is rejected without clobbering the current selection
bad_json = os.path.join(tmp, "broken.json")
with open(bad_json, "w", encoding="utf-8") as f:
    f.write("{ this is not valid json ")
before = win.json_path
win.set_json(bad_json)
check("invalid JSON rejected, previous kept", win.json_path == before)
check("error dialog shown for bad JSON", _called["critical"] == 1,
      str(_called["critical"]))

# Re-running with Overwrite OFF must skip everything and change nothing.
mtimes_before = {n: os.path.getmtime(os.path.join(models_dir, n))
                 for n in produced}
win.overwrite_cb.setChecked(False)
win.generate()
app.processEvents()
t2 = QElapsedTimer()
t2.start()
while t2.elapsed() < 8000:
    app.processEvents()
    if not win.worker.isRunning():
        break
app.processEvents()

produced2 = sorted(os.listdir(models_dir))
check("re-run skipped all (still 9 JSONs)", len(produced2) == 9, str(len(produced2)))
check("re-run overwrote nothing",
      all(os.path.getmtime(os.path.join(models_dir, n)) == mtimes_before[n]
          for n in produced2))

# With Overwrite ON the files get replaced.
win.overwrite_cb.setChecked(True)
win.generate()
app.processEvents()
t3 = QElapsedTimer()
t3.start()
while t3.elapsed() < 8000:
    app.processEvents()
    if not win.worker.isRunning():
        break
app.processEvents()
check("overwrite run keeps 9 JSONs", len(os.listdir(models_dir)) == 9)
win.overwrite_cb.setChecked(False)

shutil.rmtree(tmp, ignore_errors=True)

print("\n" + "=" * 50)
if failures:
    print("FAILED (%d): %s" % (len(failures), failures))
    sys.exit(1)
print("ALL GUI TESTS PASSED")