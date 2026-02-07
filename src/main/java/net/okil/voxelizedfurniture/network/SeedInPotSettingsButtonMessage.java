package net.okil.voxelizedfurniture.network;

import net.okil.voxelizedfurniture.procedures.Seedinpot3dProcedure;
import net.okil.voxelizedfurniture.procedures.Seedinpot2dProcedure;
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
import net.minecraft.core.BlockPos;

@EventBusSubscriber
public record SeedInPotSettingsButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<SeedInPotSettingsButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, "seed_in_pot_settings_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, SeedInPotSettingsButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SeedInPotSettingsButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new SeedInPotSettingsButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<SeedInPotSettingsButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final SeedInPotSettingsButtonMessage message, final IPayloadContext context) {
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
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			Seedinpot2dProcedure.execute(world, x, y, z, entity);
		}
		if (buttonID == 1) {

			Seedinpot3dProcedure.execute(world, x, y, z, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		VoxelizedFurnitureMod.addNetworkMessage(SeedInPotSettingsButtonMessage.TYPE, SeedInPotSettingsButtonMessage.STREAM_CODEC, SeedInPotSettingsButtonMessage::handleData);
	}
}