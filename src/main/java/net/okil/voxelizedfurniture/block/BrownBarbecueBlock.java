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

public class BrownBarbecueBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(15, 0.5, 3, 17, 2.5, 3.9), box(14.5, 0, 3.1, 17.5, 3, 4), box(15, 0.5, 16.1, 17, 2.5, 17), box(14.5, 0, 16, 17.5, 3, 16.9), box(15, 0, 4, 16, 18, 5), box(1, 2, 4, 15, 3, 5),
			box(1, 2, 15, 15, 3, 16), box(15, 2, 5, 16, 3, 15), box(0, 2, 5, 1, 3, 15), box(0, 0, 4, 1, 18, 5), box(1, 11, 4, 15, 16, 5), box(6, 12, 3.25, 10, 15, 4), box(6.75, 13.25, 2.85, 7.5, 14, 3.1), box(5.8, 11.9, 3.95, 10.1, 15.1, 4),
			box(1, 16.3, 4, 15, 18, 5), box(1, 10, 4, 15, 11, 5), box(0, 18, 4, 16, 19, 5), box(12, 18, 3, 13, 19, 4), box(3, 18, 3, 4, 19, 4), box(3, 18, 2, 13, 19, 3), box(1, 11, 15, 15, 16, 16), box(0, 11, 5, 16, 16, 15),
			box(0, 16.3, 5, 16, 18, 15), box(-1, 17.3, 5, 0, 18, 15), box(-2.5, 17.3, 5, -1.5, 18, 15), box(-4, 17.3, 5, -3, 18, 15), box(-6, 17.4, 9.5, 3, 17.8, 10.5), box(-5.5, 17.3, 5, -4.5, 18, 15), box(-7, 17.3, 5, -6, 18, 15),
			box(0, 18, 5, 16, 20, 16), box(0, 20, 6, 16, 22, 16), box(0, 22, 8.1, 16, 24.1, 16), box(1, 13.3, 5, 15, 17, 15), box(1, 16.3, 15, 15, 18, 16), box(15, 0, 15, 16, 18, 16), box(0, 0, 15, 1, 18, 16));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(-1, 0.5, 12.1, 1, 2.5, 13), box(-1.5, 0, 12, 1.5, 3, 12.9), box(-1, 0.5, -1, 1, 2.5, -0.1), box(-1.5, 0, -0.9, 1.5, 3, 0), box(0, 0, 11, 1, 18, 12), box(1, 2, 11, 15, 3, 12),
			box(1, 2, 0, 15, 3, 1), box(0, 2, 1, 1, 3, 11), box(15, 2, 1, 16, 3, 11), box(15, 0, 11, 16, 18, 12), box(1, 11, 11, 15, 16, 12), box(6, 12, 12, 10, 15, 12.75), box(8.5, 13.25, 12.9, 9.25, 14, 13.15),
			box(5.9, 11.9, 12, 10.2, 15.1, 12.05), box(1, 16.3, 11, 15, 18, 12), box(1, 10, 11, 15, 11, 12), box(0, 18, 11, 16, 19, 12), box(3, 18, 12, 4, 19, 13), box(12, 18, 12, 13, 19, 13), box(3, 18, 13, 13, 19, 14), box(1, 11, 0, 15, 16, 1),
			box(0, 11, 1, 16, 16, 11), box(0, 16.3, 1, 16, 18, 11), box(16, 17.3, 1, 17, 18, 11), box(17.5, 17.3, 1, 18.5, 18, 11), box(19, 17.3, 1, 20, 18, 11), box(13, 17.4, 5.5, 22, 17.8, 6.5), box(20.5, 17.3, 1, 21.5, 18, 11),
			box(22, 17.3, 1, 23, 18, 11), box(0, 18, 0, 16, 20, 11), box(0, 20, 0, 16, 22, 10), box(0, 22, 0, 16, 24.1, 7.9), box(1, 13.3, 1, 15, 17, 11), box(1, 16.3, 0, 15, 18, 1), box(0, 0, 0, 1, 18, 1), box(15, 0, 0, 16, 18, 1));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(12.1, 0.5, 15, 13, 2.5, 17), box(12, 0, 14.5, 12.9, 3, 17.5), box(-1, 0.5, 15, -0.1, 2.5, 17), box(-0.9, 0, 14.5, 0, 3, 17.5), box(11, 0, 15, 12, 18, 16), box(11, 2, 1, 12, 3, 15),
			box(0, 2, 1, 1, 3, 15), box(1, 2, 15, 11, 3, 16), box(1, 2, 0, 11, 3, 1), box(11, 0, 0, 12, 18, 1), box(11, 11, 1, 12, 16, 15), box(12, 12, 6, 12.75, 15, 10), box(12.9, 13.25, 6.75, 13.15, 14, 7.5), box(12, 11.9, 5.8, 12.05, 15.1, 10.1),
			box(11, 16.3, 1, 12, 18, 15), box(11, 10, 1, 12, 11, 15), box(11, 18, 0, 12, 19, 16), box(12, 18, 12, 13, 19, 13), box(12, 18, 3, 13, 19, 4), box(13, 18, 3, 14, 19, 13), box(0, 11, 1, 1, 16, 15), box(1, 11, 0, 11, 16, 16),
			box(1, 16.3, 0, 11, 18, 16), box(1, 17.3, -1, 11, 18, 0), box(1, 17.3, -2.5, 11, 18, -1.5), box(1, 17.3, -4, 11, 18, -3), box(5.5, 17.4, -6, 6.5, 17.8, 3), box(1, 17.3, -5.5, 11, 18, -4.5), box(1, 17.3, -7, 11, 18, -6),
			box(0, 18, 0, 11, 20, 16), box(0, 20, 0, 10, 22, 16), box(0, 22, 0, 7.9, 24.1, 16), box(1, 13.3, 1, 11, 17, 15), box(0, 16.3, 1, 1, 18, 15), box(0, 0, 15, 1, 18, 16), box(0, 0, 0, 1, 18, 1));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(3, 0.5, -1, 3.9, 2.5, 1), box(3.1, 0, -1.5, 4, 3, 1.5), box(16.1, 0.5, -1, 17, 2.5, 1), box(16, 0, -1.5, 16.9, 3, 1.5), box(4, 0, 0, 5, 18, 1), box(4, 2, 1, 5, 3, 15),
			box(15, 2, 1, 16, 3, 15), box(5, 2, 0, 15, 3, 1), box(5, 2, 15, 15, 3, 16), box(4, 0, 15, 5, 18, 16), box(4, 11, 1, 5, 16, 15), box(3.25, 12, 6, 4, 15, 10), box(2.85, 13.25, 8.5, 3.1, 14, 9.25), box(3.95, 11.9, 5.9, 4, 15.1, 10.2),
			box(4, 16.3, 1, 5, 18, 15), box(4, 10, 1, 5, 11, 15), box(4, 18, 0, 5, 19, 16), box(3, 18, 3, 4, 19, 4), box(3, 18, 12, 4, 19, 13), box(2, 18, 3, 3, 19, 13), box(15, 11, 1, 16, 16, 15), box(5, 11, 0, 15, 16, 16),
			box(5, 16.3, 0, 15, 18, 16), box(5, 17.3, 16, 15, 18, 17), box(5, 17.3, 17.5, 15, 18, 18.5), box(5, 17.3, 19, 15, 18, 20), box(9.5, 17.4, 13, 10.5, 17.8, 22), box(5, 17.3, 20.5, 15, 18, 21.5), box(5, 17.3, 22, 15, 18, 23),
			box(5, 18, 0, 16, 20, 16), box(6, 20, 0, 16, 22, 16), box(8.1, 22, 0, 16, 24.1, 16), box(5, 13.3, 1, 15, 17, 15), box(15, 16.3, 1, 16, 18, 15), box(15, 0, 0, 16, 18, 1), box(15, 0, 15, 16, 18, 16));

	public BrownBarbecueBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(3f, 5f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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