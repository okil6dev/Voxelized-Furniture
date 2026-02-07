package net.okil.voxelizedfurniture.client.renderer;

import net.okil.voxelizedfurniture.entity.JungleChairEntity;
import net.okil.voxelizedfurniture.client.model.chair_jungle;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class JungleChairRenderer extends MobRenderer<JungleChairEntity, chair_jungle<JungleChairEntity>> {
	public JungleChairRenderer(EntityRendererProvider.Context context) {
		super(context, new chair_jungle<JungleChairEntity>(context.bakeLayer(chair_jungle.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(JungleChairEntity entity) {
		return ResourceLocation.parse("voxelized_furniture:textures/entities/stripped_jungle_log.png");
	}
}