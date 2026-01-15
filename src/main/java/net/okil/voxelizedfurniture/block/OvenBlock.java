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

public class OvenBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(0, 0, 2, 16, 16, 3), box(2, 11, 1, 3, 12, 2), box(13, 11, 1, 14, 12, 2), box(2, 11, 0, 14, 12, 1), box(6, 13, 1.75, 10, 15, 2), box(0, 15, 3, 16, 16, 16), box(0, 0, 3, 16, 15, 16),
			box(1.25, 13.25, 1.75, 2.75, 14.75, 2), box(3.25, 13.25, 1.75, 4.75, 14.75, 2), box(11.25, 13.25, 1.75, 12.75, 14.75, 2), box(13.25, 13.25, 1.75, 14.75, 14.75, 2));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 13, 16, 16, 14), box(13, 11, 14, 14, 12, 15), box(2, 11, 14, 3, 12, 15), box(2, 11, 15, 14, 12, 16), box(6, 13, 14, 10, 15, 14.25), box(0, 15, 0, 16, 16, 13),
			box(0, 0, 0, 16, 15, 13), box(13.25, 13.25, 14, 14.75, 14.75, 14.25), box(11.25, 13.25, 14, 12.75, 14.75, 14.25), box(3.25, 13.25, 14, 4.75, 14.75, 14.25), box(1.25, 13.25, 14, 2.75, 14.75, 14.25));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(13, 0, 0, 14, 16, 16), box(14, 11, 2, 15, 12, 3), box(14, 11, 13, 15, 12, 14), box(15, 11, 2, 16, 12, 14), box(14, 13, 6, 14.25, 15, 10), box(0, 15, 0, 13, 16, 16),
			box(0, 0, 0, 13, 15, 16), box(14, 13.25, 1.25, 14.25, 14.75, 2.75), box(14, 13.25, 3.25, 14.25, 14.75, 4.75), box(14, 13.25, 11.25, 14.25, 14.75, 12.75), box(14, 13.25, 13.25, 14.25, 14.75, 14.75));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(2, 0, 0, 3, 16, 16), box(1, 11, 13, 2, 12, 14), box(1, 11, 2, 2, 12, 3), box(0, 11, 2, 1, 12, 14), box(1.75, 13, 6, 2, 15, 10), box(3, 15, 0, 16, 16, 16), box(3, 0, 0, 16, 15, 16),
			box(1.75, 13.25, 13.25, 2, 14.75, 14.75), box(1.75, 13.25, 11.25, 2, 14.75, 12.75), box(1.75, 13.25, 3.25, 2, 14.75, 4.75), box(1.75, 13.25, 1.25, 2, 14.75, 2.75));

	public OvenBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(7f, 9f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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