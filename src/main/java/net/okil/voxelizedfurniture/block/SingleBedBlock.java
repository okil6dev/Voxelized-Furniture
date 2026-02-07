package net.okil.voxelizedfurniture.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class SingleBedBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(14, 0, 0, 16, 3, 2), box(14, 0, 30, 16, 3, 32), box(0, 0, 30, 2, 3, 32), box(0, 0, 0, 2, 3, 2), box(0, 3, 0, 16, 5, 16), box(1.25, 8, 23, 14.75, 16.25, 26),
			box(1.5, 9, 26, 14.5, 14, 30), box(0, 5, 18, 16, 9, 30), box(1, 8, 2, 15, 9, 8), box(15, 5, 2, 16, 9, 8), box(0, 5, 2, 1, 9, 8), box(0.5, 5, 0.5, 15.5, 8, 16), box(0.5, 5, 16, 15.5, 8, 30), box(0, 5, 30, 16, 17, 32),
			box(0, 3, 16, 16, 5, 32));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 14, 2, 3, 16), box(0, 0, -16, 2, 3, -14), box(14, 0, -16, 16, 3, -14), box(14, 0, 14, 16, 3, 16), box(0, 3, 0, 16, 5, 16), box(1.25, 8, -10, 14.75, 16.25, -7),
			box(1.5, 9, -14, 14.5, 14, -10), box(0, 5, -14, 16, 9, -2), box(1, 8, 8, 15, 9, 14), box(0, 5, 8, 1, 9, 14), box(15, 5, 8, 16, 9, 14), box(0.5, 5, 0, 15.5, 8, 15.5), box(0.5, 5, -14, 15.5, 8, 0), box(0, 5, -16, 16, 17, -14),
			box(0, 3, -16, 16, 5, 0));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(14, 0, 14, 16, 3, 16), box(-16, 0, 14, -14, 3, 16), box(-16, 0, 0, -14, 3, 2), box(14, 0, 0, 16, 3, 2), box(0, 3, 0, 16, 5, 16), box(-10, 8, 1.25, -7, 16.25, 14.75),
			box(-14, 9, 1.5, -10, 14, 14.5), box(-14, 5, 0, -2, 9, 16), box(8, 8, 1, 14, 9, 15), box(8, 5, 15, 14, 9, 16), box(8, 5, 0, 14, 9, 1), box(0, 5, 0.5, 15.5, 8, 15.5), box(-14, 5, 0.5, 0, 8, 15.5), box(-16, 5, 0, -14, 17, 16),
			box(-16, 3, 0, 0, 5, 16));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(0, 0, 0, 2, 3, 2), box(30, 0, 0, 32, 3, 2), box(30, 0, 14, 32, 3, 16), box(0, 0, 14, 2, 3, 16), box(0, 3, 0, 16, 5, 16), box(23, 8, 1.25, 26, 16.25, 14.75), box(26, 9, 1.5, 30, 14, 14.5),
			box(18, 5, 0, 30, 9, 16), box(2, 8, 1, 8, 9, 15), box(2, 5, 0, 8, 9, 1), box(2, 5, 15, 8, 9, 16), box(0.5, 5, 0.5, 16, 8, 15.5), box(16, 5, 0.5, 30, 8, 15.5), box(30, 5, 0, 32, 17, 16), box(16, 3, 0, 32, 5, 16));

	public SingleBedBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(3f, 5f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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