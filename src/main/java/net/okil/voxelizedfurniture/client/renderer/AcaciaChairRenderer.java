package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.AcaciaChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_acacia;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class AcaciaChairRenderer extends MobRenderer<AcaciaChairEntity, LivingEntityRenderState, chair_acacia> {
	private final Identifier entityTexture = Identifier.parse("voxelized_furniture:textures/entities/stripped_acacia_log.png");

	public AcaciaChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_acacia(context.bakeLayer(chair_acacia.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public void extractRenderState(AcaciaChairEntity entity, LivingEntityRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return entityTexture;
	}
}