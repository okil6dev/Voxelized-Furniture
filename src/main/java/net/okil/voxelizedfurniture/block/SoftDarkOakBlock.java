package net.okil.voxelizedfurniture.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;

public class SoftDarkOakBlock extends Block {
	public SoftDarkOakBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2f));
	}
}