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

public class KitchenFridgeBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(0, 0, 3, 16, 16, 16), box(0, 16, 3, 16, 32, 16), box(0.25, 16, 2, 15.75, 31.5, 3), box(14, 12, 1, 15, 24, 2), box(14, 4, 1, 15, 9, 2), box(0.25, 11, 2, 15.75, 16, 3),
			box(0.25, 1, 2, 15.75, 10, 3), box(3, 26, 1.9, 7, 30, 2));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 0, 16, 16, 13), box(0, 16, 0, 16, 32, 13), box(0.25, 16, 13, 15.75, 31.5, 14), box(1, 12, 14, 2, 24, 15), box(1, 4, 14, 2, 9, 15), box(0.25, 11, 13, 15.75, 16, 14),
			box(0.25, 1, 13, 15.75, 10, 14), box(9, 26, 14, 13, 30, 14.1));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 0, 0, 13, 16, 16), box(0, 16, 0, 13, 32, 16), box(13, 16, 0.25, 14, 31.5, 15.75), box(14, 12, 14, 15, 24, 15), box(14, 4, 14, 15, 9, 15), box(13, 11, 0.25, 14, 16, 15.75),
			box(13, 1, 0.25, 14, 10, 15.75), box(14, 26, 3, 14.1, 30, 7));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(3, 0, 0, 16, 16, 16), box(3, 16, 0, 16, 32, 16), box(2, 16, 0.25, 3, 31.5, 15.75), box(1, 12, 1, 2, 24, 2), box(1, 4, 1, 2, 9, 2), box(2, 11, 0.25, 3, 16, 15.75),
			box(2, 1, 0.25, 3, 10, 15.75), box(1.9, 26, 9, 2, 30, 13));

	public KitchenFridgeBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(6f, 9f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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