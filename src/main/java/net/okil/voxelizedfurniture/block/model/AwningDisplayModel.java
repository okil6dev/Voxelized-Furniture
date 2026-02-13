package net.okil.voxelizedfurniture.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.okil.voxelizedfurniture.block.display.AwningDisplayItem;

import net.minecraft.resources.ResourceLocation;

public class AwningDisplayModel extends GeoModel<AwningDisplayItem> {
	@Override
	public ResourceLocation getAnimationResource(AwningDisplayItem animatable) {
		return ResourceLocation.parse("voxelized_furniture:animations/awning.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AwningDisplayItem animatable) {
		return ResourceLocation.parse("voxelized_furniture:geo/awning.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AwningDisplayItem entity) {
		return ResourceLocation.parse("voxelized_furniture:textures/block/awning.png");
	}
}