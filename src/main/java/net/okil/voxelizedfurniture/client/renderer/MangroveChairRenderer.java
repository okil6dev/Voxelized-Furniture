package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.MangroveChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_mangrove;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MangroveChairRenderer extends MobRenderer<MangroveChairEntity, LivingEntityRenderState, chair_mangrove> {
	private MangroveChairEntity entity = null;

	public MangroveChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_mangrove(context.bakeLayer(chair_mangrove.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(MangroveChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_mangrove_log.png");
	}
}