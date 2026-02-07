import os
import json
import math
import customtkinter as ctk
from tkinter import filedialog, messagebox, ttk

# --- CONFIG & CONSTANTS ---
WOODS = ["dark_oak", "oak", "spruce", "birch", "jungle", "acacia", "mangrove", "cherry", "bamboo", "crimson", "warped"]
LEGAL_ANGLES = [-45, -22.5, 0, 22.5, 45]
DISPLAY_MODES = [
    "gui", 
    "thirdperson_righthand", 
    "thirdperson_lefthand", 
    "firstperson_righthand", 
    "firstperson_lefthand", 
    "ground", 
    "fixed", 
    "head"
]

class MasterViewport(ctk.CTkFrame):
    """Real-time 3D Wireframe Viewport for Hand/GUI/Third-Person Preview"""
    def __init__(self, master, **kwargs):
        super().__init__(master, **kwargs)
        self.canvas = ctk.CTkCanvas(self, bg="#0f0f0f", highlightthickness=0)
        self.canvas.pack(fill="both", expand=True)
        self.display_settings = {"rotation": [0, 0, 0], "translation": [0, 0, 0], "scale": [1, 1, 1]}
        self.bind("<Configure>", lambda e: self.render())

    def update_view(self, rot, trans, scale):
        self.display_settings = {"rotation": rot, "translation": trans, "scale": scale}
        self.render()

    def render(self):
        self.canvas.delete("all")
        w, h = self.canvas.winfo_width(), self.canvas.winfo_height()
        if w < 10: return
        cx, cy = w/2, h/2
        
        rot = self.display_settings["rotation"]
        trans = self.display_settings["translation"]
        scale = self.display_settings["scale"]

        verts = [[x, y, z] for x in [-0.5, 0.5] for y in [-0.5, 0.5] for z in [-0.5, 0.5]]
        
        def project(v):
            x, y, z = v[0]*scale[0], v[1]*scale[1], v[2]*scale[2]
            rx, ry, rz = [math.radians(a) for a in rot]
            y, z = y*math.cos(rx)-z*math.sin(rx), y*math.sin(rx)+z*math.cos(rx)
            x, z = x*math.cos(ry)+z*math.sin(ry), -x*math.sin(ry)+z*math.cos(ry)
            x, y = x*math.cos(rz)-y*math.sin(rz), x*math.sin(rz)+y*math.cos(rz)
            zoom = 180
            tx, ty = trans[0]/16, trans[1]/16
            return cx + (x + tx)*zoom, cy - (y + ty)*zoom

        try:
            p = [project(v) for v in verts]
            edges = [(0,1), (0,2), (1,3), (2,3), (4,5), (4,6), (5,7), (6,7), (0,4), (1,5), (2,6), (3,7)]
            for e in edges:
                thickness = 2 if any(m in str(self.display_settings) for m in ["gui", "third"]) else 1
                self.canvas.create_line(p[e[0]], p[e[1]], fill="#00ffcc", width=thickness)
            self.canvas.create_line(cx-5, cy, cx+5, cy, fill="red")
            self.canvas.create_line(cx, cy-5, cx, cy+5, fill="red")
        except: pass

class ModelStudioProV60(ctk.CTk):
    def __init__(self):
        super().__init__()
        self.title("MODEL STUDIO PRO [v60 MASTER MULTI-SELECT]")
        self.geometry("1200x950")
        ctk.set_appearance_mode("dark")
        
        self.source_files = [] 
        self.textures_path = ""
        self.output_dir = ""
        self.texture_vars = {}
        self.cmd_entries = []
        
        # Injection Toggles
        self.inject_toggles = {
            "rotation": ctk.BooleanVar(value=True),
            "translation": ctk.BooleanVar(value=True),
            "scale": ctk.BooleanVar(value=True)
        }
        
        self._setup_ui()

    def _setup_ui(self):
        self.grid_columnconfigure(0, weight=1) 
        self.grid_columnconfigure(1, weight=1) 
        self.grid_rowconfigure(0, weight=1)

        left_panel = ctk.CTkScrollableFrame(self, label_text="ASSET CONTROLS")
        left_panel.grid(row=0, column=0, sticky="nsew", padx=10, pady=10)

        # 1. Selection
        s1 = ctk.CTkFrame(left_panel); s1.pack(fill="x", padx=10, pady=5)
        ctk.CTkLabel(s1, text="1. MASTER INPUT (MULTI-SELECT)", font=ctk.CTkFont(weight="bold")).pack(pady=5)
        self.file_info = ctk.CTkLabel(s1, text="0 Models Selected", text_color="gray"); self.file_info.pack()
        ctk.CTkButton(s1, text="Load Models (.json)", command=self.load_models).pack(fill="x", padx=10, pady=2)
        self.tex_info = ctk.CTkLabel(s1, text="No .textures Selected", text_color="gray"); self.tex_info.pack()
        ctk.CTkButton(s1, text="Load .textures", fg_color="#34495e", command=self.load_textures_file).pack(fill="x", padx=10, pady=5)

        # 2. Display Editor
        s2 = ctk.CTkFrame(left_panel); s2.pack(fill="x", padx=10, pady=5)
        ctk.CTkLabel(s2, text="2. POSITION & VIEW EDITOR", font=ctk.CTkFont(weight="bold")).pack(pady=5)
        self.mode_var = ctk.StringVar(value="gui")
        ctk.CTkOptionMenu(s2, values=DISPLAY_MODES, variable=self.mode_var, command=self.sync_display_inputs).pack(pady=5)

        self.inputs = {}
        for attr in ["rotation", "translation", "scale"]:
            f = ctk.CTkFrame(s2, fg_color="transparent"); f.pack(fill="x", pady=2)
            
            # Added Injection Checkbox
            ctk.CTkCheckBox(f, text=attr.capitalize(), variable=self.inject_toggles[attr], font=ctk.CTkFont(size=11), width=100).pack(side="left", padx=5)
            
            self.inputs[attr] = []
            for i in range(3):
                e = ctk.CTkEntry(f, width=60)
                e.insert(0, "1" if attr == "scale" else "0")
                e.pack(side="left", padx=2)
                e.bind("<KeyRelease>", lambda e: self.update_preview())
                self.inputs[attr].append(e)

        ctk.CTkButton(s2, text="Inject Checked Data to All Loaded", fg_color="#16a085", command=self.save_display_to_all).pack(fill="x", padx=10, pady=10)

        # 3. Tint Manager
        s3 = ctk.CTkFrame(left_panel); s3.pack(fill="x", padx=10, pady=5)
        ctk.CTkLabel(s3, text="3. TINT MANAGEMENT", font=ctk.CTkFont(weight="bold")).pack(pady=5)
        self.tint_scroll = ctk.CTkScrollableFrame(s3, height=120); self.tint_scroll.pack(fill="x", padx=5, pady=5)
        ctk.CTkButton(s3, text="Apply Tintindex:0 to All", fg_color="#e67e22", command=self.apply_tint_to_all).pack(fill="x", padx=10, pady=2)
        ctk.CTkButton(s3, text="Fix Non-Standard Angles", fg_color="#d35400", command=self.fix_illegal_angles_all).pack(fill="x", padx=10, pady=5)

        # 4. Command Generator
        s4 = ctk.CTkFrame(left_panel); s4.pack(fill="x", padx=10, pady=5)
        ctk.CTkLabel(s4, text="4. COMMAND GENERATOR", font=ctk.CTkFont(weight="bold")).pack(pady=5)
        for i in range(3):
            ent = ctk.CTkEntry(s4, placeholder_text=f"Command Template {i+1} (use 'oak')..."); ent.pack(fill="x", padx=10, pady=1)
            self.cmd_entries.append(ent)
        self.cmd_output = ctk.CTkTextbox(s4, height=100, font=("Consolas", 11)); self.cmd_output.pack(fill="x", padx=10, pady=5)
        ctk.CTkButton(s4, text="Generate Commands", command=self.generate_commands).pack(fill="x", padx=10, pady=5)

        # --- RIGHT PANEL: PREVIEW ---
        right_panel = ctk.CTkFrame(self)
        right_panel.grid(row=0, column=1, sticky="nsew", padx=10, pady=10)

        ctk.CTkLabel(right_panel, text="3D WIREFRAME VIEWPORT", font=ctk.CTkFont(size=16, weight="bold")).pack(pady=5)
        self.viewport = MasterViewport(right_panel)
        self.viewport.pack(fill="both", expand=True, padx=10, pady=10)

        export_frame = ctk.CTkFrame(right_panel)
        export_frame.pack(fill="x", side="bottom", padx=10, pady=10)
        
        self.dest_label = ctk.CTkLabel(export_frame, text="No Output Folder", text_color="gray"); self.dest_label.pack()
        ctk.CTkButton(export_frame, text="Select Output Folder", command=self.browse_output).pack(fill="x", padx=10, pady=2)
        ctk.CTkButton(export_frame, text="GENERATE ALL WOOD VARIANTS", fg_color="#2ecc71", height=50, font=ctk.CTkFont(weight="bold"), command=self.process_variants).pack(fill="x", padx=10, pady=10)

    # --- LOGIC ---
    def load_models(self):
        paths = filedialog.askopenfilenames(filetypes=[("Minecraft Model", "*.json")])
        if paths:
            self.source_files = list(paths)
            self.file_info.configure(text=f"{len(self.source_files)} Models Selected", text_color="#3b8ed0")
            self.sync_display_inputs()
            self.refresh_tint_list()

    def load_textures_file(self):
        path = filedialog.askopenfilename(filetypes=[("MCreator Textures", "*.textures")])
        if path:
            self.textures_path = path
            self.tex_info.configure(text=os.path.basename(path), text_color="#9b59b6")

    def sync_display_inputs(self, *args):
        if not self.source_files: return
        try:
            with open(self.source_files[0], 'r') as f: data = json.load(f)
            mode = self.mode_var.get()
            display = data.get("display", {}).get(mode, {})
            
            for attr in ["rotation", "translation", "scale"]:
                default = [1.0, 1.0, 1.0] if attr == "scale" else [0.0, 0.0, 0.0]
                vals = display.get(attr, default)
                for i in range(3):
                    self.inputs[attr][i].delete(0, 'end')
                    self.inputs[attr][i].insert(0, str(vals[i]))
            self.update_preview()
        except: pass

    def update_preview(self, *args):
        try:
            rot = [float(e.get()) for e in self.inputs["rotation"]]
            trans = [float(e.get()) for e in self.inputs["translation"]]
            scale = [float(e.get()) for e in self.inputs["scale"]]
            self.viewport.update_view(rot, trans, scale)
        except: pass

    def save_display_to_all(self):
        if not self.source_files: return
        try:
            mode = self.mode_var.get()
            updated_count = 0
            
            for path in self.source_files:
                with open(path, 'r') as f: data = json.load(f)
                if "display" not in data: data["display"] = {}
                if mode not in data["display"]: data["display"][mode] = {}
                
                # Only inject checked categories
                for attr in ["rotation", "translation", "scale"]:
                    if self.inject_toggles[attr].get():
                        data["display"][mode][attr] = [float(e.get()) for e in self.inputs[attr]]
                
                with open(path, 'w') as f: json.dump(data, f, indent=4)
                updated_count += 1
            
            messagebox.showinfo("Success", f"Updated selected fields for {mode} in {updated_count} files.")
        except Exception as e: messagebox.showerror("Error", str(e))

    def refresh_tint_list(self):
        for w in self.tint_scroll.winfo_children(): w.destroy()
        self.texture_vars = {}
        if not self.source_files: return
        try:
            with open(self.source_files[0], 'r') as f:
                data = json.load(f)
                textures = data.get("textures", {})
                for tid, tpath in textures.items():
                    var = ctk.BooleanVar(value=False); self.texture_vars[tid] = var
                    ctk.CTkCheckBox(self.tint_scroll, text=f"{tid}: {tpath.split('/')[-1]}", variable=var).pack(anchor="w", padx=5)
        except: pass

    def apply_tint_to_all(self):
        if not self.source_files: return
        selected_ids = [tid for tid, var in self.texture_vars.items() if var.get()]
        if not selected_ids: return
        
        for path in self.source_files:
            with open(path, 'r') as f: data = json.load(f)
            for e in data.get("elements", []):
                for face in e.get("faces", {}).values():
                    tex = str(face.get("texture", "")).replace("#", "")
                    if tex in selected_ids: face["tintindex"] = 0
            with open(path, 'w') as f: json.dump(data, f, indent=4)
            
        messagebox.showinfo("v60", f"Tints applied to {len(self.source_files)} models.")

    def fix_illegal_angles_all(self):
        if not self.source_files: return
        total_fixed = 0
        for path in self.source_files:
            with open(path, 'r') as f: data = json.load(f)
            for e in data.get("elements", []):
                if "rotation" in e:
                    angle = e["rotation"].get("angle", 0)
                    if angle not in LEGAL_ANGLES:
                        e["rotation"]["angle"] = min(LEGAL_ANGLES, key=lambda x:abs(x-angle))
                        total_fixed += 1
            with open(path, 'w') as f: json.dump(data, f, indent=4)
        messagebox.showinfo("v60", f"Fixed {total_fixed} angles across all models.")

    def generate_commands(self):
        self.cmd_output.delete("1.0", "end")
        results = []
        for w in WOODS:
            results.append(f"--- {w.upper()} ---")
            for entry in self.cmd_entries:
                val = entry.get().strip()
                if val: results.append(self.deep_swap(val, w))
            results.append("")
        self.cmd_output.insert("1.0", "\n".join(results))

    def deep_swap(self, content, target_wood):
        for wood in WOODS:
            if wood in content: content = content.replace(wood, target_wood)
        if target_wood == "bamboo":
            content = content.replace("bamboo_log", "bamboo_block").replace("stripped_bamboo_log", "stripped_bamboo_block")
        return content

    def browse_output(self):
        path = filedialog.askdirectory()
        if path: 
            self.output_dir = path
            self.dest_label.configure(text=path, text_color="#2ecc71")

    def process_variants(self):
        if not self.source_files or not self.output_dir: 
            messagebox.showwarning("Warning", "Load models and select an output folder first.")
            return
            
        master_tex = ""
        if self.textures_path:
            with open(self.textures_path, 'r', encoding='utf-8') as f: master_tex = f.read()

        for source_path in self.source_files:
            orig_fn = os.path.splitext(os.path.basename(source_path))[0]
            detected = next((w for w in WOODS if w in orig_fn.lower()), "")
            
            with open(source_path, 'r', encoding='utf-8') as f: master_json = f.read()
            
            for target in WOODS:
                new_fn = orig_fn.replace(detected, target) if detected else f"{target}_{orig_fn}"
                
                # Process Model JSON
                f_model = self.deep_swap(master_json.replace(orig_fn, new_fn), target)
                with open(os.path.join(self.output_dir, f"{new_fn}.json"), 'w', encoding='utf-8') as f: 
                    f.write(f_model)
                
                if master_tex:
                    f_tex = self.deep_swap(master_tex.replace(orig_fn, new_fn), target)
                    with open(os.path.join(self.output_dir, f"{new_fn}.json.textures"), 'w', encoding='utf-8') as f: 
                        f.write(f_tex)
        
        messagebox.showinfo("Complete", f"Generated variants for {len(self.source_files)} models in {len(WOODS)} wood types.")
        os.startfile(self.output_dir)

if __name__ == "__main__":
    app = ModelStudioProV60()
    app.mainloop()