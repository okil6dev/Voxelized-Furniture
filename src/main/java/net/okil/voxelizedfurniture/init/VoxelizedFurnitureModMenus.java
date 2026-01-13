/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.world.inventory.WardrobeGuiMenu;
import net.okil.voxelizedfurniture.world.inventory.ShelfGuiMenu;
import net.okil.voxelizedfurniture.world.inventory.FridgeGuiMenu;
import net.okil.voxelizedfurniture.world.inventory.CabinetsGuiMenu;
import net.okil.voxelizedfurniture.network.MenuStateUpdateMessage;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import java.util.Map;

public class VoxelizedFurnitureModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, VoxelizedFurnitureMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<CabinetsGuiMenu>> CABINETS_GUI = REGISTRY.register("cabinets_gui", () -> IMenuTypeExtension.create(CabinetsGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<FridgeGuiMenu>> FRIDGE_GUI = REGISTRY.register("fridge_gui", () -> IMenuTypeExtension.create(FridgeGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<ShelfGuiMenu>> SHELF_GUI = REGISTRY.register("shelf_gui", () -> IMenuTypeExtension.create(ShelfGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<WardrobeGuiMenu>> WARDROBE_GUI = REGISTRY.register("wardrobe_gui", () -> IMenuTypeExtension.create(WardrobeGuiMenu::new));

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayer(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide) {
				if (Minecraft.getInstance().screen instanceof VoxelizedFurnitureModScreens.ScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				ClientPacketDistributor.sendToServer(new MenuStateUpdateMessage(elementType, name, elementState));
			}
		}

		default <T> T getMenuState(int elementType, String name, T defaultValue) {
			try {
				return (T) getMenuState().getOrDefault(elementType + ":" + name, defaultValue);
			} catch (ClassCastException e) {
				return defaultValue;
			}
		}
	}
}