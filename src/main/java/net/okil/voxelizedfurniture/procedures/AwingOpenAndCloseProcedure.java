package net.okil.voxelizedfurniture.procedures;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModBlocks;
import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class AwingOpenAndCloseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (VoxelizedFurnitureModBlocks.AWNING.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("animation") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			VoxelizedFurnitureMod.queueServerWork(40, () -> {
				{
					BlockPos _bp = BlockPos.containing(x, y, z);
					BlockState _bs = VoxelizedFurnitureModBlocks.AWNING_CLOSED.get().defaultBlockState();
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
			});
		}
		if (VoxelizedFurnitureModBlocks.AWNING_CLOSED.get() == (world.getBlockState(BlockPos.containing(x, y, z))).getBlock()) {
			{
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockState _bs = VoxelizedFurnitureModBlocks.AWNING.get().defaultBlockState();
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
			{
				int _value = 2;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("animation") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
		}
	}
}