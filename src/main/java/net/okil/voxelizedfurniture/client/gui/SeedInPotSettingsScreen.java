package net.okil.voxelizedfurniture.client.gui;

import net.okil.voxelizedfurniture.world.inventory.SeedInPotSettingsMenu;
import net.okil.voxelizedfurniture.network.SeedInPotSettingsButtonMessage;
import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModScreens;

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

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SeedInPotSettingsScreen extends AbstractContainerScreen<SeedInPotSettingsMenu> implements VoxelizedFurnitureModScreens.FabricScreenAccessor {
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	private boolean menuStateUpdateActive = false;
	private Button button_2d;
	private Button button_3d;
	private Button button_ramdom;
	private Button button_random_3d;
	private static final Identifier BACKGROUND = Identifier.parse("voxelized_furniture:textures/screens/seed_in_pot_settings.png");

	public SeedInPotSettingsScreen(SeedInPotSettingsMenu container, Inventory inventory, Component text) {
		super(container, inventory, text, 85, 100);
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
		int key = event.key();
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
		button_2d = Button.builder(Component.translatable("gui.voxelized_furniture.seed_in_pot_settings.button_2d"), e -> {
			int x = SeedInPotSettingsScreen.this.x;
			int y = SeedInPotSettingsScreen.this.y;
			if (true) {
				// GUI button procedures must execute on the server only.
				// The packet handler runs the procedure server-side; executing it here as well
				// causes client/server inventory desynchronization for procedures that modify slots.
				ClientPlayNetworking.send(new SeedInPotSettingsButtonMessage(0, x, y, z));
			}
		}).bounds(this.leftPos + 24, this.topPos + 6, 35, 20).build();
		this.addRenderableWidget(button_2d);
		button_3d = Button.builder(Component.translatable("gui.voxelized_furniture.seed_in_pot_settings.button_3d"), e -> {
			int x = SeedInPotSettingsScreen.this.x;
			int y = SeedInPotSettingsScreen.this.y;
			if (true) {
				// GUI button procedures must execute on the server only.
				// The packet handler runs the procedure server-side; executing it here as well
				// causes client/server inventory desynchronization for procedures that modify slots.
				ClientPlayNetworking.send(new SeedInPotSettingsButtonMessage(1, x, y, z));
			}
		}).bounds(this.leftPos + 24, this.topPos + 28, 35, 20).build();
		this.addRenderableWidget(button_3d);
		button_ramdom = Button.builder(Component.translatable("gui.voxelized_furniture.seed_in_pot_settings.button_ramdom"), e -> {
			int x = SeedInPotSettingsScreen.this.x;
			int y = SeedInPotSettingsScreen.this.y;
			if (true) {
				// GUI button procedures must execute on the server only.
				// The packet handler runs the procedure server-side; executing it here as well
				// causes client/server inventory desynchronization for procedures that modify slots.
				ClientPlayNetworking.send(new SeedInPotSettingsButtonMessage(2, x, y, z));
			}
		}).bounds(this.leftPos + 15, this.topPos + 51, 56, 20).build();
		this.addRenderableWidget(button_ramdom);
		button_random_3d = Button.builder(Component.translatable("gui.voxelized_furniture.seed_in_pot_settings.button_random_3d"), e -> {
			int x = SeedInPotSettingsScreen.this.x;
			int y = SeedInPotSettingsScreen.this.y;
			if (true) {
				// GUI button procedures must execute on the server only.
				// The packet handler runs the procedure server-side; executing it here as well
				// causes client/server inventory desynchronization for procedures that modify slots.
				ClientPlayNetworking.send(new SeedInPotSettingsButtonMessage(3, x, y, z));
			}
		}).bounds(this.leftPos + 6, this.topPos + 73, 72, 20).build();
		this.addRenderableWidget(button_random_3d);
	}
}