package net.okil.voxelizedfurniture.procedures;

import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class OakWardrobeStackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		Direction DirectionForBlock = Direction.NORTH;
		double Used = 0;
		if (Used == 0) {
			if (!((getPropertyByName((world.getBlockState(BlockPos.containing(x, y, z))), "blockstate") instanceof IntegerProperty _getip1 ? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip1) : -1) == 1)) {
				VoxelizedFurnitureMod.queueServerWork(5, () -> {
					world.setBlock(BlockPos.containing(x, 1 + y, z), (world.getBlockState(BlockPos.containing(x, y, z))), 3);
					{
						Direction _dir = (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))));
						BlockPos _pos = BlockPos.containing(x, 1 + y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof EnumProperty _dp && _dp.getPossibleValues().contains(_dir)) {
							world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
						} else if (_bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis())) {
							world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
						}
					}
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, 1 + y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
					world.setBlock(BlockPos.containing(x, 2 + y, z), (world.getBlockState(BlockPos.containing(x, y, z))), 3);
					{
						Direction _dir = (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))));
						BlockPos _pos = BlockPos.containing(x, 2 + y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("facing") instanceof EnumProperty _dp && _dp.getPossibleValues().contains(_dir)) {
							world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
						} else if (_bs.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis())) {
							world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
						}
					}
					{
						int _value = 1;
						BlockPos _pos = BlockPos.containing(x, 2 + y, z);
						BlockState _bs = world.getBlockState(_pos);
						if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
							world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
					}
				});
				VoxelizedFurnitureMod.queueServerWork(6, () -> {
					world.setBlock(BlockPos.containing(x, 2 + y, z), Blocks.AIR.defaultBlockState(), 3);
					world.setBlock(BlockPos.containing(x, 3 + y, z), Blocks.AIR.defaultBlockState(), 3);
				});
			}
		}
		Used = 1 + Used;
	}

	private static Property<?> getPropertyByName(BlockState state, String name) {
		for (Property<?> property : state.getProperties()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	private static Direction getDirectionFromBlockState(BlockState blockState) {
		if (getPropertyByName(blockState, "facing") instanceof EnumProperty ep && ep.getValueClass() == Direction.class)
			return (Direction) blockState.getValue(ep);
		if (getPropertyByName(blockState, "axis") instanceof EnumProperty ep && ep.getValueClass() == Direction.Axis.class)
			return Direction.fromAxisAndDirection((Direction.Axis) blockState.getValue(ep), Direction.AxisDirection.POSITIVE);
		return Direction.NORTH;
	}
}