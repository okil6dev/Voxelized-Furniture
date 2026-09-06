/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

public class VoxelizedFurnitureModTabs {
	public static ResourceKey<CreativeModeTab> TAB_VF_LIVINGROOM = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "vf_livingroom"));
	public static ResourceKey<CreativeModeTab> TAB_KITCHEN = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "kitchen"));
	public static ResourceKey<CreativeModeTab> TAB_FOOD = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "food"));
	public static ResourceKey<CreativeModeTab> TAB_BATHROOM = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "bathroom"));
	public static ResourceKey<CreativeModeTab> TAB_NATURE = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "nature"));
	public static ResourceKey<CreativeModeTab> TAB_VF_ROADS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "vf_roads"));
	public static ResourceKey<CreativeModeTab> TAB_BED_ROOM = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "bed_room"));
	public static ResourceKey<CreativeModeTab> TAB_ELECTRICAL = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "electrical"));
	public static ResourceKey<CreativeModeTab> TAB_OUTSIDE = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "outside"));
	public static ResourceKey<CreativeModeTab> TAB_GEODE = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "geode"));
	public static ResourceKey<CreativeModeTab> TAB_MINIATURE = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "miniature"));

	public static void load() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_VF_LIVINGROOM,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.vf_livingroom")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.CABINET_OAK)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.CABINET_OAK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CABINET_BIRCH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CABINET_DARK_OAK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CABINET_ACACIA.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CABINET_MANGROVE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CABINET_CHERRY.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CABINET_JUNGLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CABINET_BAMBOO.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CABINET_SPRUCE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_SHELF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_SHELF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_SHELF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_SHELF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_SHELF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_SHELF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_SHELF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_SHELF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_SHELF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_CHAIR_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_CHAIR_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_CHAIR_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_CHAIR_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_CHAIR_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_CHAIR_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_CHAIR_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_CHAIR_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_CHAIR_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.GOLDEN_DESKTOP_FOUNTAIN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.GOLDEN_ZEN_FOUNTAIN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TV.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.LAPTOP.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.GREEN_BAMBOO_PLANT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BARREL_PLANT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SEED_IN_POT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_DRAWER.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KITCHEN,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.kitchen")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.FRIDGE)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.FRIDGE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_DRAWER_1.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OVEN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OVEN_VENT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_DRAWER_CORNER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_FRIDGE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.PAN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.AIRFRYER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MICROWAVE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_WHITE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_BLUE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_BROWN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_CYAN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_GRAY.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_GREEN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_LIGHT_BLUE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_LIGHT_GRAY.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_LIME.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_MAGENTA.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_ORANGE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_PINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_PURPLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_RED.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.TOASTER_YELLOW.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.WOODEN_KITCHEN_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.WOODEN_OVEN_COOKTOP.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.WOODEN_KITCHEN_SINK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.WOODEN_KITCHEN_EXHAUSTHOOD.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.WOODEN_KITCHEN_CABINET_ABOVE.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_FOOD,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.food")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.HONEY_JAR)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.JAM_JAR.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.HONEY_JAR.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_BATHROOM,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.bathroom")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.SHOWER)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.SHOWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SOAP.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_TOILET_ROLL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_TOILET_ROLL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_TOILET_ROLL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_TOILET_ROLL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_TOILET_ROLL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_TOILET_ROLL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_TOILET_ROLL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_TOILET_ROLL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_TOILET_ROLL.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_NATURE,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.nature")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.SEED_IN_POT)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.GOLDEN_DESKTOP_FOUNTAIN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.POTTED_GREEN_PLANT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.GOLDEN_ZEN_FOUNTAIN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.GREEN_BAMBOO_PLANT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BARREL_PLANT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SEED_IN_POT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.YUCCA_PLANT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.WHEAT_PLANT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CACTUS_PLANT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_CLUSTER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SACK_OF_SOIL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SOFT_OAK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SOFT_PALE_OAK.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SOFT_DARK_OAK.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_VF_ROADS,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.vf_roads")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.ASPHALT)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.ASPHALT.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_BED_ROOM,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.bed_room")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.DOUBLE_BED)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.DOUBLE_BED.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SINGLE_BED.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_WARDROBE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_PORCH_TABLE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_DRAWER.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_DRAWER.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_ELECTRICAL,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.electrical")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.LIGHT)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.LIGHT.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.LIGHT_SWITCH_BUTTON.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MODERN_BULKHEAD_LIGHT_OFF.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CUBE_CEILING_LIGHT.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_OUTSIDE,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.outside")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.OAK_COBBLE_PATH)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_COBBLE_PATH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_COBBLE_PATH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_COBBLE_PATH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_COBBLE_PATH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_COBBLE_PATH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_COBBLE_PATH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_COBBLE_PATH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_COBBLE_PATH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_COBBLE_PATH.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.OAK_DOORBELL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_DOORBELL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_DOORBELL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_DOORBELL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_DOORBELL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_DOORBELL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_DOORBELL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_DOORBELL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_DOORBELL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BRICKS.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.WOODEN_CRATE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.SACK_OF_SOIL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.GRAY_WATERING_CAN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.BLUE_WATERING_CAN.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.GREEN_WATERING_CAN.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_GEODE,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.geode")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.AMETHYST_GEODE)).type(CreativeModeTab.Type.SEARCH)
						.backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.AMETHYST_GEODE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.EMERALD_GEODE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.DIAMOND_GEODE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.REDSTONE_GEODE.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.KYANITE.asItem());
						}).build());
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_MINIATURE,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.voxelized_furniture.miniature")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.MINIATURE_RUINED_NETHER_PORTAL))
						.type(CreativeModeTab.Type.SEARCH).backgroundTexture(Identifier.withDefaultNamespace("textures/gui/container/creative_inventory/tab_item_search.png")).displayItems((parameters, tabData) -> {
							tabData.accept(VoxelizedFurnitureModBlocks.MINIATURE_RUINED_NETHER_PORTAL.asItem());
							tabData.accept(VoxelizedFurnitureModBlocks.MINIATURE_WATER_RUIN.asItem());
						}).build());
	}
}