package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.AcaciaChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_acacia;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AcaciaChairRenderer extends MobRenderer<AcaciaChairEntity, LivingEntityRenderState, chair_acacia> {
	private AcaciaChairEntity entity = null;

	public AcaciaChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_acacia(context.bakeLayer(chair_acacia.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(AcaciaChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_acacia_log.png");
	}
}