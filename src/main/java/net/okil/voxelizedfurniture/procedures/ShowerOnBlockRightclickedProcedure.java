package net.okil.voxelizedfurniture.procedures;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModParticleTypes;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class ShowerOnBlockRightclickedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		Direction rotation = Direction.NORTH;
		double ShowerTime = 0;
		if (world instanceof Level _level0 && _level0.hasNeighborSignal(BlockPos.containing(x, y, z))) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.pointed_dripstone.drip_water")), SoundSource.NEUTRAL, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.pointed_dripstone.drip_water")), SoundSource.NEUTRAL, 1, 1, false);
				}
			}
			if (Direction.NORTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (VoxelizedFurnitureModParticleTypes.SHOWER_P.get()), (0.5 + x), (1.8 + y), (0.4 + z), 30, 0.05, 0, 0.1, 1);
			} else {
				if (Direction.WEST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (VoxelizedFurnitureModParticleTypes.SHOWER_P.get()), (0.4 + x), (1.8 + y), (0.5 + z), 30, 0.1, 0, 0.05, 1);
				} else {
					if (Direction.SOUTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
						if (world instanceof ServerLevel _level)
							_level.sendParticles((SimpleParticleType) (VoxelizedFurnitureModParticleTypes.SHOWER_P.get()), (0.5 + x), (1.8 + y), (0.6 + z), 30, 0.05, 0, 0.1, 1);
					} else {
						if (Direction.EAST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
							if (world instanceof ServerLevel _level)
								_level.sendParticles((SimpleParticleType) (VoxelizedFurnitureModParticleTypes.SHOWER_P.get()), (0.6 + x), (1.8 + y), (0.5 + z), 30, 0.1, 0, 0.05, 1);
						}
					}
				}
			}
		}
	}

	private static Direction getDirectionFromBlockState(BlockState blockState) {
		Property<?> prop = blockState.getBlock().getStateDefinition().getProperty("facing");
		if (prop instanceof DirectionProperty dp)
			return blockState.getValue(dp);
		prop = blockState.getBlock().getStateDefinition().getProperty("axis");
		return prop instanceof EnumProperty ep && ep.getPossibleValues().toArray()[0] instanceof Direction.Axis ? Direction.fromAxisAndDirection((Direction.Axis) blockState.getValue(ep), Direction.AxisDirection.POSITIVE) : Direction.NORTH;
	}
}