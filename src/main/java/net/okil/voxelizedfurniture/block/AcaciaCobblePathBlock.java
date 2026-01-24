package net.okil.voxelizedfurniture.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.Vec3;
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

public class AcaciaCobblePathBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(-1, 0, -6, 21, 9, 22), box(0, 0, 11, 9, 8, 20), box(5, 0, 17, 20, 9, 26), box(-2, 0, -14, 14, 4, -5), box(-3, 0, 19, 18, 4, 32), box(11, 0, -15, 20, 4, -6), box(12, 0, 23, 21, 3, 32),
			box(0, 0, -9, 22, 7, 0));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(-5, 0, -6, 17, 9, 22), box(7, 0, -4, 16, 8, 5), box(-4, 0, -10, 11, 9, -1), box(2, 0, 21, 18, 4, 30), box(-2, 0, -16, 19, 4, -3), box(-4, 0, 22, 5, 4, 31), box(-5, 0, -16, 4, 3, -7),
			box(-6, 0, 16, 16, 7, 25));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(-6, 0, -1, 22, 9, 21), box(-4, 0, 0, 5, 8, 9), box(-10, 0, 5, -1, 9, 20), box(21, 0, -2, 30, 4, 14), box(-16, 0, -3, -3, 4, 18), box(22, 0, 11, 31, 4, 20), box(-16, 0, 12, -7, 3, 21),
			box(16, 0, 0, 25, 7, 22));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(-6, 0, -5, 22, 9, 17), box(11, 0, 7, 20, 8, 16), box(17, 0, -4, 26, 9, 11), box(-14, 0, 2, -5, 4, 18), box(19, 0, -2, 32, 4, 19), box(-15, 0, -4, -6, 4, 5), box(23, 0, -5, 32, 3, 4),
			box(-9, 0, -6, 0, 7, 16));

	public AcaciaCobblePathBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(15f, 10f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false).dynamicShape().offsetType(Block.OffsetType.XZ));
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
		Vec3 offset = state.getOffset(pos);
		return (switch (state.getValue(FACING)) {
			case NORTH -> SHAPE_NORTH;
			case SOUTH -> SHAPE_SOUTH;
			case EAST -> SHAPE_EAST;
			case WEST -> SHAPE_WEST;
			default -> SHAPE_NORTH;
		}).move(offset.x, offset.y, offset.z);
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