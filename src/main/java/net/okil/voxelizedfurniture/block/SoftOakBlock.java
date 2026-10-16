package net.okil.voxelizedfurniture.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class SoftOakBlock extends Block {
	public SoftOakBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2f));
	}
}