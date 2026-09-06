/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.client.model.*;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class VoxelizedFurnitureModModels {
	public static void clientLoad() {
		ModelLayerRegistry.registerModelLayer(chair_acacia.LAYER_LOCATION, chair_acacia::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(chair_jungle.LAYER_LOCATION, chair_jungle::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(ModelCustomModel.LAYER_LOCATION, ModelCustomModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(Modelchair.LAYER_LOCATION, Modelchair::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(chair_dark_oak.LAYER_LOCATION, chair_dark_oak::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(chair_birch.LAYER_LOCATION, chair_birch::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(chair_spruce.LAYER_LOCATION, chair_spruce::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(chair_bamboo.LAYER_LOCATION, chair_bamboo::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(chair_oak.LAYER_LOCATION, chair_oak::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(ModelPottedBambooPlant.LAYER_LOCATION, ModelPottedBambooPlant::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(chair_cherry.LAYER_LOCATION, chair_cherry::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(chair_mangrove.LAYER_LOCATION, chair_mangrove::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(SlidingDoor.LAYER_LOCATION, SlidingDoor::createBodyLayer);
	}
}