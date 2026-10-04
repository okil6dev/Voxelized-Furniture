import os
import sys
import json
import math
import ctypes
import re
from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIcon, QPainter, QPen, QColor
from PyQt6.QtWidgets import (QApplication, QWidget, QVBoxLayout, QHBoxLayout, 
                             QLabel, QPushButton, QLineEdit, QMessageBox, 
                             QFileDialog, QCheckBox)

try:
    from qfluentwidgets import (FluentWindow, FluentIcon as FIF, setTheme, Theme, 
                                 SubtitleLabel, PrimaryPushButton, ComboBox, 
                                 BodyLabel, ScrollArea)
except ImportError:
    print("Error: qfluentwidgets not found. Run: pip install qfluentwidgets")
    sys.exit(1)

# --- CONFIG & CONSTANTS ---
WOODS = ["dark_oak", "oak", "spruce", "birch", "jungle", "acacia", "mangrove", "cherry", "bamboo", "crimson", "warped"]
DISPLAY_MODES = ["gui", "thirdperson_righthand", "thirdperson_lefthand", "firstperson_righthand", "firstperson_lefthand", "ground", "fixed", "head"]
ICON_PATH = os.path.join(os.path.dirname(os.path.abspath(__file__)), "icon.png")

class MasterViewport(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setMinimumSize(300, 300) # Made viewport slightly more compact
        self.display_settings = {"rotation": [0, 0, 0], "translation": [0, 0, 0], "scale": [1, 1, 1]}

    def update_view(self, rot, trans, scale):
        self.display_settings = {"rotation": rot, "translation": trans, "scale": scale}
        self.update()

    def paintEvent(self, event):
        painter = QPainter(self)
        painter.setRenderHint(QPainter.RenderHint.Antialiasing)
        painter.fillRect(self.rect(), QColor("#0f0f0f"))
        w, h = self.width(), self.height()
        cx, cy = w / 2, h / 2
        rot = self.display_settings.get("rotation", [0, 0, 0])
        trans = self.display_settings.get("translation", [0, 0, 0])
        scale = self.display_settings.get("scale", [1, 1, 1])
        verts = [[x, y, z] for x in [-0.5, 0.5] for y in [-0.5, 0.5] for z in [-0.5, 0.5]]
        
        def project(v):
            x, y, z = v[0]*scale[0], v[1]*scale[1], v[2]*scale[2]
            rx, ry, rz = [math.radians(a) for a in rot]
            y, z = y*math.cos(rx)-z*math.sin(rx), y*math.sin(rx)+z*math.cos(rx)
            x, z = x*math.cos(ry)+z*math.sin(ry), -x*math.sin(ry)+z*math.cos(ry)
            x, y = x*math.cos(rz)-y*math.sin(rz), x*math.sin(rz)+y*math.cos(rz)
            zoom = 140 # Adjusted zoom for more compact layout space
            tx, ty = trans[0]/16, trans[1]/16
            return cx + (x + tx)*zoom, cy - (y + ty)*zoom

        try:
            p = [project(v) for v in verts]
            edges = [(0,1), (0,2), (1,3), (2,3), (4,5), (4,6), (5,7), (6,7), (0,4), (1,5), (2,6), (3,7)]
            painter.setPen(QPen(QColor("#00ffcc"), 1.5))
            for e in edges:
                painter.drawLine(int(p[e[0]][0]), int(p[e[0]][1]), int(p[e[1]][0]), int(p[e[1]][1]))
        except: pass

class ModelStudioProV60(FluentWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("MODEL STUDIO PRO [v60 MASTER]")
        self.resize(1000, 700) # Shrunk main window dimensions down from 1200x850
        self.setObjectName("main_window")
        if os.path.exists(ICON_PATH): self.setWindowIcon(QIcon(ICON_PATH))
        
        self.source_files = []
        self.textures_path = ""
        self.texture_checkboxes = {}
        self.tint_checkboxes = {}
        self._setup_ui()

    def _setup_ui(self):
        self.container = QWidget(); self.container.setObjectName("studio_interface")
        self.layout = QHBoxLayout(self.container)
        self.layout.setContentsMargins(6, 6, 6, 6) # Tighter layout borders
        self.layout.setSpacing(8)
        
        self.scroll = ScrollArea()
        self.left_panel = QWidget()
        self.left_layout = QVBoxLayout(self.left_panel)
        self.left_layout.setContentsMargins(4, 4, 4, 4) # Tighter sidebar margins
        self.left_layout.setSpacing(6) # Reduced spacing between GUI groups
        
        # --- 1. MASTER INPUT ---
        self.left_layout.addWidget(SubtitleLabel("1. MASTER INPUT"))
        self.file_info = BodyLabel("0 Models Selected"); self.left_layout.addWidget(self.file_info)
        btn_load = QPushButton("Load Models (.json)"); btn_load.clicked.connect(self.load_models); self.left_layout.addWidget(btn_load)
        
        self.tex_info = BodyLabel("No .textures Selected"); self.left_layout.addWidget(self.tex_info)
        btn_tex = QPushButton("Load .textures"); btn_tex.clicked.connect(self.load_textures_file); self.left_layout.addWidget(btn_tex)

        # --- 2. POSITION EDITOR ---
        self.left_layout.addSpacing(6)
        self.left_layout.addWidget(SubtitleLabel("2. POSITION EDITOR"))
        self.mode_box = ComboBox(); self.mode_box.addItems(DISPLAY_MODES); self.mode_box.currentTextChanged.connect(self.sync_display_inputs); self.left_layout.addWidget(self.mode_box)
        self.inputs = {}
        for attr in ["rotation", "translation", "scale"]:
            row = QHBoxLayout()
            row.setSpacing(4)
            cb = QCheckBox(attr.capitalize())
            cb.setStyleSheet("font-size: 11px;") # Made text slightly smaller to match compact GUI
            cb.setChecked(True)
            row.addWidget(cb)
            self.inputs[attr] = {"cb": cb, "edits": []}
            for _ in range(3):
                e = QLineEdit("1" if attr == "scale" else "0")
                e.setFixedWidth(42) # Made inputs narrower (from 55 down to 42)
                e.textChanged.connect(self.update_preview)
                row.addWidget(e)
                self.inputs[attr]["edits"].append(e)
            self.left_layout.addLayout(row)
        btn_inj = PrimaryPushButton("Inject Position to All"); btn_inj.clicked.connect(self.save_display_to_all); self.left_layout.addWidget(btn_inj)

        # --- 3. PRECISION GLOW ---
        self.left_layout.addSpacing(6)
        self.left_layout.addWidget(SubtitleLabel("3. PRECISION GLOW"))
        self.tint_area = QWidget(); self.tint_layout = QVBoxLayout(self.tint_area)
        self.tint_layout.setContentsMargins(2, 2, 2, 2); self.tint_layout.setSpacing(2)
        self.left_layout.addWidget(self.tint_area)
        self.emissive_edit = QLineEdit("15"); self.left_layout.addWidget(self.emissive_edit)
        btn_light = PrimaryPushButton("Apply Glow to Selected IDs"); btn_light.clicked.connect(self.apply_emissive_to_all); self.left_layout.addWidget(btn_light)

        # --- 4. MCreator TINT RUNTIME ---
        self.left_layout.addSpacing(6)
        self.left_layout.addWidget(SubtitleLabel("4. MCreator TINT RUNTIME"))
        self.mcreator_tint_area = QWidget(); self.mcreator_tint_layout = QVBoxLayout(self.mcreator_tint_area)
        self.mcreator_tint_layout.setContentsMargins(2, 2, 2, 2); self.mcreator_tint_layout.setSpacing(2)
        self.left_layout.addWidget(self.mcreator_tint_area)
        btn_tint = PrimaryPushButton("Inject Tintindex to Selected IDs"); btn_tint.clicked.connect(self.apply_tintindex_to_all); self.left_layout.addWidget(btn_tint)

        # Final side panel assembly
        self.scroll.setWidget(self.left_panel); self.scroll.setWidgetResizable(True)
        self.layout.addWidget(self.scroll, 25) # Layout distribution optimized for tighter look
        
        self.right_panel = QWidget(); self.right_layout = QVBoxLayout(self.right_panel)
        self.right_layout.setContentsMargins(2, 2, 2, 2); self.right_layout.setSpacing(6)
        
        self.viewport = MasterViewport(); self.right_layout.addWidget(self.viewport, 1)
        btn_var = PrimaryPushButton("GENERATE ALL WOOD VARIANTS"); btn_var.clicked.connect(self.process_variants); self.right_layout.addWidget(btn_var)
        self.layout.addWidget(self.right_panel, 75)
        
        self.addSubInterface(self.container, FIF.HOME, "Studio")

    def load_models(self):
        paths, _ = QFileDialog.getOpenFileNames(self, "Load Models", "", "JSON (*.json)")
        if paths:
            self.source_files = paths
            self.file_info.setText(f"{len(paths)} Models Selected")
            self.sync_display_inputs()
            self.refresh_texture_targets()

    def load_textures_file(self):
        path, _ = QFileDialog.getOpenFileName(self, "Load Textures", "", "Textures (*.textures)")
        if path:
            self.textures_path = path
            self.tex_info.setText(os.path.basename(path))

    def sync_display_inputs(self):
        if not self.source_files: return
        try:
            with open(self.source_files[0], 'r') as f: data = json.load(f)
            mode = self.mode_box.currentText()
            disp = data.get("display", {}).get(mode, {})
            for attr in ["rotation", "translation", "scale"]:
                vals = disp.get(attr, [1.0, 1.0, 1.0] if attr == "scale" else [0.0, 0.0, 0.0])
                for i in range(3): self.inputs[attr]["edits"][i].setText(str(vals[i]))
            self.update_preview()
        except: pass

    def update_preview(self):
        try:
            rot = [float(e.text()) for e in self.inputs["rotation"]["edits"]]
            trans = [float(e.text()) for e in self.inputs["translation"]["edits"]]
            scale = [float(e.text()) for e in self.inputs["scale"]["edits"]]
            self.viewport.update_view(rot, trans, scale)
        except: pass

    def refresh_texture_targets(self):
        """Refreshes the dynamic targets layout for both Emissive and Tint UI components."""
        for i in reversed(range(self.tint_layout.count())):
            if self.tint_layout.itemAt(i).widget(): self.tint_layout.itemAt(i).widget().setParent(None)
        for i in reversed(range(self.mcreator_tint_layout.count())):
            if self.mcreator_tint_layout.itemAt(i).widget(): self.mcreator_tint_layout.itemAt(i).widget().setParent(None)
            
        self.texture_checkboxes = {}
        self.tint_checkboxes = {}
        if not self.source_files: return
        try:
            with open(self.source_files[0], 'r') as f: data = json.load(f)
            textures = data.get("textures", {})
            for tid, tpath in textures.items():
                display_name = os.path.basename(tpath)
                
                cb_glow = QCheckBox(f"Glow: #{tid} ({display_name})")
                cb_glow.setStyleSheet("font-size: 11px; padding: 1px;")
                self.tint_layout.addWidget(cb_glow)
                self.texture_checkboxes[tid] = cb_glow

                cb_tint = QCheckBox(f"Tint: #{tid} ({display_name})")
                cb_tint.setStyleSheet("font-size: 11px; padding: 1px;")
                self.mcreator_tint_layout.addWidget(cb_tint)
                self.tint_checkboxes[tid] = cb_tint
        except: pass

    def save_display_to_all(self):
        if not self.source_files: return
        mode = self.mode_box.currentText()
        for path in self.source_files:
            with open(path, 'r') as f: data = json.load(f)
            if "display" not in data: data["display"] = {}
            if mode not in data["display"]: data["display"][mode] = {}
            for attr in ["rotation", "translation", "scale"]:
                if self.inputs[attr]["cb"].isChecked():
                    data["display"][mode][attr] = [float(e.text()) for e in self.inputs[attr]["edits"]]
            with open(path, 'w') as f: json.dump(data, f, indent=4)
        QMessageBox.information(self, "Success", "Display data injected.")

    def apply_emissive_to_all(self):
        selected_tids = [tid for tid, cb in self.texture_checkboxes.items() if cb.isChecked()]
        if not selected_tids: return
        val = int(self.emissive_edit.text() or 15)
        for path in self.source_files:
            with open(path, 'r') as f: data = json.load(f)
            for el in data.get("elements", []):
                for face in el.get("faces", {}).values():
                    tex_key = face.get("texture", "").replace("#", "")
                    if tex_key in selected_tids: face["light"] = val
                    elif "light" in face: del face["light"]
            with open(path, 'w') as f: json.dump(data, f, indent=4)
        QMessageBox.information(self, "Success", "Glow updated.")

    def apply_tintindex_to_all(self):
        """Injects tintindex tags targeting designated texture keys so MCreator triggers colors correctly."""
        selected_tids = [tid for tid, cb in self.tint_checkboxes.items() if cb.isChecked()]
        if not selected_tids: return
        for path in self.source_files:
            with open(path, 'r') as f: data = json.load(f)
            for el in data.get("elements", []):
                for face in el.get("faces", {}).values():
                    tex_key = face.get("texture", "").replace("#", "")
                    if tex_key in selected_tids: 
                        face["tintindex"] = 0
                    elif "tintindex" in face: 
                        del face["tintindex"]
            with open(path, 'w') as f: json.dump(data, f, indent=4)
        QMessageBox.information(self, "Success", "Tintindex structural markers injected.")

    def deep_swap(self, content, target_wood):
        for wood in WOODS:
            content = content.replace(wood, target_wood)
        if target_wood == "bamboo":
            content = content.replace("bamboo_log", "bamboo_block").replace("stripped_bamboo_log", "stripped_bamboo_block")
        return content

    def process_variants(self):
        path = QFileDialog.getExistingDirectory(self, "Select Output")
        if not path: return
        for src in self.source_files:
            orig_fn = os.path.splitext(os.path.basename(src))[0]
            detected = next((w for w in WOODS if w in orig_fn.lower()), "")
            with open(src, 'r', encoding='utf-8') as f: content = f.read()
            for target in WOODS:
                new_fn = orig_fn.replace(detected, target) if detected else f"{target}_{orig_fn}"
                new_content = self.deep_swap(content.replace(orig_fn, new_fn), target)
                with open(os.path.join(path, f"{new_fn}.json"), 'w', encoding='utf-8') as f: f.write(new_content)
        QMessageBox.information(self, "Done", "Variants created.")

if __name__ == "__main__":
    ctypes.windll.shell32.SetCurrentProcessExplicitAppUserModelID('voxelized.studio.pro.v60')
    app = QApplication(sys.argv); setTheme(Theme.DARK)
    window = ModelStudioProV60(); window.show(); sys.exit(app.exec())