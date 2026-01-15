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

public class KitchenSinkBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(0, 0, 3, 16, 11, 16), box(0, 11, 3, 16, 16, 5), box(0, 11, 12, 16, 16, 16), box(7.5, 16, 14, 8.5, 21, 15), box(14, 11, 5, 16, 16, 12), box(2, 11, 5, 14, 12, 12),
			box(0, 11, 5, 2, 16, 12), box(1, 2, 2.5, 2, 9, 3), box(1, 1, 2.5, 7, 2, 3), box(1, 9, 2.5, 7, 10, 3), box(6, 9, 2, 7, 10, 2.5), box(9, 9, 2, 10, 10, 2.5), box(6, 2, 2.5, 7, 9, 3), box(9, 2, 2.5, 10, 9, 3), box(9, 9, 2.5, 15, 10, 3),
			box(14, 2, 2.5, 15, 9, 3), box(9, 1, 2.5, 15, 2, 3));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 0, 16, 11, 13), box(0, 11, 11, 16, 16, 13), box(0, 11, 0, 16, 16, 4), box(7.5, 16, 1, 8.5, 21, 2), box(0, 11, 4, 2, 16, 11), box(2, 11, 4, 14, 12, 11), box(14, 11, 4, 16, 16, 11),
			box(14, 2, 13, 15, 9, 13.5), box(9, 1, 13, 15, 2, 13.5), box(9, 9, 13, 15, 10, 13.5), box(9, 9, 13.5, 10, 10, 14), box(6, 9, 13.5, 7, 10, 14), box(9, 2, 13, 10, 9, 13.5), box(6, 2, 13, 7, 9, 13.5), box(1, 9, 13, 7, 10, 13.5),
			box(1, 2, 13, 2, 9, 13.5), box(1, 1, 13, 7, 2, 13.5));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 0, 0, 13, 11, 16), box(11, 11, 0, 13, 16, 16), box(0, 11, 0, 4, 16, 16), box(1, 16, 7.5, 2, 21, 8.5), box(4, 11, 14, 11, 16, 16), box(4, 11, 2, 11, 12, 14), box(4, 11, 0, 11, 16, 2),
			box(13, 2, 1, 13.5, 9, 2), box(13, 1, 1, 13.5, 2, 7), box(13, 9, 1, 13.5, 10, 7), box(13.5, 9, 6, 14, 10, 7), box(13.5, 9, 9, 14, 10, 10), box(13, 2, 6, 13.5, 9, 7), box(13, 2, 9, 13.5, 9, 10), box(13, 9, 9, 13.5, 10, 15),
			box(13, 2, 14, 13.5, 9, 15), box(13, 1, 9, 13.5, 2, 15));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(3, 0, 0, 16, 11, 16), box(3, 11, 0, 5, 16, 16), box(12, 11, 0, 16, 16, 16), box(14, 16, 7.5, 15, 21, 8.5), box(5, 11, 0, 12, 16, 2), box(5, 11, 2, 12, 12, 14), box(5, 11, 14, 12, 16, 16),
			box(2.5, 2, 14, 3, 9, 15), box(2.5, 1, 9, 3, 2, 15), box(2.5, 9, 9, 3, 10, 15), box(2, 9, 9, 2.5, 10, 10), box(2, 9, 6, 2.5, 10, 7), box(2.5, 2, 9, 3, 9, 10), box(2.5, 2, 6, 3, 9, 7), box(2.5, 9, 1, 3, 10, 7), box(2.5, 2, 1, 3, 9, 2),
			box(2.5, 1, 1, 3, 2, 7));

	public KitchenSinkBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(5f, 9f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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