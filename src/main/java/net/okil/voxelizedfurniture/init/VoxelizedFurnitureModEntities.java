/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.entity.*;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class VoxelizedFurnitureModEntities {
	public static EntityType<OakChairEntity> OAK_CHAIR = register("oak_chair", EntityType.Builder.<OakChairEntity>of(OakChairEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3)

			.sized(0.6f, 1.25f));
	public static EntityType<SpruceChairEntity> SPRUCE_CHAIR = register("spruce_chair", EntityType.Builder.<SpruceChairEntity>of(SpruceChairEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3)

			.sized(0.6f, 1.25f));
	public static EntityType<MangroveChairEntity> MANGROVE_CHAIR = register("mangrove_chair", EntityType.Builder.<MangroveChairEntity>of(MangroveChairEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3)

			.sized(0.6f, 1.25f));
	public static EntityType<JungleChairEntity> JUNGLE_CHAIR = register("jungle_chair", EntityType.Builder.<JungleChairEntity>of(JungleChairEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3)

			.sized(0.6f, 1.25f));
	public static EntityType<DarkOakChairEntity> DARK_OAK_CHAIR = register("dark_oak_chair", EntityType.Builder.<DarkOakChairEntity>of(DarkOakChairEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3)

			.sized(0.6f, 1.25f));
	public static EntityType<CherryChairEntity> CHERRY_CHAIR = register("cherry_chair", EntityType.Builder.<CherryChairEntity>of(CherryChairEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3)

			.sized(0.6f, 1.25f));
	public static EntityType<BirchChairEntity> BIRCH_CHAIR = register("birch_chair", EntityType.Builder.<BirchChairEntity>of(BirchChairEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3)

			.sized(0.6f, 1.25f));
	public static EntityType<BambooChairEntity> BAMBOO_CHAIR = register("bamboo_chair", EntityType.Builder.<BambooChairEntity>of(BambooChairEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3)

			.sized(0.6f, 1.25f));
	public static EntityType<AcaciaChairEntity> ACACIA_CHAIR = register("acacia_chair", EntityType.Builder.<AcaciaChairEntity>of(AcaciaChairEntity::new, MobCategory.MONSTER).clientTrackingRange(64).updateInterval(3)

			.sized(0.6f, 1.25f));

	public static void load() {
		init();
		registerAttributes();
	}

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> EntityType<T> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, registryname),
				(EntityType<T>) entityTypeBuilder.build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, registryname))));
	}

	public static void init() {
	}

	public static void registerAttributes() {
		FabricDefaultAttributeRegistry.register(OAK_CHAIR, OakChairEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(SPRUCE_CHAIR, SpruceChairEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(MANGROVE_CHAIR, MangroveChairEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(JUNGLE_CHAIR, JungleChairEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(DARK_OAK_CHAIR, DarkOakChairEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(CHERRY_CHAIR, CherryChairEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(BIRCH_CHAIR, BirchChairEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(BAMBOO_CHAIR, BambooChairEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ACACIA_CHAIR, AcaciaChairEntity.createAttributes());
	}
}