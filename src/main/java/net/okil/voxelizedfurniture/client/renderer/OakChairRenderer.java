package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.OakChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_oak;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class OakChairRenderer extends MobRenderer<OakChairEntity, LivingEntityRenderState, chair_oak> {
	private final Identifier entityTexture = Identifier.parse("voxelized_furniture:textures/entities/stripped_oak_log.png");

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
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return entityTexture;
	}
}