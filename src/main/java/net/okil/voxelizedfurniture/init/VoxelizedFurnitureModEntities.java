/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.entity.*;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

@EventBusSubscriber
public class VoxelizedFurnitureModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, VoxelizedFurnitureMod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<OakChairEntity>> OAK_CHAIR = register("oak_chair",
			EntityType.Builder.<OakChairEntity>of(OakChairEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.25f));
	public static final DeferredHolder<EntityType<?>, EntityType<SpruceChairEntity>> SPRUCE_CHAIR = register("spruce_chair",
			EntityType.Builder.<SpruceChairEntity>of(SpruceChairEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.25f));
	public static final DeferredHolder<EntityType<?>, EntityType<MangroveChairEntity>> MANGROVE_CHAIR = register("mangrove_chair",
			EntityType.Builder.<MangroveChairEntity>of(MangroveChairEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.25f));
	public static final DeferredHolder<EntityType<?>, EntityType<JungleChairEntity>> JUNGLE_CHAIR = register("jungle_chair",
			EntityType.Builder.<JungleChairEntity>of(JungleChairEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.25f));
	public static final DeferredHolder<EntityType<?>, EntityType<DarkOakChairEntity>> DARK_OAK_CHAIR = register("dark_oak_chair",
			EntityType.Builder.<DarkOakChairEntity>of(DarkOakChairEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.25f));
	public static final DeferredHolder<EntityType<?>, EntityType<CherryChairEntity>> CHERRY_CHAIR = register("cherry_chair",
			EntityType.Builder.<CherryChairEntity>of(CherryChairEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.25f));
	public static final DeferredHolder<EntityType<?>, EntityType<BirchChairEntity>> BIRCH_CHAIR = register("birch_chair",
			EntityType.Builder.<BirchChairEntity>of(BirchChairEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.25f));
	public static final DeferredHolder<EntityType<?>, EntityType<BambooChairEntity>> BAMBOO_CHAIR = register("bamboo_chair",
			EntityType.Builder.<BambooChairEntity>of(BambooChairEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.25f));
	public static final DeferredHolder<EntityType<?>, EntityType<AcaciaChairEntity>> ACACIA_CHAIR = register("acacia_chair",
			EntityType.Builder.<AcaciaChairEntity>of(AcaciaChairEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.25f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, registryname))));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		OakChairEntity.init(event);
		SpruceChairEntity.init(event);
		MangroveChairEntity.init(event);
		JungleChairEntity.init(event);
		DarkOakChairEntity.init(event);
		CherryChairEntity.init(event);
		BirchChairEntity.init(event);
		BambooChairEntity.init(event);
		AcaciaChairEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(OAK_CHAIR.get(), OakChairEntity.createAttributes().build());
		event.put(SPRUCE_CHAIR.get(), SpruceChairEntity.createAttributes().build());
		event.put(MANGROVE_CHAIR.get(), MangroveChairEntity.createAttributes().build());
		event.put(JUNGLE_CHAIR.get(), JungleChairEntity.createAttributes().build());
		event.put(DARK_OAK_CHAIR.get(), DarkOakChairEntity.createAttributes().build());
		event.put(CHERRY_CHAIR.get(), CherryChairEntity.createAttributes().build());
		event.put(BIRCH_CHAIR.get(), BirchChairEntity.createAttributes().build());
		event.put(BAMBOO_CHAIR.get(), BambooChairEntity.createAttributes().build());
		event.put(ACACIA_CHAIR.get(), AcaciaChairEntity.createAttributes().build());
	}
}