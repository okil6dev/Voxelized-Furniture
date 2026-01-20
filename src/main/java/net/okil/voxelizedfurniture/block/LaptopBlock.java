package net.okil.voxelizedfurniture.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class LaptopBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(14, 0.75, 12, 14.5, 10.75, 14), box(1.5, 0.75, 12, 2, 10.75, 14), box(2, 0.75, 12, 14, 1.25, 14), box(1.5, 0, 2, 14.5, 0.75, 12), box(2.5, 0.36319, 11.85868, 3.5, 0.66319, 13.85868),
			box(12.5, 0.36319, 11.85868, 13.5, 0.66319, 13.85868), box(2, 10.25, 12, 14, 10.75, 14), box(2, 1.25, 13, 14, 10.25, 14), box(2, 1.25, 12.2, 14, 10.25, 13));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(1.5, 0.75, 2, 2, 10.75, 4), box(14, 0.75, 2, 14.5, 10.75, 4), box(2, 0.75, 2, 14, 1.25, 4), box(1.5, 0, 4, 14.5, 0.75, 14), box(12.5, 0.36319, 2.14132, 13.5, 0.66319, 4.14132),
			box(2.5, 0.36319, 2.14132, 3.5, 0.66319, 4.14132), box(2, 10.25, 2, 14, 10.75, 4), box(2, 1.25, 2, 14, 10.25, 3), box(2, 1.25, 3, 14, 10.25, 3.8));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(2, 0.75, 14, 4, 10.75, 14.5), box(2, 0.75, 1.5, 4, 10.75, 2), box(2, 0.75, 2, 4, 1.25, 14), box(4, 0, 1.5, 14, 0.75, 14.5), box(2.14132, 0.36319, 2.5, 4.14132, 0.66319, 3.5),
			box(2.14132, 0.36319, 12.5, 4.14132, 0.66319, 13.5), box(2, 10.25, 2, 4, 10.75, 14), box(2, 1.25, 2, 3, 10.25, 14), box(3, 1.25, 2, 3.8, 10.25, 14));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(12, 0.75, 1.5, 14, 10.75, 2), box(12, 0.75, 14, 14, 10.75, 14.5), box(12, 0.75, 2, 14, 1.25, 14), box(2, 0, 1.5, 12, 0.75, 14.5), box(11.85868, 0.36319, 12.5, 13.85868, 0.66319, 13.5),
			box(11.85868, 0.36319, 2.5, 13.85868, 0.66319, 3.5), box(12, 10.25, 2, 14, 10.75, 14), box(13, 1.25, 2, 14, 10.25, 14), box(12.2, 1.25, 2, 13, 10.25, 14));

	public LaptopBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(2f, 5f).requiresCorrectToolForDrops().noOcclusion().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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
		return (switch (state.getValue(FACING)) {
			case NORTH -> SHAPE_NORTH;
			case SOUTH -> SHAPE_SOUTH;
			case EAST -> SHAPE_EAST;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		});
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}