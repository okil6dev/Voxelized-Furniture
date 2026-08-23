import os
import sys
import json
import math
import ctypes
import re
import glob
import random
import pygetwindow as gw
from PyQt6.QtCore import Qt, QTimer, QSize
from PyQt6.QtGui import QIcon, QPainter, QPen, QColor, QPixmap, QImage
from PyQt6.QtWidgets import (QApplication, QWidget, QVBoxLayout, QHBoxLayout, 
                             QLabel, QPushButton, QLineEdit, QMessageBox, 
                             QFileDialog, QCheckBox, QListWidget, QTextEdit,
                             QScrollArea, QFrame)

try:
    from qfluentwidgets import (FluentWindow, FluentIcon as FIF, setTheme, Theme, 
                                 SubtitleLabel, PrimaryPushButton, ComboBox, 
                                 BodyLabel, ScrollArea, SearchLineEdit, 
                                 PushButton, TransparentToolButton, MSFluentWindow)
    from PIL import Image, ImageQt
except ImportError:
    print("Error: Missing libraries. Run: pip install qfluentwidgets Pillow pygetwindow PyQt6")
    sys.exit(1)

# --- MASTER CONFIG ---
WOODS = ["dark_oak", "oak", "spruce", "birch", "jungle", "acacia", "mangrove", "cherry", "bamboo", "crimson", "warped"]
DISPLAY_MODES = ["gui", "thirdperson_righthand", "thirdperson_lefthand", "firstperson_righthand", "firstperson_lefthand", "ground", "fixed", "head"]
WORKSPACE_PATH = r"C:\Users\minec\MCreatorWorkspaces\voxelized_furniture"
ELEMENTS_PATH = os.path.join(WORKSPACE_PATH, "elements")
MODELS_PATH = os.path.join(WORKSPACE_PATH, "models")
ICON_PATH = os.path.join(os.path.dirname(os.path.abspath(__file__)), "icon.png")

VANILLA_BLOCKS = ["acacia_door_bottom", "acacia_door_top", "acacia_leaves", "acacia_log", "acacia_log_top", "acacia_planks", "acacia_sapling", "acacia_trapdoor", "activator_rail", "activator_rail_on", "allium", "amethyst_block", "amethyst_cluster", "ancient_debris_side", "ancient_debris_top", "andesite", "anvil", "anvil_top", "azalea_leaves", "azalea_side", "azalea_top", "bamboo_block", "bamboo_planks", "barrel_bottom", "barrel_side", "barrel_top", "barrel_top_open", "basalt_side", "basalt_top", "beacon", "bedrock", "beehive_front", "beehive_side", "beetroots_stage0", "beetroots_stage1", "beetroots_stage2", "beetroots_stage3", "bell_side", "big_dripleaf_side", "big_dripleaf_top", "birch_door_bottom", "birch_door_top", "birch_leaves", "birch_log", "birch_log_top", "birch_planks", "blackstone", "black_concrete", "black_wool", "blast_furnace_front", "blast_furnace_side", "blast_furnace_top", "blue_ice", "bookshelf", "brewing_stand_base", "bricks", "cactus_side", "cactus_top", "cake_bottom", "cake_inner", "cake_side", "cake_top", "calcite", "campfire_log", "cauldron_side", "cauldron_top", "chain", "cherry_log", "chiseled_bookshelf_empty", "chiseled_deepslate", "chiseled_quartz_block", "chiseled_stone_bricks", "clay", "coal_block", "coal_ore", "coarse_dirt", "cobblestone", "cobbled_deepslate", "copper_block", "copper_ore", "cornflower", "cracked_stone_bricks", "crafting_table_front", "crafting_table_side", "crafting_table_top", "crying_obsidian", "dark_oak_log", "dark_prismarine", "daylight_detector_top", "dead_bush", "deepslate", "deepslate_coal_ore", "deepslate_diamond_ore", "deepslate_emerald_ore", "deepslate_gold_ore", "deepslate_iron_ore", "deepslate_lapis_ore", "deepslate_redstone_ore", "diamond_block", "diamond_ore", "dirt", "dirt_path_side", "dirt_path_top", "dispenser_front", "dispenser_front_vertical", "dragon_egg", "dried_kelp_bottom", "dried_kelp_side", "dried_kelp_top", "dripstone_block", "emerald_block", "emerald_ore", "enchanting_table_bottom", "enchanting_table_side", "enchanting_table_top", "end_portal_frame_side", "end_portal_frame_top", "end_stone", "end_stone_bricks", "farmland", "farmland_moist", "fire_0", "fire_1", "fletching_table_front", "fletching_table_side", "fletching_top", "flowering_azalea_side", "flowering_azalea_top", "frogspawn", "furnace_front", "furnace_side", "furnace_top", "gilded_blackstone", "glass", "glass_pane_top", "glowstone", "glow_lichen", "gold_block", "gold_ore", "granite", "grass", "grass_block_side", "grass_block_top", "gravel", "hay_block_side", "hay_block_top", "honey_block_side", "honey_block_top", "ice", "iron_block", "iron_door_bottom", "iron_door_top", "iron_ore", "jukebox_side", "jukebox_top", "jungle_log", "ladder", "lantern", "lapis_block", "lapis_ore", "lava_flow", "lava_still", "lectern_front", "lectern_side", "lectern_top", "lily_pad", "loom_front", "magma", "mangrove_log", "mangrove_planks", "melon_side", "melon_top", "mossy_cobblestone", "mossy_stone_bricks", "moss_block", "mud", "mud_bricks", "mushroom_stem", "mycelium_side", "mycelium_top", "netherite_block", "netherrack", "nether_bricks", "nether_gold_ore", "nether_quartz_ore", "nether_sprouts", "nether_wart_stage0", "nether_wart_stage1", "nether_wart_stage2", "note_block", "oak_leaves", "oak_log", "oak_log_top", "oak_planks", "oak_sapling", "observer_back", "observer_front", "observer_side", "observer_top", "obsidian", "packed_ice", "packed_mud", "pink_petals", "piston_bottom", "piston_inner", "piston_side", "piston_top", "piston_top_sticky", "podzol_side", "podzol_top", "polished_andesite", "polished_basalt_side", "polished_basalt_top", "polished_blackstone", "polished_deepslate", "poppy", "potatoes_stage0", "potatoes_stage1", "potatoes_stage2", "potatoes_stage3", "powder_snow", "prismarine", "prismarine_bricks", "pumpkin_side", "pumpkin_top", "purpur_block", "purpur_pillar", "quartz_block_bottom", "quartz_block_side", "quartz_block_top", "quartz_bricks", "raw_copper_block", "raw_gold_block", "raw_iron_block", "redstone_block", "redstone_dust_dot", "redstone_dust_line0", "redstone_dust_line1", "redstone_lamp", "redstone_lamp_on", "redstone_ore", "redstone_torch", "redstone_torch_off", "reinforced_deepslate_bottom", "reinforced_deepslate_side", "reinforced_deepslate_top", "repeater", "repeater_on", "respawn_anchor_bottom", "respawn_anchor_side0", "respawn_anchor_top", "rooted_dirt", "rose_bush_bottom", "rose_bush_top", "sand", "sandstone", "sandstone_bottom", "sandstone_top", "scaffolding_bottom", "scaffolding_side", "scaffolding_top", "sculk", "sculk_catalyst_side", "sculk_catalyst_top", "sculk_sensor_bottom", "sculk_sensor_side", "sculk_sensor_top", "sculk_shrieker_bottom", "sculk_shrieker_side", "sculk_shrieker_top", "sea_lantern", "sea_pickle", "shulker_box_base", "slime_block", "smithing_table_front", "smithing_table_side", "smithing_table_top", "smoker_front", "smoker_side", "smoker_top", "smooth_stone", "smooth_stone_slab_side", "snow", "soul_sand", "soul_soil", "spawner", "sponge", "spruce_leaves", "spruce_log", "spruce_planks", "stone", "stone_bricks", "stripped_oak_log", "stripped_oak_log_top", "sunflower_back", "sunflower_front", "sweet_berry_bush_stage0", "tall_grass_bottom", "tall_grass_top", "terracotta", "tinted_glass", "tnt_bottom", "tnt_side", "tnt_top", "torch", "trial_spawner_bottom", "trial_spawner_side", "trial_spawner_top", "tuff", "turtle_egg", "vine", "warped_nylium_side", "warped_nylium_top", "warped_planks", "warped_stem", "water_flow", "water_still", "wet_sponge", "wheat_stage0", "wheat_stage7", "white_wool"]

# --- OVERLAY ENGINE ---
class OverlayWindow(QWidget):
    def __init__(self, image_path):
        super().__init__()
        self.image_path = image_path
        self.setWindowFlags(Qt.WindowType.FramelessWindowHint | Qt.WindowType.WindowStaysOnTopHint | Qt.WindowType.Tool)
        self.setAttribute(Qt.WidgetAttribute.WA_TranslucentBackground)
        self.original_image = Image.open(self.image_path).convert("RGBA")
        self.scale_factor = 1.0
        self.label = QLabel(self)
        self.update_image()
        self.show()

    def update_image(self):
        w, h = int(self.original_image.width * self.scale_factor), int(self.original_image.height * self.scale_factor)
        w, h = max(w, 20), max(h, 20)
        resized = self.original_image.resize((w, h), Image.Resampling.LANCZOS)
        qimg = QImage(resized.tobytes(), resized.size[0], resized.size[1], QImage.Format.Format_RGBA8888)
        self.label.setPixmap(QPixmap.fromImage(qimg))
        self.label.adjustSize(); self.adjustSize()

    def mousePressEvent(self, event):
        if event.button() == Qt.MouseButton.LeftButton: self.old_pos = event.globalPosition().toPoint()
        if event.button() == Qt.MouseButton.RightButton: self.close()

    def mouseMoveEvent(self, event):
        delta = event.globalPosition().toPoint() - self.old_pos
        self.move(self.x() + delta.x(), self.y() + delta.y())
        self.old_pos = event.globalPosition().toPoint()

    def wheelEvent(self, event):
        self.scale_factor *= 1.1 if event.angleDelta().y() > 0 else 0.9
        self.update_image()

# --- TAB 1: MODEL STUDIO ---
class ModelStudioTab(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setObjectName("modelStudioTab") # FIX: Required by QFluentWidgets
        self.source_files = []; self.texture_checkboxes = {}
        layout = QHBoxLayout(self)
        left = QVBoxLayout(); scroll = ScrollArea(); content = QWidget(); c_lay = QVBoxLayout(content)
        
        c_lay.addWidget(SubtitleLabel("1. MODEL LOADER"))
        self.f_info = BodyLabel("0 Models Selected"); c_lay.addWidget(self.f_info)
        btn_l = PushButton("Load Models (.json)"); btn_l.clicked.connect(self.load_models); c_lay.addWidget(btn_l)
        
        c_lay.addSpacing(20); c_lay.addWidget(SubtitleLabel("2. POSITION EDITOR"))
        self.mode_box = ComboBox(); self.mode_box.addItems(DISPLAY_MODES); c_lay.addWidget(self.mode_box)
        self.inputs = {}
        for attr in ["rotation", "translation", "scale"]:
            row = QHBoxLayout(); cb = QCheckBox(attr.capitalize()); cb.setChecked(True); row.addWidget(cb)
            self.inputs[attr] = {"cb": cb, "edits": []}
            for _ in range(3):
                e = QLineEdit("1" if attr == "scale" else "0"); e.setFixedWidth(50); row.addWidget(e); self.inputs[attr]["edits"].append(e)
            c_lay.addLayout(row)
        btn_inj = PrimaryPushButton("Inject Position to All"); btn_inj.clicked.connect(self.inject_pos); c_lay.addWidget(btn_inj)

        c_lay.addSpacing(20); c_lay.addWidget(SubtitleLabel("3. PRECISION GLOW"))
        self.glow_scroll = ScrollArea(); self.glow_cont = QWidget(); self.glow_lay = QVBoxLayout(self.glow_cont)
        self.glow_scroll.setWidget(self.glow_cont); self.glow_scroll.setWidgetResizable(True); self.glow_scroll.setFixedHeight(150)
        c_lay.addWidget(self.glow_scroll)
        self.glow_val = QLineEdit("15"); c_lay.addWidget(self.glow_val)
        btn_glow = PrimaryPushButton("Apply Glow to Selected IDs"); btn_glow.clicked.connect(self.apply_glow); c_lay.addWidget(btn_glow)

        scroll.setWidget(content); scroll.setWidgetResizable(True); left.addWidget(scroll); layout.addLayout(left, 1)
        right = QVBoxLayout(); btn_v = PrimaryPushButton("GENERATE ALL WOOD VARIANTS"); btn_v.clicked.connect(self.gen_variants); right.addWidget(btn_v)
        layout.addLayout(right, 1)

    def load_models(self):
        paths, _ = QFileDialog.getOpenFileNames(self, "Load Models", "", "JSON (*.json)")
        if paths:
            self.source_files = paths; self.f_info.setText(f"{len(paths)} Models Selected")
            for i in reversed(range(self.glow_lay.count())): 
                if self.glow_lay.itemAt(i).widget(): self.glow_lay.itemAt(i).widget().setParent(None)
            self.texture_checkboxes = {}
            try:
                with open(paths[0], 'r') as f: data = json.load(f)
                for tid, tpath in data.get("textures", {}).items():
                    cb = QCheckBox(f"#{tid}: {os.path.basename(tpath)}"); self.glow_lay.addWidget(cb); self.texture_checkboxes[tid] = cb
            except: pass

    def inject_pos(self):
        mode = self.mode_box.currentText()
        for p in self.source_files:
            with open(p, 'r') as f: data = json.load(f)
            if "display" not in data: data["display"] = {}
            if mode not in data["display"]: data["display"][mode] = {}
            for attr in ["rotation", "translation", "scale"]:
                if self.inputs[attr]["cb"].isChecked():
                    data["display"][mode][attr] = [float(e.text()) for e in self.inputs[attr]["edits"]]
            with open(p, 'w') as f: json.dump(data, f, indent=4)
        QMessageBox.information(self, "Success", "Display data injected.")

    def apply_glow(self):
        tids = [tid for tid, cb in self.texture_checkboxes.items() if cb.isChecked()]
        v = int(self.glow_val.text() or 15)
        for p in self.source_files:
            with open(p, 'r') as f: data = json.load(f)
            for el in data.get("elements", []):
                for face in el.get("faces", {}).values():
                    tk = face.get("texture", "").replace("#", "")
                    if tk in tids: face["light"] = v
                    elif "light" in face: del face["light"]
            with open(p, 'w') as f: json.dump(data, f, indent=4)
        QMessageBox.information(self, "Success", "Glow updated.")

    def gen_variants(self):
        out = QFileDialog.getExistingDirectory(self, "Select Output")
        if not out: return
        for src in self.source_files:
            fn = os.path.splitext(os.path.basename(src))[0]
            det = next((w for w in WOODS if w in fn.lower()), "")
            with open(src, 'r', encoding='utf-8') as f: content = f.read()
            for t in WOODS:
                n_fn = fn.replace(det, t) if det else f"{t}_{fn}"
                n_con = content.replace(fn, n_fn)
                for w in WOODS: n_con = n_con.replace(w, t)
                if t == "bamboo": n_con = n_con.replace("bamboo_log", "bamboo_block").replace("stripped_bamboo_log", "stripped_bamboo_block")
                with open(os.path.join(out, f"{n_fn}.json"), 'w', encoding='utf-8') as f: f.write(n_con)

# --- TAB 2: MODEL DATA EDITOR (File Browser Restored) ---
class ModelDataEditorTab(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setObjectName("modelDataEditorTab")
        layout = QHBoxLayout(self)
        
        # Left sidebar browser
        self.left_side = QVBoxLayout()
        self.search = SearchLineEdit(); self.search.textChanged.connect(self.update_list); self.left_side.addWidget(self.search)
        self.file_list = QListWidget(); self.file_list.itemSelectionChanged.connect(self.on_file_select); self.left_side.addWidget(self.file_list)
        layout.addLayout(self.left_side, 1)
        
        # Right editor area
        self.right_side = QVBoxLayout()
        self.file_label = BodyLabel("Select a .textures file"); self.right_side.addWidget(self.file_label)
        self.editor = QTextEdit(); self.editor.setStyleSheet("font-family: 'Consolas'; font-size: 11pt;"); self.right_side.addWidget(self.editor)
        
        btn_lay = QHBoxLayout()
        self.inject_btn = PushButton("Inject 'minecraft:' Prefix"); self.inject_btn.clicked.connect(self.inject_prefixes); btn_lay.addWidget(self.inject_btn)
        self.save_btn = PrimaryPushButton("SAVE FILE"); self.save_btn.clicked.connect(self.save_file); btn_lay.addWidget(self.save_btn)
        self.right_side.addLayout(btn_lay)
        layout.addLayout(self.right_side, 2)
        
        self.all_files = []; self.current_file = None
        self.load_directory()

    def load_directory(self):
        pattern = os.path.join(MODELS_PATH, "**", "*.textures")
        self.all_files = glob.glob(pattern, recursive=True)
        self.update_list()

    def update_list(self):
        txt = self.search.text().lower(); self.file_list.clear()
        for f in self.all_files:
            if txt in os.path.basename(f).lower(): self.file_list.addItem(os.path.basename(f))

    def on_file_select(self):
        items = self.file_list.selectedItems()
        if not items: return
        fname = items[0].text()
        self.current_file = next((f for f in self.all_files if os.path.basename(f) == fname), None)
        self.file_label.setText(f"Editing: {fname}")
        try:
            with open(self.current_file, 'r', encoding='utf-8') as f: self.editor.setPlainText(f.read())
        except: pass

    def inject_prefixes(self):
        content = self.editor.toPlainText(); count = 0
        for b in VANILLA_BLOCKS:
            content, n = re.subn(rf'(?<!minecraft:)\"{b}\"', f'\"minecraft:{b}\"', content); count += n
        self.editor.setPlainText(content)

    def save_file(self):
        if not self.current_file: return
        try:
            with open(self.current_file, 'w', encoding='utf-8') as f: f.write(self.editor.toPlainText())
            QMessageBox.information(self, "Saved", "Texture mapping updated.")
        except Exception as e: QMessageBox.critical(self, "Error", str(e))

# --- TAB 3: TEXTURE LAB ---
class TextureTab(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setObjectName("textureTab")
        self.stack_paths = []
        layout = QVBoxLayout(self)
        layout.addWidget(SubtitleLabel("Texture Swapper"))
        h1 = QHBoxLayout(); self.target_lbl = BodyLabel("No Folder Selected"); h1.addWidget(self.target_lbl)
        btn_fd = PushButton("Target Folder"); btn_fd.clicked.connect(self.pick_dir); h1.addWidget(btn_fd)
        layout.addLayout(h1)
        h2 = QHBoxLayout(); self.src_lbl = BodyLabel("No Source Selected"); h2.addWidget(self.src_lbl)
        btn_src = PushButton("Source Image"); btn_src.clicked.connect(self.pick_src); h2.addWidget(btn_src)
        layout.addLayout(h2)
        self.file_list = QListWidget(); layout.addWidget(self.file_list)
        btn_swap = PrimaryPushButton("FORCE OVERWRITE"); btn_swap.clicked.connect(self.do_swap); layout.addWidget(btn_swap)
        layout.addSpacing(20); layout.addWidget(SubtitleLabel("Stacker & Scrambler"))
        self.stack_list = QListWidget(); layout.addWidget(self.stack_list)
        btn_add = PushButton("Add to Stack"); btn_add.clicked.connect(self.add_stack); layout.addWidget(btn_add)
        btn_gen = PrimaryPushButton("GENERATE COMBINED"); btn_gen.clicked.connect(self.gen_stack); layout.addWidget(btn_gen)

    def pick_dir(self):
        d = QFileDialog.getExistingDirectory(self); 
        if d: self.target_lbl.setText(d); self.refresh_files()

    def pick_src(self):
        f, _ = QFileDialog.getOpenFileName(self, "", "", "PNG (*.png)"); 
        if f: self.src_path = f; self.src_lbl.setText(os.path.basename(f))

    def refresh_files(self):
        self.file_list.clear()
        if os.path.exists(self.target_lbl.text()):
            self.file_list.addItems([f for f in os.listdir(self.target_lbl.text()) if f.endswith(".png")])

    def do_swap(self):
        if not self.file_list.currentItem() or not hasattr(self, 'src_path'): return
        target = os.path.join(self.target_lbl.text(), self.file_list.currentItem().text())
        with open(self.src_path, 'rb') as s, open(target, 'wb') as d: d.write(s.read())
        os.utime(target, None); QMessageBox.information(self, "Success", "Swapped.")

    def add_stack(self):
        fs, _ = QFileDialog.getOpenFileNames(self); self.stack_paths.extend(fs)
        self.stack_list.clear(); self.stack_list.addItems([os.path.basename(x) for x in self.stack_paths])

    def gen_stack(self):
        if not self.stack_paths: return
        imgs = [Image.open(p).convert("RGBA") for p in self.stack_paths]
        res = Image.new("RGBA", (max(i.width for i in imgs), sum(i.height for i in imgs)), (0,0,0,0)); cur_y = 0
        for i in imgs: res.paste(i, (0, cur_y)); cur_y += i.height
        p, _ = QFileDialog.getSaveFileName(self, "Save Stack", "combined.png", "PNG (*.png)")
        if p: res.save(p)

# --- TAB 4: MCREATOR SYNC ---
class SyncTab(QWidget):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setObjectName("syncTab")
        layout = QVBoxLayout(self)
        self.conn = BodyLabel("MCreator: Scanning..."); layout.addWidget(self.conn)
        self.tab_lbl = BodyLabel("Active: None"); layout.addWidget(self.tab_lbl)
        self.log = QTextEdit(); self.log.setReadOnly(True); layout.addWidget(self.log)
        btn_fix = PrimaryPushButton("Fix Vanilla 'minecraft:' Prefixes"); btn_fix.clicked.connect(self.manual_fix); layout.addWidget(btn_fix)
        self.timer = QTimer(); self.timer.timeout.connect(self.sync_loop); self.timer.start(1000)
        self.focused_file = None

    def sync_loop(self):
        wins = [w for w in gw.getAllWindows() if "MCreator" in w.title and "Voxelized Furniture" in w.title]
        if wins:
            try:
                t = wins[0].title.split(" - ")[1].split("(")[0].strip()
                self.conn.setText("MCreator: Linked"); self.tab_lbl.setText(f"Editing: {t}")
                all_j = glob.glob(os.path.join(ELEMENTS_PATH, "**", "*.json"), recursive=True)
                self.focused_file = next((f for f in all_j if t.lower().replace(" ", "") in os.path.basename(f).lower()), None)
                if self.focused_file: self.log.setText(f"File Linked:\n{self.focused_file}")
            except: pass
        else: self.conn.setText("Disconnected")

    def manual_fix(self):
        if not self.focused_file: return
        with open(self.focused_file, 'r', encoding='utf-8') as f: content = f.read()
        fixed = 0
        for b in VANILLA_BLOCKS:
            content, count = re.subn(rf'(?<!minecraft:)\"{b}\"', f'\"minecraft:{b}\"', content); fixed += count
        with open(self.focused_file, 'w', encoding='utf-8') as f: f.write(content)
        QMessageBox.information(self, "Done", f"Applied {fixed} prefixes.")

# --- MAIN SUITE ---
class MasterStudioSuite(MSFluentWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("VOXELIZED MASTER STUDIO [v82]")
        self.resize(1200, 850)
        
        # Initialize interfaces with objectNames to prevent ValueErrors
        self.studio = ModelStudioTab()
        self.editor = ModelDataEditorTab()
        self.tex = TextureTab()
        self.sync_tab = SyncTab()
        
        # Add to sidebar
        self.addSubInterface(self.studio, FIF.HOME, "Model Studio")
        self.addSubInterface(self.editor, FIF.DOCUMENT, "Model Data Editor")
        self.addSubInterface(self.tex, FIF.PHOTO, "Texture Lab")
        self.addSubInterface(self.sync_tab, FIF.SYNC, "MCreator Sync")
        
        self.navigationInterface.addWidget("overlay", TransparentToolButton(FIF.VIEW), self.spawn_overlay, Qt.Edge.BottomEdge)

    def spawn_overlay(self):
        f, _ = QFileDialog.getOpenFileName(self, "Select Image", "", "Images (*.png *.jpg *.jpeg)")
        if f: self.overlay = OverlayWindow(f)

if __name__ == "__main__":
    ctypes.windll.shell32.SetCurrentProcessExplicitAppUserModelID('voxelized.master.suite.v82')
    app = QApplication(sys.argv); setTheme(Theme.DARK)
    window = MasterStudioSuite(); window.show(); sys.exit(app.exec())