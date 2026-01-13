package net.okil.voxelizedfurniture.procedures;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModBlocks;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class LightSwitchProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (VoxelizedFurnitureModBlocks.LIGHT.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			world.setBlock(BlockPos.containing(x, y, z), VoxelizedFurnitureModBlocks.LIGHTON.get().defaultBlockState(), 3);
		} else {
			world.setBlock(BlockPos.containing(x, y, z), VoxelizedFurnitureModBlocks.LIGHT.get().defaultBlockState(), 3);
		}
	}
}