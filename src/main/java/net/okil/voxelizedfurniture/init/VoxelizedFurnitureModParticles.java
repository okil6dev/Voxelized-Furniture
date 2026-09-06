/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.client.particle.ShowerPParticle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class VoxelizedFurnitureModParticles {
	public static void clientLoad() {
		ParticleProviderRegistry.getInstance().register(VoxelizedFurnitureModParticleTypes.SHOWER_P, ShowerPParticle::provider);
	}
}