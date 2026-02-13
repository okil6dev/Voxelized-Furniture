package net.okil.voxelizedfurniture.block.renderer;

import software.bernie.geckolib.renderer.GeoBlockRenderer;

import net.okil.voxelizedfurniture.block.model.AwningBlockModel;
import net.okil.voxelizedfurniture.block.entity.AwningTileEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

public class AwningTileRenderer extends GeoBlockRenderer<AwningTileEntity> {
	public AwningTileRenderer() {
		super(new AwningBlockModel());
	}

	@Override
	public RenderType getRenderType(AwningTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}