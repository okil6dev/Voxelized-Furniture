package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.SpruceChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_spruce;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class SpruceChairRenderer extends MobRenderer<SpruceChairEntity, LivingEntityRenderState, chair_spruce> {
	private final Identifier entityTexture = Identifier.parse("voxelized_furniture:textures/entities/stripped_spruce_log.png");

	public SpruceChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_spruce(context.bakeLayer(chair_spruce.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(SpruceChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return entityTexture;
	}
}