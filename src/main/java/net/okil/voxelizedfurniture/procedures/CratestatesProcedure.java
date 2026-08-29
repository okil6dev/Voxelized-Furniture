package net.okil.voxelizedfurniture.procedures;

import net.neoforged.neoforge.items.ItemHandlerHelper;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;

public class CratestatesProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double StopProcedure = 0;
		StopProcedure = 0;
		if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip1 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip1) : -1) == 1) {
			if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.BAMBOO.asItem())) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.DIRT.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack8 = new ItemStack(Blocks.DIRT).copy();
						_setstack8.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack8);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 0;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack14 = new ItemStack(Blocks.DIRT).copy();
						_setstack14.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack14);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 0;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.DIRT.asItem())) {
					if (entity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(Blocks.DIRT).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
					{
						int _value = 0;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				}
			}
		} else if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip21 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip21) : -1) == 0) {
			if (StopProcedure == 0) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.DIRT.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack26 = new ItemStack(Blocks.DIRT).copy();
						_setstack26.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack26);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				}
			}
		}
		if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip29 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip29) : -1) == 2) {
			if (StopProcedure == 0) {
				if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.BAMBOO.asItem())) {
					if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.GRASS_BLOCK.asItem()) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack36 = new ItemStack(Blocks.GRASS_BLOCK).copy();
							_setstack36.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack36);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						{
							int _value = 0;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
						StopProcedure = 1;
					} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack42 = new ItemStack(Blocks.GRASS_BLOCK).copy();
							_setstack42.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack42);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						{
							int _value = 0;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
						StopProcedure = 1;
					} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.GRASS_BLOCK.asItem())) {
						if (entity instanceof Player _player) {
							ItemStack _setstack = new ItemStack(Blocks.GRASS_BLOCK).copy();
							_setstack.setCount(1);
							ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
						}
						{
							int _value = 0;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
						StopProcedure = 1;
					}
				}
			}
		} else if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip49 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip49) : -1) == 0) {
			if (StopProcedure == 0) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.GRASS_BLOCK.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack54 = new ItemStack(Blocks.GRASS_BLOCK).copy();
						_setstack54.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack54);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 2;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				}
			}
		}
		if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip57 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip57) : -1) == 4) {
			if (StopProcedure == 0) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.BAMBOO.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack62 = new ItemStack(Blocks.BAMBOO).copy();
						_setstack62.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack62);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 2;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack68 = new ItemStack(Blocks.BAMBOO).copy();
						_setstack68.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack68);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 2;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.BAMBOO.asItem())) {
					if (entity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(Blocks.BAMBOO).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
					{
						int _value = 2;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				}
			}
		}
		if (StopProcedure == 0) {
			if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip75 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip75) : -1) == 2) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.BAMBOO.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack80 = new ItemStack(Blocks.BAMBOO).copy();
						_setstack80.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack80);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 4;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				}
			}
		}
		if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip83 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip83) : -1) == 3) {
			if (StopProcedure == 0) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.BAMBOO.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack88 = new ItemStack(Blocks.BAMBOO).copy();
						_setstack88.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack88);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.AIR.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack94 = new ItemStack(Blocks.BAMBOO).copy();
						_setstack94.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack94);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.BAMBOO.asItem())) {
					if (entity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(Blocks.BAMBOO).copy();
						_setstack.setCount(1);
						ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
					}
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
				}
			}
		}
		if (StopProcedure == 0) {
			if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip101 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip101) : -1) == 1) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.BAMBOO.asItem()) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack106 = new ItemStack(Blocks.BAMBOO).copy();
						_setstack106.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack106);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						int _value = 3;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					StopProcedure = 1;
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