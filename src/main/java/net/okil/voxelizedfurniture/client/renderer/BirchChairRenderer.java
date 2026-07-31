package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.BirchChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_birch;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BirchChairRenderer extends MobRenderer<BirchChairEntity, LivingEntityRenderState, chair_birch> {
	private final Identifier entityTexture = Identifier.parse("voxelized_furniture:textures/entities/stripped_birch_log.png");

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
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return entityTexture;
	}
}