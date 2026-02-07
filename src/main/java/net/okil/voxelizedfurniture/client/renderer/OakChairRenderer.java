package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.OakChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_oak;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class OakChairRenderer extends MobRenderer<OakChairEntity, chair_oak<OakChairEntity>> {
	public OakChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_oak<OakChairEntity>(context.bakeLayer(chair_oak.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(OakChairEntity entity) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_oak_log.png");
	}
}