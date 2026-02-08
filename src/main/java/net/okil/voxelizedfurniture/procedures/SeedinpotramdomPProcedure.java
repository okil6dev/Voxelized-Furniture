package net.okil.voxelizedfurniture.procedures;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModBlocks;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

public class SeedinpotramdomPProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		world.setBlock(BlockPos.containing(x, y, z), VoxelizedFurnitureModBlocks.SEED_IN_POT_RAMDOM.get().defaultBlockState(), 3);
		if (entity instanceof Player _player)
			_player.closeContainer();
	}
}