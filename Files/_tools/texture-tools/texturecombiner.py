import tkinter as tk
from tkinter import filedialog, messagebox, ttk
import random
try:
    from PIL import Image
except ImportError:
    import sys
    print("Error: Pillow library not found. Install it using: pip install Pillow")
    sys.exit(1)

class InfiniteTextureCombiner:
    def __init__(self, root):
        self.root = root
        self.root.title("MC Infinite Texture Stacker & Scrambler")
        self.root.geometry("750x650")
        
        # Internal state
        self.texture_paths = []
        self.force_square = tk.BooleanVar(value=False)

        # Header
        header = tk.Label(root, text="Minecraft Texture Stacker", font=("Arial", 16, "bold"))
        header.pack(pady=10)

        # Main Layout Frame
        main_frame = tk.Frame(root)
        main_frame.pack(fill=tk.BOTH, expand=True, padx=20)

        # Left side: Listbox
        list_container = tk.Frame(main_frame)
        list_container.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)

        tk.Label(list_container, text="Current Stack (Top to Bottom):").pack(anchor="w")
        
        list_inner = tk.Frame(list_container)
        list_inner.pack(fill=tk.BOTH, expand=True)

        self.scrollbar = tk.Scrollbar(list_inner, orient=tk.VERTICAL)
        self.listbox = tk.Listbox(
            list_inner, 
            yscrollcommand=self.scrollbar.set, 
            selectmode=tk.SINGLE, 
            font=("Consolas", 10)
        )
        self.scrollbar.config(command=self.listbox.yview)
        
        self.scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
        self.listbox.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)

        # Right side: Reorder & FX Buttons
        side_btn_frame = tk.Frame(main_frame)
        side_btn_frame.pack(side=tk.RIGHT, fill=tk.Y, padx=(10, 0), pady=20)

        tk.Label(side_btn_frame, text="Reorder", font=("Arial", 9, "bold")).pack()
        tk.Button(side_btn_frame, text="▲ Move Up", command=lambda: self.move_texture(-1), width=15).pack(pady=2)
        tk.Button(side_btn_frame, text="▼ Move Down", command=lambda: self.move_texture(1), width=15).pack(pady=2)
        
        tk.Label(side_btn_frame, text="Effects", font=("Arial", 9, "bold")).pack(pady=(15, 0))
        tk.Button(
            side_btn_frame, 
            text="✨ Scramble", 
            command=self.scramble_texture, 
            bg="#9b59b6", 
            fg="white", 
            width=15
        ).pack(pady=2)
        
        tk.Label(side_btn_frame, text="Settings", font=("Arial", 9, "bold")).pack(pady=(15, 0))
        tk.Checkbutton(side_btn_frame, text="Force 1:1 Ratio", variable=self.force_square).pack(pady=2)

        # Control Buttons (Bottom)
        btn_frame = tk.Frame(root)
        btn_frame.pack(pady=15)

        tk.Button(btn_frame, text="+ Add Textures", command=self.add_texture, bg="#3498db", fg="white", width=15).grid(row=0, column=0, padx=5)
        tk.Button(btn_frame, text="- Remove Selected", command=self.remove_texture, bg="#e67e22", fg="white", width=15).grid(row=0, column=1, padx=5)
        tk.Button(btn_frame, text="Clear List", command=self.clear_list, bg="#95a5a6", fg="white", width=15).grid(row=0, column=2, padx=5)

        # Action Button
        self.combine_btn = tk.Button(
            root, 
            text="GENERATE COMBINED TEXTURE", 
            font=("Arial", 12, "bold"), 
            bg="#2ecc71", 
            fg="white", 
            height=2, 
            command=self.combine_textures
        )
        self.combine_btn.pack(fill=tk.X, padx=20, pady=20)

        # Status Bar
        self.status = tk.Label(root, text="Ready", bd=1, relief=tk.SUNKEN, anchor=tk.W)
        self.status.pack(side=tk.BOTTOM, fill=tk.X)

    def add_texture(self):
        files = filedialog.askopenfilenames(
            title="Select Textures",
            filetypes=[("Image files", "*.png *.jpg *.jpeg *.bmp")]
        )
        if files:
            for f in files:
                self.texture_paths.append(f)
            self.refresh_listbox()
            self.status.config(text=f"Added {len(files)} textures.")

    def move_texture(self, direction):
        selected = self.listbox.curselection()
        if not selected:
            return
        
        idx = selected[0]
        new_idx = idx + direction
        
        if 0 <= new_idx < len(self.texture_paths):
            self.texture_paths[idx], self.texture_paths[new_idx] = self.texture_paths[new_idx], self.texture_paths[idx]
            self.refresh_listbox()
            self.listbox.selection_set(new_idx)

    def remove_texture(self):
        selected = self.listbox.curselection()
        if selected:
            idx = selected[0]
            self.texture_paths.pop(idx)
            self.refresh_listbox()
            self.status.config(text="Texture removed.")

    def scramble_texture(self):
        selected = self.listbox.curselection()
        if not selected:
            messagebox.showinfo("Scrambler", "Please select a texture from the list to scramble!")
            return
        
        idx = selected[0]
        original_path = self.texture_paths[idx]
        
        try:
            img = Image.open(original_path).convert("RGBA")
            pixels = img.load()
            width, height = img.size
            
            passes = 2 
            for _ in range(passes):
                for y in range(height):
                    for x in range(width):
                        nx = x + random.randint(-1, 1)
                        ny = y + random.randint(-1, 1)
                        if 0 <= nx < width and 0 <= ny < height:
                            c1 = pixels[x, y]
                            c2 = pixels[nx, ny]
                            if c1[3] == 0 or c2[3] == 0:
                                continue
                            diff = sum((a - b) ** 2 for a, b in zip(c1[:3], c2[:3])) ** 0.5
                            if diff < 60: 
                                pixels[x, y], pixels[nx, ny] = c2, c1
            
            save_path = filedialog.asksaveasfilename(
                title="Save Scrambled Texture As",
                defaultextension=".png",
                initialfile="scrambled_" + original_path.split('/')[-1]
            )
            
            if save_path:
                img.save(save_path)
                self.texture_paths[idx] = save_path
                self.refresh_listbox()
                self.status.config(text="Texture scrambled and updated in list.")
                
        except Exception as e:
            messagebox.showerror("Scramble Error", f"Failed to scramble texture: {e}")

    def refresh_listbox(self):
        self.listbox.delete(0, tk.END)
        for i, path in enumerate(self.texture_paths):
            self.listbox.insert(tk.END, f"[{i+1}] {path.split('/')[-1]}")

    def clear_list(self):
        if self.texture_paths and messagebox.askyesno("Clear All", "Remove all textures from the list?"):
            self.texture_paths = []
            self.refresh_listbox()
            self.status.config(text="List cleared.")

    def combine_textures(self):
        if not self.texture_paths:
            messagebox.showwarning("Warning", "The list is empty! Add some textures first.")
            return

        try:
            images = [Image.open(p).convert("RGBA") for p in self.texture_paths]
            
            max_width = max(img.width for img in images)
            total_height = sum(img.height for img in images)

            # Determine canvas size
            canvas_w = max_width
            canvas_h = total_height

            if self.force_square.get():
                side = max(max_width, total_height)
                canvas_w = side
                canvas_h = side

            combined = Image.new("RGBA", (canvas_w, canvas_h), (0, 0, 0, 0))

            current_y = 0
            for img in images:
                # Center the individual textures if canvas is wider than the image
                offset_x = (canvas_w - img.width) // 2 if self.force_square.get() else 0
                combined.paste(img, (offset_x, current_y))
                current_y += img.height

            save_path = filedialog.asksaveasfilename(
                defaultextension=".png",
                filetypes=[("PNG file", "*.png")],
                title="Save Combined Texture"
            )
            
            if save_path:
                combined.save(save_path, "PNG")
                messagebox.showinfo("Success", f"Texture saved!\nFinal Size: {canvas_w}x{canvas_h}")
                self.status.config(text=f"Saved {canvas_w}x{canvas_h} texture.")

        except Exception as e:
            messagebox.showerror("Error", f"An error occurred:\n{str(e)}")

if __name__ == "__main__":
    root = tk.Tk()
    app = InfiniteTextureCombiner(root)
    root.mainloop()