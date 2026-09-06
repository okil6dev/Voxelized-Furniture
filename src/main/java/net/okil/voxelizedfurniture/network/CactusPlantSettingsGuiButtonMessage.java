package net.okil.voxelizedfurniture.network;

import net.okil.voxelizedfurniture.procedures.CactusPlantR2DPProcedure;
import net.okil.voxelizedfurniture.procedures.CactusPlantN2DPProcedure;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.SectionPos;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public record CactusPlantSettingsGuiButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<CactusPlantSettingsGuiButtonMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "cactus_plant_settings_gui_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, CactusPlantSettingsGuiButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, CactusPlantSettingsGuiButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new CactusPlantSettingsGuiButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));

	@Override
	public Type<CactusPlantSettingsGuiButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final CactusPlantSettingsGuiButtonMessage message, final ServerPlayNetworking.Context context) {
		context.server().execute(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z));
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.getChunkSource().hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z)))
			return;
		if (buttonID == 0) {

			CactusPlantN2DPProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			CactusPlantR2DPProcedure.execute(world, x, y, z, entity);
		}
	}
}