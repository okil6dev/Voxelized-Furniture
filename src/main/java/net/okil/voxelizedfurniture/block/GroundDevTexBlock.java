package net.okil.voxelizedfurniture.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class GroundDevTexBlock extends Block {
	public GroundDevTexBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(-1, 3600000));
	}
}