package net.okil.voxelizedfurniture.procedures;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class ShowerOnBlockRightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		Direction rotation = Direction.NORTH;
		double ShowerTime = 0;
		if (world instanceof Level _level0 && _level0.hasNeighborSignal(BlockPos.containing(x, y, z))) {
			if (Direction.NORTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.DRIPPING_DRIPSTONE_WATER, (0.5 + x), (1.8 + y), (0.4 + z), 5, 0.05, 0, 0.1, 1);
			} else {
				if (Direction.WEST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles(ParticleTypes.DRIPPING_DRIPSTONE_WATER, (0.4 + x), (1.8 + y), (0.5 + z), 5, 0.1, 0, 0.05, 1);
				} else {
					if (Direction.SOUTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
						if (world instanceof ServerLevel _level)
							_level.sendParticles(ParticleTypes.DRIPPING_DRIPSTONE_WATER, (0.5 + x), (1.8 + y), (0.6 + z), 5, 0.05, 0, 0.1, 1);
					} else {
						if (Direction.EAST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
							if (world instanceof ServerLevel _level)
								_level.sendParticles(ParticleTypes.DRIPPING_DRIPSTONE_WATER, (0.6 + x), (1.8 + y), (0.5 + z), 5, 0.1, 0, 0.05, 1);
						}
					}
				}
			}
		}
	}

	private static Direction getDirectionFromBlockState(BlockState blockState) {
		if (blockState.getBlock().getStateDefinition().getProperty("facing") instanceof EnumProperty ep && ep.getValueClass() == Direction.class)
			return (Direction) blockState.getValue(ep);
		if (blockState.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty ep && ep.getValueClass() == Direction.Axis.class)
			return Direction.fromAxisAndDirection((Direction.Axis) blockState.getValue(ep), Direction.AxisDirection.POSITIVE);
		return Direction.NORTH;
	}
}