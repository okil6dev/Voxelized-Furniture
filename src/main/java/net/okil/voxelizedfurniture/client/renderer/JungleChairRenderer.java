package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.JungleChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_jungle;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class JungleChairRenderer extends MobRenderer<JungleChairEntity, LivingEntityRenderState, chair_jungle> {
	private JungleChairEntity entity = null;

	public JungleChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_jungle(context.bakeLayer(chair_jungle.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(JungleChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		this.entity = entity;
	}

	@Override
	public ResourceLocation getTextureLocation(LivingEntityRenderState state) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_jungle_log.png");
	}
}