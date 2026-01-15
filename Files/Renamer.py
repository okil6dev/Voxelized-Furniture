import os
import json
import random
import customtkinter as ctk
from tkinter import filedialog, messagebox
from PIL import Image

# --- CONFIG ---
WOODS = ["dark_oak", "oak", "spruce", "birch", "jungle", "acacia", "mangrove", "cherry", "bamboo"]
LEGAL_ANGLES = [-45, -22.5, 0, 22.5, 45]
DISPLAY_MODES = ["gui", "ground", "fixed", "thirdperson_righthand", "firstperson_righthand"]

class ModelProcessorApp(ctk.CTk):
    def __init__(self):
        super().__init__()
        self.title("Model Studio Pro - v58.0")
        self.geometry("650x1100")
        ctk.set_appearance_mode("dark") 
        
        self.main_container = ctk.CTkScrollableFrame(self, fg_color="transparent")
        self.main_container.pack(fill="both", expand=True)

        self.source_path = ""
        self.textures_path = ""
        self.output_dir = ""
        self.texture_vars = {}
        self.cmd_entries = []

        self.setup_ui()

    def setup_ui(self):
        header = ctk.CTkFrame(self.main_container, fg_color="transparent")
        header.pack(fill="x", padx=30, pady=(20, 10))
        ctk.CTkLabel(header, text="MCreator Wood Suite", font=ctk.CTkFont(size=22, weight="bold")).pack(side="left")

        # 1. LOAD FILES
        step1 = ctk.CTkFrame(self.main_container); step1.pack(fill="x", padx=30, pady=10)
        ctk.CTkLabel(step1, text="1. SELECT MASTER MODEL", font=ctk.CTkFont(size=12, weight="bold"), text_color="#3b8ed0").pack(anchor="w", padx=15, pady=(10, 0))
        self.file_info = ctk.CTkLabel(step1, text="No Model Selected", text_color="gray"); self.file_info.pack(anchor="w", padx=15)
        ctk.CTkButton(step1, text="Browse Model", command=self.load_model).pack(fill="x", padx=15, pady=5)
        self.tex_info = ctk.CTkLabel(step1, text="No .textures Selected", text_color="gray"); self.tex_info.pack(anchor="w", padx=15)
        ctk.CTkButton(step1, text="Browse .textures", fg_color="#555", command=self.load_textures_file).pack(fill="x", padx=15, pady=(5, 15))
        
        # 2. JSON TWEAKS & TOOLS
        step2 = ctk.CTkFrame(self.main_container); step2.pack(fill="x", padx=30, pady=10)
        ctk.CTkLabel(step2, text="2. JSON TWEAKS & TOOLS", font=ctk.CTkFont(size=12, weight="bold"), text_color="#e67e22").pack(anchor="w", padx=15, pady=(10, 0))
        
        self.scroll_frame = ctk.CTkScrollableFrame(step2, label_text="Identify & Select Tint Layers", height=120); self.scroll_frame.pack(fill="x", padx=15, pady=5)
        
        tweaks_grid = ctk.CTkFrame(step2, fg_color="transparent"); tweaks_grid.pack(fill="x", padx=15, pady=5)
        ctk.CTkButton(tweaks_grid, text="Apply Sel. Tints", fg_color="#e67e22", command=self.apply_tint_only).pack(fill="x", pady=2)
        ctk.CTkButton(tweaks_grid, text="Remove All Tints", fg_color="#c0392b", command=self.remove_tints).pack(fill="x", pady=2)
        
        # --- CUSTOM SCALING FEATURE ---
        scale_tool_frame = ctk.CTkFrame(step2, fg_color="#2c3e50"); scale_tool_frame.pack(fill="x", padx=15, pady=10)
        ctk.CTkLabel(scale_tool_frame, text="Custom Scale Tool", font=ctk.CTkFont(size=11, weight="bold")).pack(pady=2)
        
        input_row = ctk.CTkFrame(scale_tool_frame, fg_color="transparent"); input_row.pack(fill="x", padx=10, pady=5)
        self.scale_mode_dropdown = ctk.CTkOptionMenu(input_row, values=DISPLAY_MODES, width=120); self.scale_mode_dropdown.set("gui"); self.scale_mode_dropdown.pack(side="left", padx=2)
        self.custom_scale_input = ctk.CTkEntry(input_row, placeholder_text="Value (e.g. 0.35)", width=100); self.custom_scale_input.pack(side="left", padx=2, expand=True, fill="x")
        
        ctk.CTkButton(scale_tool_frame, text="Apply Custom Scale", fg_color="#16a085", command=self.apply_custom_scale).pack(fill="x", padx=10, pady=(0, 10))

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
    def apply_custom_scale(self):
        val_str = self.custom_scale_input.get().strip()
        mode = self.scale_mode_dropdown.get()
        try:
            val = float(val_str)
            self.set_display_scale(mode, val)
        except ValueError:
            messagebox.showerror("Error", "Please enter a valid number (e.g., 0.35)")

    def set_display_scale(self, display_key, scale_val):
        if not self.source_path or not self.source_path.endswith(".json"): return
        try:
            with open(self.source_path, 'r') as f:
                data = json.load(f)
            
            if "display" not in data: data["display"] = {}
            if display_key not in data["display"]: data["display"][display_key] = {}
            
            data["display"][display_key]["scale"] = [scale_val, scale_val, scale_val]
            
            with open(self.source_path, 'w') as f:
                json.dump(data, f, indent=4)
            messagebox.showinfo("Success", f"Updated {display_key} scale to {scale_val}.")
        except Exception as e: messagebox.showerror("Error", str(e))

    def fix_illegal_angles(self):
        if not self.source_path or not self.source_path.endswith(".json"): return
        try:
            with open(self.source_path, 'r') as f:
                data = json.load(f)
            changed = 0
            for e in data.get("elements", []):
                if "rotation" in e:
                    angle = e["rotation"].get("angle", 0)
                    if angle not in LEGAL_ANGLES:
                        # Find the closest legal angle from [-45, -22.5, 0, 22.5, 45]
                        e["rotation"]["angle"] = min(LEGAL_ANGLES, key=lambda x:abs(x-angle))
                        changed += 1
            if changed > 0:
                with open(self.source_path, 'w') as f:
                    json.dump(data, f, indent=4)
                messagebox.showinfo("Success", f"Fixed {changed} illegal rotation angles.")
            else:
                messagebox.showinfo("Check", "No illegal rotation angles found.")
        except Exception as e: messagebox.showerror("Error", str(e))

    # --- CORE FUNCTIONS ---
    def load_model(self):
        path = filedialog.askopenfilename(filetypes=[("Model Files", "*.json *.java")])
        if path:
            self.source_path = path; self.file_info.configure(text=os.path.basename(path), text_color="#3b8ed0")
            for w in self.scroll_frame.winfo_children(): w.destroy()
            if path.endswith(".json"):
                with open(path, 'r') as f:
                    data = json.load(f); textures = data.get("textures", {})
                    for tid, tpath in textures.items():
                        var = ctk.BooleanVar(value=False); self.texture_vars[tid] = var
                        ctk.CTkCheckBox(self.scroll_frame, text=f"{tid}: {tpath.split('/')[-1]}", variable=var).pack(anchor="w", padx=10, pady=2)

    def load_textures_file(self):
        path = filedialog.askopenfilename(filetypes=[("MCreator Textures", "*.textures")])
        if path: self.textures_path = path; self.tex_info.configure(text=os.path.basename(path), text_color="#9b59b6")

    def remove_tints(self):
        if not self.source_path or not self.source_path.endswith(".json"): return
        data = json.load(open(self.source_path)); has_tints = False
        for e in data.get("elements", []):
            for f in e.get("faces", {}).values():
                if "tintindex" in f: del f["tintindex"]; has_tints = True
        if has_tints: json.dump(data, open(self.source_path, 'w'), indent=4); messagebox.showinfo("Success", "Tints removed.")

    def apply_tint_only(self):
        if not self.source_path or not self.source_path.endswith(".json"): return
        data = json.load(open(self.source_path)); sel = [str(tid) for tid, var in self.texture_vars.items() if var.get()]
        for e in data.get("elements", []):
            for f in e.get("faces", {}).values():
                if str(f.get("texture", "")).replace("#", "") in sel: f["tintindex"] = 0
        json.dump(data, open(self.source_path, 'w'), indent=4); messagebox.showinfo("Success", "Tints applied.")

    def deep_swap(self, content, target_wood):
        for wood in WOODS:
            if wood in content: content = content.replace(wood, target_wood)
        if target_wood == "bamboo":
            content = content.replace("bamboo_log", "bamboo_block")
            content = content.replace("stripped_bamboo_log", "stripped_bamboo_block")
        return content

    def process_variants(self):
        if not self.source_path or not self.output_dir: return
        orig_filename = os.path.splitext(os.path.basename(self.source_path))[0]
        detected_wood = next((w for w in WOODS if w in orig_filename.lower()), "")
        try:
            with open(self.source_path, 'r', encoding='utf-8') as f: master_model = f.read()
            master_tex = ""
            if self.textures_path:
                with open(self.textures_path, 'r', encoding='utf-8') as f: master_tex = f.read()
            for target_wood in WOODS:
                new_fn = orig_filename.replace(detected_wood, target_wood) if detected_wood else f"{orig_filename}_{target_wood}"
                ext = os.path.splitext(self.source_path)[1]
                f_model = self.deep_swap(master_model.replace(orig_filename, new_fn), target_wood)
                with open(os.path.join(self.output_dir, f"{new_fn}{ext}"), 'w', encoding='utf-8') as f: f.write(f_model)
                if master_tex:
                    f_tex = self.deep_swap(master_tex.replace(orig_filename, new_fn), target_wood)
                    with open(os.path.join(self.output_dir, f"{new_fn}{ext}.textures"), 'w', encoding='utf-8') as f: f.write(f_tex)
            messagebox.showinfo("Success", "Wood variants generated!"); os.startfile(self.output_dir)
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