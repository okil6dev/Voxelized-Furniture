package net.okil.voxelizedfurniture.client.gui;

import net.okil.voxelizedfurniture.world.inventory.SeedInPotSettingsMenu;
import net.okil.voxelizedfurniture.network.SeedInPotSettingsButtonMessage;
import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModScreens;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import com.mojang.blaze3d.systems.RenderSystem;

public class SeedInPotSettingsScreen extends AbstractContainerScreen<SeedInPotSettingsMenu> implements VoxelizedFurnitureModScreens.ScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_2d;
	private Button button_3d;

	public SeedInPotSettingsScreen(SeedInPotSettingsMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 45;
		this.imageHeight = 60;
	}

	@Override
	public void updateMenuState(int elementType, String name, Object elementState) {
		menuStateUpdateActive = true;
		menuStateUpdateActive = false;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("voxelized_furniture:textures/screens/seed_in_pot_settings.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		button_2d = Button.builder(Component.translatable("gui.voxelized_furniture.seed_in_pot_settings.button_2d"), e -> {
			int x = SeedInPotSettingsScreen.this.x;
			int y = SeedInPotSettingsScreen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new SeedInPotSettingsButtonMessage(0, x, y, z));
				SeedInPotSettingsButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 4, this.topPos + 8, 35, 20).build();
		this.addRenderableWidget(button_2d);
		button_3d = Button.builder(Component.translatable("gui.voxelized_furniture.seed_in_pot_settings.button_3d"), e -> {
			int x = SeedInPotSettingsScreen.this.x;
			int y = SeedInPotSettingsScreen.this.y;
			if (true) {
				PacketDistributor.sendToServer(new SeedInPotSettingsButtonMessage(1, x, y, z));
				SeedInPotSettingsButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 4, this.topPos + 31, 35, 20).build();
		this.addRenderableWidget(button_3d);
	}
}