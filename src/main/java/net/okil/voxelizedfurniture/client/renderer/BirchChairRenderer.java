package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.BirchChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_birch;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BirchChairRenderer extends MobRenderer<BirchChairEntity, LivingEntityRenderState, chair_birch> {
	private BirchChairEntity entity = null;

	public BirchChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_birch(context.bakeLayer(chair_birch.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(BirchChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_birch_log.png");
	}
}