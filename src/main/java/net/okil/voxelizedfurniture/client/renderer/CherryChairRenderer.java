package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.CherryChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_cherry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CherryChairRenderer extends MobRenderer<CherryChairEntity, chair_cherry<CherryChairEntity>> {
	public CherryChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_cherry<CherryChairEntity>(context.bakeLayer(chair_cherry.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(CherryChairEntity entity) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_cherry_log.png");
	}
}