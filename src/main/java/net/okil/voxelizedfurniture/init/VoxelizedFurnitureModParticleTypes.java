/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.Registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

public class VoxelizedFurnitureModParticleTypes {
	public static final SimpleParticleType SHOWER_P = FabricParticleTypes.simple(true);

	public static void load() {
		register("shower_p", SHOWER_P);
	}

	private static void register(String registryname, SimpleParticleType element) {
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, registryname), element);
	}
}