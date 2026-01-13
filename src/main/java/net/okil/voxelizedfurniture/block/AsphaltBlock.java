package net.okil.voxelizedfurniture.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class AsphaltBlock extends Block {
	public AsphaltBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(7f, 10f).requiresCorrectToolForDrops());
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 15;
	}
}