/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.block.entity.*;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

@EventBusSubscriber
public class VoxelizedFurnitureModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, VoxelizedFurnitureMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetOakBlockEntity>> CABINET_OAK = register("cabinet_oak", VoxelizedFurnitureModBlocks.CABINET_OAK, CabinetOakBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetBirchBlockEntity>> CABINET_BIRCH = register("cabinet_birch", VoxelizedFurnitureModBlocks.CABINET_BIRCH, CabinetBirchBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetDarkOakBlockEntity>> CABINET_DARK_OAK = register("cabinet_dark_oak", VoxelizedFurnitureModBlocks.CABINET_DARK_OAK, CabinetDarkOakBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetAcaciaBlockEntity>> CABINET_ACACIA = register("cabinet_acacia", VoxelizedFurnitureModBlocks.CABINET_ACACIA, CabinetAcaciaBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetMangroveBlockEntity>> CABINET_MANGROVE = register("cabinet_mangrove", VoxelizedFurnitureModBlocks.CABINET_MANGROVE, CabinetMangroveBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetCherryBlockEntity>> CABINET_CHERRY = register("cabinet_cherry", VoxelizedFurnitureModBlocks.CABINET_CHERRY, CabinetCherryBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetJungleBlockEntity>> CABINET_JUNGLE = register("cabinet_jungle", VoxelizedFurnitureModBlocks.CABINET_JUNGLE, CabinetJungleBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetBambooBlockEntity>> CABINET_BAMBOO = register("cabinet_bamboo", VoxelizedFurnitureModBlocks.CABINET_BAMBOO, CabinetBambooBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FridgeBlockEntity>> FRIDGE = register("fridge", VoxelizedFurnitureModBlocks.FRIDGE, FridgeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetSpruceBlockEntity>> CABINET_SPRUCE = register("cabinet_spruce", VoxelizedFurnitureModBlocks.CABINET_SPRUCE, CabinetSpruceBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShowerBlockEntity>> SHOWER = register("shower", VoxelizedFurnitureModBlocks.SHOWER, ShowerBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OakShelfBlockEntity>> OAK_SHELF = register("oak_shelf", VoxelizedFurnitureModBlocks.OAK_SHELF, OakShelfBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpruceShelfBlockEntity>> SPRUCE_SHELF = register("spruce_shelf", VoxelizedFurnitureModBlocks.SPRUCE_SHELF, SpruceShelfBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MangroveShelfBlockEntity>> MANGROVE_SHELF = register("mangrove_shelf", VoxelizedFurnitureModBlocks.MANGROVE_SHELF, MangroveShelfBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<JungleShelfBlockEntity>> JUNGLE_SHELF = register("jungle_shelf", VoxelizedFurnitureModBlocks.JUNGLE_SHELF, JungleShelfBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DarkOakShelfBlockEntity>> DARK_OAK_SHELF = register("dark_oak_shelf", VoxelizedFurnitureModBlocks.DARK_OAK_SHELF, DarkOakShelfBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CherryShelfBlockEntity>> CHERRY_SHELF = register("cherry_shelf", VoxelizedFurnitureModBlocks.CHERRY_SHELF, CherryShelfBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BirchShelfBlockEntity>> BIRCH_SHELF = register("birch_shelf", VoxelizedFurnitureModBlocks.BIRCH_SHELF, BirchShelfBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BambooShelfBlockEntity>> BAMBOO_SHELF = register("bamboo_shelf", VoxelizedFurnitureModBlocks.BAMBOO_SHELF, BambooShelfBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AcaciaShelfBlockEntity>> ACACIA_SHELF = register("acacia_shelf", VoxelizedFurnitureModBlocks.ACACIA_SHELF, AcaciaShelfBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OakChair1BlockEntity>> OAK_CHAIR_1 = register("oak_chair_1", VoxelizedFurnitureModBlocks.OAK_CHAIR_1, OakChair1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpruceChair1BlockEntity>> SPRUCE_CHAIR_1 = register("spruce_chair_1", VoxelizedFurnitureModBlocks.SPRUCE_CHAIR_1, SpruceChair1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MangroveChair1BlockEntity>> MANGROVE_CHAIR_1 = register("mangrove_chair_1", VoxelizedFurnitureModBlocks.MANGROVE_CHAIR_1, MangroveChair1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<JungleChair1BlockEntity>> JUNGLE_CHAIR_1 = register("jungle_chair_1", VoxelizedFurnitureModBlocks.JUNGLE_CHAIR_1, JungleChair1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DarkOakChair1BlockEntity>> DARK_OAK_CHAIR_1 = register("dark_oak_chair_1", VoxelizedFurnitureModBlocks.DARK_OAK_CHAIR_1, DarkOakChair1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CherryChair1BlockEntity>> CHERRY_CHAIR_1 = register("cherry_chair_1", VoxelizedFurnitureModBlocks.CHERRY_CHAIR_1, CherryChair1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BirchChair1BlockEntity>> BIRCH_CHAIR_1 = register("birch_chair_1", VoxelizedFurnitureModBlocks.BIRCH_CHAIR_1, BirchChair1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BambooChair1BlockEntity>> BAMBOO_CHAIR_1 = register("bamboo_chair_1", VoxelizedFurnitureModBlocks.BAMBOO_CHAIR_1, BambooChair1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AcaciaChair1BlockEntity>> ACACIA_CHAIR_1 = register("acacia_chair_1", VoxelizedFurnitureModBlocks.ACACIA_CHAIR_1, AcaciaChair1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OakWardrobeBlockEntity>> OAK_WARDROBE = register("oak_wardrobe", VoxelizedFurnitureModBlocks.OAK_WARDROBE, OakWardrobeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpruceWardrobeBlockEntity>> SPRUCE_WARDROBE = register("spruce_wardrobe", VoxelizedFurnitureModBlocks.SPRUCE_WARDROBE, SpruceWardrobeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MangroveWardrobeBlockEntity>> MANGROVE_WARDROBE = register("mangrove_wardrobe", VoxelizedFurnitureModBlocks.MANGROVE_WARDROBE, MangroveWardrobeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<JungleWardrobeBlockEntity>> JUNGLE_WARDROBE = register("jungle_wardrobe", VoxelizedFurnitureModBlocks.JUNGLE_WARDROBE, JungleWardrobeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DarkOakWardrobeBlockEntity>> DARK_OAK_WARDROBE = register("dark_oak_wardrobe", VoxelizedFurnitureModBlocks.DARK_OAK_WARDROBE, DarkOakWardrobeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CherryWardrobeBlockEntity>> CHERRY_WARDROBE = register("cherry_wardrobe", VoxelizedFurnitureModBlocks.CHERRY_WARDROBE, CherryWardrobeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BirchWardrobeBlockEntity>> BIRCH_WARDROBE = register("birch_wardrobe", VoxelizedFurnitureModBlocks.BIRCH_WARDROBE, BirchWardrobeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BambooWardrobeBlockEntity>> BAMBOO_WARDROBE = register("bamboo_wardrobe", VoxelizedFurnitureModBlocks.BAMBOO_WARDROBE, BambooWardrobeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AcaciaWardrobeBlockEntity>> ACACIA_WARDROBE = register("acacia_wardrobe", VoxelizedFurnitureModBlocks.ACACIA_WARDROBE, AcaciaWardrobeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenDrawerBlockEntity>> KITCHEN_DRAWER = register("kitchen_drawer", VoxelizedFurnitureModBlocks.KITCHEN_DRAWER, KitchenDrawerBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenDrawer1BlockEntity>> KITCHEN_DRAWER_1 = register("kitchen_drawer_1", VoxelizedFurnitureModBlocks.KITCHEN_DRAWER_1, KitchenDrawer1BlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenSinkBlockEntity>> KITCHEN_SINK = register("kitchen_sink", VoxelizedFurnitureModBlocks.KITCHEN_SINK, KitchenSinkBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OvenVentBlockEntity>> OVEN_VENT = register("oven_vent", VoxelizedFurnitureModBlocks.OVEN_VENT, OvenVentBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenFridgeBlockEntity>> KITCHEN_FRIDGE = register("kitchen_fridge", VoxelizedFurnitureModBlocks.KITCHEN_FRIDGE, KitchenFridgeBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CherryDoorbellBlockEntity>> CHERRY_DOORBELL = register("cherry_doorbell", VoxelizedFurnitureModBlocks.CHERRY_DOORBELL, CherryDoorbellBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BirchDoorbellBlockEntity>> BIRCH_DOORBELL = register("birch_doorbell", VoxelizedFurnitureModBlocks.BIRCH_DOORBELL, BirchDoorbellBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BambooDoorbellBlockEntity>> BAMBOO_DOORBELL = register("bamboo_doorbell", VoxelizedFurnitureModBlocks.BAMBOO_DOORBELL, BambooDoorbellBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AcaciaDoorbellBlockEntity>> ACACIA_DOORBELL = register("acacia_doorbell", VoxelizedFurnitureModBlocks.ACACIA_DOORBELL, AcaciaDoorbellBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MangrovePorchTableBlockEntity>> MANGROVE_PORCH_TABLE = register("mangrove_porch_table", VoxelizedFurnitureModBlocks.MANGROVE_PORCH_TABLE, MangrovePorchTableBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<JunglePorchTableBlockEntity>> JUNGLE_PORCH_TABLE = register("jungle_porch_table", VoxelizedFurnitureModBlocks.JUNGLE_PORCH_TABLE, JunglePorchTableBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DarkOakPorchTableBlockEntity>> DARK_OAK_PORCH_TABLE = register("dark_oak_porch_table", VoxelizedFurnitureModBlocks.DARK_OAK_PORCH_TABLE, DarkOakPorchTableBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CherryPorchTableBlockEntity>> CHERRY_PORCH_TABLE = register("cherry_porch_table", VoxelizedFurnitureModBlocks.CHERRY_PORCH_TABLE, CherryPorchTableBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BirchPorchTableBlockEntity>> BIRCH_PORCH_TABLE = register("birch_porch_table", VoxelizedFurnitureModBlocks.BIRCH_PORCH_TABLE, BirchPorchTableBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BambooPorchTableBlockEntity>> BAMBOO_PORCH_TABLE = register("bamboo_porch_table", VoxelizedFurnitureModBlocks.BAMBOO_PORCH_TABLE, BambooPorchTableBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AcaciaPorchTableBlockEntity>> ACACIA_PORCH_TABLE = register("acacia_porch_table", VoxelizedFurnitureModBlocks.ACACIA_PORCH_TABLE, AcaciaPorchTableBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpruceToiletRollBlockEntity>> SPRUCE_TOILET_ROLL = register("spruce_toilet_roll", VoxelizedFurnitureModBlocks.SPRUCE_TOILET_ROLL, SpruceToiletRollBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MangroveToiletRollBlockEntity>> MANGROVE_TOILET_ROLL = register("mangrove_toilet_roll", VoxelizedFurnitureModBlocks.MANGROVE_TOILET_ROLL, MangroveToiletRollBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<JungleToiletRollBlockEntity>> JUNGLE_TOILET_ROLL = register("jungle_toilet_roll", VoxelizedFurnitureModBlocks.JUNGLE_TOILET_ROLL, JungleToiletRollBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DarkOakToiletRollBlockEntity>> DARK_OAK_TOILET_ROLL = register("dark_oak_toilet_roll", VoxelizedFurnitureModBlocks.DARK_OAK_TOILET_ROLL, DarkOakToiletRollBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CherryToiletRollBlockEntity>> CHERRY_TOILET_ROLL = register("cherry_toilet_roll", VoxelizedFurnitureModBlocks.CHERRY_TOILET_ROLL, CherryToiletRollBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BirchToiletRollBlockEntity>> BIRCH_TOILET_ROLL = register("birch_toilet_roll", VoxelizedFurnitureModBlocks.BIRCH_TOILET_ROLL, BirchToiletRollBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BambooToiletRollBlockEntity>> BAMBOO_TOILET_ROLL = register("bamboo_toilet_roll", VoxelizedFurnitureModBlocks.BAMBOO_TOILET_ROLL, BambooToiletRollBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AcaciaToiletRollBlockEntity>> ACACIA_TOILET_ROLL = register("acacia_toilet_roll", VoxelizedFurnitureModBlocks.ACACIA_TOILET_ROLL, AcaciaToiletRollBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
		return REGISTRY.register(registryname, () -> new BlockEntityType(supplier, block.get()));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CABINET_OAK.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CABINET_BIRCH.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CABINET_DARK_OAK.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CABINET_ACACIA.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CABINET_MANGROVE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CABINET_CHERRY.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CABINET_JUNGLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CABINET_BAMBOO.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FRIDGE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CABINET_SPRUCE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SHOWER.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, OAK_SHELF.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SPRUCE_SHELF.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, MANGROVE_SHELF.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, JUNGLE_SHELF.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DARK_OAK_SHELF.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CHERRY_SHELF.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BIRCH_SHELF.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BAMBOO_SHELF.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ACACIA_SHELF.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, OAK_CHAIR_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SPRUCE_CHAIR_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, MANGROVE_CHAIR_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, JUNGLE_CHAIR_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DARK_OAK_CHAIR_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CHERRY_CHAIR_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BIRCH_CHAIR_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BAMBOO_CHAIR_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ACACIA_CHAIR_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, OAK_WARDROBE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SPRUCE_WARDROBE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, MANGROVE_WARDROBE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, JUNGLE_WARDROBE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DARK_OAK_WARDROBE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CHERRY_WARDROBE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BIRCH_WARDROBE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BAMBOO_WARDROBE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ACACIA_WARDROBE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, KITCHEN_DRAWER.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, KITCHEN_DRAWER_1.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, KITCHEN_SINK.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, OVEN_VENT.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, KITCHEN_FRIDGE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CHERRY_DOORBELL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BIRCH_DOORBELL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BAMBOO_DOORBELL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ACACIA_DOORBELL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, MANGROVE_PORCH_TABLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, JUNGLE_PORCH_TABLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DARK_OAK_PORCH_TABLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CHERRY_PORCH_TABLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BIRCH_PORCH_TABLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BAMBOO_PORCH_TABLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ACACIA_PORCH_TABLE.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, SPRUCE_TOILET_ROLL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, MANGROVE_TOILET_ROLL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, JUNGLE_TOILET_ROLL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, DARK_OAK_TOILET_ROLL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CHERRY_TOILET_ROLL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BIRCH_TOILET_ROLL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BAMBOO_TOILET_ROLL.get(), SidedInvWrapper::new);
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ACACIA_TOILET_ROLL.get(), SidedInvWrapper::new);
	}
}