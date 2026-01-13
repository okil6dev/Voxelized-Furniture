package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.SpruceChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_spruce;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SpruceChairRenderer extends MobRenderer<SpruceChairEntity, LivingEntityRenderState, chair_spruce> {
	private SpruceChairEntity entity = null;

	public SpruceChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_spruce(context.bakeLayer(chair_spruce.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(SpruceChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_spruce_log.png");
	}
}