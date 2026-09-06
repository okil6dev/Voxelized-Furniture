package net.okil.voxelizedfurniture.procedures;

import net.okil.voxelizedfurniture.world.inventory.KitchenCounterGuiMenu;
import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModBlocks;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import io.netty.buffer.Unpooled;

public class SinkStateSwitcherProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == VoxelizedFurnitureModBlocks.KITCHEN_SINK)) {
			if (!((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == VoxelizedFurnitureModBlocks.WOODEN_KITCHEN_SINK)) {
				if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip5 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip5) : -1) == 0) {
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				} else if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip8 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip8) : -1) == 1) {
					{
						int _value = 0;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				}
			} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == VoxelizedFurnitureModBlocks.WOODEN_KITCHEN_SINK) {
				if (!entity.isShiftKeyDown()) {
					if (entity instanceof ServerPlayer _ent) {
						BlockPos _bpos = BlockPos.containing(x, y, z);
						_ent.openMenu(new MenuProvider() {
							@Override
							public Component getDisplayName() {
								return Component.literal("KitchenCounterGui");
							}

							@Override
							public boolean shouldCloseCurrentScreen() {
								return false;
							}

							@Override
							public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
								return new KitchenCounterGuiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
							}
						});
					}
				} else if (entity.isShiftKeyDown()) {
					if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip16 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip16) : -1) == 0) {
						{
							int _value = 1;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
					} else if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip19 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip19) : -1) == 1) {
						{
							int _value = 0;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
					}
				}
			}
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == VoxelizedFurnitureModBlocks.KITCHEN_SINK) {
			if (!entity.isShiftKeyDown()) {
				if (entity instanceof ServerPlayer _ent) {
					BlockPos _bpos = BlockPos.containing(x, y, z);
					_ent.openMenu(new MenuProvider() {
						@Override
						public Component getDisplayName() {
							return Component.literal("KitchenCounterGui");
						}

						@Override
						public boolean shouldCloseCurrentScreen() {
							return false;
						}

						@Override
						public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
							return new KitchenCounterGuiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
						}
					});
				}
			} else if (entity.isShiftKeyDown()) {
				if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip27 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip27) : -1) == 0) {
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				} else if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip30 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip30) : -1) == 1) {
					{
						int _value = 0;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				}
			}
		}
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
}