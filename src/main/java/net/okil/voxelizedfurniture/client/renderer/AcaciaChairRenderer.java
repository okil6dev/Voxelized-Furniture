package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.AcaciaChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_acacia;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AcaciaChairRenderer extends MobRenderer<AcaciaChairEntity, chair_acacia<AcaciaChairEntity>> {
	private final ResourceLocation entityTexture = ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_acacia_log.png");

	public AcaciaChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_acacia<AcaciaChairEntity>(context.bakeLayer(chair_acacia.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(AcaciaChairEntity entity) {
		return entityTexture;
	}
}