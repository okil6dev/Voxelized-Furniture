package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.CherryChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_cherry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CherryChairRenderer extends MobRenderer<CherryChairEntity, LivingEntityRenderState, chair_cherry> {
	private CherryChairEntity entity = null;

	public CherryChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_cherry(context.bakeLayer(chair_cherry.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(CherryChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_cherry_log.png");
	}
}