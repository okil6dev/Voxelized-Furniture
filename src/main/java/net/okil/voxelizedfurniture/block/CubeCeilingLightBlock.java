package net.okil.voxelizedfurniture.block;

import org.checkerframework.checker.units.qual.s;

import net.okil.voxelizedfurniture.procedures.LightSwitchProcedure;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class CubeCeilingLightBlock extends Block {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 1);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_1_NORTH = Shapes.or(box(5, 14, 5, 11, 16, 11), box(1, 12, 8, 9, 13, 9), box(0.975, 12.025, 7.975, 9.025, 13.025, 9.025), box(0.975, 12.025, 9.025, 2.025, 13.025, 14.975),
			box(7.975, 12.025, 9.025, 9.025, 13.025, 14.975), box(0.975, 12.025, 14.975, 9.025, 13.025, 16.025), box(6.975, 12.025, 1.025, 8.025, 13.025, 6.975), box(6.975, 12.025, 6.975, 15.025, 13.025, 8.025),
			box(13.975, 12.025, 1.025, 15.025, 13.025, 6.975), box(6.975, 12.025, -0.025, 15.025, 13.025, 1.025), box(6, 13, 8, 7, 14, 9), box(8, 13, 7, 9, 14, 8), box(8, 13, 9, 9, 14, 10), box(1, 12, 15, 9, 13, 16), box(8, 12, 9, 9, 13, 15),
			box(1, 12, 9, 2, 13, 15), box(7, 12, 0, 15, 13, 1), box(14, 12, 1, 15, 13, 7), box(7, 12, 7, 15, 13, 8), box(7, 12, 1, 8, 13, 7));
	private static final VoxelShape SHAPE_1_SOUTH = Shapes.or(box(5, 14, 5, 11, 16, 11), box(7, 12, 7, 15, 13, 8), box(6.975, 12.025, 6.975, 15.025, 13.025, 8.025), box(13.975, 12.025, 1.025, 15.025, 13.025, 6.975),
			box(6.975, 12.025, 1.025, 8.025, 13.025, 6.975), box(6.975, 12.025, -0.025, 15.025, 13.025, 1.025), box(7.975, 12.025, 9.025, 9.025, 13.025, 14.975), box(0.975, 12.025, 7.975, 9.025, 13.025, 9.025),
			box(0.975, 12.025, 9.025, 2.025, 13.025, 14.975), box(0.975, 12.025, 14.975, 9.025, 13.025, 16.025), box(9, 13, 7, 10, 14, 8), box(7, 13, 8, 8, 14, 9), box(7, 13, 6, 8, 14, 7), box(7, 12, 0, 15, 13, 1), box(7, 12, 1, 8, 13, 7),
			box(14, 12, 1, 15, 13, 7), box(1, 12, 15, 9, 13, 16), box(1, 12, 9, 2, 13, 15), box(1, 12, 8, 9, 13, 9), box(8, 12, 9, 9, 13, 15));
	private static final VoxelShape SHAPE_1_EAST = Shapes.or(box(5, 14, 5, 11, 16, 11), box(7, 12, 1, 8, 13, 9), box(6.975, 12.025, 0.975, 8.025, 13.025, 9.025), box(1.025, 12.025, 0.975, 6.975, 13.025, 2.025),
			box(1.025, 12.025, 7.975, 6.975, 13.025, 9.025), box(-0.025, 12.025, 0.975, 1.025, 13.025, 9.025), box(9.025, 12.025, 6.975, 14.975, 13.025, 8.025), box(7.975, 12.025, 6.975, 9.025, 13.025, 15.025),
			box(9.025, 12.025, 13.975, 14.975, 13.025, 15.025), box(14.975, 12.025, 6.975, 16.025, 13.025, 15.025), box(7, 13, 6, 8, 14, 7), box(8, 13, 8, 9, 14, 9), box(6, 13, 8, 7, 14, 9), box(0, 12, 1, 1, 13, 9), box(1, 12, 8, 7, 13, 9),
			box(1, 12, 1, 7, 13, 2), box(15, 12, 7, 16, 13, 15), box(9, 12, 14, 15, 13, 15), box(8, 12, 7, 9, 13, 15), box(9, 12, 7, 15, 13, 8));
	private static final VoxelShape SHAPE_1_WEST = Shapes.or(box(5, 14, 5, 11, 16, 11), box(8, 12, 7, 9, 13, 15), box(7.975, 12.025, 6.975, 9.025, 13.025, 15.025), box(9.025, 12.025, 13.975, 14.975, 13.025, 15.025),
			box(9.025, 12.025, 6.975, 14.975, 13.025, 8.025), box(14.975, 12.025, 6.975, 16.025, 13.025, 15.025), box(1.025, 12.025, 7.975, 6.975, 13.025, 9.025), box(6.975, 12.025, 0.975, 8.025, 13.025, 9.025),
			box(1.025, 12.025, 0.975, 6.975, 13.025, 2.025), box(-0.025, 12.025, 0.975, 1.025, 13.025, 9.025), box(8, 13, 9, 9, 14, 10), box(7, 13, 7, 8, 14, 8), box(9, 13, 7, 10, 14, 8), box(15, 12, 7, 16, 13, 15), box(9, 12, 7, 15, 13, 8),
			box(9, 12, 14, 15, 13, 15), box(0, 12, 1, 1, 13, 9), box(1, 12, 1, 7, 13, 2), box(7, 12, 1, 8, 13, 9), box(1, 12, 8, 7, 13, 9));
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(5, 14, 5, 11, 16, 11), box(1, 12, 8, 9, 13, 9), box(0.975, 12.025, 7.975, 9.025, 13.025, 9.025), box(0.975, 12.025, 9.025, 2.025, 13.025, 14.975),
			box(7.975, 12.025, 9.025, 9.025, 13.025, 14.975), box(0.975, 12.025, 14.975, 9.025, 13.025, 16.025), box(6.975, 12.025, 1.025, 8.025, 13.025, 6.975), box(6.975, 12.025, 6.975, 15.025, 13.025, 8.025),
			box(13.975, 12.025, 1.025, 15.025, 13.025, 6.975), box(6.975, 12.025, -0.025, 15.025, 13.025, 1.025), box(6, 13, 8, 7, 14, 9), box(8, 13, 7, 9, 14, 8), box(8, 13, 9, 9, 14, 10), box(1, 12, 15, 9, 13, 16), box(8, 12, 9, 9, 13, 15),
			box(1, 12, 9, 2, 13, 15), box(7, 12, 0, 15, 13, 1), box(14, 12, 1, 15, 13, 7), box(7, 12, 7, 15, 13, 8), box(7, 12, 1, 8, 13, 7));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(5, 14, 5, 11, 16, 11), box(7, 12, 7, 15, 13, 8), box(6.975, 12.025, 6.975, 15.025, 13.025, 8.025), box(13.975, 12.025, 1.025, 15.025, 13.025, 6.975),
			box(6.975, 12.025, 1.025, 8.025, 13.025, 6.975), box(6.975, 12.025, -0.025, 15.025, 13.025, 1.025), box(7.975, 12.025, 9.025, 9.025, 13.025, 14.975), box(0.975, 12.025, 7.975, 9.025, 13.025, 9.025),
			box(0.975, 12.025, 9.025, 2.025, 13.025, 14.975), box(0.975, 12.025, 14.975, 9.025, 13.025, 16.025), box(9, 13, 7, 10, 14, 8), box(7, 13, 8, 8, 14, 9), box(7, 13, 6, 8, 14, 7), box(7, 12, 0, 15, 13, 1), box(7, 12, 1, 8, 13, 7),
			box(14, 12, 1, 15, 13, 7), box(1, 12, 15, 9, 13, 16), box(1, 12, 9, 2, 13, 15), box(1, 12, 8, 9, 13, 9), box(8, 12, 9, 9, 13, 15));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(5, 14, 5, 11, 16, 11), box(7, 12, 1, 8, 13, 9), box(6.975, 12.025, 0.975, 8.025, 13.025, 9.025), box(1.025, 12.025, 0.975, 6.975, 13.025, 2.025),
			box(1.025, 12.025, 7.975, 6.975, 13.025, 9.025), box(-0.025, 12.025, 0.975, 1.025, 13.025, 9.025), box(9.025, 12.025, 6.975, 14.975, 13.025, 8.025), box(7.975, 12.025, 6.975, 9.025, 13.025, 15.025),
			box(9.025, 12.025, 13.975, 14.975, 13.025, 15.025), box(14.975, 12.025, 6.975, 16.025, 13.025, 15.025), box(7, 13, 6, 8, 14, 7), box(8, 13, 8, 9, 14, 9), box(6, 13, 8, 7, 14, 9), box(0, 12, 1, 1, 13, 9), box(1, 12, 8, 7, 13, 9),
			box(1, 12, 1, 7, 13, 2), box(15, 12, 7, 16, 13, 15), box(9, 12, 14, 15, 13, 15), box(8, 12, 7, 9, 13, 15), box(9, 12, 7, 15, 13, 8));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(5, 14, 5, 11, 16, 11), box(8, 12, 7, 9, 13, 15), box(7.975, 12.025, 6.975, 9.025, 13.025, 15.025), box(9.025, 12.025, 13.975, 14.975, 13.025, 15.025),
			box(9.025, 12.025, 6.975, 14.975, 13.025, 8.025), box(14.975, 12.025, 6.975, 16.025, 13.025, 15.025), box(1.025, 12.025, 7.975, 6.975, 13.025, 9.025), box(6.975, 12.025, 0.975, 8.025, 13.025, 9.025),
			box(1.025, 12.025, 0.975, 6.975, 13.025, 2.025), box(-0.025, 12.025, 0.975, 1.025, 13.025, 9.025), box(8, 13, 9, 9, 14, 10), box(7, 13, 7, 8, 14, 8), box(9, 13, 7, 10, 14, 8), box(15, 12, 7, 16, 13, 15), box(9, 12, 7, 15, 13, 8),
			box(9, 12, 14, 15, 13, 15), box(0, 12, 1, 1, 13, 9), box(1, 12, 1, 7, 13, 2), box(7, 12, 1, 8, 13, 9), box(1, 12, 8, 7, 13, 9));

	public CubeCeilingLightBlock() {
		super(BlockBehaviour.Properties.of().strength(2f, 3f).lightLevel(s -> (new Object() {
			public int getLightLevel() {
				if (s.getValue(BLOCKSTATE) == 1)
					return 11;
				return 0;
			}
		}.getLightLevel())).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
		if (state.getValue(BLOCKSTATE) == 1) {
			return (switch (state.getValue(FACING)) {
				case NORTH -> SHAPE_1_NORTH;
				case SOUTH -> SHAPE_1_SOUTH;
				case EAST -> SHAPE_1_EAST;
				case WEST -> SHAPE_1_WEST;
				default -> SHAPE_1_NORTH;
			});
		}
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
		builder.add(FACING, BLOCKSTATE);
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

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		LightSwitchProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}