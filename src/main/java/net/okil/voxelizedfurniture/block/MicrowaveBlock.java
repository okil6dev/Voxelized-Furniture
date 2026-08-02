package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.AirfryerOnBlockRightclickedProcedure;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class MicrowaveBlock extends Block implements SimpleWaterloggedBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 1);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public MicrowaveBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.METAL).strength(3.5f, 4f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(STATE, 0).setValue(WATERLOGGED, false));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(1, 0, 4, 5, 9, 12), box(1, 8, 3.85, 5, 9, 4), box(1, 0, 3.85, 5, 2, 4), box(1, 9, 3.85, 5, 9.15, 6), box(2, 5, 3.5, 4, 7, 4), box(2.5, 3, 3.6, 3.5, 4, 4), box(5, 0, 4, 15, 2, 12), box(5, 8, 4, 14, 9, 12),
						box(6, 3, 4, 13, 7, 4.25), box(6, 2, 4, 13, 3, 4.25), box(13, 2, 4, 14, 8, 4.25), box(6, 7, 4, 13, 8, 4.25), box(5, 2, 4, 6, 8, 4.25), box(5, 2, 11, 14, 8, 12), box(14, 2, 4, 15, 9, 12));
				case EAST -> Shapes.or(box(4, 0, 1, 12, 9, 5), box(12, 8, 1, 12.15, 9, 5), box(12, 0, 1, 12.15, 2, 5), box(10, 9, 1, 12.15, 9.15, 5), box(12, 5, 2, 12.5, 7, 4), box(12, 3, 2.5, 12.4, 4, 3.5), box(4, 0, 5, 12, 2, 15),
						box(4, 8, 5, 12, 9, 14), box(11.75, 3, 6, 12, 7, 13), box(11.75, 2, 6, 12, 3, 13), box(11.75, 2, 13, 12, 8, 14), box(11.75, 7, 6, 12, 8, 13), box(11.75, 2, 5, 12, 8, 6), box(4, 2, 5, 5, 8, 14), box(4, 2, 14, 12, 9, 15));
				case WEST -> Shapes.or(box(4, 0, 11, 12, 9, 15), box(3.85, 8, 11, 4, 9, 15), box(3.85, 0, 11, 4, 2, 15), box(3.85, 9, 11, 6, 9.15, 15), box(3.5, 5, 12, 4, 7, 14), box(3.6, 3, 12.5, 4, 4, 13.5), box(4, 0, 1, 12, 2, 11),
						box(4, 8, 2, 12, 9, 11), box(4, 3, 3, 4.25, 7, 10), box(4, 2, 3, 4.25, 3, 10), box(4, 2, 2, 4.25, 8, 3), box(4, 7, 3, 4.25, 8, 10), box(4, 2, 10, 4.25, 8, 11), box(11, 2, 2, 12, 8, 11), box(4, 2, 1, 12, 9, 2));
				default -> Shapes.or(box(11, 0, 4, 15, 9, 12), box(11, 8, 12, 15, 9, 12.15), box(11, 0, 12, 15, 2, 12.15), box(11, 9, 10, 15, 9.15, 12.15), box(12, 5, 12, 14, 7, 12.5), box(12.5, 3, 12, 13.5, 4, 12.4), box(1, 0, 4, 11, 2, 12),
						box(2, 8, 4, 11, 9, 12), box(3, 3, 11.75, 10, 7, 12), box(3, 2, 11.75, 10, 3, 12), box(2, 2, 11.75, 3, 8, 12), box(3, 7, 11.75, 10, 8, 12), box(10, 2, 11.75, 11, 8, 12), box(2, 2, 4, 11, 8, 5), box(1, 2, 4, 2, 9, 12));
			};
		}, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public int getLightDampening(BlockState state) {
		return propagatesSkylightDown(state) ? 0 : 1;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, STATE, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		return state.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(STATE, 0).setValue(WATERLOGGED, flag);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
		if (state.getValue(WATERLOGGED)) {
			scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return super.updateShape(state, world, scheduledTickAccess, currentPos, facing, facingPos, facingState, random);
	}

	@Override
	public InteractionResult useWithoutItem(BlockState blockstate, Level world, BlockPos pos, Player entity, BlockHitResult hit) {
		super.useWithoutItem(blockstate, world, pos, entity, hit);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		double hitX = hit.getLocation().x;
		double hitY = hit.getLocation().y;
		double hitZ = hit.getLocation().z;
		Direction direction = hit.getDirection();
		AirfryerOnBlockRightclickedProcedure.execute(world, x, y, z);
		return InteractionResult.SUCCESS;
	}
}