/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.client.model.*;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(Dist.CLIENT)
public class VoxelizedFurnitureModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(chair_oak.LAYER_LOCATION, chair_oak::createBodyLayer);
		event.registerLayerDefinition(Modelchair.LAYER_LOCATION, Modelchair::createBodyLayer);
		event.registerLayerDefinition(chair_acacia.LAYER_LOCATION, chair_acacia::createBodyLayer);
		event.registerLayerDefinition(chair_dark_oak.LAYER_LOCATION, chair_dark_oak::createBodyLayer);
		event.registerLayerDefinition(chair_birch.LAYER_LOCATION, chair_birch::createBodyLayer);
		event.registerLayerDefinition(chair_spruce.LAYER_LOCATION, chair_spruce::createBodyLayer);
		event.registerLayerDefinition(chair_jungle.LAYER_LOCATION, chair_jungle::createBodyLayer);
		event.registerLayerDefinition(chair_cherry.LAYER_LOCATION, chair_cherry::createBodyLayer);
		event.registerLayerDefinition(chair_mangrove.LAYER_LOCATION, chair_mangrove::createBodyLayer);
		event.registerLayerDefinition(SlidingDoor.LAYER_LOCATION, SlidingDoor::createBodyLayer);
		event.registerLayerDefinition(chair_bamboo.LAYER_LOCATION, chair_bamboo::createBodyLayer);
	}
}