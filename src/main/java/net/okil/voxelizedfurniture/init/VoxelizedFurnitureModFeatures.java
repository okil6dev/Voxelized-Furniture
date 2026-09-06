/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.world.features.BambooclusterfeatureFeature;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;

import java.util.function.Predicate;

public class VoxelizedFurnitureModFeatures {
	public static void load() {
		register("bambooclusterfeature", new BambooclusterfeatureFeature(), BambooclusterfeatureFeature.GENERATE_BIOMES, GenerationStep.Decoration.SURFACE_STRUCTURES);
	}

	private static void register(String registryname, Feature feature, Predicate<BiomeSelectionContext> biomes, GenerationStep.Decoration stage) {
		register(registryname, feature);
		BiomeModifications.addFeature(biomes, stage, ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, registryname)));
	}

	private static void register(String registryname, Feature feature) {
		Registry.register(BuiltInRegistries.FEATURE, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, registryname), feature);
	}
}