import os
import json
import customtkinter as ctk
from tkinter import filedialog, messagebox
import re

# --- CONFIG ---
WOODS = ["dark_oak", "oak", "spruce", "birch", "jungle", "acacia", "mangrove", "cherry", "bamboo"]
LEGAL_ANGLES = [-45, -22.5, 0, 22.5, 45]

class ModelProcessorApp(ctk.CTk):
    def __init__(self):
        super().__init__()

        self.title("Model Studio Pro - v46.0")
        self.geometry("600x800")
        ctk.set_appearance_mode("dark") 
        
        self.main_container = ctk.CTkScrollableFrame(self, fg_color="transparent")
        self.main_container.pack(fill="both", expand=True)

        self.source_path = ""
        self.textures_path = ""
        self.output_dir = ""
        self.texture_vars = {}

        self.setup_ui()

    def setup_ui(self):
        header = ctk.CTkFrame(self.main_container, fg_color="transparent")
        header.pack(fill="x", padx=30, pady=(20, 10))
        ctk.CTkLabel(header, text="MCreator Wood Suite", font=ctk.CTkFont(size=22, weight="bold")).pack(side="left")

        # 1. LOAD FILES
        step1 = ctk.CTkFrame(self.main_container)
        step1.pack(fill="x", padx=30, pady=10)
        ctk.CTkLabel(step1, text="1. SELECT MASTER MODEL", font=ctk.CTkFont(size=12, weight="bold"), text_color="#3b8ed0").pack(anchor="w", padx=15, pady=(10, 0))
        self.file_info = ctk.CTkLabel(step1, text="No File Selected", text_color="gray")
        self.file_info.pack(anchor="w", padx=15)
        ctk.CTkButton(step1, text="Browse (.json / .java)", command=self.load_model).pack(fill="x", padx=15, pady=5)
        
        # 2. JSON TWEAKS (Direct Apply Buttons)
        step2 = ctk.CTkFrame(self.main_container)
        step2.pack(fill="x", padx=30, pady=10)
        ctk.CTkLabel(step2, text="2. JSON TWEAKS", font=ctk.CTkFont(size=12, weight="bold"), text_color="#e67e22").pack(anchor="w", padx=15, pady=(10, 0))
        
        # Scaling inputs
        scale_frame = ctk.CTkFrame(step2, fg_color="#333")
        scale_frame.pack(fill="x", padx=15, pady=10)
        ctk.CTkLabel(scale_frame, text="Scale Value:").grid(row=0, column=0, padx=5, pady=5)
        self.scale_entry = ctk.CTkEntry(scale_frame, width=60)
        self.scale_entry.insert(0, "0.5")
        self.scale_entry.grid(row=0, column=1, padx=5, pady=5)
        
        self.btn_scale_drop = ctk.CTkButton(scale_frame, text="Apply Dropped Scale", fg_color="#d35400", command=lambda: self.apply_scaling(["ground", "fixed"]))
        self.btn_scale_drop.grid(row=1, column=0, columnspan=2, sticky="ew", padx=5, pady=2)
        self.btn_scale_gui = ctk.CTkButton(scale_frame, text="Apply GUI Scale", fg_color="#2980b9", command=lambda: self.apply_scaling(["gui"]))
        self.btn_scale_gui.grid(row=2, column=0, columnspan=2, sticky="ew", padx=5, pady=(2, 5))

        # Tints and Rotations
        self.scroll_frame = ctk.CTkScrollableFrame(step2, label_text="Layers to Tint", height=120)
        self.scroll_frame.pack(fill="x", padx=15, pady=10)
        
        btn_grid = ctk.CTkFrame(step2, fg_color="transparent")
        btn_grid.pack(fill="x", padx=15, pady=(0, 15))
        self.tint_btn = ctk.CTkButton(btn_grid, text="Apply Tints", fg_color="#e67e22", command=self.apply_tint_only)
        self.tint_btn.pack(side="left", expand=True, fill="x", padx=2)
        self.rot_btn = ctk.CTkButton(btn_grid, text="Fix Rotations", fg_color="#e67e22", command=self.apply_rotation_only)
        self.rot_btn.pack(side="left", expand=True, fill="x", padx=2)

        # 3. OUTPUT
        step3 = ctk.CTkFrame(self.main_container)
        step3.pack(fill="x", padx=30, pady=10)
        self.dest_label = ctk.CTkLabel(step3, text="No folder selected", text_color="gray")
        self.dest_label.pack(anchor="w", padx=15, pady=5)
        ctk.CTkButton(step3, text="Select Output Folder", fg_color="#555", command=self.browse_output_folder).pack(fill="x", padx=15, pady=5)

        # 4. GENERATE
        ctk.CTkButton(self.main_container, text="Generate Wood Types", fg_color="#2ecc71", hover_color="#27ae60", height=50, font=ctk.CTkFont(size=15, weight="bold"), command=self.process_variants).pack(fill="x", padx=30, pady=20)

    # --- ACTION LOGIC (Directly updates the file) ---
    def apply_scaling(self, targets):
        if not self.source_path or not self.source_path.endswith(".json"): return
        try:
            val = float(self.scale_entry.get())
            with open(self.source_path, 'r') as f: data = json.load(f)
            if "display" not in data: data["display"] = {}
            for t in targets:
                data["display"][t] = {"scale": [val, val, val]}
            with open(self.source_path, 'w') as f: json.dump(data, f, indent=4)
            messagebox.showinfo("Success", f"Updated {targets} scale to {val}")
        except: messagebox.showerror("Error", "Check scale value or file.")

    def apply_tint_only(self):
        if not self.source_path or not self.source_path.endswith(".json"): return
        with open(self.source_path, 'r') as f: data = json.load(f)
        selected = [str(tid) for tid, var in self.texture_vars.items() if var.get()]
        for e in data.get("elements", []):
            for f in e.get("faces", {}).values():
                if str(f.get("texture", "")).replace("#", "") in selected: f["tintindex"] = 0
        with open(self.source_path, 'w') as f: json.dump(data, f, indent=4)
        messagebox.showinfo("Success", "Tints applied.")

    def apply_rotation_only(self):
        if not self.source_path or not self.source_path.endswith(".json"): return
        with open(self.source_path, 'r') as f: data = json.load(f)
        for e in data.get("elements", []):
            if "rotation" in e:
                a = e["rotation"].get("angle", 0)
                e["rotation"]["angle"] = min(LEGAL_ANGLES, key=lambda x: abs(x - a))
        with open(self.source_path, 'w') as f: json.dump(data, f, indent=4)
        messagebox.showinfo("Success", "Rotations fixed.")

    def load_model(self):
        path = filedialog.askopenfilename(filetypes=[("Model Files", "*.json *.java")])
        if path:
            self.source_path = path
            self.file_info.configure(text=os.path.basename(path), text_color="#3b8ed0")
            for widget in self.scroll_frame.winfo_children(): widget.destroy()
            if path.endswith(".json"):
                with open(path, 'r') as f:
                    data = json.load(f)
                    textures = data.get("textures", {})
                    for tid in textures.keys():
                        var = ctk.BooleanVar(value=False)
                        self.texture_vars[tid] = var
                        ctk.CTkCheckBox(self.scroll_frame, text=f"Layer #{tid}", variable=var).pack(anchor="w", padx=10, pady=2)

    def browse_output_folder(self):
        path = filedialog.askdirectory()
        if path:
            self.output_dir = path
            self.dest_label.configure(text=path, text_color="#2ecc71")

    def process_variants(self):
        if not self.source_path or not self.output_dir:
            messagebox.showerror("Error", "Paths missing!")
            return

        orig_full_name = os.path.splitext(os.path.basename(self.source_path))[0]
        base_name = orig_full_name
        source_wood = ""
        for wood in WOODS:
            if wood in orig_full_name.lower():
                source_wood = wood
                base_name = re.sub(wood, "", orig_full_name, flags=re.I).strip("_").strip("-")
                break 

        try:
            with open(self.source_path, 'r', encoding='utf-8') as f: clean_content = f.read()
            for wood in WOODS:
                new_name = f"{base_name}_{wood}"
                final = clean_content.replace(orig_full_name, new_name)
                if source_wood:
                    final = re.sub(source_wood, wood, final, flags=re.I)
                
                ext = os.path.splitext(self.source_path)[1]
                with open(os.path.join(self.output_dir, f"{new_name}{ext}"), 'w', encoding='utf-8') as f:
                    f.write(final)
            
            messagebox.showinfo("Success", "Wood types generated.")
            os.startfile(self.output_dir)
        except Exception as e:
            messagebox.showerror("Error", str(e))

if __name__ == "__main__":
    app = ModelProcessorApp()
    app.mainloop()