import tkinter as tk
from tkinter import filedialog, messagebox
from PIL import Image, ImageTk

class OverlayWindow(tk.Toplevel):
    """The actual floating, transparent image window."""
    def __init__(self, master, image_path):
        super().__init__(master)
        self.image_path = image_path
        
        # Window setup
        self.overrideredirect(True)  # Remove borders
        self.attributes("-topmost", True)  # Always on top
        
        # Transparency logic (Windows/macOS handles this differently)
        # We use a placeholder color to make the background invisible
        self.config(bg='magenta')
        self.attributes("-transparentcolor", "magenta")

        self.original_image = Image.open(self.image_path).convert("RGBA")
        self.scale_factor = 1.0
        
        self.label = tk.Label(self, bg='magenta')
        self.label.pack()

        # Bindings
        self.label.bind("<Button-3>", lambda e: self.destroy()) # Right-click to remove
        self.label.bind("<Button-1>", self.start_move)
        self.label.bind("<B1-Motion>", self.do_move)
        self.bind("<MouseWheel>", self.zoom) # Windows
        self.bind("<Button-4>", self.zoom)   # Linux Scroll Up
        self.bind("<Button-5>", self.zoom)   # Linux Scroll Down

        self.update_image()

    def update_image(self):
        width = int(self.original_image.width * self.scale_factor)
        height = int(self.original_image.height * self.scale_factor)
        
        # Prevent the image from becoming invisible/too small
        width = max(width, 20)
        height = max(height, 20)

        resized_img = self.original_image.resize((width, height), Image.Resampling.LANCZOS)
        self.tk_image = ImageTk.PhotoImage(resized_img)
        self.label.config(image=self.tk_image)

    def zoom(self, event):
        # Respond to mouse wheel
        if event.num == 4 or event.delta > 0:
            self.scale_factor *= 1.1
        elif event.num == 5 or event.delta < 0:
            self.scale_factor *= 0.9
        self.update_image()

    def start_move(self, event):
        self.x = event.x
        self.y = event.y

    def do_move(self, event):
        x = self.winfo_x() + (event.x - self.x)
        y = self.winfo_y() + (event.y - self.y)
        self.geometry(f"+{x}+{y}")

class ControlPanel:
    """The main GUI to select and manage images."""
    def __init__(self, root):
        self.root = root
        self.root.title("Image Overlay Controller")
        self.root.geometry("300x150")
        
        self.label = tk.Label(root, text="Image Overlay Tool", font=("Arial", 12, "bold"))
        self.label.pack(pady=10)

        self.btn_select = tk.Button(root, text="Select & Display Image", command=self.open_image, 
                                   width=20, height=2, bg="#e1e1e1")
        self.btn_select.pack(pady=10)
        
        self.info_label = tk.Label(root, text="Right-click image to close it\nMouse wheel to resize", font=("Arial", 8))
        self.info_label.pack()

    def open_image(self):
        file_path = filedialog.askopenfilename(
            filetypes=[("Images", "*.png *.jpg *.jpeg *.bmp *.gif")]
        )
        if file_path:
            try:
                OverlayWindow(self.root, file_path)
            except Exception as e:
                messagebox.showerror("Error", f"Could not load image: {e}")

if __name__ == "__main__":
    root = tk.Tk()
    app = ControlPanel(root)
    root.mainloop()