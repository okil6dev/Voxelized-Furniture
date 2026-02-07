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

public class BarrelPlantBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(1, 0.5, 4, 15, 1.5, 12), box(1, 1.5, 4, 15, 8.5, 5), box(1, 1.5, 11, 15, 8.5, 12), box(3, 0.5, 12, 5, 8.5, 12.5), box(11, 0.5, 12, 13, 8.5, 12.5), box(11, 0.5, 3.5, 13, 8.5, 4),
			box(11, 0, 3.5, 13, 0.5, 12.5), box(3, 0, 3.5, 5, 0.5, 12.5), box(3, 0.5, 3.5, 5, 8.5, 4), box(1, 1.5, 5, 2, 8.5, 11), box(14, 1.5, 5, 15, 8.5, 11), box(2, 1.5, 5, 14, 7.5, 11));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(1, 0.5, 4, 15, 1.5, 12), box(1, 1.5, 11, 15, 8.5, 12), box(1, 1.5, 4, 15, 8.5, 5), box(11, 0.5, 3.5, 13, 8.5, 4), box(3, 0.5, 3.5, 5, 8.5, 4), box(3, 0.5, 12, 5, 8.5, 12.5),
			box(3, 0, 3.5, 5, 0.5, 12.5), box(11, 0, 3.5, 13, 0.5, 12.5), box(11, 0.5, 12, 13, 8.5, 12.5), box(14, 1.5, 5, 15, 8.5, 11), box(1, 1.5, 5, 2, 8.5, 11), box(2, 1.5, 5, 14, 7.5, 11));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(4, 0.5, 1, 12, 1.5, 15), box(11, 1.5, 1, 12, 8.5, 15), box(4, 1.5, 1, 5, 8.5, 15), box(3.5, 0.5, 3, 4, 8.5, 5), box(3.5, 0.5, 11, 4, 8.5, 13), box(12, 0.5, 11, 12.5, 8.5, 13),
			box(3.5, 0, 11, 12.5, 0.5, 13), box(3.5, 0, 3, 12.5, 0.5, 5), box(12, 0.5, 3, 12.5, 8.5, 5), box(5, 1.5, 1, 11, 8.5, 2), box(5, 1.5, 14, 11, 8.5, 15), box(5, 1.5, 2, 11, 7.5, 14));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(4, 0.5, 1, 12, 1.5, 15), box(4, 1.5, 1, 5, 8.5, 15), box(11, 1.5, 1, 12, 8.5, 15), box(12, 0.5, 11, 12.5, 8.5, 13), box(12, 0.5, 3, 12.5, 8.5, 5), box(3.5, 0.5, 3, 4, 8.5, 5),
			box(3.5, 0, 3, 12.5, 0.5, 5), box(3.5, 0, 11, 12.5, 0.5, 13), box(3.5, 0.5, 11, 4, 8.5, 13), box(5, 1.5, 14, 11, 8.5, 15), box(5, 1.5, 1, 11, 8.5, 2), box(5, 1.5, 2, 11, 7.5, 14));

	public BarrelPlantBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1.5f, 4f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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