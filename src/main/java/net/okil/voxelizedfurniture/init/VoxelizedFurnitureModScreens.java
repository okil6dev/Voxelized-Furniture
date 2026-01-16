/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.client.gui.WardrobeGuiScreen;
import net.okil.voxelizedfurniture.client.gui.ShelfGuiScreen;
import net.okil.voxelizedfurniture.client.gui.KitchenCounterGuiScreen;
import net.okil.voxelizedfurniture.client.gui.FridgeGuiScreen;
import net.okil.voxelizedfurniture.client.gui.CabinetsGuiScreen;

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
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}