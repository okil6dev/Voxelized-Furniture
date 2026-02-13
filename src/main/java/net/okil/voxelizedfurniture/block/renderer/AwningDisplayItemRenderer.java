package net.okil.voxelizedfurniture.block.renderer;

import software.bernie.geckolib.renderer.GeoItemRenderer;

import net.okil.voxelizedfurniture.block.model.AwningDisplayModel;
import net.okil.voxelizedfurniture.block.display.AwningDisplayItem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

public class AwningDisplayItemRenderer extends GeoItemRenderer<AwningDisplayItem> {
	public AwningDisplayItemRenderer() {
		super(new AwningDisplayModel());
	}

	@Override
	public RenderType getRenderType(AwningDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}