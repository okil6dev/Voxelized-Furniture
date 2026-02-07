package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.MangroveChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_mangrove;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MangroveChairRenderer extends MobRenderer<MangroveChairEntity, chair_mangrove<MangroveChairEntity>> {
	public MangroveChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_mangrove<MangroveChairEntity>(context.bakeLayer(chair_mangrove.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(MangroveChairEntity entity) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_mangrove_log.png");
	}
}