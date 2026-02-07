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
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class IronTentPartBlock extends Block {
	public static final DirectionProperty FACING = DirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(0, 0, 15, 16, 16, 16), box(0, 0, 14, 16, 1, 15), box(0, 3, 14, 16, 4, 15), box(0, 6, 14, 16, 7, 15), box(0, 15, 14, 16, 16, 15), box(0, 12, 14, 16, 13, 15), box(0, 9, 14, 16, 10, 15));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 0, 16, 16, 1), box(0, 0, 1, 16, 1, 2), box(0, 3, 1, 16, 4, 2), box(0, 6, 1, 16, 7, 2), box(0, 15, 1, 16, 16, 2), box(0, 12, 1, 16, 13, 2), box(0, 9, 1, 16, 10, 2));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 0, 0, 1, 16, 16), box(1, 0, 0, 2, 1, 16), box(1, 3, 0, 2, 4, 16), box(1, 6, 0, 2, 7, 16), box(1, 15, 0, 2, 16, 16), box(1, 12, 0, 2, 13, 16), box(1, 9, 0, 2, 10, 16));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(15, 0, 0, 16, 16, 16), box(14, 0, 0, 15, 1, 16), box(14, 3, 0, 15, 4, 16), box(14, 6, 0, 15, 7, 16), box(14, 15, 0, 15, 16, 16), box(14, 12, 0, 15, 13, 16), box(14, 9, 0, 15, 10, 16));
	private static final VoxelShape SHAPE_UP = Shapes.or(box(0, 0, 0, 16, 1, 16), box(0, 1, 0, 16, 2, 1), box(0, 1, 3, 16, 2, 4), box(0, 1, 6, 16, 2, 7), box(0, 1, 15, 16, 2, 16), box(0, 1, 12, 16, 2, 13), box(0, 1, 9, 16, 2, 10));
	private static final VoxelShape SHAPE_DOWN = Shapes.or(box(0, 15, 0, 16, 16, 16), box(0, 14, 15, 16, 15, 16), box(0, 14, 12, 16, 15, 13), box(0, 14, 9, 16, 15, 10), box(0, 14, 0, 16, 15, 1), box(0, 14, 3, 16, 15, 4), box(0, 14, 6, 16, 15, 7));

	public IronTentPartBlock() {
		super(BlockBehaviour.Properties.of().strength(2f, 3f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
			case UP -> SHAPE_UP;
			case DOWN -> SHAPE_DOWN;
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
		return super.getStateForPlacement(context).setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}