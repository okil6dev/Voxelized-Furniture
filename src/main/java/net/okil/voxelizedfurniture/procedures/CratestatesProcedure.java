package net.okil.voxelizedfurniture.procedures;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
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
			if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BAMBOO)) {
				if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.WHEAT_SEEDS)) {
					if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.DIRT) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack10 = new ItemStack(Items.DIRT).copy();
							_setstack10.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack10);
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
					} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.AIR) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack16 = new ItemStack(Items.DIRT).copy();
							_setstack16.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack16);
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
					} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.DIRT)) {
						if (entity instanceof Player _player) {
							ItemStack _setstack = new ItemStack(Items.DIRT).copy();
							_setstack.setCount(1);
							_player.addItem(_setstack);
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
		} else if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip23 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip23) : -1) == 0) {
			if (StopProcedure == 0) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.DIRT) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack28 = new ItemStack(Items.DIRT).copy();
						_setstack28.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack28);
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
		if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip31 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip31) : -1) == 2) {
			if (StopProcedure == 0) {
				if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BAMBOO)) {
					if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.WHEAT_SEEDS)) {
						if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.GRASS_BLOCK) {
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack40 = new ItemStack(Items.GRASS_BLOCK).copy();
								_setstack40.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack40);
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
						} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.AIR) {
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack46 = new ItemStack(Items.GRASS_BLOCK).copy();
								_setstack46.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack46);
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
						} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.GRASS_BLOCK)) {
							if (entity instanceof Player _player) {
								ItemStack _setstack = new ItemStack(Items.GRASS_BLOCK).copy();
								_setstack.setCount(1);
								_player.addItem(_setstack);
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
			}
		} else if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip53 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip53) : -1) == 0) {
			if (StopProcedure == 0) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.GRASS_BLOCK) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack58 = new ItemStack(Items.GRASS_BLOCK).copy();
						_setstack58.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack58);
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
		if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip61 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip61) : -1) == 4) {
			if (StopProcedure == 0) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BAMBOO) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack66 = new ItemStack(Items.BAMBOO).copy();
						_setstack66.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack66);
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
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.AIR) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack72 = new ItemStack(Items.BAMBOO).copy();
						_setstack72.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack72);
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
				} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BAMBOO)) {
					if (entity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(Items.BAMBOO).copy();
						_setstack.setCount(1);
						_player.addItem(_setstack);
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
			if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip79 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip79) : -1) == 2) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BAMBOO) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack84 = new ItemStack(Items.BAMBOO).copy();
						_setstack84.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack84);
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
		if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip87 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip87) : -1) == 3) {
			if (StopProcedure == 0) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BAMBOO) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack92 = new ItemStack(Items.BAMBOO).copy();
						_setstack92.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack92);
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
				} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.AIR) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack98 = new ItemStack(Items.BAMBOO).copy();
						_setstack98.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() + 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack98);
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
				} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BAMBOO)) {
					if (entity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(Items.BAMBOO).copy();
						_setstack.setCount(1);
						_player.addItem(_setstack);
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
			if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip105 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip105) : -1) == 1) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.BAMBOO) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack110 = new ItemStack(Items.BAMBOO).copy();
						_setstack110.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack110);
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
		if (StopProcedure == 0) {
			if ((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip113 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip113) : -1) == 1) {
				if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.WHEAT_SEEDS) {
					{
						int _value = 2;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack119 = new ItemStack(Items.WHEAT_SEEDS).copy();
						_setstack119.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack119);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
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