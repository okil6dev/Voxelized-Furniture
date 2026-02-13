package net.okil.voxelizedfurniture.procedures;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModBlocks;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class WheatPlantR2DPProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Direction Rotation = Direction.NORTH;
		Rotation = getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z))));
		world.setBlock(BlockPos.containing(x, y, z), VoxelizedFurnitureModBlocks.RANDOM_WHEAT_PLANT.get().defaultBlockState(), 3);
		{
			Direction _dir = Rotation;
			BlockPos _pos = BlockPos.containing(x, y, z);
			BlockState _bs = world.getBlockState(_pos);
			Property<?> _property = _bs.getBlock().getStateDefinition().getProperty("facing");
			if (_property instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(_dir)) {
				world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
			} else {
				_property = _bs.getBlock().getStateDefinition().getProperty("axis");
				if (_property instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis()))
					world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
			}
		}
		if (entity instanceof Player _player)
			_player.closeContainer();
	}

	private static Direction getDirectionFromBlockState(BlockState blockState) {
		Property<?> prop = blockState.getBlock().getStateDefinition().getProperty("facing");
		if (prop instanceof DirectionProperty dp)
			return blockState.getValue(dp);
		prop = blockState.getBlock().getStateDefinition().getProperty("axis");
		return prop instanceof EnumProperty ep && ep.getPossibleValues().toArray()[0] instanceof Direction.Axis ? Direction.fromAxisAndDirection((Direction.Axis) blockState.getValue(ep), Direction.AxisDirection.POSITIVE) : Direction.NORTH;
	}
}