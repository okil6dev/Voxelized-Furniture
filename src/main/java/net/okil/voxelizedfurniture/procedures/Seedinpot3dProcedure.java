package net.okil.voxelizedfurniture.procedures;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModBlocks;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

public class Seedinpot3dProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (VoxelizedFurnitureModBlocks.SEED_IN_POT == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			if (entity instanceof Player _player)
				_player.containerMenu = _player.inventoryMenu;
		}
		if (VoxelizedFurnitureModBlocks.SEED_IN_POT_RAMDOM == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			{
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockState _bs = VoxelizedFurnitureModBlocks.SEED_IN_POT.defaultBlockState();
				BlockState _bso = world.getBlockState(_bp);
				for (Property<?> _propertyOld : _bso.getProperties()) {
					Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
					if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
						try {
							_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
						} catch (Exception e) {
						}
				}
				world.setBlock(_bp, _bs, 3);
			}
			Seedinpot3dProcedure.execute(world, x, y, z, entity);
		}
	}
}