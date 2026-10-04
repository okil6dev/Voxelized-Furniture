import os
import shutil
import tkinter as tk
from tkinter import filedialog, messagebox

class TextureSwapper:
    def __init__(self, root):
        self.root = root
        self.root.title("Master Texture Swapper - Force Overwrite")
        self.root.geometry("500x600")

        self.directory = ""
        self.source_path = ""
        self.all_files = []

        self.create_widgets()

    def create_widgets(self):
        # 1. Folder Selection
        tk.Button(self.root, text="Step 1: Select Target Folder", command=self.load_folder).pack(pady=10)
        self.label_dir = tk.Label(self.root, text="No folder selected", fg="grey")
        self.label_dir.pack()

        # 2. Source Selection
        tk.Button(self.root, text="Step 2: Select New Image (Source)", command=self.select_source, bg="#e3f2fd").pack(pady=10)
        self.label_source = tk.Label(self.root, text="No source selected", fg="blue")
        self.label_source.pack()

        # 3. Search Bar
        tk.Label(self.root, text="Search Textures:").pack(pady=(15, 0))
        self.search_var = tk.StringVar()
        self.search_var.trace_add("write", self.filter_list)
        self.search_entry = tk.Entry(self.root, textvariable=self.search_var)
        self.search_entry.pack(fill="x", padx=40)

        # 4. File List
        self.file_listbox = tk.Listbox(self.root, selectmode="single")
        self.file_listbox.pack(fill="both", expand=True, padx=40, pady=10)

        # 5. Execute
        tk.Button(self.root, text="SWAP NOW", command=self.perform_swap, 
                  bg="#d32f2f", fg="white", font=("Arial", 12, "bold"), height=2).pack(fill="x", padx=60, pady=20)

    def load_folder(self):
        self.directory = filedialog.askdirectory()
        if self.directory:
            self.label_dir.config(text=self.directory, fg="black")
            self.refresh_file_list()

    def select_source(self):
        self.source_path = filedialog.askopenfilename(filetypes=[("PNG files", "*.png")])
        if self.source_path:
            self.label_source.config(text=os.path.basename(self.source_path), fg="black")

    def refresh_file_list(self):
        if not self.directory: return
        self.all_files = [f for f in os.listdir(self.directory) if f.lower().endswith('.png')]
        self.filter_list()

    def filter_list(self, *args):
        search_term = self.search_var.get().lower()
        self.file_listbox.delete(0, tk.END)
        for f in self.all_files:
            if search_term in f.lower():
                self.file_listbox.insert(tk.END, f)

    def perform_swap(self):
        selection = self.file_listbox.curselection()
        if not selection or not self.source_path:
            messagebox.showwarning("Missing Info", "Ensure you selected a Source Image AND a Target from the list.")
            return

        target_filename = self.file_listbox.get(selection[0])
        target_full_path = os.path.join(self.directory, target_filename)

        try:
            # FORCE SWAP LOGIC:
            # 1. Open the source data first
            with open(self.source_path, 'rb') as src_file:
                data = src_file.read()

            # 2. Write the data to the target path (this forces an overwrite)
            with open(target_full_path, 'wb') as dest_file:
                dest_file.write(data)
            
            # 3. Touch the file to update timestamps (helps with cache)
            os.utime(target_full_path, None)

            messagebox.showinfo("Success", f"The image inside '{target_filename}' has been updated!")
            
        except PermissionError:
            messagebox.showerror("Error", "Permission Denied. Is the image open in another program?")
        except Exception as e:
            messagebox.showerror("Error", f"Failed: {str(e)}")

if __name__ == "__main__":
    root = tk.Tk()
    app = TextureSwapper(root)
    root.mainloop()