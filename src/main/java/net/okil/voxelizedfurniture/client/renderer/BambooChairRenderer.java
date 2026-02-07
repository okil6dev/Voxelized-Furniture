package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.BambooChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_bamboo;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BambooChairRenderer extends MobRenderer<BambooChairEntity, chair_bamboo<BambooChairEntity>> {
	public BambooChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_bamboo<BambooChairEntity>(context.bakeLayer(chair_bamboo.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(BambooChairEntity entity) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_bamboo_block.png");
	}
}