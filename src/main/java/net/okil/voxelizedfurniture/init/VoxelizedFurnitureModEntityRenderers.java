/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.client.renderer.*;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class VoxelizedFurnitureModEntityRenderers {
	public static void clientLoad() {
		EntityRendererRegistry.register(VoxelizedFurnitureModEntities.OAK_CHAIR, OakChairRenderer::new);
		EntityRendererRegistry.register(VoxelizedFurnitureModEntities.SPRUCE_CHAIR, SpruceChairRenderer::new);
		EntityRendererRegistry.register(VoxelizedFurnitureModEntities.MANGROVE_CHAIR, MangroveChairRenderer::new);
		EntityRendererRegistry.register(VoxelizedFurnitureModEntities.JUNGLE_CHAIR, JungleChairRenderer::new);
		EntityRendererRegistry.register(VoxelizedFurnitureModEntities.DARK_OAK_CHAIR, DarkOakChairRenderer::new);
		EntityRendererRegistry.register(VoxelizedFurnitureModEntities.CHERRY_CHAIR, CherryChairRenderer::new);
		EntityRendererRegistry.register(VoxelizedFurnitureModEntities.BIRCH_CHAIR, BirchChairRenderer::new);
		EntityRendererRegistry.register(VoxelizedFurnitureModEntities.BAMBOO_CHAIR, BambooChairRenderer::new);
		EntityRendererRegistry.register(VoxelizedFurnitureModEntities.ACACIA_CHAIR, AcaciaChairRenderer::new);
	}
}