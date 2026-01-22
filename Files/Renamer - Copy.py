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
        self.title("Model Studio Pro - v60.0 (Batch Processing)")
        self.geometry("650x1150")
        ctk.set_appearance_mode("dark") 
        
        self.main_container = ctk.CTkScrollableFrame(self, fg_color="transparent")
        self.main_container.pack(fill="both", expand=True)

        self.source_paths = [] # Changed to list to support multiple files
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
                            # To make it "glow", we set light level AND disable shading
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
                            if "shade" in f: del f["shade"] # Restore default shading
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
        """Reloads the first selected model to update UI checkboxes."""
        if not self.source_paths: return
        
        for w in self.scroll_frame.winfo_children(): w.destroy()
        self.texture_vars = {} 

        # We only scan the FIRST file for the UI preview
        primary_path = self.source_paths[0]
        if primary_path.endswith(".json"):
            try:
                with open(primary_path, 'r') as f:
                    data = json.load(f)
                
                textures = data.get("textures", {})
                
                # Scan elements to restore state (Light & Tint)
                emissive_data = {} # tid -> light_value
                tint_data = set()  # tid
                
                for e in data.get("elements", []):
                    for f_data in e.get("faces", {}).values():
                        tid = str(f_data.get("texture", "")).replace("#", "")
                        if not tid: continue
                        
                        if "light" in f_data:
                            emissive_data[tid] = f_data["light"]
                        if "tintindex" in f_data:
                            tint_data.add(tid)
                
                for tid, tpath in textures.items():
                    is_emissive = tid in emissive_data
                    is_tinted = tid in tint_data
                    
                    var = ctk.BooleanVar(value=(is_emissive or is_tinted))
                    self.texture_vars[tid] = var
                    
                    label_text = f"{tid}: {tpath.split('/')[-1]}"
                    if is_emissive: label_text += f" [Light: {emissive_data[tid]}]"
                    if is_tinted: label_text += " [Tinted]"
                    
                    ctk.CTkCheckBox(self.scroll_frame, text=label_text, variable=var).pack(anchor="w", padx=10, pady=2)
            except Exception as e:
                print(f"Error refreshing UI: {e}")

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
                if self.textures_path: # Only uses the one loaded texture file
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