package net.okil.voxelizedfurniture.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
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

public class KitchenDrawerCornerBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(0, 0, 3, 16, 14, 16), box(0, 0, 0, 13, 14, 3), box(10, 14, 0, 13.75, 16, 2.25), box(2, 14, 0, 5, 16, 2.25), box(0, 14, 0, 1, 16, 2.25), box(6, 14, 0, 9, 16, 2.25),
			box(9, 14, 0, 10, 16, 6), box(5, 14, 0, 6, 16, 10), box(1, 14, 0, 2, 16, 14), box(0, 14, 15, 16, 16, 16), box(1, 14, 14, 16, 16, 15), box(0, 14, 11, 16, 16, 14), box(5, 14, 10, 16, 16, 11), box(0, 14, 7, 16, 16, 10),
			box(0, 14, 6, 9, 16, 7), box(0, 14, 10, 5, 16, 11), box(0, 14, 14, 1, 16, 15), box(9, 14, 6, 16, 16, 7), box(0, 14, 2.25, 16, 16, 6));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 0, 16, 14, 13), box(3, 0, 13, 16, 14, 16), box(2.25, 14, 13.75, 6, 16, 16), box(11, 14, 13.75, 14, 16, 16), box(15, 14, 13.75, 16, 16, 16), box(7, 14, 13.75, 10, 16, 16),
			box(6, 14, 10, 7, 16, 16), box(10, 14, 6, 11, 16, 16), box(14, 14, 2, 15, 16, 16), box(0, 14, 0, 16, 16, 1), box(0, 14, 1, 15, 16, 2), box(0, 14, 2, 16, 16, 5), box(0, 14, 5, 11, 16, 6), box(0, 14, 6, 16, 16, 9),
			box(7, 14, 9, 16, 16, 10), box(11, 14, 5, 16, 16, 6), box(15, 14, 1, 16, 16, 2), box(0, 14, 9, 7, 16, 10), box(0, 14, 10, 16, 16, 13.75));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 0, 0, 13, 14, 16), box(13, 0, 0, 16, 14, 13), box(13.75, 14, 10, 16, 16, 13.75), box(13.75, 14, 2, 16, 16, 5), box(13.75, 14, 0, 16, 16, 1), box(13.75, 14, 6, 16, 16, 9),
			box(10, 14, 9, 16, 16, 10), box(6, 14, 5, 16, 16, 6), box(2, 14, 1, 16, 16, 2), box(0, 14, 0, 1, 16, 16), box(1, 14, 1, 2, 16, 16), box(2, 14, 0, 5, 16, 16), box(5, 14, 5, 6, 16, 16), box(6, 14, 0, 9, 16, 16), box(9, 14, 0, 10, 16, 9),
			box(5, 14, 0, 6, 16, 5), box(1, 14, 0, 2, 16, 1), box(9, 14, 9, 10, 16, 16), box(10, 14, 0, 13.75, 16, 16));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(3, 0, 0, 16, 14, 16), box(0, 0, 3, 3, 14, 16), box(0, 14, 2.25, 2.25, 16, 6), box(0, 14, 11, 2.25, 16, 14), box(0, 14, 15, 2.25, 16, 16), box(0, 14, 7, 2.25, 16, 10),
			box(0, 14, 6, 6, 16, 7), box(0, 14, 10, 10, 16, 11), box(0, 14, 14, 14, 16, 15), box(15, 14, 0, 16, 16, 16), box(14, 14, 0, 15, 16, 15), box(11, 14, 0, 14, 16, 16), box(10, 14, 0, 11, 16, 11), box(7, 14, 0, 10, 16, 16),
			box(6, 14, 7, 7, 16, 16), box(10, 14, 11, 11, 16, 16), box(14, 14, 15, 15, 16, 16), box(6, 14, 0, 7, 16, 7), box(2.25, 14, 0, 6, 16, 16));

	public KitchenDrawerCornerBlock() {
		super(BlockBehaviour.Properties.of().strength(5f, 9f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
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