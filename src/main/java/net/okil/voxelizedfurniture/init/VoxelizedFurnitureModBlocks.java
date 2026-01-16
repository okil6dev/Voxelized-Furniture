/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.block.*;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public class VoxelizedFurnitureModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(VoxelizedFurnitureMod.MODID);
	public static final DeferredBlock<Block> CABINET_OAK;
	public static final DeferredBlock<Block> CABINET_BIRCH;
	public static final DeferredBlock<Block> CABINET_DARK_OAK;
	public static final DeferredBlock<Block> CABINET_ACACIA;
	public static final DeferredBlock<Block> CABINET_MANGROVE;
	public static final DeferredBlock<Block> CABINET_CHERRY;
	public static final DeferredBlock<Block> CABINET_JUNGLE;
	public static final DeferredBlock<Block> CABINET_BAMBOO;
	public static final DeferredBlock<Block> FRIDGE;
	public static final DeferredBlock<Block> GREENWALL;
	public static final DeferredBlock<Block> OAK_SINK;
	public static final DeferredBlock<Block> SPRUCE_SINK;
	public static final DeferredBlock<Block> MANGROVE_SINK;
	public static final DeferredBlock<Block> JUNGLE_SINK;
	public static final DeferredBlock<Block> DARK_OAK_SINK;
	public static final DeferredBlock<Block> BIRCH_SINK;
	public static final DeferredBlock<Block> ACACIA_SINK;
	public static final DeferredBlock<Block> CHERRY_SINK;
	public static final DeferredBlock<Block> BAMBOO_SINK;
	public static final DeferredBlock<Block> CABINET_SPRUCE;
	public static final DeferredBlock<Block> JAM_JAR;
	public static final DeferredBlock<Block> HONEY_JAR;
	public static final DeferredBlock<Block> SHOWER;
	public static final DeferredBlock<Block> OAK_SHELF;
	public static final DeferredBlock<Block> SPRUCE_SHELF;
	public static final DeferredBlock<Block> MANGROVE_SHELF;
	public static final DeferredBlock<Block> JUNGLE_SHELF;
	public static final DeferredBlock<Block> DARK_OAK_SHELF;
	public static final DeferredBlock<Block> CHERRY_SHELF;
	public static final DeferredBlock<Block> BIRCH_SHELF;
	public static final DeferredBlock<Block> BAMBOO_SHELF;
	public static final DeferredBlock<Block> ACACIA_SHELF;
	public static final DeferredBlock<Block> SOAP;
	public static final DeferredBlock<Block> OAK_CHAIR_1;
	public static final DeferredBlock<Block> SPRUCE_CHAIR_1;
	public static final DeferredBlock<Block> MANGROVE_CHAIR_1;
	public static final DeferredBlock<Block> JUNGLE_CHAIR_1;
	public static final DeferredBlock<Block> DARK_OAK_CHAIR_1;
	public static final DeferredBlock<Block> CHERRY_CHAIR_1;
	public static final DeferredBlock<Block> BIRCH_CHAIR_1;
	public static final DeferredBlock<Block> BAMBOO_CHAIR_1;
	public static final DeferredBlock<Block> ACACIA_CHAIR_1;
	public static final DeferredBlock<Block> GROUND_DEV_TEX;
	public static final DeferredBlock<Block> GOLDEN_DESKTOP_FOUNTAIN;
	public static final DeferredBlock<Block> POTTED_GREEN_PLANT;
	public static final DeferredBlock<Block> GOLDEN_ZEN_FOUNTAIN;
	public static final DeferredBlock<Block> TV;
	public static final DeferredBlock<Block> LAPTOP;
	public static final DeferredBlock<Block> ASPHALT;
	public static final DeferredBlock<Block> DOUBLE_BED;
	public static final DeferredBlock<Block> SINGLE_BED;
	public static final DeferredBlock<Block> OAK_WARDROBE;
	public static final DeferredBlock<Block> SPRUCE_WARDROBE;
	public static final DeferredBlock<Block> MANGROVE_WARDROBE;
	public static final DeferredBlock<Block> JUNGLE_WARDROBE;
	public static final DeferredBlock<Block> DARK_OAK_WARDROBE;
	public static final DeferredBlock<Block> CHERRY_WARDROBE;
	public static final DeferredBlock<Block> BIRCH_WARDROBE;
	public static final DeferredBlock<Block> BAMBOO_WARDROBE;
	public static final DeferredBlock<Block> ACACIA_WARDROBE;
	public static final DeferredBlock<Block> LIGHT;
	public static final DeferredBlock<Block> LIGHT_SWITCH_BUTTON;
	public static final DeferredBlock<Block> LIGHTON;
	public static final DeferredBlock<Block> BROWN_BARBECUE;
	public static final DeferredBlock<Block> KITCHEN_DRAWER;
	public static final DeferredBlock<Block> KITCHEN_DRAWER_1;
	public static final DeferredBlock<Block> KITCHEN_SINK;
	public static final DeferredBlock<Block> OVEN;
	public static final DeferredBlock<Block> OVEN_VENT;
	public static final DeferredBlock<Block> KITCHEN_TABLE;
	public static final DeferredBlock<Block> KITCHEN_DRAWER_CORNER;
	public static final DeferredBlock<Block> KITCHEN_FRIDGE;
	static {
		CABINET_OAK = register("cabinet_oak", CabinetOakBlock::new);
		CABINET_BIRCH = register("cabinet_birch", CabinetBirchBlock::new);
		CABINET_DARK_OAK = register("cabinet_dark_oak", CabinetDarkOakBlock::new);
		CABINET_ACACIA = register("cabinet_acacia", CabinetAcaciaBlock::new);
		CABINET_MANGROVE = register("cabinet_mangrove", CabinetMangroveBlock::new);
		CABINET_CHERRY = register("cabinet_cherry", CabinetCherryBlock::new);
		CABINET_JUNGLE = register("cabinet_jungle", CabinetJungleBlock::new);
		CABINET_BAMBOO = register("cabinet_bamboo", CabinetBambooBlock::new);
		FRIDGE = register("fridge", FridgeBlock::new);
		GREENWALL = register("greenwall", GreenwallBlock::new);
		OAK_SINK = register("oak_sink", OakSinkBlock::new);
		SPRUCE_SINK = register("spruce_sink", SpruceSinkBlock::new);
		MANGROVE_SINK = register("mangrove_sink", MangroveSinkBlock::new);
		JUNGLE_SINK = register("jungle_sink", JungleSinkBlock::new);
		DARK_OAK_SINK = register("dark_oak_sink", DarkOakSinkBlock::new);
		BIRCH_SINK = register("birch_sink", BirchSinkBlock::new);
		ACACIA_SINK = register("acacia_sink", AcaciaSinkBlock::new);
		CHERRY_SINK = register("cherry_sink", CherrySinkBlock::new);
		BAMBOO_SINK = register("bamboo_sink", BambooSinkBlock::new);
		CABINET_SPRUCE = register("cabinet_spruce", CabinetSpruceBlock::new);
		JAM_JAR = register("jam_jar", JamJarBlock::new);
		HONEY_JAR = register("honey_jar", HoneyJarBlock::new);
		SHOWER = register("shower", ShowerBlock::new);
		OAK_SHELF = register("oak_shelf", OakShelfBlock::new);
		SPRUCE_SHELF = register("spruce_shelf", SpruceShelfBlock::new);
		MANGROVE_SHELF = register("mangrove_shelf", MangroveShelfBlock::new);
		JUNGLE_SHELF = register("jungle_shelf", JungleShelfBlock::new);
		DARK_OAK_SHELF = register("dark_oak_shelf", DarkOakShelfBlock::new);
		CHERRY_SHELF = register("cherry_shelf", CherryShelfBlock::new);
		BIRCH_SHELF = register("birch_shelf", BirchShelfBlock::new);
		BAMBOO_SHELF = register("bamboo_shelf", BambooShelfBlock::new);
		ACACIA_SHELF = register("acacia_shelf", AcaciaShelfBlock::new);
		SOAP = register("soap", SoapBlock::new);
		OAK_CHAIR_1 = register("oak_chair_1", OakChair1Block::new);
		SPRUCE_CHAIR_1 = register("spruce_chair_1", SpruceChair1Block::new);
		MANGROVE_CHAIR_1 = register("mangrove_chair_1", MangroveChair1Block::new);
		JUNGLE_CHAIR_1 = register("jungle_chair_1", JungleChair1Block::new);
		DARK_OAK_CHAIR_1 = register("dark_oak_chair_1", DarkOakChair1Block::new);
		CHERRY_CHAIR_1 = register("cherry_chair_1", CherryChair1Block::new);
		BIRCH_CHAIR_1 = register("birch_chair_1", BirchChair1Block::new);
		BAMBOO_CHAIR_1 = register("bamboo_chair_1", BambooChair1Block::new);
		ACACIA_CHAIR_1 = register("acacia_chair_1", AcaciaChair1Block::new);
		GROUND_DEV_TEX = register("ground_dev_tex", GroundDevTexBlock::new);
		GOLDEN_DESKTOP_FOUNTAIN = register("golden_desktop_fountain", GoldenDesktopFountainBlock::new);
		POTTED_GREEN_PLANT = register("potted_green_plant", PottedGreenPlantBlock::new);
		GOLDEN_ZEN_FOUNTAIN = register("golden_zen_fountain", GoldenZenFountainBlock::new);
		TV = register("tv", TvBlock::new);
		LAPTOP = register("laptop", LaptopBlock::new);
		ASPHALT = register("asphalt", AsphaltBlock::new);
		DOUBLE_BED = register("double_bed", DoubleBedBlock::new);
		SINGLE_BED = register("single_bed", SingleBedBlock::new);
		OAK_WARDROBE = register("oak_wardrobe", OakWardrobeBlock::new);
		SPRUCE_WARDROBE = register("spruce_wardrobe", SpruceWardrobeBlock::new);
		MANGROVE_WARDROBE = register("mangrove_wardrobe", MangroveWardrobeBlock::new);
		JUNGLE_WARDROBE = register("jungle_wardrobe", JungleWardrobeBlock::new);
		DARK_OAK_WARDROBE = register("dark_oak_wardrobe", DarkOakWardrobeBlock::new);
		CHERRY_WARDROBE = register("cherry_wardrobe", CherryWardrobeBlock::new);
		BIRCH_WARDROBE = register("birch_wardrobe", BirchWardrobeBlock::new);
		BAMBOO_WARDROBE = register("bamboo_wardrobe", BambooWardrobeBlock::new);
		ACACIA_WARDROBE = register("acacia_wardrobe", AcaciaWardrobeBlock::new);
		LIGHT = register("light", LightBlock::new);
		LIGHT_SWITCH_BUTTON = register("light_switch_button", LightSwitchButtonBlock::new);
		LIGHTON = register("lighton", LightonBlock::new);
		BROWN_BARBECUE = register("brown_barbecue", BrownBarbecueBlock::new);
		KITCHEN_DRAWER = register("kitchen_drawer", KitchenDrawerBlock::new);
		KITCHEN_DRAWER_1 = register("kitchen_drawer_1", KitchenDrawer1Block::new);
		KITCHEN_SINK = register("kitchen_sink", KitchenSinkBlock::new);
		OVEN = register("oven", OvenBlock::new);
		OVEN_VENT = register("oven_vent", OvenVentBlock::new);
		KITCHEN_TABLE = register("kitchen_table", KitchenTableBlock::new);
		KITCHEN_DRAWER_CORNER = register("kitchen_drawer_corner", KitchenDrawerCornerBlock::new);
		KITCHEN_FRIDGE = register("kitchen_fridge", KitchenFridgeBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> supplier) {
		return REGISTRY.registerBlock(name, supplier);
	}

	@EventBusSubscriber(Dist.CLIENT)
	public static class BlocksClientSideHandler {
		@SubscribeEvent
		public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
			GreenwallBlock.blockColorLoad(event);
			OakShelfBlock.blockColorLoad(event);
			SpruceShelfBlock.blockColorLoad(event);
			MangroveShelfBlock.blockColorLoad(event);
			JungleShelfBlock.blockColorLoad(event);
			DarkOakShelfBlock.blockColorLoad(event);
			CherryShelfBlock.blockColorLoad(event);
			BirchShelfBlock.blockColorLoad(event);
			BambooShelfBlock.blockColorLoad(event);
			AcaciaShelfBlock.blockColorLoad(event);
			PottedGreenPlantBlock.blockColorLoad(event);
		}
	}
}