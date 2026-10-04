import os
import sys
import glob
import re
import ctypes # Added for Taskbar Icon fix
import pygetwindow as gw
from PyQt6.QtCore import Qt, QTimer
from PyQt6.QtGui import QIcon
from PyQt6.QtWidgets import (QApplication, QWidget, QVBoxLayout, 
                             QHBoxLayout, QLabel, QPushButton, QListWidget, 
                             QLineEdit, QTextEdit, QMessageBox)

# Using FluentWindow for the modern Mica/Acrylic look
try:
    from qfluentwidgets import FluentWindow, FluentIcon as FIF, setTheme, Theme
except ImportError:
    print("Error: qfluentwidgets not found. Run: pip install qfluentwidgets")
    sys.exit(1)

# --- MASTER CONFIGURATION ---
WORKSPACE_PATH = r"C:\Users\minec\MCreatorWorkspaces\voxelized_furniture"
ELEMENTS_PATH = os.path.join(WORKSPACE_PATH, "elements")
MODELS_PATH = os.path.join(WORKSPACE_PATH, "models") 
# Make sure icon.png is in the same folder as this script!
ICON_PATH = os.path.join(os.path.dirname(os.path.abspath(__file__)), "icon.png")

# --- COMPLETE VANILLA BLOCKS LIST ---
VANILLA_BLOCKS = [
    "acacia_door_bottom", "acacia_door_top", "acacia_leaves", "acacia_log", "acacia_log_top", "acacia_planks", 
    "acacia_sapling", "acacia_trapdoor", "activator_rail", "activator_rail_on", "allium", "amethyst_block", 
    "amethyst_cluster", "ancient_debris_side", "ancient_debris_top", "andesite", "anvil", "anvil_top", 
    "azalea_leaves", "azalea_side", "azalea_top", "bamboo_block", "bamboo_planks", "barrel_bottom", 
    "barrel_side", "barrel_top", "barrel_top_open", "basalt_side", "basalt_top", "beacon", "bedrock", 
    "beehive_front", "beehive_side", "beetroots_stage0", "beetroots_stage1", "beetroots_stage2", 
    "beetroots_stage3", "bell_side", "big_dripleaf_side", "big_dripleaf_top", "birch_door_bottom", 
    "birch_door_top", "birch_leaves", "birch_log", "birch_log_top", "birch_planks", "blackstone", 
    "black_concrete", "black_wool", "blast_furnace_front", "blast_furnace_side", "blast_furnace_top", 
    "blue_ice", "bookshelf", "brewing_stand_base", "bricks", "cactus_side", "cactus_top", "cake_bottom", 
    "cake_inner", "cake_side", "cake_top", "calcite", "campfire_log", "cauldron_side", "cauldron_top", 
    "chain", "cherry_log", "chiseled_bookshelf_empty", "chiseled_deepslate", "chiseled_quartz_block", 
    "chiseled_stone_bricks", "clay", "coal_block", "coal_ore", "coarse_dirt", "cobblestone", 
    "cobbled_deepslate", "copper_block", "copper_ore", "cornflower", "cracked_stone_bricks", 
    "crafting_table_front", "crafting_table_side", "crafting_table_top", "crying_obsidian", "dark_oak_log", 
    "dark_prismarine", "daylight_detector_top", "dead_bush", "deepslate", "deepslate_coal_ore", 
    "deepslate_diamond_ore", "deepslate_emerald_ore", "deepslate_gold_ore", "deepslate_iron_ore", 
    "deepslate_lapis_ore", "deepslate_redstone_ore", "diamond_block", "diamond_ore", "dirt", 
    "dirt_path_side", "dirt_path_top", "dispenser_front", "dispenser_front_vertical", "dragon_egg", 
    "dried_kelp_bottom", "dried_kelp_side", "dried_kelp_top", "dripstone_block", "emerald_block", 
    "emerald_ore", "enchanting_table_bottom", "enchanting_table_side", "enchanting_table_top", 
    "end_portal_frame_side", "end_portal_frame_top", "end_stone", "end_stone_bricks", "farmland", 
    "farmland_moist", "fire_0", "fire_1", "fletching_table_front", "fletching_table_side", "fletching_top", 
    "flowering_azalea_side", "flowering_azalea_top", "frogspawn", "furnace_front", "furnace_side", 
    "furnace_top", "gilded_blackstone", "glass", "glass_pane_top", "glowstone", "glow_lichen", "gold_block", 
    "gold_ore", "granite", "grass", "grass_block_side", "grass_block_top", "gravel", "hay_block_side", 
    "hay_block_top", "honey_block_side", "honey_block_top", "ice", "iron_block", "iron_door_bottom", 
    "iron_door_top", "iron_ore", "jukebox_side", "jukebox_top", "jungle_log", "ladder", "lantern", 
    "lapis_block", "lapis_ore", "lava_flow", "lava_still", "lectern_front", "lectern_side", "lectern_top", 
    "lily_pad", "loom_front", "magma", "mangrove_log", "mangrove_planks", "melon_side", "melon_top", 
    "mossy_cobblestone", "mossy_stone_bricks", "moss_block", "mud", "mud_bricks", "mushroom_stem", 
    "mycelium_side", "mycelium_top", "netherite_block", "netherrack", "nether_bricks", "nether_gold_ore", 
    "nether_quartz_ore", "nether_sprouts", "nether_wart_stage0", "nether_wart_stage1", "nether_wart_stage2", 
    "note_block", "oak_leaves", "oak_log", "oak_log_top", "oak_planks", "oak_sapling", "observer_back", 
    "observer_front", "observer_side", "observer_top", "obsidian", "packed_ice", "packed_mud", "pink_petals", 
    "piston_bottom", "piston_inner", "piston_side", "piston_top", "piston_top_sticky", "podzol_side", 
    "podzol_top", "polished_andesite", "polished_basalt_side", "polished_basalt_top", "polished_blackstone", 
    "polished_deepslate", "poppy", "potatoes_stage0", "potatoes_stage1", "potatoes_stage2", "potatoes_stage3", 
    "powder_snow", "prismarine", "prismarine_bricks", "pumpkin_side", "pumpkin_top", "purpur_block", 
    "purpur_pillar", "quartz_block_bottom", "quartz_block_side", "quartz_block_top", "quartz_bricks", 
    "raw_copper_block", "raw_gold_block", "raw_iron_block", "redstone_block", "redstone_dust_dot", 
    "redstone_dust_line0", "redstone_dust_line1", "redstone_lamp", "redstone_lamp_on", "redstone_ore", 
    "redstone_torch", "redstone_torch_off", "reinforced_deepslate_bottom", "reinforced_deepslate_side", 
    "reinforced_deepslate_top", "repeater", "repeater_on", "respawn_anchor_bottom", "respawn_anchor_side0", 
    "respawn_anchor_top", "rooted_dirt", "rose_bush_bottom", "rose_bush_top", "sand", "sandstone", 
    "sandstone_bottom", "sandstone_top", "scaffolding_bottom", "scaffolding_side", "scaffolding_top", 
    "sculk", "sculk_catalyst_side", "sculk_catalyst_top", "sculk_sensor_bottom", "sculk_sensor_side", 
    "sculk_sensor_top", "sculk_shrieker_bottom", "sculk_shrieker_side", "sculk_shrieker_top", "sea_lantern", 
    "sea_pickle", "shulker_box_base", "slime_block", "smithing_table_front", "smithing_table_side", 
    "smithing_table_top", "smoker_front", "smoker_side", "smoker_top", "smooth_stone", "smooth_stone_slab_side", 
    "snow", "soul_sand", "soul_soil", "spawner", "sponge", "spruce_leaves", "spruce_log", "spruce_planks", 
    "stone", "stone_bricks", "stripped_oak_log", "stripped_oak_log_top", "sunflower_back", "sunflower_front", 
    "sweet_berry_bush_stage0", "tall_grass_bottom", "tall_grass_top", "terracotta", "tinted_glass", 
    "tnt_bottom", "tnt_side", "tnt_top", "torch", "trial_spawner_bottom", "trial_spawner_side", 
    "trial_spawner_top", "tuff", "turtle_egg", "vine", "warped_nylium_side", "warped_nylium_top", 
    "warped_planks", "warped_stem", "water_flow", "water_still", "wet_sponge", "wheat_stage0", 
    "wheat_stage7", "white_wool"
]

class TextureBrowser(FluentWindow):
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setWindowTitle("Model Texture Explorer")
        self.resize(900, 650)
        
        if os.path.exists(ICON_PATH):
            self.setWindowIcon(QIcon(ICON_PATH))
        
        self.container = QWidget()
        self.container.setObjectName("browser_container")
        self.layout = QHBoxLayout(self.container)
        
        self.left_layout = QVBoxLayout()
        self.search_entry = QLineEdit()
        self.search_entry.setPlaceholderText("Search .textures Files...")
        self.search_entry.textChanged.connect(self.update_list)
        self.file_listbox = QListWidget()
        self.file_listbox.itemSelectionChanged.connect(self.on_file_select)
        
        self.left_layout.addWidget(QLabel("Files:"))
        self.left_layout.addWidget(self.search_entry)
        self.left_layout.addWidget(self.file_listbox)
        
        self.right_layout = QVBoxLayout()
        self.file_label = QLabel("Select a model mapping file")
        self.editor = QTextEdit()
        self.editor.setStyleSheet("font-family: 'Consolas'; font-size: 11pt;")
        
        self.btn_layout = QHBoxLayout()
        self.inject_btn = QPushButton("INJECT PREFIX")
        self.save_btn = QPushButton("SAVE FILE")
        self.inject_btn.clicked.connect(self.inject_into_editor)
        self.save_btn.clicked.connect(self.save_manual_edits)
        
        self.btn_layout.addWidget(self.inject_btn)
        self.btn_layout.addWidget(self.save_btn)
        
        self.right_layout.addWidget(self.file_label)
        self.right_layout.addWidget(self.editor)
        self.right_layout.addLayout(self.btn_layout)
        
        self.layout.addLayout(self.left_layout, 1)
        self.layout.addLayout(self.right_layout, 2)
        self.addSubInterface(self.container, FIF.FOLDER, "Explorer")

        self.all_files = []
        self.current_open_file = None
        self.load_files()

    def load_files(self):
        pattern = os.path.join(MODELS_PATH, "**", "*.textures")
        self.all_files = glob.glob(pattern, recursive=True)
        self.update_list()

    def update_list(self):
        search_term = self.search_entry.text().lower()
        self.file_listbox.clear()
        for f in self.all_files:
            fname = os.path.basename(f)
            if search_term in fname.lower():
                self.file_listbox.addItem(fname)

    def on_file_select(self):
        items = self.file_listbox.selectedItems()
        if not items: return
        fname = items[0].text()
        self.current_open_file = next((f for f in self.all_files if os.path.basename(f) == fname), None)
        self.file_label.setText(f"Editing: {fname}")
        try:
            with open(self.current_open_file, 'r', encoding='utf-8') as f:
                self.editor.setPlainText(f.read())
        except: pass

    def save_manual_edits(self):
        if not self.current_open_file: return
        try:
            with open(self.current_open_file, 'w', encoding='utf-8') as f:
                f.write(self.editor.toPlainText())
            QMessageBox.information(self, "Saved", "File saved successfully!")
        except Exception as e:
            QMessageBox.critical(self, "Error", f"Failed to save: {e}")

    def inject_into_editor(self):
        content = self.editor.toPlainText()
        fixed = 0
        for block in VANILLA_BLOCKS:
            pattern = rf'(?<!minecraft:)"{block}"'
            new_content, count = re.subn(pattern, f'"minecraft:{block}"', content)
            content = new_content
            fixed += count
        self.editor.setPlainText(content)
        if fixed > 0: self.save_manual_edits()

class MainApp(FluentWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("MCreator Master Sync")
        self.resize(400, 450)
        
        if os.path.exists(ICON_PATH):
            self.setWindowIcon(QIcon(ICON_PATH))
        
        self.view = QWidget()
        self.view.setObjectName("main_sync_view")
        self.layout = QVBoxLayout(self.view)
        
        self.title_label = QLabel("Voxelized Furniture Sync")
        self.title_label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.title_label.setStyleSheet("font-size: 14pt; font-weight: bold;")
        
        self.conn_label = QLabel("Scanning...")
        self.status_label = QLabel("No Tab Detected")
        
        self.fix_btn = QPushButton("FIX ACTIVE ELEMENT")
        self.fix_btn.setEnabled(False)
        self.fix_btn.clicked.connect(self.manual_fix)
        
        self.browser_btn = QPushButton("BROWSER MODEL TEXTURES")
        self.browser_btn.clicked.connect(self.open_browser)

        self.layout.addWidget(self.title_label)
        self.layout.addWidget(self.conn_label)
        self.layout.addWidget(self.status_label)
        self.layout.addWidget(self.fix_btn)
        self.layout.addWidget(self.browser_btn)
        self.addSubInterface(self.view, FIF.SYNC, "Sync Console")

        self.focused_file = None
        self.current_tab = ""
        self.last_checked_file = None 
        
        self.timer = QTimer(self)
        self.timer.timeout.connect(self.sync_loop)
        self.timer.start(1000)

    def sync_loop(self):
        try:
            m_windows = [w for w in gw.getAllWindows() if "MCreator" in w.title and "Voxelized Furniture" in w.title]
            if m_windows:
                title = m_windows[0].title
                parts = title.split(" - ")
                if len(parts) >= 2:
                    tab_name = parts[1].split("(")[0].strip()
                    if tab_name != self.current_tab:
                        self.current_tab = tab_name
                        self.update_focused_file(tab_name)
                        self.auto_check()
                    self.conn_label.setText("MCreator: Connected")
                    self.status_label.setText(f"Active Tab: {tab_name}")
                    self.fix_btn.setEnabled(True)
            else:
                self.conn_label.setText("Disconnected")
                self.fix_btn.setEnabled(False)
        except: pass

    def update_focused_file(self, tab_name):
        clean = tab_name.lower().replace(" ", "")
        all_json = glob.glob(os.path.join(ELEMENTS_PATH, "**", "*.json"), recursive=True)
        self.focused_file = next((f for f in all_json if clean in os.path.basename(f).lower()), None)

    def auto_check(self):
        if not self.focused_file or self.focused_file == self.last_checked_file: return
        try:
            with open(self.focused_file, 'r', encoding='utf-8') as f: content = f.read()
            found = any(re.search(rf'(?<!minecraft:)"{b}"', content) for b in VANILLA_BLOCKS)
            if found:
                res = QMessageBox.question(self, "Vanilla Detected", 
                                         f"Exact vanilla keywords detected. Update?",
                                         QMessageBox.StandardButton.Yes | QMessageBox.StandardButton.No)
                if res == QMessageBox.StandardButton.Yes: self.manual_fix()
            self.last_checked_file = self.focused_file
        except: pass

    def manual_fix(self):
        if not self.focused_file: return
        with open(self.focused_file, 'r', encoding='utf-8') as f: content = f.read()
        fixed = 0
        for block in VANILLA_BLOCKS:
            pattern = rf'(?<!minecraft:)"{block}"'
            content, count = re.subn(pattern, f'"minecraft:{block}"', content)
            fixed += count
        with open(self.focused_file, 'w', encoding='utf-8') as f: f.write(content)
        QMessageBox.information(self, "Done", f"Applied {fixed} prefixes.")

    def open_browser(self):
        self.browser = TextureBrowser()
        self.browser.show()

if __name__ == "__main__":
    # TASKBAR ICON FIX: Create a unique AppUserModelID
    my_appid = 'mycompany.myproduct.subproduct.version' # arbitrary string
    ctypes.windll.shell32.SetCurrentProcessExplicitAppUserModelID(my_appid)

    app = QApplication(sys.argv)
    setTheme(Theme.DARK)
    window = MainApp()
    window.show()
    sys.exit(app.exec())