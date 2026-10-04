"""
woodcreator - Minecraft Wood Texture Variant Generator
-----------------------------------------------------
Import a model JSON + a texture PNG, then generate a matching set of copies
for every selected wood type. The wood name goes in front of the filename:

    chair.png   -> oak_chair.png, spruce_chair.png, ...
    chair.json  -> oak_chair.json, spruce_chair.json, ...

Tick "Also prefix with wood_" in the app to get wood_oak_chair.png instead.

Existing files are never clobbered unless "Overwrite old files" is ticked.

Each generated JSON has its *selected* texture references rewritten to point
at that wood variant's PNG, so every pair is self-consistent and can be
dropped straight into a Minecraft mod's assets folder.

Names are always lowercase because Minecraft resource locations are
case-sensitive and only lowercase is safe.

Run:  python woodcreator.py
"""

import os
import sys
import json
import copy
import shutil
import ctypes

from PyQt6.QtCore import Qt, QThread, pyqtSignal
from PyQt6.QtGui import QPixmap
from PyQt6.QtWidgets import (QApplication, QWidget, QVBoxLayout, QHBoxLayout,
                             QLabel, QCheckBox, QFileDialog, QMessageBox,
                             QFrame, QScrollArea, QGroupBox)

try:
    from qfluentwidgets import (BodyLabel, SubtitleLabel, PrimaryPushButton,
                                 PushButton, LineEdit, TextEdit, InfoBar,
                                 InfoBarPosition, setTheme, Theme)
except ImportError:
    print("Error: Missing libraries. Run: pip install qfluentwidgets PyQt6")
    sys.exit(1)

# --- CONSTANTS ---
WOOD_TYPES = ["oak", "spruce", "birch", "jungle", "acacia", "dark_oak",
              "mangrove", "cherry", "bamboo"]
APP_ID = "voxelized.woodcreator.v1"
HERE = os.path.dirname(os.path.abspath(__file__))
CONFIG_PATH = os.path.join(HERE, "config.json")

# Keys whose string values are NOT texture references
NON_TEXTURE_KEYS = {"parent", "model"}

# ============================================================
# CORE LOGIC  (no Qt - stays independently testable)
# ============================================================

def is_texture_ref(key, value):
    """True when `value` under `key` looks like a Minecraft texture reference."""
    if not isinstance(value, str):
        return False
    tail = key.split("|")[-1]
    if tail in NON_TEXTURE_KEYS or tail.startswith("parent"):
        return False
    v = value.strip()
    if not v or " " in v:
        return False
    # Must be namespaced or pathed - bare names like "cube_all" are parents.
    if ":" not in v and "/" not in v:
        return False
    parts = v.split(":")
    path = parts[-1]
    for segment in path.split("/"):
        if segment == "" or not all(c.islower() or c.isdigit() or c in "_.-" for c in segment):
            return False
    return True


def collect_texture_refs(node, trail="", out=None):
    """Recursively walk parsed JSON, returning [(trail, texture_ref), ...].

    `trail` is a "|"-joined key path ("textures|all") so each reference can be
    written back precisely. Lists are indexed with their position number.
    """
    if out is None:
        out = []
    if isinstance(node, dict):
        for key, value in node.items():
            child = "%s|%s" % (trail, key) if trail else str(key)
            collect_texture_refs(value, child, out)
    elif isinstance(node, list):
        for index, value in enumerate(node):
            child = "%s|%d" % (trail, index) if trail else str(index)
            collect_texture_refs(value, child, out)
    elif isinstance(node, str):
        if is_texture_ref(trail.split("|")[-1], node):
            out.append((trail, node.strip()))
    return out


def ordered_unique(pairs):
    """De-duplicate texture refs, keeping first-seen order."""
    seen, result = set(), []
    for _trail, value in pairs:
        if value not in seen:
            seen.add(value)
            result.append(value)
    return result


def set_at_trail(root, trail, value):
    """Assign `value` at the "|"-joined trail inside nested dicts/lists."""
    parts = trail.split("|")
    node = root
    for part in parts[:-1]:
        node = node[int(part)] if part.isdigit() else node[part]
    last = parts[-1]
    if last.isdigit():
        node[int(last)] = value
    else:
        node[last] = value


def rewrite_texture_ref(old, new_stem):
    """Swap a texture ref's filename for `new_stem`, keeping namespace + folder."""
    namespace, path = None, old.strip()
    if ":" in path:
        namespace, path = path.split(":", 1)
    if "/" in path:
        new_path = "%s/%s" % (path.rsplit("/", 1)[0], new_stem)
    else:
        new_path = new_stem
    return "%s:%s" % (namespace, new_path) if namespace else new_path


def ref_folder(ref):
    """Last folder segment of a texture ref, e.g. 'block' for minecraft:block/x."""
    path = ref.split(":", 1)[-1]
    if "/" in path:
        return path.rsplit("/", 1)[0].split("/")[-1].lower()
    return ""


def prefixed(stem, wood, use_wood_prefix=False):
    """Prepend the wood name to a filename stem.

    Default is just the wood name: chair + oak -> oak_chair.
    With use_wood_prefix=True it becomes wood_oak_chair.
    """
    prefix = ("wood_" if use_wood_prefix else "") + wood + "_"
    # Avoid doubling an already-applied prefix (oak_oak_chair, wood_oak_oak_chair)
    while stem.startswith(prefix):
        stem = stem[len(prefix):]
    return prefix + stem

# ============================================================
# GENERATION
# ============================================================

def generate_variants(json_path, png_path, json_out_dir, png_out_dir,
                      woods, selected_refs, log=None, use_wood_prefix=False,
                      overwrite=False):
    """Create one PNG + JSON pair per wood type.

    `selected_refs` is an iterable of texture reference strings to rewrite;
    every other reference in the model is copied through untouched.

    With overwrite=False (the default) any destination file that already exists
    is left alone and reported as SKIPPED. With overwrite=True it is replaced.

    Returns (files_written, textures_rewritten, files_skipped).
    """
    emit = log if log else (lambda _msg: None)

    with open(json_path, "r", encoding="utf-8") as f:
        base_data = json.load(f)

    all_pairs = collect_texture_refs(base_data)
    wanted = set(selected_refs)
    # Every occurrence of a selected ref, not just the first.
    to_rewrite = [(trail, ref) for trail, ref in all_pairs if ref in wanted]

    json_stem = os.path.splitext(os.path.basename(json_path))[0]
    png_stem = os.path.splitext(os.path.basename(png_path))[0]

    files_written = 0
    textures_rewritten = 0
    files_skipped = 0

    for wood in woods:
        new_png_stem = prefixed(png_stem, wood, use_wood_prefix)
        new_json_stem = prefixed(json_stem, wood, use_wood_prefix)

        # 1. Copy the PNG
        dest_png = os.path.join(png_out_dir, "%s.png" % new_png_stem)
        dest_json = os.path.join(json_out_dir, "%s.json" % new_json_stem)

        png_exists = os.path.exists(dest_png)
        json_exists = os.path.exists(dest_json)

        if not overwrite and png_exists:
            emit("SKIP  -> %s (already exists)" % dest_png)
            files_skipped += 1
        else:
            shutil.copy2(png_path, dest_png)
            emit("PNG   -> %s" % dest_png)
            files_written += 1

        # 2. Deep-copy the model and rewrite the selected references
        if not overwrite and json_exists:
            emit("SKIP  -> %s  (already exists)" % dest_json)
            files_skipped += 1
        else:
            data = copy.deepcopy(base_data)
            for trail, ref in to_rewrite:
                set_at_trail(data, trail, rewrite_texture_ref(ref, new_png_stem))

            with open(dest_json, "w", encoding="utf-8") as f:
                json.dump(data, f, indent=2, ensure_ascii=False)
                f.write("\n")
            emit("JSON  -> %s  (%d ref%s rewritten)"
                 % (dest_json, len(to_rewrite), "" if len(to_rewrite) == 1 else "s"))
            files_written += 1
            textures_rewritten += len(to_rewrite)

    emit("Done: %d wood(s) -> %d file(s) written, %d skipped (already existed), "
         "%d texture reference(s) rewritten."
         % (len(woods), files_written, files_skipped, textures_rewritten))
    return files_written, textures_rewritten, files_skipped


def load_config():
    try:
        with open(CONFIG_PATH, "r", encoding="utf-8") as f:
            return json.load(f)
    except Exception:
        return {}


def save_config(data):
    try:
        with open(CONFIG_PATH, "w", encoding="utf-8") as f:
            json.dump(data, f, indent=2)
    except Exception:
        pass  # config problems must never break the app


class GenerateWorker(QThread):
    """Runs generate_variants off the UI thread so the window never freezes."""

    logged = pyqtSignal(str)
    finished_ok = pyqtSignal(int, int, int, int)   # files, refs, wood count, skipped
    failed = pyqtSignal(str)

    def __init__(self, json_path, png_path, json_out, png_out, woods, refs,
                 parent=None, use_wood_prefix=False, overwrite=False):
        super().__init__(parent)
        self.json_path = json_path
        self.png_path = png_path
        self.json_out = json_out
        self.png_out = png_out
        self.woods = woods
        self.refs = refs
        self.use_wood_prefix = use_wood_prefix
        self.overwrite = overwrite

    def run(self):
        try:
            files, refs, skipped = generate_variants(
                self.json_path, self.png_path, self.json_out, self.png_out,
                self.woods, self.refs, log=self.logged.emit,
                use_wood_prefix=self.use_wood_prefix, overwrite=self.overwrite)
            self.finished_ok.emit(files, refs, len(self.woods), skipped)
        except Exception as exc:
            self.failed.emit(str(exc))

# ============================================================
# GUI
# ============================================================

class WoodCreator(QWidget):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Wood Creator - Minecraft Wood Variants")
        self.resize(1000, 840)

        self.json_path = ""
        self.png_path = ""
        self.texture_refs = []      # unique refs found in the JSON
        self.texture_boxes = {}     # ref -> QCheckBox
        self.wood_boxes = {}        # wood -> QCheckBox
        self.worker = None

        self._build_ui()
        self._restore_config()

    # ---------------- UI construction ----------------
    def _build_ui(self):
        root = QVBoxLayout(self)
        root.setContentsMargins(18, 18, 18, 18)
        root.setSpacing(12)

        root.addWidget(SubtitleLabel("Wood Creator", self))
        subtitle = BodyLabel(
            "Duplicate a model JSON + texture PNG for every Minecraft wood type. "
            "Names stay lowercase.", self)
        subtitle.setWordWrap(True)
        root.addWidget(subtitle)

        root.addWidget(self._build_source_group())
        root.addWidget(self._build_destination_group())
        root.addWidget(self._build_texture_group(), 1)
        root.addWidget(self._build_wood_group())
        root.addWidget(self._build_output_group())

    def _make_row(self, label_text, button_text, callback):
        """A label + readonly path field + browse button, returned as a layout."""
        row = QHBoxLayout()
        row.setSpacing(8)
        lbl = BodyLabel(label_text, self)
        lbl.setFixedWidth(110)
        edit = LineEdit(self)
        edit.setReadOnly(True)
        edit.setPlaceholderText("Not selected")
        btn = PushButton(button_text, self)
        btn.clicked.connect(callback)
        row.addWidget(lbl)
        row.addWidget(edit, 1)
        row.addWidget(btn)
        return row, edit

    def _build_source_group(self):
        box = QGroupBox("Source Files", self)
        lay = QVBoxLayout(box)
        lay.setSpacing(8)

        json_row, self.json_edit = self._make_row(
            "Model JSON", "Select JSON", self.browse_json)
        lay.addLayout(json_row)

        png_row, self.png_edit = self._make_row(
            "Texture PNG", "Select PNG", self.browse_png)
        lay.addLayout(png_row)

        preview_row = QHBoxLayout()
        self.preview = QLabel(self)
        self.preview.setFixedSize(64, 64)
        self.preview.setFrameShape(QFrame.Shape.StyledPanel)
        self.preview.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.preview.setText("no\npreview")
        self.preview_label = BodyLabel("Select a PNG to preview it.", self)
        self.preview_label.setWordWrap(True)
        preview_row.addWidget(self.preview)
        preview_row.addWidget(self.preview_label, 1)
        lay.addLayout(preview_row)
        return box

    def _build_destination_group(self):
        box = QGroupBox("Destination Folders", self)
        lay = QVBoxLayout(box)
        lay.setSpacing(8)

        json_row, self.json_out_edit = self._make_row(
            "JSON output", "Browse", self.browse_json_out)
        lay.addLayout(json_row)

        png_row, self.png_out_edit = self._make_row(
            "PNG output", "Browse", self.browse_png_out)
        lay.addLayout(png_row)

        self.folder_warning = BodyLabel("", self)
        self.folder_warning.setWordWrap(True)
        self.folder_warning.setStyleSheet("color: #d99a00;")
        self.folder_warning.setVisible(False)
        lay.addWidget(self.folder_warning)
        return box

    def _build_texture_group(self):
        """Scrollable list of every texture found in the JSON, each selectable."""
        box = QGroupBox(
            "Textures Found In JSON  (tick the ones to point at the wood PNG)", self)
        outer = QVBoxLayout(box)
        outer.setSpacing(6)

        btn_row = QHBoxLayout()
        self.select_all_btn = PushButton("Select All", self)
        self.select_all_btn.clicked.connect(lambda: self._set_all_textures(True))
        self.select_none_btn = PushButton("Select None", self)
        self.select_none_btn.clicked.connect(lambda: self._set_all_textures(False))
        self.texture_count_lbl = BodyLabel("Load a JSON to see its textures.", self)
        btn_row.addWidget(self.select_all_btn)
        btn_row.addWidget(self.select_none_btn)
        btn_row.addWidget(self.texture_count_lbl, 1)
        outer.addLayout(btn_row)

        self.texture_scroll = QScrollArea(self)
        self.texture_scroll.setWidgetResizable(True)
        self.texture_container = QWidget()
        self.texture_layout = QVBoxLayout(self.texture_container)
        self.texture_layout.setSpacing(4)
        self.texture_scroll.setWidget(self.texture_container)
        outer.addWidget(self.texture_scroll)

        self.select_all_btn.setEnabled(False)
        self.select_none_btn.setEnabled(False)
        self.texture_layout.addWidget(BodyLabel(
            "Load a model JSON to list its texture references.", self))
        return box

    def _build_wood_group(self):
        box = QGroupBox("Wood Types", self)
        lay = QVBoxLayout(box)
        lay.setSpacing(8)

        btn_row = QHBoxLayout()
        all_btn = PushButton("All", self)
        all_btn.clicked.connect(lambda: self._set_all_woods(True))
        none_btn = PushButton("None", self)
        none_btn.clicked.connect(lambda: self._set_all_woods(False))
        btn_row.addWidget(all_btn)
        btn_row.addWidget(none_btn)
        btn_row.addStretch()
        lay.addLayout(btn_row)

        wood_row = QHBoxLayout()
        for wood in WOOD_TYPES:
            cb = QCheckBox(wood, self)
            cb.setChecked(True)
            self.wood_boxes[wood] = cb
            wood_row.addWidget(cb)
        wood_row.addStretch()
        lay.addLayout(wood_row)
        return box

    def _build_output_group(self):
        box = QGroupBox("Generate", self)
        lay = QVBoxLayout(box)
        lay.setSpacing(8)

        self.generate_btn = PrimaryPushButton("Generate Wood Variants", self)
        self.generate_btn.clicked.connect(self.generate)
        lay.addWidget(self.generate_btn)

        opt_row = QHBoxLayout()
        self.wood_prefix_cb = QCheckBox('Also prefix with "wood_" (oak_chair -> wood_oak_chair)', self)
        self.wood_prefix_cb.setChecked(False)
        self.overwrite_cb = QCheckBox("Overwrite old files", self)
        self.overwrite_cb.setChecked(False)
        self.overwrite_cb.setToolTip(
            "Off: existing PNG/JSON files are skipped and left untouched.\n"
            "On: existing files are replaced.")
        opt_row.addWidget(self.wood_prefix_cb)
        opt_row.addWidget(self.overwrite_cb)
        opt_row.addStretch()
        lay.addLayout(opt_row)

        self.log_edit = TextEdit(self)
        self.log_edit.setReadOnly(True)
        self.log_edit.setPlaceholderText("Log output will appear here.")
        self.log_edit.setFixedHeight(110)
        lay.addWidget(self.log_edit)
        return box

    # ---------------- browse handlers ----------------
    def browse_json(self):
        path, _ = QFileDialog.getOpenFileName(
            self, "Select Model JSON", "", "JSON Files (*.json);;All Files (*)")
        if path:
            self.set_json(path)

    def browse_png(self):
        path, _ = QFileDialog.getOpenFileName(
            self, "Select Texture PNG", "", "PNG Images (*.png);;All Files (*)")
        if path:
            self.set_png(path)

    def browse_json_out(self):
        self._pick_folder(self.json_out_edit, "Select JSON Output Folder")

    def browse_png_out(self):
        self._pick_folder(self.png_out_edit, "Select PNG Output Folder")

    def _pick_folder(self, edit, title):
        path = QFileDialog.getExistingDirectory(self, title, edit.text() or HERE)
        if path:
            edit.setText(path)
            self._check_folder_match()

# ---------------- loading handlers ----------------
    def set_json(self, path):
        try:
            with open(path, "r", encoding="utf-8") as f:
                data = json.load(f)
        except json.JSONDecodeError as exc:
            QMessageBox.critical(
                self, "Invalid JSON", "Could not parse this file:\n%s" % exc)
            return
        except OSError as exc:
            QMessageBox.critical(self, "Read Error", str(exc))
            return

        self.json_path = path
        self.json_edit.setText(path)
        self.json_edit.setToolTip(path)
        self.texture_refs = ordered_unique(collect_texture_refs(data))
        self._populate_textures()
        self._check_folder_match()
        self.log_edit.append("Loaded JSON: %s (%d texture reference(s) found)"
                             % (os.path.basename(path), len(self.texture_refs)))

    def set_png(self, path):
        self.png_path = path
        self.png_edit.setText(path)
        self.png_edit.setToolTip(path)
        self.preview_label.setText(os.path.basename(path))
        pixmap = QPixmap(path)
        if not pixmap.isNull():
            self.preview.setPixmap(pixmap.scaled(
                60, 60, Qt.AspectRatioMode.KeepAspectRatio,
                Qt.TransformationMode.SmoothTransformation))
        self._check_folder_match()

    def _populate_textures(self):
        """Rebuild the checkbox list from the refs discovered in the JSON."""
        while self.texture_layout.count():
            item = self.texture_layout.takeAt(0)
            if item.widget():
                item.widget().deleteLater()
        self.texture_boxes = {}

        if not self.texture_refs:
            self.texture_layout.addWidget(
                BodyLabel("No texture references found in this JSON.", self))

        for ref in self.texture_refs:
            row = QWidget(self)
            row_lay = QHBoxLayout(row)
            row_lay.setContentsMargins(2, 0, 2, 0)
            row_lay.setSpacing(8)

            cb = QCheckBox(ref, row)
            cb.setChecked(True)
            cb.setToolTip("Rewrites %r in the generated JSONs" % ref)
            cb.stateChanged.connect(self._update_texture_count)
            row_lay.addWidget(cb, 1)

            folder = ref_folder(ref)
            if folder:
                tag = BodyLabel("[%s]" % folder, row)
                tag.setStyleSheet("color: #888888;")
                row_lay.addWidget(tag)

            self.texture_boxes[ref] = cb
            self.texture_layout.addWidget(row)

        self.texture_layout.addStretch()
        has_refs = bool(self.texture_refs)
        self.select_all_btn.setEnabled(has_refs)
        self.select_none_btn.setEnabled(has_refs)
        self._update_texture_count()

    def selected_textures(self):
        return [ref for ref, cb in self.texture_boxes.items() if cb.isChecked()]

    def selected_woods(self):
        return [w for w in WOOD_TYPES if self.wood_boxes[w].isChecked()]

    def _set_all_textures(self, checked):
        for cb in self.texture_boxes.values():
            cb.setChecked(checked)
        self._update_texture_count()

    def _set_all_woods(self, checked):
        for cb in self.wood_boxes.values():
            cb.setChecked(checked)

    def _update_texture_count(self):
        if self.texture_refs:
            self.texture_count_lbl.setText(
                "%d texture(s) found - %d selected"
                % (len(self.texture_refs), len(self.selected_textures())))

# ---------------- destination sanity check ----------------
    def _check_folder_match(self):
        """Warn when the PNG folder doesn't match the refs' subfolder.

        Minecraft resolves 'minecraft:block/x' against <ns>/textures/block/, so
        the PNGs must land in that same folder or the game won't find them.
        """
        self.folder_warning.setVisible(False)
        png_out = self.png_out_edit.text().strip()
        if not png_out or not self.texture_refs:
            return
        folders = {ref_folder(r) for r in self.texture_refs if ref_folder(r)}
        if not folders:
            return
        target = os.path.basename(os.path.normpath(png_out)).lower()
        if target not in folders:
            self.folder_warning.setText(
                "Note: these references point at folder(s) %s, but your PNG output "
                "folder is named '%s'. Put the PNGs in textures/%s/ or the game "
                "won't find them."
                % (", ".join(sorted(folders)), target or "(none)",
                   sorted(folders)[0]))
            self.folder_warning.setVisible(True)

    # ---------------- generation ----------------
    def generate(self):
        """Validate everything, then hand the job to the worker thread."""
        json_out = self.json_out_edit.text().strip()
        png_out = self.png_out_edit.text().strip()

        missing = []
        if not self.json_path:
            missing.append("a model JSON")
        if not self.png_path:
            missing.append("a texture PNG")
        if not json_out:
            missing.append("a PNG output folder")
        if not png_out:
            missing.append("a JSON output folder")
        if missing:
            self._warn("Please select %s." % " and ".join(missing))
            return

        for path, label in ((json_out, "PNG output folder"),
                            (png_out, "JSON output folder")):
            if not os.path.isdir(path):
                self._warn("The %s does not exist:\n%s" % (label, path))
                return
        if not os.path.isfile(self.json_path):
            self._warn("The model JSON no longer exists:\n%s" % self.json_path)
            return
        if not os.path.isfile(self.png_path):
            self._warn("The texture PNG no longer exists:\n%s" % self.png_path)
            return

        woods = self.selected_woods()
        refs = self.selected_textures()
        if not woods:
            self._warn("Select at least one wood type.")
            return
        if not refs:
            self._warn("Select at least one texture to point at the wood PNG.")
            return

        self._save_config()
        self.log_edit.clear()
        self.log_edit.append("Generating %d wood variant(s) from %s..."
                             % (len(woods), os.path.basename(self.png_path)))

        self.generate_btn.setEnabled(False)
        self.worker = GenerateWorker(self.json_path, self.png_path,
                                     json_out, png_out, woods, refs, self,
                                     use_wood_prefix=self.wood_prefix_cb.isChecked(),
                                     overwrite=self.overwrite_cb.isChecked())
        self.worker.logged.connect(self.log_edit.append)
        self.worker.finished_ok.connect(self._on_success)
        self.worker.failed.connect(self._on_failure)
        self.worker.start()

    def _on_success(self, files, refs, wood_count, skipped):
        self.generate_btn.setEnabled(True)
        self.log_edit.append("-" * 50)
        self.log_edit.append("SUCCESS: %d file(s) written across %d wood type(s), "
                             "%d texture reference(s) rewritten, %d skipped."
                             % (files, wood_count, refs, skipped))
        if skipped:
            InfoBar.warning(
                title="Variants Generated",
                content="%d file(s) written, %d skipped because they already "
                        "existed. Tick Overwrite old files to replace them."
                        % (files, skipped),
                orient=Qt.Orientation.Horizontal, isClosable=True,
                position=InfoBarPosition.TOP, duration=5000, parent=self)
        else:
            InfoBar.success(
                title="Variants Generated",
                content="%d files written for %d wood type(s)."
                        % (files, wood_count),
                orient=Qt.Orientation.Horizontal, isClosable=True,
                position=InfoBarPosition.TOP, duration=4000, parent=self)

    def _on_failure(self, error):
        self.generate_btn.setEnabled(True)
        self.log_edit.append("ERROR: %s" % error)
        QMessageBox.critical(self, "Generation Failed", error)

    def _warn(self, message):
        InfoBar.warning(title="Cannot Generate", content=message,
                        orient=Qt.Orientation.Horizontal, isClosable=True,
                        position=InfoBarPosition.TOP, duration=4000, parent=self)

    # ---------------- config persistence ----------------
    def _save_config(self):
        save_config({
            "json": self.json_path,
            "png": self.png_path,
            "png_out": self.json_out_edit.text().strip(),
            "json_out": self.png_out_edit.text().strip(),
        })

    def _restore_config(self):
        cfg = load_config()
        if cfg.get("png_out"):
            self.json_out_edit.setText(cfg["png_out"])
        if cfg.get("json_out"):
            self.png_out_edit.setText(cfg["json_out"])
        if cfg.get("json") and os.path.isfile(cfg["json"]):
            self.set_json(cfg["json"])
        if cfg.get("png") and os.path.isfile(cfg["png"]):
            self.set_png(cfg["png"])
        self._check_folder_match()

    def closeEvent(self, event):
        self._save_config()
        super().closeEvent(event)


if __name__ == "__main__":
    ctypes.windll.shell32.SetCurrentProcessExplicitAppUserModelID(APP_ID)
    app = QApplication(sys.argv)
    setTheme(Theme.DARK)
    window = WoodCreator()
    window.show()
    sys.exit(app.exec())