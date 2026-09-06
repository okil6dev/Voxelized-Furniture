package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.CherryChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_cherry;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class CherryChairRenderer extends MobRenderer<CherryChairEntity, LivingEntityRenderState, chair_cherry> {
	private final Identifier entityTexture = Identifier.parse("voxelized_furniture:textures/entities/stripped_cherry_log.png");

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
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return entityTexture;
	}
}