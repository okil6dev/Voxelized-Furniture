"""
Headless test for the woodcreator core logic.
Exercises texture discovery + renaming against real project files.
"""
import os
import sys
import json
import shutil
import tempfile

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from woodcreator import (collect_texture_refs, ordered_unique, prefixed,
                         rewrite_texture_ref, generate_variants, WOOD_TYPES)

WS = r"C:\Users\minec\MCreatorWorkspaces\voxelized_furniture"
SRC = os.path.join(WS, "src", "main", "resources", "assets",
                   "voxelized_furniture")
REAL_JSON = os.path.join(SRC, "models", "block", "acacia_drawer.json")
REAL_PNG = os.path.join(SRC, "textures", "block", "acaciasprinkler.png")

failures = []


def check(label, condition, detail=""):
    status = "PASS" if condition else "FAIL"
    print("[%s] %s%s" % (status, label, ("  -> " + detail) if detail else ""))
    if not condition:
        failures.append(label)


# --- 1. Texture discovery on a real model ---
data = json.load(open(REAL_JSON, encoding="utf-8"))
refs = ordered_unique(collect_texture_refs(data))
print("Refs found:", refs)
check("finds the 4 unique textures", len(refs) == 4, str(len(refs)))
check("ignores the parent ref",
      not any("custom/acacia_drawer" in r for r in refs))
check("includes the all/particle refs",
      "minecraft:block/stripped_acacia_log" in refs)
check("includes the concrete ref",
      "minecraft:block/light_blue_concrete" in refs)

# --- 2. Naming rules ---
check("default prefix is just the wood name",
      prefixed("chair", "oak") == "oak_chair")
check("dark_oak keeps its underscore",
      prefixed("chair", "dark_oak") == "dark_oak_chair")
check("optional wood_ prefix works",
      prefixed("chair", "oak", True) == "wood_oak_chair")
check("optional wood_ prefix with dark_oak",
      prefixed("chair", "dark_oak", True) == "wood_dark_oak_chair")
check("no doubled prefix",
      prefixed("oak_chair", "oak") == "oak_chair")
check("no doubled prefix with wood_",
      prefixed("wood_oak_chair", "oak", True) == "wood_oak_chair")
check("all 9 woods lowercase",
      all(w == w.lower() and " " not in w for w in WOOD_TYPES), str(WOOD_TYPES))
check("cherry and bamboo included",
      "cherry" in WOOD_TYPES and "bamboo" in WOOD_TYPES)
check("cherry naming", prefixed("chair", "cherry") == "cherry_chair")
check("bamboo naming", prefixed("chair", "bamboo") == "bamboo_chair")

# --- 3. Ref rewriting keeps namespace + folder ---
check("keeps namespace and folder",
      rewrite_texture_ref("minecraft:block/stripped_acacia_log", "wood_oak_chair")
      == "minecraft:block/wood_oak_chair")
check("handles unnamespaced ref",
      rewrite_texture_ref("block/stripped_oak", "wood_spruce_x")
      == "block/wood_spruce_x")

# --- 4. Full generation, selecting ONLY the wood textures ---
tmp = tempfile.mkdtemp(prefix="woodcreator_test_")
png_out = os.path.join(tmp, "block")
json_out = os.path.join(tmp, "models")
os.makedirs(png_out)
os.makedirs(json_out)

wanted = [r for r in refs if "acacia" in r or "oak" in r]
files, rewritten, skipped = generate_variants(
    REAL_JSON, REAL_PNG, json_out, png_out, WOOD_TYPES, wanted)
print("\nGenerated %d files, %d refs rewritten, %d skipped\n"
      % (files, rewritten, skipped))
check("wrote 18 files (9 wood x 2)", files == 18, str(files))
check("nothing skipped on a clean folder", skipped == 0, str(skipped))

for wood in WOOD_TYPES:
    # Derive expected names from prefixed() so de-duplication is accounted for
    # (e.g. wood "acacia" on source "acacia_drawer" -> "acacia_drawer").
    exp_png = prefixed("acaciasprinkler", wood)
    exp_json = prefixed("acacia_drawer", wood)
    png = os.path.join(png_out, "%s.png" % exp_png)
    js = os.path.join(json_out, "%s.json" % exp_json)
    check("png exists: %s.png" % exp_png, os.path.isfile(png))
    check("json exists: %s.json" % exp_json, os.path.isfile(js))

# --- 5. Inspect the generated oak JSON ---
oak = json.load(open(os.path.join(json_out, "oak_acacia_drawer.json"),
                     encoding="utf-8"))
print("\noak_acacia_drawer.json:")
print(json.dumps(oak, indent=2))

check("oak 'all' ref rewritten",
      oak["textures"]["all"] == "minecraft:block/oak_acaciasprinkler")
check("oak 'particle' ref rewritten",
      oak["textures"]["particle"] == "minecraft:block/oak_acaciasprinkler")
check("oak '0' ref rewritten",
      oak["textures"]["0"] == "minecraft:block/oak_acaciasprinkler")
check("unselected concrete ref untouched",
      oak["textures"]["1"] == "minecraft:block/light_blue_concrete")
check("unselected white_concrete untouched",
      oak["textures"]["2"] == "minecraft:block/white_concrete")
check("parent left untouched",
      oak["parent"] == "voxelized_furniture:custom/acacia_drawer")

# --- 6. Every wood variant points at its OWN png ---
for wood in WOOD_TYPES:
    d = json.load(open(os.path.join(json_out,
                                    "%s.json" % prefixed("acacia_drawer", wood)),
                       encoding="utf-8"))
    check("%s self-consistent" % wood,
          d["textures"]["all"]
          == "minecraft:block/%s" % prefixed("acaciasprinkler", wood))

# --- 6b. Optional wood_ prefix produces wood_oak_* names ---
f2, _, _ = generate_variants(REAL_JSON, REAL_PNG, json_out, png_out,
                           ["oak"], wanted, use_wood_prefix=True)
check("wood_ mode writes wood_oak_acacia_drawer.json",
      os.path.isfile(os.path.join(json_out, "wood_oak_acacia_drawer.json")))
check("wood_ mode writes wood_oak_acaciasprinkler.png",
      os.path.isfile(os.path.join(png_out, "wood_oak_acaciasprinkler.png")))
wp = json.load(open(os.path.join(json_out, "wood_oak_acacia_drawer.json"),
                    encoding="utf-8"))
check("wood_ mode rewrites refs to wood_oak_*",
      wp["textures"]["all"] == "minecraft:block/wood_oak_acaciasprinkler")

# --- 7. Source files untouched ---
check("original json untouched",
      json.load(open(REAL_JSON, encoding="utf-8")) == data)
check("original png untouched", os.path.isfile(REAL_PNG))

# --- 7. Skip-if-exists (overwrite=False, the default) ---
# Re-run into the folders that are already full from the run above.
f3, r3, s3 = generate_variants(REAL_JSON, REAL_PNG, json_out, png_out,
                               WOOD_TYPES, wanted)
check("second run writes 0 files", f3 == 0, str(f3))
check("second run skips all 18", s3 == 18, str(s3))

# A pre-existing file must survive verbatim.
guard_png = os.path.join(png_out, "oak_acaciasprinkler.png")
with open(guard_png, "rb") as f:
    before_bytes = f.read()
with open(os.path.join(json_out, "oak_acacia_drawer.json"), "w",
          encoding="utf-8") as f:
    f.write('{"sentinel": true}')
f4, _, s4 = generate_variants(REAL_JSON, REAL_PNG, json_out, png_out,
                               ["oak"], wanted)
with open(guard_png, "rb") as f:
    check("existing PNG left untouched", f.read() == before_bytes)
check("existing JSON left untouched",
      json.load(open(os.path.join(json_out, "oak_acacia_drawer.json"),
                     encoding="utf-8")) == {"sentinel": True})
check("skip counted for that wood", s4 == 2, str(s4))

# --- 8. Overwrite enabled replaces them ---
f5, _, s5 = generate_variants(REAL_JSON, REAL_PNG, json_out, png_out,
                               ["oak"], wanted, overwrite=True)
check("overwrite run writes 2 files", f5 == 2, str(f5))
check("overwrite run skips nothing", s5 == 0, str(s5))
check("overwrote JSON with real content",
      json.load(open(os.path.join(json_out, "oak_acacia_drawer.json"),
                     encoding="utf-8")).get("sentinel") is None)

shutil.rmtree(tmp, ignore_errors=True)

print("\n" + "=" * 50)
if failures:
    print("FAILED (%d): %s" % (len(failures), failures))
    sys.exit(1)
print("ALL TESTS PASSED")