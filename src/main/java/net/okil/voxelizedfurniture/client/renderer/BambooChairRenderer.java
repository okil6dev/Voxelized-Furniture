package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.BambooChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_bamboo;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BambooChairRenderer extends MobRenderer<BambooChairEntity, LivingEntityRenderState, chair_bamboo> {
	private BambooChairEntity entity = null;

	public BambooChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_bamboo(context.bakeLayer(chair_bamboo.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(BambooChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_bamboo_block.png");
	}
}