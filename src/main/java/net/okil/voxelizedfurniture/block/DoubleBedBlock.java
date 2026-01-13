package net.okil.voxelizedfurniture.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.EnumProperty;
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

public class DoubleBedBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(14, 0, 0, 16, 3, 2), box(14, 0, 30, 16, 3, 32), box(0, 3, 0, 16, 5, 16), box(-16, 3, 0, 0, 5, 16), box(-16, 3, 16, 0, 5, 32), box(-15.5, 5, 16, 0, 8, 30), box(-16, 5, 18, 0, 9, 30),
			box(-15, 9, 26, -2, 14, 30), box(-15.25, 8, 23, -1.75, 16.25, 26), box(1.75, 8, 23, 15.25, 16.25, 26), box(2, 9, 26, 15, 14, 30), box(0, 5, 18, 16, 9, 30), box(-15.5, 5, 0.5, 0, 8, 16), box(-15, 8, 2, 0, 9, 8), box(0, 8, 2, 15, 9, 8),
			box(15, 5, 2, 16, 9, 8), box(-16, 5, 2, -15, 9, 8), box(0, 5, 0.5, 15.5, 8, 16), box(0, 5, 16, 15.5, 8, 30), box(-16, 5, 30, 0, 17, 32), box(0, 5, 30, 16, 17, 32), box(0, 3, 16, 16, 5, 32), box(-16, 0, 0, -14, 3, 2),
			box(-16, 0, 30, -14, 3, 32));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 14, 2, 3, 16), box(0, 0, -16, 2, 3, -14), box(0, 3, 0, 16, 5, 16), box(16, 3, 0, 32, 5, 16), box(16, 3, -16, 32, 5, 0), box(16, 5, -14, 31.5, 8, 0), box(16, 5, -14, 32, 9, -2),
			box(18, 9, -14, 31, 14, -10), box(17.75, 8, -10, 31.25, 16.25, -7), box(0.75, 8, -10, 14.25, 16.25, -7), box(1, 9, -14, 14, 14, -10), box(0, 5, -14, 16, 9, -2), box(16, 5, 0, 31.5, 8, 15.5), box(16, 8, 8, 31, 9, 14),
			box(1, 8, 8, 16, 9, 14), box(0, 5, 8, 1, 9, 14), box(31, 5, 8, 32, 9, 14), box(0.5, 5, 0, 16, 8, 15.5), box(0.5, 5, -14, 16, 8, 0), box(16, 5, -16, 32, 17, -14), box(0, 5, -16, 16, 17, -14), box(0, 3, -16, 16, 5, 0),
			box(30, 0, 14, 32, 3, 16), box(30, 0, -16, 32, 3, -14));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(14, 0, 14, 16, 3, 16), box(-16, 0, 14, -14, 3, 16), box(0, 3, 0, 16, 5, 16), box(0, 3, -16, 16, 5, 0), box(-16, 3, -16, 0, 5, 0), box(-14, 5, -15.5, 0, 8, 0), box(-14, 5, -16, -2, 9, 0),
			box(-14, 9, -15, -10, 14, -2), box(-10, 8, -15.25, -7, 16.25, -1.75), box(-10, 8, 1.75, -7, 16.25, 15.25), box(-14, 9, 2, -10, 14, 15), box(-14, 5, 0, -2, 9, 16), box(0, 5, -15.5, 15.5, 8, 0), box(8, 8, -15, 14, 9, 0),
			box(8, 8, 0, 14, 9, 15), box(8, 5, 15, 14, 9, 16), box(8, 5, -16, 14, 9, -15), box(0, 5, 0, 15.5, 8, 15.5), box(-14, 5, 0, 0, 8, 15.5), box(-16, 5, -16, -14, 17, 0), box(-16, 5, 0, -14, 17, 16), box(-16, 3, 0, 0, 5, 16),
			box(14, 0, -16, 16, 3, -14), box(-16, 0, -16, -14, 3, -14));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(0, 0, 0, 2, 3, 2), box(30, 0, 0, 32, 3, 2), box(0, 3, 0, 16, 5, 16), box(0, 3, 16, 16, 5, 32), box(16, 3, 16, 32, 5, 32), box(16, 5, 16, 30, 8, 31.5), box(18, 5, 16, 30, 9, 32),
			box(26, 9, 18, 30, 14, 31), box(23, 8, 17.75, 26, 16.25, 31.25), box(23, 8, 0.75, 26, 16.25, 14.25), box(26, 9, 1, 30, 14, 14), box(18, 5, 0, 30, 9, 16), box(0.5, 5, 16, 16, 8, 31.5), box(2, 8, 16, 8, 9, 31), box(2, 8, 1, 8, 9, 16),
			box(2, 5, 0, 8, 9, 1), box(2, 5, 31, 8, 9, 32), box(0.5, 5, 0.5, 16, 8, 16), box(16, 5, 0.5, 30, 8, 16), box(30, 5, 16, 32, 17, 32), box(30, 5, 0, 32, 17, 16), box(16, 3, 0, 32, 5, 16), box(0, 0, 30, 2, 3, 32), box(30, 0, 30, 32, 3, 32));

	public DoubleBedBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(4f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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