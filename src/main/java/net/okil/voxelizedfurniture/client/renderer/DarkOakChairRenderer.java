package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.DarkOakChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_dark_oak;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DarkOakChairRenderer extends MobRenderer<DarkOakChairEntity, LivingEntityRenderState, chair_dark_oak> {
	private DarkOakChairEntity entity = null;

	public DarkOakChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_dark_oak(context.bakeLayer(chair_dark_oak.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(DarkOakChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_dark_oak_log.png");
	}
}