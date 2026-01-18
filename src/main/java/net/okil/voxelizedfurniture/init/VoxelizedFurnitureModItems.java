/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.item.JamBottleItem;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import java.util.function.Function;

public class VoxelizedFurnitureModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(VoxelizedFurnitureMod.MODID);
	public static final DeferredItem<Item> CABINET_OAK;
	public static final DeferredItem<Item> CABINET_BIRCH;
	public static final DeferredItem<Item> CABINET_DARK_OAK;
	public static final DeferredItem<Item> CABINET_ACACIA;
	public static final DeferredItem<Item> CABINET_MANGROVE;
	public static final DeferredItem<Item> CABINET_CHERRY;
	public static final DeferredItem<Item> CABINET_JUNGLE;
	public static final DeferredItem<Item> CABINET_BAMBOO;
	public static final DeferredItem<Item> FRIDGE;
	public static final DeferredItem<Item> GREENWALL;
	public static final DeferredItem<Item> OAK_SINK;
	public static final DeferredItem<Item> SPRUCE_SINK;
	public static final DeferredItem<Item> MANGROVE_SINK;
	public static final DeferredItem<Item> JUNGLE_SINK;
	public static final DeferredItem<Item> DARK_OAK_SINK;
	public static final DeferredItem<Item> BIRCH_SINK;
	public static final DeferredItem<Item> ACACIA_SINK;
	public static final DeferredItem<Item> CHERRY_SINK;
	public static final DeferredItem<Item> BAMBOO_SINK;
	public static final DeferredItem<Item> CABINET_SPRUCE;
	public static final DeferredItem<Item> JAM_JAR;
	public static final DeferredItem<Item> HONEY_JAR;
	public static final DeferredItem<Item> JAM_BOTTLE;
	public static final DeferredItem<Item> SHOWER;
	public static final DeferredItem<Item> OAK_SHELF;
	public static final DeferredItem<Item> SPRUCE_SHELF;
	public static final DeferredItem<Item> MANGROVE_SHELF;
	public static final DeferredItem<Item> JUNGLE_SHELF;
	public static final DeferredItem<Item> DARK_OAK_SHELF;
	public static final DeferredItem<Item> CHERRY_SHELF;
	public static final DeferredItem<Item> BIRCH_SHELF;
	public static final DeferredItem<Item> BAMBOO_SHELF;
	public static final DeferredItem<Item> ACACIA_SHELF;
	public static final DeferredItem<Item> SOAP;
	public static final DeferredItem<Item> OAK_CHAIR_1;
	public static final DeferredItem<Item> SPRUCE_CHAIR_1;
	public static final DeferredItem<Item> MANGROVE_CHAIR_1;
	public static final DeferredItem<Item> JUNGLE_CHAIR_1;
	public static final DeferredItem<Item> DARK_OAK_CHAIR_1;
	public static final DeferredItem<Item> CHERRY_CHAIR_1;
	public static final DeferredItem<Item> BIRCH_CHAIR_1;
	public static final DeferredItem<Item> BAMBOO_CHAIR_1;
	public static final DeferredItem<Item> ACACIA_CHAIR_1;
	public static final DeferredItem<Item> GROUND_DEV_TEX;
	public static final DeferredItem<Item> GOLDEN_DESKTOP_FOUNTAIN;
	public static final DeferredItem<Item> POTTED_GREEN_PLANT;
	public static final DeferredItem<Item> GOLDEN_ZEN_FOUNTAIN;
	public static final DeferredItem<Item> TV;
	public static final DeferredItem<Item> LAPTOP;
	public static final DeferredItem<Item> ASPHALT;
	public static final DeferredItem<Item> DOUBLE_BED;
	public static final DeferredItem<Item> SINGLE_BED;
	public static final DeferredItem<Item> OAK_WARDROBE;
	public static final DeferredItem<Item> SPRUCE_WARDROBE;
	public static final DeferredItem<Item> MANGROVE_WARDROBE;
	public static final DeferredItem<Item> JUNGLE_WARDROBE;
	public static final DeferredItem<Item> DARK_OAK_WARDROBE;
	public static final DeferredItem<Item> CHERRY_WARDROBE;
	public static final DeferredItem<Item> BIRCH_WARDROBE;
	public static final DeferredItem<Item> BAMBOO_WARDROBE;
	public static final DeferredItem<Item> ACACIA_WARDROBE;
	public static final DeferredItem<Item> LIGHT;
	public static final DeferredItem<Item> LIGHT_SWITCH_BUTTON;
	public static final DeferredItem<Item> LIGHTON;
	public static final DeferredItem<Item> BROWN_BARBECUE;
	public static final DeferredItem<Item> KITCHEN_DRAWER;
	public static final DeferredItem<Item> KITCHEN_DRAWER_1;
	public static final DeferredItem<Item> KITCHEN_SINK;
	public static final DeferredItem<Item> OVEN;
	public static final DeferredItem<Item> OVEN_VENT;
	public static final DeferredItem<Item> KITCHEN_TABLE;
	public static final DeferredItem<Item> KITCHEN_DRAWER_CORNER;
	public static final DeferredItem<Item> KITCHEN_FRIDGE;
	public static final DeferredItem<Item> MODERN_BULKHEAD_LIGHT_OFF;
	public static final DeferredItem<Item> MODERN_BULKHEAD_LIGHT_ON;
	public static final DeferredItem<Item> OAK_COBBLE_PATH;
	public static final DeferredItem<Item> SPRUCE_COBBLE_PATH;
	public static final DeferredItem<Item> MANGROVE_COBBLE_PATH;
	public static final DeferredItem<Item> JUNGLE_COBBLE_PATH;
	public static final DeferredItem<Item> DARK_OAK_COBBLE_PATH;
	public static final DeferredItem<Item> CHERRY_COBBLE_PATH;
	public static final DeferredItem<Item> BIRCH_COBBLE_PATH;
	public static final DeferredItem<Item> BAMBOO_COBBLE_PATH;
	public static final DeferredItem<Item> ACACIA_COBBLE_PATH;
	static {
		CABINET_OAK = block(VoxelizedFurnitureModBlocks.CABINET_OAK);
		CABINET_BIRCH = block(VoxelizedFurnitureModBlocks.CABINET_BIRCH);
		CABINET_DARK_OAK = block(VoxelizedFurnitureModBlocks.CABINET_DARK_OAK);
		CABINET_ACACIA = block(VoxelizedFurnitureModBlocks.CABINET_ACACIA);
		CABINET_MANGROVE = block(VoxelizedFurnitureModBlocks.CABINET_MANGROVE);
		CABINET_CHERRY = block(VoxelizedFurnitureModBlocks.CABINET_CHERRY);
		CABINET_JUNGLE = block(VoxelizedFurnitureModBlocks.CABINET_JUNGLE);
		CABINET_BAMBOO = block(VoxelizedFurnitureModBlocks.CABINET_BAMBOO);
		FRIDGE = block(VoxelizedFurnitureModBlocks.FRIDGE, new Item.Properties().fireResistant());
		GREENWALL = block(VoxelizedFurnitureModBlocks.GREENWALL);
		OAK_SINK = block(VoxelizedFurnitureModBlocks.OAK_SINK);
		SPRUCE_SINK = block(VoxelizedFurnitureModBlocks.SPRUCE_SINK);
		MANGROVE_SINK = block(VoxelizedFurnitureModBlocks.MANGROVE_SINK);
		JUNGLE_SINK = block(VoxelizedFurnitureModBlocks.JUNGLE_SINK);
		DARK_OAK_SINK = block(VoxelizedFurnitureModBlocks.DARK_OAK_SINK);
		BIRCH_SINK = block(VoxelizedFurnitureModBlocks.BIRCH_SINK);
		ACACIA_SINK = block(VoxelizedFurnitureModBlocks.ACACIA_SINK);
		CHERRY_SINK = block(VoxelizedFurnitureModBlocks.CHERRY_SINK);
		BAMBOO_SINK = block(VoxelizedFurnitureModBlocks.BAMBOO_SINK);
		CABINET_SPRUCE = block(VoxelizedFurnitureModBlocks.CABINET_SPRUCE);
		JAM_JAR = block(VoxelizedFurnitureModBlocks.JAM_JAR);
		HONEY_JAR = block(VoxelizedFurnitureModBlocks.HONEY_JAR);
		JAM_BOTTLE = register("jam_bottle", JamBottleItem::new);
		SHOWER = block(VoxelizedFurnitureModBlocks.SHOWER, new Item.Properties().fireResistant());
		OAK_SHELF = block(VoxelizedFurnitureModBlocks.OAK_SHELF);
		SPRUCE_SHELF = block(VoxelizedFurnitureModBlocks.SPRUCE_SHELF);
		MANGROVE_SHELF = block(VoxelizedFurnitureModBlocks.MANGROVE_SHELF);
		JUNGLE_SHELF = block(VoxelizedFurnitureModBlocks.JUNGLE_SHELF);
		DARK_OAK_SHELF = block(VoxelizedFurnitureModBlocks.DARK_OAK_SHELF);
		CHERRY_SHELF = block(VoxelizedFurnitureModBlocks.CHERRY_SHELF);
		BIRCH_SHELF = block(VoxelizedFurnitureModBlocks.BIRCH_SHELF);
		BAMBOO_SHELF = block(VoxelizedFurnitureModBlocks.BAMBOO_SHELF);
		ACACIA_SHELF = block(VoxelizedFurnitureModBlocks.ACACIA_SHELF);
		SOAP = block(VoxelizedFurnitureModBlocks.SOAP, new Item.Properties().fireResistant());
		OAK_CHAIR_1 = block(VoxelizedFurnitureModBlocks.OAK_CHAIR_1);
		SPRUCE_CHAIR_1 = block(VoxelizedFurnitureModBlocks.SPRUCE_CHAIR_1);
		MANGROVE_CHAIR_1 = block(VoxelizedFurnitureModBlocks.MANGROVE_CHAIR_1);
		JUNGLE_CHAIR_1 = block(VoxelizedFurnitureModBlocks.JUNGLE_CHAIR_1);
		DARK_OAK_CHAIR_1 = block(VoxelizedFurnitureModBlocks.DARK_OAK_CHAIR_1);
		CHERRY_CHAIR_1 = block(VoxelizedFurnitureModBlocks.CHERRY_CHAIR_1);
		BIRCH_CHAIR_1 = block(VoxelizedFurnitureModBlocks.BIRCH_CHAIR_1);
		BAMBOO_CHAIR_1 = block(VoxelizedFurnitureModBlocks.BAMBOO_CHAIR_1);
		ACACIA_CHAIR_1 = block(VoxelizedFurnitureModBlocks.ACACIA_CHAIR_1);
		GROUND_DEV_TEX = block(VoxelizedFurnitureModBlocks.GROUND_DEV_TEX, new Item.Properties().fireResistant());
		GOLDEN_DESKTOP_FOUNTAIN = block(VoxelizedFurnitureModBlocks.GOLDEN_DESKTOP_FOUNTAIN);
		POTTED_GREEN_PLANT = block(VoxelizedFurnitureModBlocks.POTTED_GREEN_PLANT, new Item.Properties().fireResistant());
		GOLDEN_ZEN_FOUNTAIN = block(VoxelizedFurnitureModBlocks.GOLDEN_ZEN_FOUNTAIN);
		TV = block(VoxelizedFurnitureModBlocks.TV, new Item.Properties().rarity(Rarity.RARE).fireResistant());
		LAPTOP = block(VoxelizedFurnitureModBlocks.LAPTOP, new Item.Properties().rarity(Rarity.RARE).fireResistant());
		ASPHALT = block(VoxelizedFurnitureModBlocks.ASPHALT, new Item.Properties().fireResistant());
		DOUBLE_BED = block(VoxelizedFurnitureModBlocks.DOUBLE_BED);
		SINGLE_BED = block(VoxelizedFurnitureModBlocks.SINGLE_BED);
		OAK_WARDROBE = block(VoxelizedFurnitureModBlocks.OAK_WARDROBE);
		SPRUCE_WARDROBE = block(VoxelizedFurnitureModBlocks.SPRUCE_WARDROBE);
		MANGROVE_WARDROBE = block(VoxelizedFurnitureModBlocks.MANGROVE_WARDROBE);
		JUNGLE_WARDROBE = block(VoxelizedFurnitureModBlocks.JUNGLE_WARDROBE);
		DARK_OAK_WARDROBE = block(VoxelizedFurnitureModBlocks.DARK_OAK_WARDROBE);
		CHERRY_WARDROBE = block(VoxelizedFurnitureModBlocks.CHERRY_WARDROBE);
		BIRCH_WARDROBE = block(VoxelizedFurnitureModBlocks.BIRCH_WARDROBE);
		BAMBOO_WARDROBE = block(VoxelizedFurnitureModBlocks.BAMBOO_WARDROBE);
		ACACIA_WARDROBE = block(VoxelizedFurnitureModBlocks.ACACIA_WARDROBE);
		LIGHT = block(VoxelizedFurnitureModBlocks.LIGHT, new Item.Properties().fireResistant());
		LIGHT_SWITCH_BUTTON = block(VoxelizedFurnitureModBlocks.LIGHT_SWITCH_BUTTON, new Item.Properties().fireResistant());
		LIGHTON = block(VoxelizedFurnitureModBlocks.LIGHTON, new Item.Properties().fireResistant());
		BROWN_BARBECUE = block(VoxelizedFurnitureModBlocks.BROWN_BARBECUE, new Item.Properties().fireResistant());
		KITCHEN_DRAWER = block(VoxelizedFurnitureModBlocks.KITCHEN_DRAWER, new Item.Properties().fireResistant());
		KITCHEN_DRAWER_1 = block(VoxelizedFurnitureModBlocks.KITCHEN_DRAWER_1, new Item.Properties().fireResistant());
		KITCHEN_SINK = block(VoxelizedFurnitureModBlocks.KITCHEN_SINK, new Item.Properties().fireResistant());
		OVEN = block(VoxelizedFurnitureModBlocks.OVEN, new Item.Properties().fireResistant());
		OVEN_VENT = block(VoxelizedFurnitureModBlocks.OVEN_VENT);
		KITCHEN_TABLE = block(VoxelizedFurnitureModBlocks.KITCHEN_TABLE, new Item.Properties().fireResistant());
		KITCHEN_DRAWER_CORNER = block(VoxelizedFurnitureModBlocks.KITCHEN_DRAWER_CORNER, new Item.Properties().fireResistant());
		KITCHEN_FRIDGE = block(VoxelizedFurnitureModBlocks.KITCHEN_FRIDGE, new Item.Properties().fireResistant());
		MODERN_BULKHEAD_LIGHT_OFF = block(VoxelizedFurnitureModBlocks.MODERN_BULKHEAD_LIGHT_OFF, new Item.Properties().fireResistant());
		MODERN_BULKHEAD_LIGHT_ON = block(VoxelizedFurnitureModBlocks.MODERN_BULKHEAD_LIGHT_ON, new Item.Properties().fireResistant());
		OAK_COBBLE_PATH = block(VoxelizedFurnitureModBlocks.OAK_COBBLE_PATH, new Item.Properties().fireResistant());
		SPRUCE_COBBLE_PATH = block(VoxelizedFurnitureModBlocks.SPRUCE_COBBLE_PATH, new Item.Properties().fireResistant());
		MANGROVE_COBBLE_PATH = block(VoxelizedFurnitureModBlocks.MANGROVE_COBBLE_PATH, new Item.Properties().fireResistant());
		JUNGLE_COBBLE_PATH = block(VoxelizedFurnitureModBlocks.JUNGLE_COBBLE_PATH, new Item.Properties().fireResistant());
		DARK_OAK_COBBLE_PATH = block(VoxelizedFurnitureModBlocks.DARK_OAK_COBBLE_PATH, new Item.Properties().fireResistant());
		CHERRY_COBBLE_PATH = block(VoxelizedFurnitureModBlocks.CHERRY_COBBLE_PATH, new Item.Properties().fireResistant());
		BIRCH_COBBLE_PATH = block(VoxelizedFurnitureModBlocks.BIRCH_COBBLE_PATH, new Item.Properties().fireResistant());
		BAMBOO_COBBLE_PATH = block(VoxelizedFurnitureModBlocks.BAMBOO_COBBLE_PATH, new Item.Properties().fireResistant());
		ACACIA_COBBLE_PATH = block(VoxelizedFurnitureModBlocks.ACACIA_COBBLE_PATH, new Item.Properties().fireResistant());
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> supplier) {
		return REGISTRY.registerItem(name, supplier, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return block(block, new Item.Properties());
	}

	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block, Item.Properties properties) {
		return REGISTRY.registerItem(block.getId().getPath(), prop -> new BlockItem(block.get(), prop), properties);
	}
}