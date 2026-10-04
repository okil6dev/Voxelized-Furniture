package net.okil.voxelizedfurniture.client.gui;

import net.okil.voxelizedfurniture.world.inventory.YuccaPlantSettingsGuiMenu;
import net.okil.voxelizedfurniture.network.YuccaPlantSettingsGuiButtonMessage;
import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModScreens;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import com.mojang.blaze3d.platform.InputConstants;

public class YuccaPlantSettingsGuiScreen extends AbstractContainerScreen<YuccaPlantSettingsGuiMenu> implements VoxelizedFurnitureModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_normal_2d;
	private Button button_random_2d;
	private static final Identifier BACKGROUND = Identifier.parse("voxelized_furniture:textures/screens/yucca_plant_settings_gui.png");

	public YuccaPlantSettingsGuiScreen(YuccaPlantSettingsGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 85, 50);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		int key = InputConstants.getKey(event).getValue();
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		button_normal_2d = Button.builder(Component.translatable("gui.voxelized_furniture.yucca_plant_settings_gui.button_normal_2d"), e -> {
			int x = YuccaPlantSettingsGuiScreen.this.x;
			int y = YuccaPlantSettingsGuiScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new YuccaPlantSettingsGuiButtonMessage(0, x, y, z));
				YuccaPlantSettingsGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 4, 72, 20).build();
		this.addRenderableWidget(button_normal_2d);
		button_random_2d = Button.builder(Component.translatable("gui.voxelized_furniture.yucca_plant_settings_gui.button_random_2d"), e -> {
			int x = YuccaPlantSettingsGuiScreen.this.x;
			int y = YuccaPlantSettingsGuiScreen.this.y;
			if (true) {
				ClientPacketDistributor.sendToServer(new YuccaPlantSettingsGuiButtonMessage(1, x, y, z));
				YuccaPlantSettingsGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 6, this.topPos + 24, 72, 20).build();
		this.addRenderableWidget(button_random_2d);
	}
}