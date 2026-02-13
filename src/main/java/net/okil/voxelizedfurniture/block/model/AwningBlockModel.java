package net.okil.voxelizedfurniture.block.model;

import software.bernie.geckolib.model.GeoModel;

import net.okil.voxelizedfurniture.block.entity.AwningTileEntity;

import net.minecraft.resources.ResourceLocation;

public class AwningBlockModel extends GeoModel<AwningTileEntity> {
	@Override
	public ResourceLocation getAnimationResource(AwningTileEntity animatable) {
		return ResourceLocation.parse("voxelized_furniture:animations/awning.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(AwningTileEntity animatable) {
		return ResourceLocation.parse("voxelized_furniture:geo/awning.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AwningTileEntity animatable) {
		return ResourceLocation.parse("voxelized_furniture:textures/block/awning.png");
	}
}