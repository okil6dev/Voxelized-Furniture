package net.okil.voxelizedfurniture.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class AsphaltBlock extends Block {
	public AsphaltBlock() {
		super(BlockBehaviour.Properties.of().strength(7f, 10f).requiresCorrectToolForDrops());
	}
}