package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.BirchChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_birch;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BirchChairRenderer extends MobRenderer<BirchChairEntity, chair_birch<BirchChairEntity>> {
	public BirchChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_birch<BirchChairEntity>(context.bakeLayer(chair_birch.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(BirchChairEntity entity) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_birch_log.png");
	}
}