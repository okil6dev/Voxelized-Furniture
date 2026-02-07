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

public class SoapBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(27.5, 4.45, 12.5, 28.5, 5.45, 14.5), box(26.5, 0, 13, 29.5, 4, 15), box(27.25, 3.2, 13.25, 28.75, 4.2, 14.75), box(27.5, 3.45, 13.5, 28.5, 4.45, 14.5));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(-12.5, 4.45, 1.5, -11.5, 5.45, 3.5), box(-13.5, 0, 1, -10.5, 4, 3), box(-12.75, 3.2, 1.25, -11.25, 4.2, 2.75), box(-12.5, 3.45, 1.5, -11.5, 4.45, 2.5));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(1.5, 4.45, 27.5, 3.5, 5.45, 28.5), box(1, 0, 26.5, 3, 4, 29.5), box(1.25, 3.2, 27.25, 2.75, 4.2, 28.75), box(1.5, 3.45, 27.5, 2.5, 4.45, 28.5));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(12.5, 4.45, -12.5, 14.5, 5.45, -11.5), box(13, 0, -13.5, 15, 4, -10.5), box(13.25, 3.2, -12.75, 14.75, 4.2, -11.25), box(13.5, 3.45, -12.5, 14.5, 4.45, -11.5));

	public SoapBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(0f, 10f).noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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