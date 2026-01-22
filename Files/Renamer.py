import os
import json
import random
import math
import customtkinter as ctk
from tkinter import filedialog, messagebox
from PIL import Image

# --- CONFIG ---
WOODS = ["dark_oak", "oak", "spruce", "birch", "jungle", "acacia", "mangrove", "cherry", "bamboo"]
LEGAL_ANGLES = [-45, -22.5, 0, 22.5, 45]
DISPLAY_MODES = ["gui", "ground", "fixed", "thirdperson_righthand", "firstperson_righthand", "head", "firstperson_lefthand", "thirdperson_lefthand"]

class ManualDisplayEditor(ctk.CTkToplevel):
    def __init__(self, parent):
        super().__init__(parent)
        self.parent = parent
        self.title("Manual Display Settings Editor")
        self.geometry("1000x600")
        self.attributes("-topmost", True)
        
        self.grid_columnconfigure(0, weight=1) # Input Panel
        self.grid_columnconfigure(1, weight=1) # Preview Panel
        self.grid_rowconfigure(0, weight=1)

        # --- LEFT PANEL (INPUTS) ---
        input_frame = ctk.CTkFrame(self)
        input_frame.grid(row=0, column=0, sticky="nsew", padx=10, pady=10)
        
        ctk.CTkLabel(input_frame, text="Display Mode:", font=ctk.CTkFont(weight="bold")).pack(pady=5)
        self.mode_var = ctk.StringVar(value="gui")
        self.mode_dropdown = ctk.CTkOptionMenu(input_frame, values=DISPLAY_MODES, variable=self.mode_var, command=self.load_values_from_first_model)
        self.mode_dropdown.pack(pady=5)

        self.entries = {}
        
        # Helper to create row of 3 inputs
        def create_input_row(parent, label):
            f = ctk.CTkFrame(parent, fg_color="transparent")
            f.pack(fill="x", pady=5, padx=5)
            ctk.CTkLabel(f, text=label, width=80, anchor="w").pack(side="left")
            row_entries = []
            for axis in ["X", "Y", "Z"]:
                e = ctk.CTkEntry(f, width=60, placeholder_text=axis)
                e.pack(side="left", padx=5)
                e.bind("<KeyRelease>", self.update_preview)
                row_entries.append(e)
            return row_entries

        self.entries["rotation"] = create_input_row(input_frame, "Rotation:")
        self.entries["translation"] = create_input_row(input_frame, "Translation:")
        self.entries["scale"] = create_input_row(input_frame, "Scale:")

        ctk.CTkButton(input_frame, text="Reset to Defaults", fg_color="#95a5a6", command=self.reset_defaults).pack(pady=20)

        # --- RIGHT PANEL (PREVIEW) ---
        preview_frame = ctk.CTkFrame(self)
        preview_frame.grid(row=0, column=1, sticky="nsew", padx=10, pady=10)
        
        ctk.CTkLabel(preview_frame, text="Real-time Wireframe Preview", font=ctk.CTkFont(weight="bold")).pack(pady=5)
        self.canvas = ctk.CTkCanvas(preview_frame, bg="#1a1a1a", highlightthickness=0)
        self.canvas.pack(fill="both", expand=True, padx=10, pady=5)

        # --- BOTTOM BAR ---
        btn_frame = ctk.CTkFrame(self, fg_color="transparent")
        btn_frame.grid(row=1, column=0, columnspan=2, fill="x", pady=15)
        
        self.status_label = ctk.CTkLabel(btn_frame, text="", text_color="gray", font=ctk.CTkFont(size=10))
        self.status_label.pack(side="left", padx=10)
        
        ctk.CTkButton(btn_frame, text="Apply Settings to All Selected Models", fg_color="#2ecc71", font=ctk.CTkFont(size=14, weight="bold"), height=40, command=self.apply_changes).pack(side="right", padx=20)
        ctk.CTkButton(btn_frame, text="Close", fg_color="#e74c3c", command=self.destroy).pack(side="right", padx=10)

        # Load initial data
        self.load_values_from_first_model()

    def get_vec3(self, key, default=[0,0,0]):
        try:
            return [float(e.get()) for e in self.entries[key]]
        except:
            return default

    def set_vec3(self, key, values):
        for i, val in enumerate(values):
            self.entries[key][i].delete(0, "end")
            self.entries[key][i].insert(0, str(val))

    def reset_defaults(self):
        self.set_vec3("rotation", [0, 0, 0])
        self.set_vec3("translation", [0, 0, 0])
        self.set_vec3("scale", [1, 1, 1])
        self.update_preview()
        self.status_label.configure(text="Reset to defaults.")

    def load_values_from_first_model(self, choice=None):
        if not self.parent.source_paths:
            self.reset_defaults()
            self.status_label.configure(text="No source file selected.")
            return
            
        path = self.parent.source_paths[0]
        filename = os.path.basename(path)
        self.title(f"Manual Display Settings Editor - Reading: {filename}")
        
        mode = choice if choice else self.mode_var.get()
        
        try:
            with open(path, 'r', encoding='utf-8') as f:
                data = json.load(f)
            
            # Robustly fetch display section
            display_section = data.get("display") or {}
            mode_settings = display_section.get(mode, None)
            
            if mode_settings is None:
                self.reset_defaults()
                self.status_label.configure(text=f"No settings found for '{mode}'. Showing defaults.")
            else:
                self.set_vec3("rotation", mode_settings.get("rotation", [0, 0, 0]))
                self.set_vec3("translation", mode_settings.get("translation", [0, 0, 0]))
                self.set_vec3("scale", mode_settings.get("scale", [1, 1, 1]))
                self.update_preview()
                self.status_label.configure(text=f"Loaded '{mode}' settings from file.")
        except Exception as e:
            print(f"Failed to load display settings: {e}")
            self.status_label.configure(text="Error reading file. Showing defaults.")
            self.reset_defaults()

    def project(self, x, y, z, rotation, translation, scale):
        # 1. Scale
        x, y, z = x * scale[0], y * scale[1], z * scale[2]
        
        # 2. Rotation (Euler angles in degrees)
        ax, ay, az = [math.radians(a) for a in rotation]
        
        # Rot X
        ny = y * math.cos(ax) - z * math.sin(ax)
        nz = y * math.sin(ax) + z * math.cos(ax)
        y, z = ny, nz
        
        # Rot Y
        nx = x * math.cos(ay) + z * math.sin(ay)
        nz = -x * math.sin(ay) + z * math.cos(ay)
        x, z = nx, nz
        
        # Rot Z
        nx = x * math.cos(az) - y * math.sin(az)
        ny = x * math.sin(az) + y * math.cos(az)
        x, y = nx, ny

        # 3. Translation (Scaled visual factor)
        x += translation[0] * 1.5
        y -= translation[1] * 1.5

        # 4. Projection
        factor = 120
        win_w = self.canvas.winfo_width() / 2
        win_h = self.canvas.winfo_height() / 2
        
        return x * factor + win_w, y * factor + win_h

    def update_preview(self, event=None):
        self.canvas.delete("all")
        
        rot = self.get_vec3("rotation", [0,0,0])
        trans = self.get_vec3("translation", [0,0,0])
        scale = self.get_vec3("scale", [1,1,1])
        
        # 8 Corners of a block centered at 0
        v = [
            (-0.5, -0.5, -0.5), (0.5, -0.5, -0.5), (0.5, 0.5, -0.5), (-0.5, 0.5, -0.5),
            (-0.5, -0.5, 0.5), (0.5, -0.5, 0.5), (0.5, 0.5, 0.5), (-0.5, 0.5, 0.5)
        ]
        
        try:
            proj = [self.project(p[0], p[1], p[2], rot, trans, scale) for p in v]
            
            edges = [
                (0,1), (1,2), (2,3), (3,0), # Back face
                (4,5), (5,6), (6,7), (7,4), # Front face
                (0,4), (1,5), (2,6), (3,7)  # Connecting lines
            ]
            
            # Draw axis reference
            cx, cy = self.canvas.winfo_width()/2, self.canvas.winfo_height()/2
            self.canvas.create_line(cx-10, cy, cx+10, cy, fill="#444")
            self.canvas.create_line(cx, cy-10, cx, cy+10, fill="#444")

            for e in edges:
                p1, p2 = proj[e[0]], proj[e[1]]
                self.canvas.create_line(p1[0], p1[1], p2[0], p2[1], fill="#3498db", width=2)
        except:
            pass

    def apply_changes(self):
        if not self.parent.source_paths:
            messagebox.showwarning("Warning", "No models selected in main app.")
            return

        mode = self.mode_var.get()
        rot = self.get_vec3("rotation")
        trans = self.get_vec3("translation")
        scale = self.get_vec3("scale")
        
        count = 0
        try:
            for path in self.parent.source_paths:
                if not path.endswith(".json"): continue
                
                with open(path, 'r') as f: data = json.load(f)
                
                if "display" not in data: data["display"] = {}
                data["display"][mode] = {
                    "rotation": rot,
                    "translation": trans,
                    "scale": scale
                }
                
                with open(path, 'w') as f: json.dump(data, f, indent=4)
                count += 1
            
            messagebox.showinfo("Success", f"Applied settings to '{mode}' for {count} models.")
        except Exception as e:
            messagebox.showerror("Error", str(e))


class SnippetPreviewer(ctk.CTkToplevel):
    def __init__(self, parent):
        super().__init__(parent)
        self.parent = parent
        self.title("GUI Snippet Importer")
        self.geometry("900x600")
        self.attributes("-topmost", True)
        
        self.grid_columnconfigure(0, weight=1)
        self.grid_columnconfigure(1, weight=1)
        self.grid_rowconfigure(0, weight=1)

        left_panel = ctk.CTkFrame(self)
        left_panel.grid(row=0, column=0, sticky="nsew", padx=10, pady=10)
        
        ctk.CTkLabel(left_panel, text="Paste JSON Snippet:", font=ctk.CTkFont(weight="bold")).pack(pady=5)
        self.textbox = ctk.CTkTextbox(left_panel, font=ctk.CTkFont(family="Consolas", size=11))
        self.textbox.pack(padx=10, pady=5, fill="both", expand=True)
        self.textbox.bind("<<Modified>>", self.on_text_change)

        right_panel = ctk.CTkFrame(self)
        right_panel.grid(row=0, column=1, sticky="nsew", padx=10, pady=10)
        
        ctk.CTkLabel(right_panel, text="3D View", font=ctk.CTkFont(weight="bold")).pack(pady=5)
        self.canvas = ctk.CTkCanvas(right_panel, bg="#1a1a1a", highlightthickness=0)
        self.canvas.pack(fill="both", expand=True, padx=10, pady=5)
        
        btn_frame = ctk.CTkFrame(self, fg_color="transparent")
        btn_frame.grid(row=1, column=0, columnspan=2, fill="x", pady=15)
        
        ctk.CTkButton(btn_frame, text="Apply to All Selected Models", fg_color="#2ecc71", command=self.apply_snippet).pack(side="right", padx=20)
        ctk.CTkButton(btn_frame, text="Update Preview", command=self.update_preview).pack(side="right", padx=10)
        ctk.CTkButton(btn_frame, text="Close", fg_color="#e74c3c", command=self.destroy).pack(side="right", padx=10)

        self.current_display_data = {}

    def on_text_change(self, event=None):
        self.textbox.edit_modified(0)
        self.update_preview()

    def project(self, x, y, z, rotation, translation, scale):
        sx, sy, sz = scale[0], scale[1], scale[2]
        x, y, z = x * sx, y * sy, z * sz
        ax, ay, az = [math.radians(a) for a in rotation]
        ny = y * math.cos(ax) - z * math.sin(ax); nz = y * math.sin(ax) + z * math.cos(ax); y, z = ny, nz
        nx = x * math.cos(ay) + z * math.sin(ay); nz = -x * math.sin(ay) + z * math.cos(ay); x, z = nx, nz
        nx = x * math.cos(az) - y * math.sin(az); ny = x * math.sin(az) + y * math.cos(az); x, y = nx, ny
        tx, ty, tz = translation[0] * 2, translation[1] * 2, translation[2] * 2
        x += tx; y -= ty
        factor = 150
        win_w = self.canvas.winfo_width() / 2; win_h = self.canvas.winfo_height() / 2
        return x * factor + win_w, y * factor + win_h

    def update_preview(self):
        raw_text = self.textbox.get("1.0", "end-1c").strip()
        if not raw_text: return
        try:
            snippet_data = json.loads(raw_text)
            display = snippet_data.get("display", snippet_data)
            gui = display.get("gui", {})
            rot = gui.get("rotation", [0, 0, 0]); trans = gui.get("translation", [0, 0, 0]); scale = gui.get("scale", [1, 1, 1])
            self.canvas.delete("all")
            v = [(-0.5, -0.5, -0.5), (0.5, -0.5, -0.5), (0.5, 0.5, -0.5), (-0.5, 0.5, -0.5), (-0.5, -0.5, 0.5), (0.5, -0.5, 0.5), (0.5, 0.5, 0.5), (-0.5, 0.5, 0.5)]
            proj = [self.project(p[0], p[1], p[2], rot, trans, scale) for p in v]
            edges = [(0,1), (1,2), (2,3), (3,0), (4,5), (5,6), (6,7), (7,4), (0,4), (1,5), (2,6), (3,7)]
            for e in edges:
                p1, p2 = proj[e[0]], proj[e[1]]
                self.canvas.create_line(p1[0], p1[1], p2[0], p2[1], fill="#3b8ed0", width=2)
            self.current_display_data = display
        except: pass

    def apply_snippet(self):
        if not self.current_display_data:
            messagebox.showwarning("Invalid", "No valid display data found.")
            return
        count = 0
        for path in self.parent.source_paths:
            if not path.endswith(".json"): continue
            with open(path, 'r') as f: data = json.load(f)
            data["display"] = self.current_display_data
            with open(path, 'w') as f: json.dump(data, f, indent=4)
            count += 1
        messagebox.showinfo("Success", f"Snippet applied to {count} files.")
        self.destroy()

class ModelProcessorApp(ctk.CTk):
    def __init__(self):
        super().__init__()
        self.title("Model Studio Pro - v62.0 (Editor Refresh Fix)")
        self.geometry("650x1200")
        ctk.set_appearance_mode("dark") 
        
        self.main_container = ctk.CTkScrollableFrame(self, fg_color="transparent")
        self.main_container.pack(fill="both", expand=True)

        self.source_paths = [] 
        self.textures_path = ""
        self.output_dir = ""
        self.texture_vars = {}
        self.cmd_entries = []
        self.snippet_window = None
        self.manual_editor = None

        self.setup_ui()

    def setup_ui(self):
        header = ctk.CTkFrame(self.main_container, fg_color="transparent")
        header.pack(fill="x", padx=30, pady=(20, 10))
        ctk.CTkLabel(header, text="MCreator Wood Suite", font=ctk.CTkFont(size=22, weight="bold")).pack(side="left")

        # 1. LOAD FILES
        step1 = ctk.CTkFrame(self.main_container); step1.pack(fill="x", padx=30, pady=10)
        ctk.CTkLabel(step1, text="1. SELECT MASTER MODEL(S)", font=ctk.CTkFont(size=12, weight="bold"), text_color="#3b8ed0").pack(anchor="w", padx=15, pady=(10, 0))
        self.file_info = ctk.CTkLabel(step1, text="No Models Selected", text_color="gray"); self.file_info.pack(anchor="w", padx=15)
        ctk.CTkButton(step1, text="Browse Models (Multi-Select)", command=self.load_model).pack(fill="x", padx=15, pady=5)
        self.tex_info = ctk.CTkLabel(step1, text="No .textures Selected", text_color="gray"); self.tex_info.pack(anchor="w", padx=15)
        ctk.CTkButton(step1, text="Browse .textures", fg_color="#555", command=self.load_textures_file).pack(fill="x", padx=15, pady=(5, 15))
        
        # 2. JSON TWEAKS & TOOLS
        step2 = ctk.CTkFrame(self.main_container); step2.pack(fill="x", padx=30, pady=10)
        ctk.CTkLabel(step2, text="2. JSON TWEAKS & TOOLS (BATCH APPLY)", font=ctk.CTkFont(size=12, weight="bold"), text_color="#e67e22").pack(anchor="w", padx=15, pady=(10, 0))
        
        self.scroll_frame = ctk.CTkScrollableFrame(step2, label_text="Identify & Select Texture Layers (From 1st File)", height=150); self.scroll_frame.pack(fill="x", padx=15, pady=5)
        
        # Tints & Emissive Grid
        tweaks_grid = ctk.CTkFrame(step2, fg_color="transparent"); tweaks_grid.pack(fill="x", padx=15, pady=5)
        
        # Row 1: Tints
        tint_btns = ctk.CTkFrame(tweaks_grid, fg_color="transparent"); tint_btns.pack(fill="x", pady=2)
        ctk.CTkButton(tint_btns, text="Apply Sel. Tints", fg_color="#e67e22", command=self.apply_tint_only).pack(side="left", expand=True, fill="x", padx=2)
        ctk.CTkButton(tint_btns, text="Remove All Tints", fg_color="#c0392b", command=self.remove_tints).pack(side="left", expand=True, fill="x", padx=2)
        
        # Row 2: Emissive Controls
        emissive_ctrl = ctk.CTkFrame(tweaks_grid, fg_color="#2c3e50"); emissive_ctrl.pack(fill="x", pady=5, padx=2)
        ctk.CTkLabel(emissive_ctrl, text="Glow Intensity (0-15):", font=ctk.CTkFont(size=11, weight="bold")).pack(side="left", padx=10)
        self.emissive_slider = ctk.CTkSlider(emissive_ctrl, from_=0, to=15, number_of_steps=15); self.emissive_slider.set(15)
        self.emissive_slider.pack(side="left", fill="x", expand=True, padx=10)
        
        # Row 3: Emissive Buttons
        emissive_btns = ctk.CTkFrame(tweaks_grid, fg_color="transparent"); emissive_btns.pack(fill="x", pady=2)
        ctk.CTkButton(emissive_btns, text="Apply Sel. Glow", fg_color="#f1c40f", text_color="black", command=self.apply_emissive_only).pack(side="left", expand=True, fill="x", padx=2)
        ctk.CTkButton(emissive_btns, text="Remove All Glow", fg_color="#7f8c8d", command=self.remove_emissive).pack(side="left", expand=True, fill="x", padx=2)
        
        # --- CUSTOM SCALING & SNIPPETS ---
        scale_tool_frame = ctk.CTkFrame(step2, fg_color="#2c3e50"); scale_tool_frame.pack(fill="x", padx=15, pady=10)
        ctk.CTkLabel(scale_tool_frame, text="Display / Scale Tool", font=ctk.CTkFont(size=11, weight="bold")).pack(pady=2)
        
        input_row = ctk.CTkFrame(scale_tool_frame, fg_color="transparent"); input_row.pack(fill="x", padx=10, pady=5)
        self.scale_mode_dropdown = ctk.CTkOptionMenu(input_row, values=DISPLAY_MODES, width=120); self.scale_mode_dropdown.set("gui"); self.scale_mode_dropdown.pack(side="left", padx=2)
        self.custom_scale_input = ctk.CTkEntry(input_row, placeholder_text="Value (e.g. 0.35)", width=100); self.custom_scale_input.pack(side="left", padx=2, expand=True, fill="x")
        
        ctk.CTkButton(scale_tool_frame, text="Apply Custom Scale", fg_color="#16a085", command=self.apply_custom_scale).pack(fill="x", padx=10, pady=2)
        ctk.CTkButton(scale_tool_frame, text="Open Manual Display Editor", fg_color="#8e44ad", command=self.open_manual_editor).pack(fill="x", padx=10, pady=(2, 2))
        
        snip_btns = ctk.CTkFrame(scale_tool_frame, fg_color="transparent"); snip_btns.pack(fill="x", padx=5, pady=5)
        ctk.CTkButton(snip_btns, text="Set MC Default GUI", fg_color="#7289da", width=140, command=self.set_minecraft_default_gui).pack(side="left", padx=5)
        ctk.CTkButton(snip_btns, text="Import Snippet", fg_color="#95a5a6", width=140, command=self.open_snippet_window).pack(side="left", padx=5)

        # Preset Scaling Buttons
        scale_frame = ctk.CTkFrame(step2, fg_color="transparent"); scale_frame.pack(fill="x", padx=15, pady=5)
        ctk.CTkButton(scale_frame, text="Set GUI (0.35)", fg_color="#3498db", command=lambda: self.set_display_scale("gui", 0.35)).pack(side="left", expand=True, fill="x", padx=2)
        ctk.CTkButton(scale_frame, text="Set Dropped (0.25)", fg_color="#2980b9", command=lambda: self.set_display_scale("ground", 0.25)).pack(side="left", expand=True, fill="x", padx=2)

        # --- THE ROTATION FIXER ---
        tools_frame = ctk.CTkFrame(step2, fg_color="transparent"); tools_frame.pack(fill="x", padx=15, pady=(5, 10))
        ctk.CTkButton(tools_frame, text="Fix All Illegal Rotations", fg_color="#d35400", command=self.fix_illegal_angles).pack(fill="x", padx=2, pady=2)

        # 3. COMMAND GENERATOR
        step3 = ctk.CTkFrame(self.main_container); step3.pack(fill="x", padx=30, pady=10)
        ctk.CTkLabel(step3, text="3. COMMAND GENERATOR", font=ctk.CTkFont(size=12, weight="bold"), text_color="#9b59b6").pack(anchor="w", padx=15, pady=(10, 5))
        for i in range(4):
            entry = ctk.CTkEntry(step3, placeholder_text=f"Command {i+1}"); entry.pack(fill="x", padx=15, pady=2); self.cmd_entries.append(entry)
        self.cmd_output = ctk.CTkTextbox(step3, height=120, font=ctk.CTkFont(family="Consolas", size=11)); self.cmd_output.pack(fill="x", padx=15, pady=10)
        cmd_btn_frame = ctk.CTkFrame(step3, fg_color="transparent"); cmd_btn_frame.pack(fill="x", padx=15, pady=(0, 15))
        ctk.CTkButton(cmd_btn_frame, text="Generate View", fg_color="#8e44ad", command=self.generate_wood_commands).pack(side="left", expand=True, fill="x", padx=2)
        ctk.CTkButton(cmd_btn_frame, text="Save to File", fg_color="#2980b9", command=self.save_commands_to_file).pack(side="left", expand=True, fill="x", padx=2)

        # 4. OUTPUT & RUN
        step4 = ctk.CTkFrame(self.main_container); step4.pack(fill="x", padx=30, pady=10)
        self.dest_label = ctk.CTkLabel(step4, text="No folder selected", text_color="gray"); self.dest_label.pack(anchor="w", padx=15)
        ctk.CTkButton(step4, text="Select Output Folder", fg_color="#555", command=self.browse_output_folder).pack(fill="x", padx=15, pady=5)
        ctk.CTkButton(self.main_container, text="Generate Wood Files", fg_color="#2ecc71", height=50, font=ctk.CTkFont(weight="bold"), command=self.process_variants).pack(fill="x", padx=30, pady=20)

    # --- TOOLS ---
    def open_snippet_window(self):
        if not self.source_paths:
            messagebox.showwarning("Selection", "Please select master models first.")
            return
        if self.snippet_window is None or not self.snippet_window.winfo_exists():
            self.snippet_window = SnippetPreviewer(self)
        else:
            self.snippet_window.focus()

    def open_manual_editor(self):
        if not self.source_paths:
            messagebox.showwarning("Selection", "Please select master models first.")
            return
        if self.manual_editor is None or not self.manual_editor.winfo_exists():
            self.manual_editor = ManualDisplayEditor(self)
        else:
            self.manual_editor.focus()
            self.manual_editor.load_values_from_first_model()

    def set_minecraft_default_gui(self):
        if not self.source_paths: return
        count = 0
        try:
            for path in self.source_paths:
                if not path.endswith(".json"): continue
                with open(path, 'r') as f: data = json.load(f)
                if "display" not in data: data["display"] = {}
                data["display"]["gui"] = {"rotation": [30, -135, 0], "translation": [0, 0, 0], "scale": [0.625, 0.625, 0.625]}
                with open(path, 'w') as f: json.dump(data, f, indent=4)
                count += 1
            messagebox.showinfo("Success", f"Applied Minecraft Default GUI settings to {count} files.")
        except Exception as e: messagebox.showerror("Error", str(e))

    def apply_custom_scale(self):
        val_str = self.custom_scale_input.get().strip()
        mode = self.scale_mode_dropdown.get()
        try:
            val = float(val_str)
            self.set_display_scale(mode, val)
        except ValueError:
            messagebox.showerror("Error", "Please enter a valid number (e.g., 0.35)")

    def set_display_scale(self, display_key, scale_val):
        if not self.source_paths: return
        count = 0
        try:
            for path in self.source_paths:
                if not path.endswith(".json"): continue
                with open(path, 'r') as f: data = json.load(f)
                if "display" not in data: data["display"] = {}
                if display_key not in data["display"]: data["display"][display_key] = {}
                data["display"][display_key]["scale"] = [scale_val, scale_val, scale_val]
                with open(path, 'w') as f: json.dump(data, f, indent=4)
                count += 1
            messagebox.showinfo("Success", f"Updated {display_key} scale for {count} files.")
        except Exception as e: messagebox.showerror("Error", str(e))

    def fix_illegal_angles(self):
        if not self.source_paths: return
        try:
            total_changed = 0
            file_count = 0
            for path in self.source_paths:
                if not path.endswith(".json"): continue
                with open(path, 'r') as f: data = json.load(f)
                changed = 0
                for e in data.get("elements", []):
                    if "rotation" in e:
                        angle = e["rotation"].get("angle", 0)
                        if angle not in LEGAL_ANGLES:
                            e["rotation"]["angle"] = min(LEGAL_ANGLES, key=lambda x:abs(x-angle))
                            changed += 1
                if changed > 0:
                    with open(path, 'w') as f: json.dump(data, f, indent=4)
                    total_changed += changed
                    file_count += 1
            messagebox.showinfo("Success", f"Fixed {total_changed} angles in {file_count} files.")
        except Exception as e: messagebox.showerror("Error", str(e))

    # --- GLOW / EMISSIVE TOOLS ---
    def apply_emissive_only(self):
        if not self.source_paths: return
        try:
            sel = [str(tid) for tid, var in self.texture_vars.items() if var.get()]
            intensity = int(self.emissive_slider.get())
            count = 0
            for path in self.source_paths:
                if not path.endswith(".json"): continue
                with open(path, 'r') as f: data = json.load(f)
                changed = False
                for e in data.get("elements", []):
                    for f in e.get("faces", {}).values():
                        if str(f.get("texture", "")).replace("#", "") in sel:
                            f["light"] = intensity
                            f["shade"] = False 
                            changed = True
                if changed:
                    with open(path, 'w') as f: json.dump(data, f, indent=4)
                    count += 1
            self.refresh_model_ui()
            messagebox.showinfo("Success", f"Applied glow effect (Light: {intensity}, Shade: False) to {count} files.")
        except Exception as e: messagebox.showerror("Error", str(e))

    def remove_emissive(self):
        if not self.source_paths: return
        try:
            count = 0
            for path in self.source_paths:
                if not path.endswith(".json"): continue
                with open(path, 'r') as f: data = json.load(f)
                changed = False
                for e in data.get("elements", []):
                    for f in e.get("faces", {}).values():
                        if "light" in f or "shade" in f:
                            if "light" in f: del f["light"]
                            if "shade" in f: del f["shade"] 
                            changed = True
                if changed:
                    with open(path, 'w') as f: json.dump(data, f, indent=4)
                    count += 1
            self.refresh_model_ui()
            messagebox.showinfo("Success", f"Removed glow effects from {count} files.")
        except Exception as e: messagebox.showerror("Error", str(e))

    def apply_tint_only(self):
        if not self.source_paths: return
        try:
            sel = [str(tid) for tid, var in self.texture_vars.items() if var.get()]
            count = 0
            for path in self.source_paths:
                if not path.endswith(".json"): continue
                with open(path, 'r') as f: data = json.load(f)
                for e in data.get("elements", []):
                    for f in e.get("faces", {}).values():
                        if str(f.get("texture", "")).replace("#", "") in sel: f["tintindex"] = 0
                with open(path, 'w') as f: json.dump(data, f, indent=4)
                count += 1
            self.refresh_model_ui()
            messagebox.showinfo("Success", f"Tints applied to {count} files.")
        except Exception as e: messagebox.showerror("Error", str(e))

    def remove_tints(self):
        if not self.source_paths: return
        try:
            count = 0
            for path in self.source_paths:
                if not path.endswith(".json"): continue
                with open(path, 'r') as f: data = json.load(f)
                has_tints = False
                for e in data.get("elements", []):
                    for f in e.get("faces", {}).values():
                        if "tintindex" in f:
                            del f["tintindex"]
                            has_tints = True
                if has_tints:
                    with open(path, 'w') as f: json.dump(data, f, indent=4)
                    count += 1
            self.refresh_model_ui()
            messagebox.showinfo("Success", f"Removed tints from {count} files.")
        except Exception as e: messagebox.showerror("Error", str(e))

    # --- CORE FUNCTIONS ---
    def load_model(self):
        paths = filedialog.askopenfilenames(filetypes=[("Model Files", "*.json *.java")])
        if paths:
            self.source_paths = paths
            if len(paths) == 1:
                self.file_info.configure(text=os.path.basename(paths[0]), text_color="#3b8ed0")
            else:
                self.file_info.configure(text=f"{len(paths)} files selected", text_color="#3b8ed0")
            self.refresh_model_ui()

    def refresh_model_ui(self):
        if not self.source_paths: return
        for w in self.scroll_frame.winfo_children(): w.destroy()
        self.texture_vars = {} 
        primary_path = self.source_paths[0]
        if primary_path.endswith(".json"):
            try:
                with open(primary_path, 'r') as f: data = json.load(f)
                textures = data.get("textures", {})
                emissive_data = {} 
                tint_data = set()  
                for e in data.get("elements", []):
                    for f_data in e.get("faces", {}).values():
                        tid = str(f_data.get("texture", "")).replace("#", "")
                        if not tid: continue
                        if "light" in f_data: emissive_data[tid] = f_data["light"]
                        if "tintindex" in f_data: tint_data.add(tid)
                for tid, tpath in textures.items():
                    is_emissive = tid in emissive_data
                    is_tinted = tid in tint_data
                    var = ctk.BooleanVar(value=(is_emissive or is_tinted))
                    self.texture_vars[tid] = var
                    label_text = f"{tid}: {tpath.split('/')[-1]}"
                    if is_emissive: label_text += f" [Light: {emissive_data[tid]}]"
                    if is_tinted: label_text += " [Tinted]"
                    ctk.CTkCheckBox(self.scroll_frame, text=label_text, variable=var).pack(anchor="w", padx=10, pady=2)
            except Exception as e: print(f"Error refreshing UI: {e}")

    def load_textures_file(self):
        path = filedialog.askopenfilename(filetypes=[("MCreator Textures", "*.textures")])
        if path: self.textures_path = path; self.tex_info.configure(text=os.path.basename(path), text_color="#9b59b6")

    def deep_swap(self, content, target_wood):
        for wood in WOODS:
            if wood in content: content = content.replace(wood, target_wood)
        if target_wood == "bamboo":
            content = content.replace("bamboo_log", "bamboo_block")
            content = content.replace("stripped_bamboo_log", "stripped_bamboo_block")
        return content

    def process_variants(self):
        if not self.source_paths or not self.output_dir: 
            messagebox.showerror("Error", "Setup incomplete!")
            return
        success_count = 0
        try:
            for path in self.source_paths:
                orig_filename = os.path.splitext(os.path.basename(path))[0]
                detected_wood = next((w for w in WOODS if w in orig_filename.lower()), "")
                with open(path, 'r', encoding='utf-8') as f: master_model = f.read()
                master_tex = ""
                if self.textures_path: 
                    with open(self.textures_path, 'r', encoding='utf-8') as f: master_tex = f.read()
                for target_wood in WOODS:
                    new_fn = orig_filename.replace(detected_wood, target_wood) if detected_wood else f"{orig_filename}_{target_wood}"
                    ext = os.path.splitext(path)[1]
                    f_model = self.deep_swap(master_model.replace(orig_filename, new_fn), target_wood)
                    with open(os.path.join(self.output_dir, f"{new_fn}{ext}"), 'w', encoding='utf-8') as f: f.write(f_model)
                    if master_tex:
                        f_tex = self.deep_swap(master_tex.replace(orig_filename, new_fn), target_wood)
                        with open(os.path.join(self.output_dir, f"{new_fn}{ext}.textures"), 'w', encoding='utf-8') as f: f.write(f_tex)
                success_count += 1
            messagebox.showinfo("Success", f"Processed {success_count} files (9 variants each)!")
            os.startfile(self.output_dir)
        except Exception as e: messagebox.showerror("Error", str(e))

    def generate_wood_commands(self):
        self.cmd_output.delete("1.0", "end"); res = []
        for w in WOODS:
            res.append(f"--- {w.upper()} ---")
            for entry in self.cmd_entries:
                if entry.get().strip(): res.append(self.deep_swap(entry.get().strip(), w))
            res.append("")
        self.cmd_output.insert("1.0", "\n".join(res))

    def browse_output_folder(self):
        path = filedialog.askdirectory()
        if path: self.output_dir = path; self.dest_label.configure(text=path, text_color="#2ecc71")

    def save_commands_to_file(self):
        fp = filedialog.asksaveasfilename(defaultextension=".txt", filetypes=[("Text", "*.txt"), ("JSON", "*.json")])
        if fp: open(fp, 'w').write(self.cmd_output.get("1.0", "end")); messagebox.showinfo("Success", "Saved!")

if __name__ == "__main__":
    app = ModelProcessorApp(); app.mainloop()