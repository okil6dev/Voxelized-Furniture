package net.okil.voxelizedfurniture.procedures;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModBlocks;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

public class SoilsackstateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == VoxelizedFurnitureModBlocks.SACK_OF_SOIL.asItem()) {
			if (!(entity.isShiftKeyDown() == true)) {
				if (!((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip4 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip4) : -1) == 7)) {
					if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == VoxelizedFurnitureModBlocks.SACK_OF_SOIL.asItem()) {
						{
							int _value = (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip8 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip8) : -1) + 1;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack12 = new ItemStack(VoxelizedFurnitureModBlocks.SACK_OF_SOIL).copy();
							_setstack12.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack12);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.place")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.place")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					} else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.AIR) {
						{
							int _value = (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip17 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip17) : -1) + 1;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack21 = new ItemStack(VoxelizedFurnitureModBlocks.SACK_OF_SOIL).copy();
							_setstack21.setCount((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getCount() - 1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack21);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.place")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.place")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					} else if (!((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Items.AIR)) {
						{
							int _value = (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip26 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip26) : -1) + 1;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
						if (entity instanceof Player _player) {
							ItemStack _stktoremove = new ItemStack(VoxelizedFurnitureModBlocks.SACK_OF_SOIL);
							_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.place")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.place")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					}
				} else {
					if (!((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip31 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip31) : -1) == 0)) {
						{
							int _value = (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip33 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip33) : -1) - 1;
							BlockPos _pos = BlockPos.containing(x, y, z);
							BlockState _bs = world.getBlockState(_pos);
							if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
								world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
						}
						if (entity instanceof Player _player) {
							ItemStack _setstack = new ItemStack(VoxelizedFurnitureModBlocks.SACK_OF_SOIL).copy();
							_setstack.setCount(1);
							_player.addItem(_setstack);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.break")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.break")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					}
				}
			} else {
				if (!((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip38 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip38) : -1) == 0)) {
					{
						int _value = (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip40 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip40) : -1) - 1;
						BlockPos _pos = BlockPos.containing(x, y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					if (entity instanceof Player _player) {
						ItemStack _setstack = new ItemStack(VoxelizedFurnitureModBlocks.SACK_OF_SOIL).copy();
						_setstack.setCount(1);
						_player.addItem(_setstack);
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.break")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.break")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
				}
			}
		} else {
			if (!((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip45 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip45) : -1) == 0)) {
				{
					int _value = (getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "state") instanceof IntegerProperty _getip47 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip47) : -1) - 1;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("state") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				if (entity instanceof Player _player) {
					ItemStack _setstack = new ItemStack(VoxelizedFurnitureModBlocks.SACK_OF_SOIL).copy();
					_setstack.setCount(1);
					_player.addItem(_setstack);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.break")), SoundSource.BLOCKS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.rooted_dirt.break")), SoundSource.BLOCKS, 1, 1, false);
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