package net.okil.voxelizedfurniture;

import net.okil.voxelizedfurniture.network.VoxelizedFurnitureModVariables;
import net.okil.voxelizedfurniture.init.*;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ClientModInitializer;

@Environment(EnvType.CLIENT)
public class VoxelizedFurnitureModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		VoxelizedFurnitureModModels.clientLoad();
		VoxelizedFurnitureModBlocks.clientLoad();
		VoxelizedFurnitureModEntityRenderers.clientLoad();
		VoxelizedFurnitureModParticles.clientLoad();
		VoxelizedFurnitureModScreens.clientLoad();
		VoxelizedFurnitureModMenus.clientLoad();
		ClientPlayNetworking.registerGlobalReceiver(VoxelizedFurnitureModVariables.SavedDataSyncMessage.TYPE, VoxelizedFurnitureModVariables.SavedDataSyncMessage::handleData);
		// Start of user code block mod init
		// End of user code block mod init
	}
	// Start of user code block mod methods
	// End of user code block mod methods
}