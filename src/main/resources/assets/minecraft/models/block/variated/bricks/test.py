import tkinter as tk
from tkinter import filedialog, messagebox
import shutil
import os

def select_files():
    files = filedialog.askopenfilenames(
        title="Select files"
    )
    file_list.clear()
    file_list.extend(files)
    label.config(text=f"{len(file_list)} file(s) selected")

def execute_copy():
    if not file_list:
        messagebox.showwarning("No files", "Please select files first.")
        return

    for file_path in file_list:
        folder, filename = os.path.split(file_path)
        new_filename = "yellow_" + filename
        new_path = os.path.join(folder, new_filename)

        shutil.copy2(file_path, new_path)

    messagebox.showinfo("Done", "Files copied successfully!")

# --- GUI setup ---
root = tk.Tk()
root.title("Yellow File Copier")
root.geometry("350x200")

file_list = []

btn_select = tk.Button(root, text="Select Files", command=select_files)
btn_select.pack(pady=10)

label = tk.Label(root, text="No files selected")
label.pack(pady=5)

btn_execute = tk.Button(root, text="Execute", command=execute_copy)
btn_execute.pack(pady=20)

root.mainloop()
