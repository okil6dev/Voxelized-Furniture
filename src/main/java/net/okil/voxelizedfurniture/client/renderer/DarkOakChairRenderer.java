package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.DarkOakChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_dark_oak;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DarkOakChairRenderer extends MobRenderer<DarkOakChairEntity, chair_dark_oak<DarkOakChairEntity>> {
	public DarkOakChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_dark_oak<DarkOakChairEntity>(context.bakeLayer(chair_dark_oak.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(DarkOakChairEntity entity) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_dark_oak_log.png");
	}
}