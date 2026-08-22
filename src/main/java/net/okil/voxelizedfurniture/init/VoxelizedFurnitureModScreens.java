/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.client.gui.*;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

@EventBusSubscriber(Dist.CLIENT)
public class VoxelizedFurnitureModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(VoxelizedFurnitureModMenus.CABINETS_GUI.get(), CabinetsGuiScreen::new);
		event.register(VoxelizedFurnitureModMenus.FRIDGE_GUI.get(), FridgeGuiScreen::new);
		event.register(VoxelizedFurnitureModMenus.SHELF_GUI.get(), ShelfGuiScreen::new);
		event.register(VoxelizedFurnitureModMenus.WARDROBE_GUI.get(), WardrobeGuiScreen::new);
		event.register(VoxelizedFurnitureModMenus.KITCHEN_COUNTER_GUI.get(), KitchenCounterGuiScreen::new);
		event.register(VoxelizedFurnitureModMenus.SEED_IN_POT_SETTINGS.get(), SeedInPotSettingsScreen::new);
		event.register(VoxelizedFurnitureModMenus.YUCCA_PLANT_SETTINGS_GUI.get(), YuccaPlantSettingsGuiScreen::new);
		event.register(VoxelizedFurnitureModMenus.WHEAT_PLANT_SETTINGS_GUI.get(), WheatPlantSettingsGuiScreen::new);
		event.register(VoxelizedFurnitureModMenus.CACTUS_PLANT_SETTINGS_GUI.get(), CactusPlantSettingsGuiScreen::new);
		event.register(VoxelizedFurnitureModMenus.DRAWER_GUI.get(), DrawerGuiScreen::new);
		event.register(VoxelizedFurnitureModMenus.COOKING_GUI.get(), CookingGuiScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}