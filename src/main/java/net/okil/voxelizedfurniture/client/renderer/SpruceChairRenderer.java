package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.SpruceChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_spruce;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SpruceChairRenderer extends MobRenderer<SpruceChairEntity, chair_spruce<SpruceChairEntity>> {
	public SpruceChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_spruce<SpruceChairEntity>(context.bakeLayer(chair_spruce.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(SpruceChairEntity entity) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_spruce_log.png");
	}
}