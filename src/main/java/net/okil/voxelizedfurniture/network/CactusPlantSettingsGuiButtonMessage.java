package net.okil.voxelizedfurniture.network;

import net.okil.voxelizedfurniture.procedures.CactusPlantR2DPProcedure;
import net.okil.voxelizedfurniture.procedures.CactusPlantN2DPProcedure;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.SectionPos;

@EventBusSubscriber
public record CactusPlantSettingsGuiButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {
	public static final Type<CactusPlantSettingsGuiButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "cactus_plant_settings_gui_buttons"));
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

	public static void handleData(final CactusPlantSettingsGuiButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
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

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		VoxelizedFurnitureMod.addNetworkMessage(CactusPlantSettingsGuiButtonMessage.TYPE, CactusPlantSettingsGuiButtonMessage.STREAM_CODEC, CactusPlantSettingsGuiButtonMessage::handleData);
	}
}