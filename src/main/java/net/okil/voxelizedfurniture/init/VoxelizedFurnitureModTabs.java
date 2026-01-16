/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class VoxelizedFurnitureModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VoxelizedFurnitureMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VF_LIVINGROOM = REGISTRY.register("vf_livingroom",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.voxelized_furniture.vf_livingroom")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.CABINET_OAK.get())).displayItems((parameters, tabData) -> {
				tabData.accept(VoxelizedFurnitureModBlocks.CABINET_OAK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CABINET_BIRCH.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CABINET_DARK_OAK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CABINET_ACACIA.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CABINET_MANGROVE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CABINET_CHERRY.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CABINET_JUNGLE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CABINET_BAMBOO.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CABINET_SPRUCE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.OAK_SHELF.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_SHELF.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_SHELF.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_SHELF.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_SHELF.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_SHELF.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_SHELF.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_SHELF.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_SHELF.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.OAK_CHAIR_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_CHAIR_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_CHAIR_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_CHAIR_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_CHAIR_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_CHAIR_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_CHAIR_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_CHAIR_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_CHAIR_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.GOLDEN_DESKTOP_FOUNTAIN.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.GOLDEN_ZEN_FOUNTAIN.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.TV.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.LAPTOP.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.OAK_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_WARDROBE.get().asItem());
			}).withSearchBar().build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> KITCHEN = REGISTRY.register("kitchen",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.voxelized_furniture.kitchen")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.FRIDGE.get())).displayItems((parameters, tabData) -> {
				tabData.accept(VoxelizedFurnitureModBlocks.FRIDGE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.OAK_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_DRAWER.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_DRAWER_1.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_SINK.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.OVEN.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.OVEN_VENT.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_TABLE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_DRAWER_CORNER.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.KITCHEN_FRIDGE.get().asItem());
			}).withSearchBar().withTabsBefore(VF_LIVINGROOM.getId()).build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NATURE = REGISTRY.register("nature",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.voxelized_furniture.nature")).icon(() -> new ItemStack(Blocks.SHORT_DRY_GRASS)).displayItems((parameters, tabData) -> {
				tabData.accept(VoxelizedFurnitureModBlocks.GREENWALL.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.POTTED_GREEN_PLANT.get().asItem());
			}).withSearchBar().withTabsBefore(KITCHEN.getId()).build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FOOD = REGISTRY.register("food",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.voxelized_furniture.food")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.JAM_JAR.get())).displayItems((parameters, tabData) -> {
				tabData.accept(VoxelizedFurnitureModBlocks.JAM_JAR.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.HONEY_JAR.get().asItem());
			}).withSearchBar().withTabsBefore(NATURE.getId()).build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BATHROOM = REGISTRY.register("bathroom",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.voxelized_furniture.bathroom")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.SHOWER.get())).displayItems((parameters, tabData) -> {
				tabData.accept(VoxelizedFurnitureModBlocks.SHOWER.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.SOAP.get().asItem());
			}).withSearchBar().withTabsBefore(FOOD.getId()).build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VF_ROADS = REGISTRY.register("vf_roads",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.voxelized_furniture.vf_roads")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.ASPHALT.get())).displayItems((parameters, tabData) -> {
				tabData.accept(VoxelizedFurnitureModBlocks.ASPHALT.get().asItem());
			}).withSearchBar().withTabsBefore(BATHROOM.getId()).build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BED_ROOM = REGISTRY.register("bed_room",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.voxelized_furniture.bed_room")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.DOUBLE_BED.get())).displayItems((parameters, tabData) -> {
				tabData.accept(VoxelizedFurnitureModBlocks.DOUBLE_BED.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.SINGLE_BED.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.OAK_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.SPRUCE_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.MANGROVE_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.JUNGLE_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.DARK_OAK_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.CHERRY_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BIRCH_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.BAMBOO_WARDROBE.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.ACACIA_WARDROBE.get().asItem());
			}).withSearchBar().withTabsBefore(VF_ROADS.getId()).build());
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ELECTRICAL = REGISTRY.register("electrical",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.voxelized_furniture.electrical")).icon(() -> new ItemStack(VoxelizedFurnitureModBlocks.LIGHT.get())).displayItems((parameters, tabData) -> {
				tabData.accept(VoxelizedFurnitureModBlocks.LIGHT.get().asItem());
				tabData.accept(VoxelizedFurnitureModBlocks.LIGHT_SWITCH_BUTTON.get().asItem());
			}).withSearchBar().withTabsBefore(BED_ROOM.getId()).build());
}