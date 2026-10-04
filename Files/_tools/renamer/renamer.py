import os
import sys
import json
import ctypes
import re
import threading
from PyQt6.QtCore import Qt, QThread, pyqtSignal
from PyQt6.QtGui import QIcon, QPixmap
from PyQt6.QtWidgets import (QApplication, QWidget, QVBoxLayout, QHBoxLayout,
                             QLabel, QPushButton, QLineEdit, QMessageBox, 
                             QFileDialog, QCheckBox, QTextEdit, QFrame, QGridLayout)

try:
    import google.generativeai as genai
    GEMINI_AVAILABLE = True
except ImportError:
    GEMINI_AVAILABLE = False

try:
    from qfluentwidgets import (FluentWindow, FluentIcon as FIF, setTheme, Theme, 
                                 SubtitleLabel, PrimaryPushButton, ComboBox, 
                                 BodyLabel, ScrollArea, LineEdit, TextEdit,
                                 InfoBar, InfoBarPosition)
except ImportError:
    print("Error: qfluentwidgets not found. Run: pip install qfluentwidgets")
    sys.exit(1)

CONFIG_PATH = os.path.join(os.path.dirname(os.path.abspath(__file__)), "config.json")

# --- CONFIG & CONSTANTS ---
WOODS = ["dark_oak", "oak", "spruce", "birch", "jungle", "acacia", "mangrove", "cherry", "bamboo"]
COLORS = ["white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"]
WOOLS = [f"{color}_wool" for color in COLORS]
CONCRETES = [f"{color}_concrete" for color in COLORS]
DISPLAY_MODES = ["gui", "thirdperson_righthand", "thirdperson_lefthand", "firstperson_righthand", "firstperson_lefthand", "ground", "fixed", "head"]
ICON_PATH = os.path.join(os.path.dirname(os.path.abspath(__file__)), "icon.png")

class GeminiWorker(QThread):
    """Runs Gemini API call off the main thread."""
    result_ready = pyqtSignal(str, str)   # hardness, resistance
    error_occurred = pyqtSignal(str)

    def __init__(self, api_key, block_name, image_path=None):
        super().__init__()
        self.api_key = api_key
        self.block_name = block_name
        self.image_path = image_path

    def run(self):
        try:
            genai.configure(api_key=self.api_key)
            model = genai.GenerativeModel("gemini-2.5-flash") # Updated to latest version
            prompt = (
                f"Minecraft modding expert. Block/Idea: '{self.block_name}'.\n"
                "Suggest Java Edition values:\n"
                "- hardness (float, e.g. 0.5 grass, 1.5 stone, 5.0 iron, 50.0 obsidian, -1.0 unbreakable)\n"
                "- resistance (float, e.g. 0.5 grass, 6.0 stone, 1200.0 obsidian)\n\n"
                "Balanced for survival. "
                "If an image is attached, carefully analyze its visual textures, colors, and apparent materials (e.g. glass, metal, organic wood/leaves, stone) "
                "to provide highly thematic, realistic stats matching its visual identity.\n\n"
                "Format ONLY:\n"
                "HARDNESS: <value>\n"
                "RESISTANCE: <value>\n"
                "REASON: <one sentence>"
            )
            payload = [prompt]
            if self.image_path and os.path.exists(self.image_path):
                from PIL import Image
                img = Image.open(self.image_path)
                payload.append(img)
                
            response = model.generate_content(payload)
            text = response.text.strip()
            hardness, resistance = "?", "?"
            reason = ""
            for line in text.splitlines():
                if line.startswith("HARDNESS:"):
                    hardness = line.split(":", 1)[1].strip()
                elif line.startswith("RESISTANCE:"):
                    resistance = line.split(":", 1)[1].strip()
                elif line.startswith("REASON:"):
                    reason = line.split(":", 1)[1].strip()
            self.result_ready.emit(hardness, resistance + (f"\n\n💡 {reason}" if reason else ""))
        except Exception as e:
            self.error_occurred.emit(str(e))


class ModelStudioProV60(FluentWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("MODEL STUDIO PRO [v60 MASTER]")
        self.resize(520, 680)
        self.setObjectName("main_window")
        if os.path.exists(ICON_PATH): self.setWindowIcon(QIcon(ICON_PATH))
        
        self.source_files = []
        self.textures_path = ""
        self.texture_checkboxes = {}
        self.tint_checkboxes = {}
        self._gemini_worker = None
        self.selected_ai_image_path = ""
        self._load_config()
        self._setup_ui()
        self._setup_ai_tab()
        self._setup_export_tab()

    def _load_config(self):
        """Load persisted settings from config.json."""
        self._settings = {
            "gemini_api_key": "",
            "emissive_value": "15",
            "last_mode": "gui",
            "last_output_dir": "",
            "last_export_category": "Wood"
        }
        if os.path.exists(CONFIG_PATH):
            try:
                with open(CONFIG_PATH, 'r') as f:
                    data = json.load(f)
                self._settings.update(data)
            except Exception:
                pass
        self._api_key = self._settings.get("gemini_api_key", "")

    def _save_config(self):
        """Persist settings to config.json."""
        try:
            self._settings["gemini_api_key"] = self._api_key
            if hasattr(self, 'emissive_edit'):
                self._settings["emissive_value"] = self.emissive_edit.text()
            if hasattr(self, 'mode_box'):
                self._settings["last_mode"] = self.mode_box.currentText()
            if hasattr(self, 'export_dir_edit'):
                self._settings["last_output_dir"] = self.export_dir_edit.text()
            if hasattr(self, 'export_category_box'):
                self._settings["last_export_category"] = self.export_category_box.currentText()
                
            with open(CONFIG_PATH, 'w') as f:
                json.dump(self._settings, f, indent=2)
        except Exception:
            pass

    def _setup_ui(self):
        # Outer container: vertical — scroll area on top, generate button pinned at bottom
        self.container = QWidget()
        self.container.setObjectName("studio_interface")
        self.outer_layout = QVBoxLayout(self.container)
        self.outer_layout.setContentsMargins(8, 8, 8, 8)
        self.outer_layout.setSpacing(6)

        # Scrollable content panel
        self.scroll = ScrollArea()
        self.scroll.setWidgetResizable(True)
        self.left_panel = QWidget()
        self.left_layout = QVBoxLayout(self.left_panel)
        self.left_layout.setContentsMargins(6, 6, 6, 6)
        self.left_layout.setSpacing(6)

        # --- 1. MASTER INPUT ---
        self.left_layout.addWidget(SubtitleLabel("1. MASTER INPUT"))
        self.file_info = BodyLabel("0 Models Selected")
        self.left_layout.addWidget(self.file_info)
        btn_load = QPushButton("Load Models (.json)")
        btn_load.clicked.connect(self.load_models)
        self.left_layout.addWidget(btn_load)
        self.tex_info = BodyLabel("No .textures Selected")
        self.left_layout.addWidget(self.tex_info)
        btn_tex = QPushButton("Load .textures")
        btn_tex.clicked.connect(self.load_textures_file)
        self.left_layout.addWidget(btn_tex)

        # --- 2. POSITION EDITOR ---
        self.left_layout.addSpacing(6)
        self.left_layout.addWidget(SubtitleLabel("2. POSITION EDITOR"))
        self.mode_box = ComboBox()
        self.mode_box.addItems(DISPLAY_MODES)
        self.mode_box.setCurrentText(self._settings.get("last_mode", "gui"))
        self.mode_box.currentTextChanged.connect(self.sync_display_inputs)
        self.left_layout.addWidget(self.mode_box)
        self.inputs = {}
        for attr in ["rotation", "translation", "scale"]:
            row = QHBoxLayout()
            row.setSpacing(4)
            cb = QCheckBox(attr.capitalize())
            cb.setStyleSheet("font-size: 11px;")
            cb.setChecked(True)
            row.addWidget(cb)
            self.inputs[attr] = {"cb": cb, "edits": []}
            for _ in range(3):
                e = QLineEdit("1" if attr == "scale" else "0")
                e.setFixedWidth(55)
                e.textChanged.connect(self.update_preview)
                row.addWidget(e)
            self.inputs[attr]["edits"] = [w for w in [row.itemAt(i).widget() for i in range(row.count())] if isinstance(w, QLineEdit)]
            self.left_layout.addLayout(row)
        btn_inj = PrimaryPushButton("Inject Position to All")
        btn_inj.clicked.connect(self.save_display_to_all)
        self.left_layout.addWidget(btn_inj)

        # --- 3. PRECISION GLOW ---
        self.left_layout.addSpacing(6)
        self.left_layout.addWidget(SubtitleLabel("3. PRECISION GLOW"))
        self.tint_area = QWidget()
        self.tint_layout = QVBoxLayout(self.tint_area)
        self.tint_layout.setContentsMargins(2, 2, 2, 2)
        self.tint_layout.setSpacing(2)
        self.left_layout.addWidget(self.tint_area)
        self.emissive_edit = QLineEdit(self._settings.get("emissive_value", "15"))
        self.left_layout.addWidget(self.emissive_edit)
        btn_light = PrimaryPushButton("Apply Glow to Selected IDs")
        btn_light.clicked.connect(self.apply_emissive_to_all)
        self.left_layout.addWidget(btn_light)

        # --- 4. MCreator TINT RUNTIME ---
        self.left_layout.addSpacing(6)
        self.left_layout.addWidget(SubtitleLabel("4. MCreator TINT RUNTIME"))
        self.mcreator_tint_area = QWidget()
        self.mcreator_tint_layout = QVBoxLayout(self.mcreator_tint_area)
        self.mcreator_tint_layout.setContentsMargins(2, 2, 2, 2)
        self.mcreator_tint_layout.setSpacing(2)
        self.left_layout.addWidget(self.mcreator_tint_area)
        btn_tint = PrimaryPushButton("Inject Tintindex to Selected IDs")
        btn_tint.clicked.connect(self.apply_tintindex_to_all)
        self.left_layout.addWidget(btn_tint)

        self.left_layout.addStretch(1)
        self.scroll.setWidget(self.left_panel)
        self.outer_layout.addWidget(self.scroll, 1)

        self.addSubInterface(self.container, FIF.HOME, "Studio")

    def _setup_ai_tab(self):
        """Builds the Hardness & Resistance Generator tab powered by Gemini."""
        self.ai_page = QWidget()
        self.ai_page.setObjectName("ai_stats_page")
        ai_outer = QVBoxLayout(self.ai_page)
        ai_outer.setContentsMargins(14, 14, 14, 14)
        ai_outer.setSpacing(10)

        # ── API Key row ────────────────────────────────────────────
        ai_outer.addWidget(SubtitleLabel("💎 Hardness & Resistance Generator"))
        ai_outer.addWidget(BodyLabel("Ask Gemini to generate Minecraft block stats based on your idea."))

        sep = QFrame()
        sep.setFrameShape(QFrame.Shape.HLine)
        sep.setStyleSheet("color: #444;")
        ai_outer.addWidget(sep)

        key_lbl = BodyLabel("Gemini API Key:")
        ai_outer.addWidget(key_lbl)
        key_row = QHBoxLayout()
        self.api_key_edit = QLineEdit(self._api_key)
        self.api_key_edit.setEchoMode(QLineEdit.EchoMode.Password)
        self.api_key_edit.setPlaceholderText("Paste your API key here...")
        self.api_key_edit.setStyleSheet("padding: 4px; border-radius: 4px;")
        key_row.addWidget(self.api_key_edit)
        btn_save_key = QPushButton("Save Key")
        btn_save_key.setFixedWidth(80)
        btn_save_key.clicked.connect(self._save_api_key)
        key_row.addWidget(btn_save_key)
        ai_outer.addLayout(key_row)

        if not GEMINI_AVAILABLE:
            warn = BodyLabel("⚠️  google-generativeai not installed.\nRun:  pip install google-generativeai")
            warn.setStyleSheet("color: #ff9800; font-size: 11px;")
            ai_outer.addWidget(warn)

        # ── Block name input ───────────────────────────────────────
        sep2 = QFrame()
        sep2.setFrameShape(QFrame.Shape.HLine)
        sep2.setStyleSheet("color: #444;")
        ai_outer.addWidget(sep2)

        ai_outer.addWidget(BodyLabel("Block Name / Idea:"))
        self.block_name_edit = QLineEdit()
        self.block_name_edit.setPlaceholderText("e.g. Crystal Glass, Lava Stone Bricks, Mossy Nether Quartz...")
        self.block_name_edit.setStyleSheet("padding: 6px; border-radius: 4px; font-size: 13px;")
        self.block_name_edit.returnPressed.connect(self._run_ai_generate)
        ai_outer.addWidget(self.block_name_edit)

        # ── Image attachment row ───────────────────────────────────
        ai_outer.addWidget(BodyLabel("Attach Image (Optional):"))
        img_row = QHBoxLayout()
        btn_choose_img = QPushButton("🖼️  Choose Image...")
        btn_choose_img.clicked.connect(self._choose_ai_image)
        img_row.addWidget(btn_choose_img)
        
        btn_clear_img = QPushButton("Remove")
        btn_clear_img.clicked.connect(self._clear_ai_image)
        img_row.addWidget(btn_clear_img)
        img_row.addStretch(1)
        ai_outer.addLayout(img_row)
        
        self.image_preview_lbl = BodyLabel("No image selected (text-only mode)")
        self.image_preview_lbl.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.image_preview_lbl.setFixedHeight(120)
        self.image_preview_lbl.setStyleSheet("border: 1px dashed #444; border-radius: 4px; padding: 4px; background: #0d1117;")
        ai_outer.addWidget(self.image_preview_lbl)

        self.btn_generate_ai = PrimaryPushButton("✨  Generate with AI")
        self.btn_generate_ai.clicked.connect(self._run_ai_generate)
        ai_outer.addWidget(self.btn_generate_ai)

        # ── Result display ─────────────────────────────────────────
        result_frame = QWidget()
        result_frame.setStyleSheet(
            "background: #1a1a2e; border-radius: 8px; padding: 8px;"
        )
        result_layout = QVBoxLayout(result_frame)
        result_layout.setSpacing(8)

        self.ai_status_lbl = BodyLabel("Awaiting input...")
        self.ai_status_lbl.setStyleSheet("color: #888; font-style: italic;")
        result_layout.addWidget(self.ai_status_lbl)

        hard_row = QHBoxLayout()
        hard_row.addWidget(BodyLabel("Hardness:"))
        self.hardness_result = QLineEdit()
        self.hardness_result.setReadOnly(True)
        self.hardness_result.setPlaceholderText("—")
        self.hardness_result.setStyleSheet(
            "font-size: 20px; font-weight: bold; color: #00e5ff;"
            "background: #0d1117; border-radius: 4px; padding: 4px;"
        )
        hard_row.addWidget(self.hardness_result)
        result_layout.addLayout(hard_row)

        res_row = QHBoxLayout()
        res_row.addWidget(BodyLabel("Resistance:"))
        self.resistance_result = QLineEdit()
        self.resistance_result.setReadOnly(True)
        self.resistance_result.setPlaceholderText("—")
        self.resistance_result.setStyleSheet(
            "font-size: 20px; font-weight: bold; color: #69ff47;"
            "background: #0d1117; border-radius: 4px; padding: 4px;"
        )
        res_row.addWidget(self.resistance_result)
        result_layout.addLayout(res_row)

        self.ai_reason_lbl = BodyLabel("")
        self.ai_reason_lbl.setStyleSheet("color: #aaa; font-size: 11px;")
        self.ai_reason_lbl.setWordWrap(True)
        result_layout.addWidget(self.ai_reason_lbl)

        ai_outer.addWidget(result_frame)
        ai_outer.addStretch(1)

        self.addSubInterface(self.ai_page, FIF.ROBOT, "Hardness & Resistance")

    def _setup_export_tab(self):
        """Builds the Variant Exporter tab."""
        self.export_page = QWidget()
        self.export_page.setObjectName("variant_exporter_page")
        
        # Outer layout: ScrollArea to ensure all options fit nicely
        export_outer = QVBoxLayout(self.export_page)
        export_outer.setContentsMargins(0, 0, 0, 0)
        export_outer.setSpacing(0)
        
        scroll = ScrollArea()
        scroll.setWidgetResizable(True)
        scroll_content = QWidget()
        layout = QVBoxLayout(scroll_content)
        layout.setContentsMargins(14, 14, 14, 14)
        layout.setSpacing(12)
        
        # ── Title and Description ─────────────────────────────────
        layout.addWidget(SubtitleLabel("🎨 Variant Exporter"))
        layout.addWidget(BodyLabel("Batch generate Minecraft model variations for wood, wool, or concrete types."))
        
        sep1 = QFrame()
        sep1.setFrameShape(QFrame.Shape.HLine)
        sep1.setStyleSheet("color: #444;")
        layout.addWidget(sep1)
        
        # ── Master Selection Info ─────────────────────────────────
        info_row = QHBoxLayout()
        layout.addWidget(BodyLabel("Models Loaded for Export:"))
        self.export_file_info = BodyLabel(f"{len(self.source_files)} Models Selected")
        self.export_file_info.setStyleSheet("font-weight: bold; color: #00e5ff;")
        info_row.addWidget(self.export_file_info)
        info_row.addStretch(1)
        layout.addLayout(info_row)
        
        sep2 = QFrame()
        sep2.setFrameShape(QFrame.Shape.HLine)
        sep2.setStyleSheet("color: #444;")
        layout.addWidget(sep2)
        
        # ── Category Selection ────────────────────────────────────
        cat_row = QHBoxLayout()
        cat_row.addWidget(BodyLabel("Select Category:"))
        self.export_category_box = ComboBox()
        self.export_category_box.addItems(["Wood", "Wool", "Concrete"])
        self.export_category_box.setCurrentText(self._settings.get("last_export_category", "Wood"))
        self.export_category_box.currentTextChanged.connect(self.refresh_checkbox_grid)
        cat_row.addWidget(self.export_category_box)
        cat_row.addStretch(1)
        layout.addLayout(cat_row)
        
        # ── Variations Selection Grid ─────────────────────────────
        layout.addWidget(SubtitleLabel("Select Target Variations"))
        
        # Toggle buttons row
        toggle_row = QHBoxLayout()
        btn_all = QPushButton("Select All")
        btn_all.clicked.connect(self.select_all_woods)
        btn_none = QPushButton("Deselect All")
        btn_none.clicked.connect(self.deselect_all_woods)
        toggle_row.addWidget(btn_all)
        toggle_row.addWidget(btn_none)
        toggle_row.addStretch(1)
        layout.addLayout(toggle_row)
        
        # Grid layout for checkboxes
        self.grid_widget = QWidget()
        self.grid_layout = QGridLayout(self.grid_widget)
        self.grid_layout.setContentsMargins(0, 4, 0, 4)
        self.grid_layout.setSpacing(6)
        
        layout.addWidget(self.grid_widget)
        self.refresh_checkbox_grid() # Initial build of grid checkboxes
        
        sep3 = QFrame()
        sep3.setFrameShape(QFrame.Shape.HLine)
        sep3.setStyleSheet("color: #444;")
        layout.addWidget(sep3)
        
        # ── Target Export Folder ──────────────────────────────────
        layout.addWidget(SubtitleLabel("Target Export Directory"))
        path_row = QHBoxLayout()
        self.export_dir_edit = LineEdit()
        self.export_dir_edit.setText(self._settings.get("last_output_dir", ""))
        self.export_dir_edit.setPlaceholderText("Select output directory...")
        path_row.addWidget(self.export_dir_edit, 1)
        
        btn_browse = QPushButton("Browse...")
        btn_browse.clicked.connect(self.browse_export_dir)
        path_row.addWidget(btn_browse)
        layout.addLayout(path_row)
        
        layout.addSpacing(10)
        
        # ── Export Action ─────────────────────────────────────────
        self.btn_export = PrimaryPushButton("⚡  GENERATE SELECTED VARIANTS")
        self.btn_export.clicked.connect(self.process_variants)
        layout.addWidget(self.btn_export)
        
        layout.addStretch(1)
        
        scroll.setWidget(scroll_content)
        export_outer.addWidget(scroll)
        
        self.addSubInterface(self.export_page, FIF.PALETTE, "Exporter")

    def refresh_checkbox_grid(self):
        # Clear existing grid widgets
        for i in reversed(range(self.grid_layout.count())):
            w = self.grid_layout.itemAt(i).widget()
            if w:
                w.setParent(None)
                
        category = self.export_category_box.currentText().lower()
        self.wood_checkboxes = {}
        
        if category == "wood":
            items = WOODS
        elif category == "wool":
            items = WOOLS
        else:
            items = CONCRETES
            
        cols = 2
        for idx, item in enumerate(items):
            r = idx // cols
            c = idx % cols
            label = " ".join([w.capitalize() for w in item.split("_")])
            cb = QCheckBox(label)
            cb.setChecked(True)
            cb.setStyleSheet("font-size: 12px; padding: 2px;")
            self.grid_layout.addWidget(cb, r, c)
            self.wood_checkboxes[item] = cb

    def select_all_woods(self):
        for cb in self.wood_checkboxes.values():
            cb.setChecked(True)

    def deselect_all_woods(self):
        for cb in self.wood_checkboxes.values():
            cb.setChecked(False)

    def browse_export_dir(self):
        path = QFileDialog.getExistingDirectory(self, "Select Output Directory", self.export_dir_edit.text())
        if path:
            self.export_dir_edit.setText(path)
            self._save_config()

    # ── AI tab helpers ─────────────────────────────────────────────────────────

    def _save_api_key(self):
        self._api_key = self.api_key_edit.text().strip()
        self._save_config()
        InfoBar.success(
            title="Saved",
            content="API key saved to config.json.",
            orient=Qt.Orientation.Horizontal,
            isClosable=True,
            position=InfoBarPosition.TOP,
            duration=2500,
            parent=self
        )

    def _run_ai_generate(self):
        if not GEMINI_AVAILABLE:
            QMessageBox.warning(self, "Missing Package",
                                "Please run:\n  pip install google-generativeai\nthen restart the app.")
            return
        key = self.api_key_edit.text().strip() or self._api_key
        if not key:
            QMessageBox.warning(self, "No API Key", "Please enter your Gemini API key first.")
            return
        block = self.block_name_edit.text().strip()
        if not block:
            QMessageBox.warning(self, "No Block Name", "Please enter a block name or idea.")
            return

        self.btn_generate_ai.setEnabled(False)
        self.ai_status_lbl.setText("⏳  Asking Gemini...")
        self.hardness_result.clear()
        self.resistance_result.clear()
        self.ai_reason_lbl.setText("")

        self._gemini_worker = GeminiWorker(key, block, self.selected_ai_image_path)
        self._gemini_worker.result_ready.connect(self._on_ai_result)
        self._gemini_worker.error_occurred.connect(self._on_ai_error)
        self._gemini_worker.start()

    def _on_ai_result(self, hardness: str, resistance_and_reason: str):
        parts = resistance_and_reason.split("\n\n💡 ", 1)
        resistance = parts[0]
        reason = parts[1] if len(parts) > 1 else ""
        self.hardness_result.setText(hardness)
        self.resistance_result.setText(resistance)
        self.ai_reason_lbl.setText(f"💡 {reason}" if reason else "")
        self.ai_status_lbl.setText(f"✅  Results for: {self.block_name_edit.text().strip()}")
        self.ai_status_lbl.setStyleSheet("color: #69ff47; font-style: normal;")
        self.btn_generate_ai.setEnabled(True)

    def _on_ai_error(self, error: str):
        self.ai_status_lbl.setText("❌  Error — see details below")
        self.ai_status_lbl.setStyleSheet("color: #ff4444; font-style: normal;")
        self.ai_reason_lbl.setText(error)
        self.btn_generate_ai.setEnabled(True)

    def _choose_ai_image(self):
        try:
            from PIL import Image
        except ImportError:
            QMessageBox.warning(
                self, 
                "Missing Package", 
                "To analyze images, Pillow is required.\nPlease run:\n  pip install Pillow\nthen try again."
            )
            return
            
        file_path, _ = QFileDialog.getOpenFileName(
            self, 
            "Select Image", 
            "", 
            "Images (*.png *.jpg *.jpeg *.webp *.bmp)"
        )
        if file_path:
            self.selected_ai_image_path = file_path
            pixmap = QPixmap(file_path)
            if not pixmap.isNull():
                scaled_pixmap = pixmap.scaled(
                    self.image_preview_lbl.width(), 
                    self.image_preview_lbl.height() - 10, 
                    Qt.AspectRatioMode.KeepAspectRatio, 
                    Qt.TransformationMode.SmoothTransformation
                )
                self.image_preview_lbl.setPixmap(scaled_pixmap)
            else:
                self.image_preview_lbl.setText("Failed to load image preview.")
                
    def _clear_ai_image(self):
        self.selected_ai_image_path = ""
        self.image_preview_lbl.clear()
        self.image_preview_lbl.setText("No image selected (text-only mode)")
        self.image_preview_lbl.setStyleSheet("border: 1px dashed #444; border-radius: 4px; padding: 4px; background: #0d1117;")

    def load_models(self):
        paths, _ = QFileDialog.getOpenFileNames(self, "Load Models", "", "JSON (*.json)")
        if paths:
            self.source_files = paths
            self.file_info.setText(f"{len(paths)} Models Selected")
            if hasattr(self, 'export_file_info'):
                self.export_file_info.setText(f"{len(paths)} Models Selected")
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
        pass  # Viewport removed

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
        self._save_config() # Save settings when user actually performs an action
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
        self._save_config() # Save settings when user actually performs an action
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

    def deep_swap_variant(self, content, target, category):
        if category == "wood":
            for wood in WOODS:
                content = content.replace(wood, target)
            if target == "bamboo":
                content = content.replace("bamboo_log", "bamboo_block").replace("stripped_bamboo_log", "stripped_bamboo_block")
        elif category == "wool":
            for wool in WOOLS:
                content = content.replace(wool, target)
            target_color = target.replace("_wool", "")
            for color in COLORS:
                content = content.replace(color, target_color)
        elif category == "concrete":
            for concrete in CONCRETES:
                content = content.replace(concrete, target)
            target_color = target.replace("_concrete", "")
            for color in COLORS:
                content = content.replace(color, target_color)
        return content

    def process_variants(self):
        if not self.source_files:
            InfoBar.warning(
                title="No Models Loaded",
                content="Please load source models first in the Studio tab.",
                orient=Qt.Orientation.Horizontal,
                isClosable=True,
                position=InfoBarPosition.TOP,
                duration=3000,
                parent=self
            )
            return
            
        selected_items = [item for item, cb in self.wood_checkboxes.items() if cb.isChecked()]
        if not selected_items:
            InfoBar.warning(
                title="No Variations Selected",
                content="Please select at least one variation to export.",
                orient=Qt.Orientation.Horizontal,
                isClosable=True,
                position=InfoBarPosition.TOP,
                duration=3000,
                parent=self
            )
            return
            
        path = self.export_dir_edit.text().strip()
        if not path or not os.path.isdir(path):
            path = QFileDialog.getExistingDirectory(self, "Select Output Directory", path)
            if not path:
                return
            self.export_dir_edit.setText(path)
            
        self._save_config()
        
        category = self.export_category_box.currentText().lower()
        
        if category == "wood":
            search_list = WOODS
        elif category == "wool":
            search_list = WOOLS + COLORS
        else:
            search_list = CONCRETES + COLORS
            
        try:
            for src in self.source_files:
                orig_fn = os.path.splitext(os.path.basename(src))[0]
                detected = next((item for item in search_list if item in orig_fn.lower()), "")
                
                with open(src, 'r', encoding='utf-8') as f: 
                    content = f.read()
                    
                for target in selected_items:
                    if detected:
                        new_fn = orig_fn.replace(detected, target)
                    else:
                        new_fn = f"{target}_{orig_fn}"
                        
                    new_content = self.deep_swap_variant(content, target, category)
                    if detected:
                        new_content = new_content.replace(orig_fn, new_fn)
                    
                    with open(os.path.join(path, f"{new_fn}.json"), 'w', encoding='utf-8') as f: 
                        f.write(new_content)
                        
            InfoBar.success(
                title="Variants Generated",
                content=f"Created {len(self.source_files) * len(selected_items)} variants in output directory.",
                orient=Qt.Orientation.Horizontal,
                isClosable=True,
                position=InfoBarPosition.TOP,
                duration=3500,
                parent=self
            )
        except Exception as e:
            QMessageBox.critical(self, "Error", f"Failed to generate variants:\n{str(e)}")

if __name__ == "__main__":
    ctypes.windll.shell32.SetCurrentProcessExplicitAppUserModelID('voxelized.studio.pro.v60')
    app = QApplication(sys.argv); setTheme(Theme.DARK)
    window = ModelStudioProV60(); window.show(); sys.exit(app.exec())
