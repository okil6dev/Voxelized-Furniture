/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.client.renderer.*;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(Dist.CLIENT)
public class VoxelizedFurnitureModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(VoxelizedFurnitureModEntities.OAK_CHAIR.get(), OakChairRenderer::new);
		event.registerEntityRenderer(VoxelizedFurnitureModEntities.SPRUCE_CHAIR.get(), SpruceChairRenderer::new);
		event.registerEntityRenderer(VoxelizedFurnitureModEntities.MANGROVE_CHAIR.get(), MangroveChairRenderer::new);
		event.registerEntityRenderer(VoxelizedFurnitureModEntities.JUNGLE_CHAIR.get(), JungleChairRenderer::new);
		event.registerEntityRenderer(VoxelizedFurnitureModEntities.DARK_OAK_CHAIR.get(), DarkOakChairRenderer::new);
		event.registerEntityRenderer(VoxelizedFurnitureModEntities.CHERRY_CHAIR.get(), CherryChairRenderer::new);
		event.registerEntityRenderer(VoxelizedFurnitureModEntities.BIRCH_CHAIR.get(), BirchChairRenderer::new);
		event.registerEntityRenderer(VoxelizedFurnitureModEntities.BAMBOO_CHAIR.get(), BambooChairRenderer::new);
		event.registerEntityRenderer(VoxelizedFurnitureModEntities.ACACIA_CHAIR.get(), AcaciaChairRenderer::new);
	}
}