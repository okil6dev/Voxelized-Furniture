/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.world.inventory.*;
import net.okil.voxelizedfurniture.network.MenuStateUpdateMessage;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.client.Minecraft;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.Map;

public class VoxelizedFurnitureModMenus {
	public static MenuType<CabinetsGuiMenu> CABINETS_GUI;
	public static MenuType<FridgeGuiMenu> FRIDGE_GUI;
	public static MenuType<ShelfGuiMenu> SHELF_GUI;
	public static MenuType<WardrobeGuiMenu> WARDROBE_GUI;
	public static MenuType<KitchenCounterGuiMenu> KITCHEN_COUNTER_GUI;
	public static MenuType<SeedInPotSettingsMenu> SEED_IN_POT_SETTINGS;
	public static MenuType<YuccaPlantSettingsGuiMenu> YUCCA_PLANT_SETTINGS_GUI;
	public static MenuType<WheatPlantSettingsGuiMenu> WHEAT_PLANT_SETTINGS_GUI;
	public static MenuType<CactusPlantSettingsGuiMenu> CACTUS_PLANT_SETTINGS_GUI;
	public static MenuType<DrawerGuiMenu> DRAWER_GUI;
	public static MenuType<CookingGuiMenu> COOKING_GUI;
	public static MenuType<OvencooktopguiMenu> OVENCOOKTOPGUI;

	public static void load() {
		CABINETS_GUI = register("cabinets_gui", CabinetsGuiMenu::new);
		CabinetsGuiMenu.screenInit();
		FRIDGE_GUI = register("fridge_gui", FridgeGuiMenu::new);
		FridgeGuiMenu.screenInit();
		SHELF_GUI = register("shelf_gui", ShelfGuiMenu::new);
		ShelfGuiMenu.screenInit();
		WARDROBE_GUI = register("wardrobe_gui", WardrobeGuiMenu::new);
		WardrobeGuiMenu.screenInit();
		KITCHEN_COUNTER_GUI = register("kitchen_counter_gui", KitchenCounterGuiMenu::new);
		KitchenCounterGuiMenu.screenInit();
		SEED_IN_POT_SETTINGS = register("seed_in_pot_settings", SeedInPotSettingsMenu::new);
		SeedInPotSettingsMenu.screenInit();
		YUCCA_PLANT_SETTINGS_GUI = register("yucca_plant_settings_gui", YuccaPlantSettingsGuiMenu::new);
		YuccaPlantSettingsGuiMenu.screenInit();
		WHEAT_PLANT_SETTINGS_GUI = register("wheat_plant_settings_gui", WheatPlantSettingsGuiMenu::new);
		WheatPlantSettingsGuiMenu.screenInit();
		CACTUS_PLANT_SETTINGS_GUI = register("cactus_plant_settings_gui", CactusPlantSettingsGuiMenu::new);
		CactusPlantSettingsGuiMenu.screenInit();
		DRAWER_GUI = register("drawer_gui", DrawerGuiMenu::new);
		DrawerGuiMenu.screenInit();
		COOKING_GUI = register("cooking_gui", CookingGuiMenu::new);
		CookingGuiMenu.screenInit();
		OVENCOOKTOPGUI = register("ovencooktopgui", OvencooktopguiMenu::new);
		OvencooktopguiMenu.screenInit();
		PayloadTypeRegistry.serverboundPlay().register(MenuStateUpdateMessage.TYPE, MenuStateUpdateMessage.STREAM_CODEC);
		ServerPlayNetworking.registerGlobalReceiver(MenuStateUpdateMessage.TYPE, MenuStateUpdateMessage::handleMenuState);
	}

	public static void clientLoad() {
		PayloadTypeRegistry.clientboundPlay().register(MenuStateUpdateMessage.TYPE, MenuStateUpdateMessage.STREAM_CODEC);
		ClientPlayNetworking.registerGlobalReceiver(MenuStateUpdateMessage.TYPE, MenuStateUpdateMessage::handleClientMenuState);
	}

	public interface MenuAccessor {
		Map<String, Object> getMenuState();

		Map<Integer, Slot> getSlots();

		default void sendMenuStateUpdate(Player player, int elementType, String name, Object elementState, boolean needClientUpdate) {
			getMenuState().put(elementType + ":" + name, elementState);
			if (player instanceof ServerPlayer serverPlayer) {
				ServerPlayNetworking.send(serverPlayer, new MenuStateUpdateMessage(elementType, name, elementState));
			} else if (player.level().isClientSide()) {
				if (Minecraft.getInstance().gui.screen() instanceof VoxelizedFurnitureModScreens.FabricScreenAccessor accessor && needClientUpdate)
					accessor.updateMenuState(elementType, name, elementState);
				ClientPlayNetworking.send(new MenuStateUpdateMessage(elementType, name, elementState));
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

	private static <M extends AbstractContainerMenu> MenuType<M> register(String registryname, MenuType.MenuSupplier<M> element) {
		return Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, registryname), new MenuType<>(element, FeatureFlags.DEFAULT_FLAGS));
	}
}