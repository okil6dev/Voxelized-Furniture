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

public class KitchenDrawer1Block extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(0, 0, 3, 16, 14, 16), box(1, 12.25, 2.5, 15, 13.25, 3), box(1, 10.25, 2.5, 15, 11.25, 3), box(14, 11.25, 2.5, 15, 12.25, 3), box(1, 11.25, 2.5, 2, 12.25, 3),
			box(1, 2.75, 2.5, 2, 5.75, 3), box(1, 1.75, 2.5, 15, 2.75, 3), box(1, 5.75, 2.5, 15, 6.75, 3), box(7, 5.75, 2, 9, 6.75, 2.5), box(7, 12.25, 2, 9, 13.25, 2.5), box(1, 7, 2.5, 15, 8, 3), box(14, 8, 2.5, 15, 9, 3), box(1, 9, 2.5, 15, 10, 3),
			box(7, 9, 2, 9, 10, 2.5), box(1, 8, 2.5, 2, 9, 3), box(14, 2.75, 2.5, 15, 5.75, 3), box(0, 14, 7, 16, 16, 10), box(0, 14, 11, 16, 16, 14), box(0, 14, 15, 16, 16, 16), box(0, 14, 14, 16, 16, 15), box(0, 14, 10, 16, 16, 11),
			box(0, 14, 6, 16, 16, 7), box(0, 14, 2.25, 16, 16, 6));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 0, 16, 14, 13), box(1, 12.25, 13, 15, 13.25, 13.5), box(1, 10.25, 13, 15, 11.25, 13.5), box(1, 11.25, 13, 2, 12.25, 13.5), box(14, 11.25, 13, 15, 12.25, 13.5),
			box(14, 2.75, 13, 15, 5.75, 13.5), box(1, 1.75, 13, 15, 2.75, 13.5), box(1, 5.75, 13, 15, 6.75, 13.5), box(7, 5.75, 13.5, 9, 6.75, 14), box(7, 12.25, 13.5, 9, 13.25, 14), box(1, 7, 13, 15, 8, 13.5), box(1, 8, 13, 2, 9, 13.5),
			box(1, 9, 13, 15, 10, 13.5), box(7, 9, 13.5, 9, 10, 14), box(14, 8, 13, 15, 9, 13.5), box(1, 2.75, 13, 2, 5.75, 13.5), box(0, 14, 6, 16, 16, 9), box(0, 14, 2, 16, 16, 5), box(0, 14, 0, 16, 16, 1), box(0, 14, 1, 16, 16, 2),
			box(0, 14, 5, 16, 16, 6), box(0, 14, 9, 16, 16, 10), box(0, 14, 10, 16, 16, 13.75));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 0, 0, 13, 14, 16), box(13, 12.25, 1, 13.5, 13.25, 15), box(13, 10.25, 1, 13.5, 11.25, 15), box(13, 11.25, 14, 13.5, 12.25, 15), box(13, 11.25, 1, 13.5, 12.25, 2),
			box(13, 2.75, 1, 13.5, 5.75, 2), box(13, 1.75, 1, 13.5, 2.75, 15), box(13, 5.75, 1, 13.5, 6.75, 15), box(13.5, 5.75, 7, 14, 6.75, 9), box(13.5, 12.25, 7, 14, 13.25, 9), box(13, 7, 1, 13.5, 8, 15), box(13, 8, 14, 13.5, 9, 15),
			box(13, 9, 1, 13.5, 10, 15), box(13.5, 9, 7, 14, 10, 9), box(13, 8, 1, 13.5, 9, 2), box(13, 2.75, 14, 13.5, 5.75, 15), box(6, 14, 0, 9, 16, 16), box(2, 14, 0, 5, 16, 16), box(0, 14, 0, 1, 16, 16), box(1, 14, 0, 2, 16, 16),
			box(5, 14, 0, 6, 16, 16), box(9, 14, 0, 10, 16, 16), box(10, 14, 0, 13.75, 16, 16));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(3, 0, 0, 16, 14, 16), box(2.5, 12.25, 1, 3, 13.25, 15), box(2.5, 10.25, 1, 3, 11.25, 15), box(2.5, 11.25, 1, 3, 12.25, 2), box(2.5, 11.25, 14, 3, 12.25, 15),
			box(2.5, 2.75, 14, 3, 5.75, 15), box(2.5, 1.75, 1, 3, 2.75, 15), box(2.5, 5.75, 1, 3, 6.75, 15), box(2, 5.75, 7, 2.5, 6.75, 9), box(2, 12.25, 7, 2.5, 13.25, 9), box(2.5, 7, 1, 3, 8, 15), box(2.5, 8, 1, 3, 9, 2), box(2.5, 9, 1, 3, 10, 15),
			box(2, 9, 7, 2.5, 10, 9), box(2.5, 8, 14, 3, 9, 15), box(2.5, 2.75, 1, 3, 5.75, 2), box(7, 14, 0, 10, 16, 16), box(11, 14, 0, 14, 16, 16), box(15, 14, 0, 16, 16, 16), box(14, 14, 0, 15, 16, 16), box(10, 14, 0, 11, 16, 16),
			box(6, 14, 0, 7, 16, 16), box(2.25, 14, 0, 6, 16, 16));

	public KitchenDrawer1Block(BlockBehaviour.Properties properties) {
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