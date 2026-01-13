package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.OakChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_oak;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class OakChairRenderer extends MobRenderer<OakChairEntity, LivingEntityRenderState, chair_oak> {
	private OakChairEntity entity = null;

	public OakChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_oak(context.bakeLayer(chair_oak.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(OakChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_oak_log.png");
	}
}