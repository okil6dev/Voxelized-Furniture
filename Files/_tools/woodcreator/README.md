# Wood Creator

Generates Minecraft wood variants of a model JSON + texture PNG pair.

## Run

```bash
pip install PyQt6 qfluentwidgets
python woodcreator.py
```

## What it does

1. **Select JSON** and **Select PNG** (two separate buttons).
2. Choose an output folder for the PNGs and one for the JSONs.
3. It scans the JSON and lists **every texture reference it finds**, each with a
   checkbox so you choose which textures get the wood PNG. Everything you leave
   unticked is copied through untouched.
4. Tick the wood types you want.
5. **Generate Wood Variants**.

For each wood type you get a matching pair, named with the wood type in front:

```
chair.png   ->  oak_chair.png, spruce_chair.png, dark_oak_chair.png, ...
chair.json  ->  oak_chair.json, spruce_chair.json, dark_oak_chair.json, ...
```

Tick **"Also prefix with `wood_`"** under the Generate button if you'd rather have
`wood_oak_chair.png`. It's off by default.

A doubled prefix is never produced, so `acacia_drawer.json` with the `acacia`
wood type stays `acacia_drawer.json` instead of becoming `acacia_acacia_drawer.json`.

Wood types: `oak, spruce, birch, jungle, acacia, dark_oak, mangrove, cherry,
bamboo` — always lowercase, since Minecraft resource locations only accept
lowercase.

Each generated JSON's selected texture refs are rewritten to point at that wood
variant's own PNG, so every pair is self-consistent:

```json
"textures": {
  "all": "minecraft:block/oak_chair",
  "1": "minecraft:block/light_blue_concrete"
}
```

Notes:
- The **namespace and subfolder are preserved**; only the filename changes. So
  your PNG folder needs to match the reference folder (usually `textures/block/`)
  — the app warns you in the GUI if it doesn't.
- The `parent` reference is left untouched.
- Originals are never modified; everything is written to your chosen output dirs.
- **Overwrite old files is OFF by default** — any PNG/JSON that already exists in
  the output folder is skipped and left alone (the log marks them `SKIP`). Tick it
  to replace existing files instead.

## Tests

```bash
python test_core.py   # logic, naming, rewriting (headless)
python test_gui.py    # builds the real window headless, runs a generation
```