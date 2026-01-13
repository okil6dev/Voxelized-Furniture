package net.okil.voxelizedfurniture.block;

import org.checkerframework.checker.units.qual.s;

import net.okil.voxelizedfurniture.procedures.LightSwitchProcedure;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

public class LightonBlock extends Block {
	private static final VoxelShape SHAPE = Shapes.or(box(7, 6, 7, 9, 16, 9), box(5, 0, 5, 11, 6, 11));

	public LightonBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.GLASS).strength(2f, 4f).lightLevel(s -> 15).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return (SHAPE);
	}

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		LightSwitchProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}