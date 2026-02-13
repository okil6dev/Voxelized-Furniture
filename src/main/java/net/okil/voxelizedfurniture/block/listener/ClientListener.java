package net.okil.voxelizedfurniture.block.listener;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModBlockEntities;
import net.okil.voxelizedfurniture.block.renderer.AwningTileRenderer;
import net.okil.voxelizedfurniture.block.entity.AwningTileEntity;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.block.entity.BlockEntityType;

@EventBusSubscriber(modid = VoxelizedFurnitureMod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientListener {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer((BlockEntityType<AwningTileEntity>) VoxelizedFurnitureModBlockEntities.AWNING.get(), context -> new AwningTileRenderer());
	}
}