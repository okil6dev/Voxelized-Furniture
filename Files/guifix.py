import tkinter as tk
from tkinter import ttk, messagebox
import pyautogui
import time
import threading
import os

# --- INSTRUCTIONS ---
# 1. Take a small screenshot of the "GUI" sub-tab text/icon when active in Blockbench.
# 2. Save it as 'gui_tab.png' in the same folder as this script.
# 3. Take a small screenshot of the "Preview Scene" toggle (the eyeball or button).
# 4. Save it as 'preview_toggle.png'.

class BlockbenchAutomation:
    def __init__(self, root):
        self.root = root
        self.root.title("Blockbench UI Helper")
        self.root.geometry("300x200")
        
        self.running = False
        self.is_gui_active = False # Tracks if we've already toggled
        
        self.setup_ui()
        
    def setup_ui(self):
        self.label = ttk.Label(self.root, text="Blockbench Auto-Toggle", font=("Arial", 12, "bold"))
        self.label.pack(pady=10)
        
        self.status_label = ttk.Label(self.root, text="Status: Stopped", foreground="red")
        self.status_label.pack(pady=5)
        
        self.start_btn = ttk.Button(self.root, text="Start Monitoring", command=self.toggle_monitoring)
        self.start_btn.pack(pady=10)
        
        self.info_text = ttk.Label(self.root, text="Checks for 'Display -> GUI' tab", font=("Arial", 8))
        self.info_text.pack(pady=5)

    def toggle_monitoring(self):
        if not self.running:
            # Check for required images first
            if not os.path.exists('gui_tab.png') or not os.path.exists('preview_toggle.png'):
                messagebox.showerror("Error", "Missing reference images!\nPlease save 'gui_tab.png' and 'preview_toggle.png' in the script folder.")
                return
            
            self.running = True
            self.start_btn.config(text="Stop Monitoring")
            self.status_label.config(text="Status: Running", foreground="green")
            threading.Thread(target=self.monitor_loop, daemon=True).start()
        else:
            self.running = False
            self.start_btn.config(text="Start Monitoring")
            self.status_label.config(text="Status: Stopped", foreground="red")

    def click_preview_toggle(self):
        """Finds and clicks the preview scene toggle button."""
        try:
            location = pyautogui.locateOnScreen('preview_toggle.png', confidence=0.8)
            if location:
                pyautogui.click(location)
                return True
        except Exception as e:
            print(f"Error locating toggle: {e}")
        return False

    def monitor_loop(self):
        """The main loop that watches the screen."""
        while self.running:
            try:
                # 1. Search for the 'GUI' tab indicator on screen
                gui_tab_location = pyautogui.locateOnScreen('gui_tab.png', confidence=0.8)
                
                if gui_tab_location:
                    # We are on the GUI tab
                    if not self.is_gui_active:
                        print("Detected GUI tab: Disabling Preview Scene...")
                        self.click_preview_toggle()
                        self.is_gui_active = True
                else:
                    # We are NOT on the GUI tab
                    if self.is_gui_active:
                        print("Left GUI tab: Enabling Preview Scene...")
                        self.click_preview_toggle()
                        self.is_gui_active = False
                        
            except Exception as e:
                print(f"Monitoring error: {e}")
            
            # Wait a bit before checking again to save CPU
            time.sleep(1.0)

if __name__ == "__main__":
    root = tk.Tk()
    app = BlockbenchAutomation(root)
    root.mainloop()