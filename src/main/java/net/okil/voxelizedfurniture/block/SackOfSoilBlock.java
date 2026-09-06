package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.SoilsackstateProcedure;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class SackOfSoilBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 7);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public SackOfSoilBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.GRAVEL).strength(0.75f, 1f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(STATE, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(STATE) == 1) {
				return switch (state.getValue(FACING)) {
					case NORTH -> box(3, 0, 2, 13, 4, 13);
					case EAST -> box(3, 0, 3, 14, 4, 13);
					case WEST -> box(2, 0, 3, 13, 4, 13);
					default -> box(3, 0, 3, 13, 4, 14);
				};
			} else if (state.getValue(STATE) == 2) {
				return switch (state.getValue(FACING)) {
					case NORTH -> box(3, 0, 2, 13, 6, 13);
					case EAST -> box(3, 0, 3, 14, 6, 13);
					case WEST -> box(2, 0, 3, 13, 6, 13);
					default -> box(3, 0, 3, 13, 6, 14);
				};
			} else if (state.getValue(STATE) == 3) {
				return switch (state.getValue(FACING)) {
					case NORTH -> box(3, 0, 2, 13, 8, 13);
					case EAST -> box(3, 0, 3, 14, 8, 13);
					case WEST -> box(2, 0, 3, 13, 8, 13);
					default -> box(3, 0, 3, 13, 8, 14);
				};
			} else if (state.getValue(STATE) == 4) {
				return switch (state.getValue(FACING)) {
					case NORTH -> box(3, 0, 2, 13, 10, 13);
					case EAST -> box(3, 0, 3, 14, 10, 13);
					case WEST -> box(2, 0, 3, 13, 10, 13);
					default -> box(3, 0, 3, 13, 10, 14);
				};
			} else if (state.getValue(STATE) == 5) {
				return switch (state.getValue(FACING)) {
					case NORTH -> box(3, 0, 2, 13, 12, 13);
					case EAST -> box(3, 0, 3, 14, 12, 13);
					case WEST -> box(2, 0, 3, 13, 12, 13);
					default -> box(3, 0, 3, 13, 12, 14);
				};
			} else if (state.getValue(STATE) == 6) {
				return switch (state.getValue(FACING)) {
					case NORTH -> box(3, 0, 2, 13, 14, 13);
					case EAST -> box(3, 0, 3, 14, 14, 13);
					case WEST -> box(2, 0, 3, 13, 14, 13);
					default -> box(3, 0, 3, 13, 14, 14);
				};
			} else if (state.getValue(STATE) == 7) {
				return switch (state.getValue(FACING)) {
					case NORTH -> box(3, 0, 2, 13, 16, 13);
					case EAST -> box(3, 0, 3, 14, 16, 13);
					case WEST -> box(2, 0, 3, 13, 16, 13);
					default -> box(3, 0, 3, 13, 16, 14);
				};
			}
			return switch (state.getValue(FACING)) {
				case NORTH -> box(3, 0, 2, 13, 2, 13);
				case EAST -> box(3, 0, 3, 14, 2, 13);
				case WEST -> box(2, 0, 3, 13, 2, 13);
				default -> box(3, 0, 3, 13, 2, 14);
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, STATE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		return state.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(STATE, 0);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
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
		SoilsackstateProcedure.execute(world, x, y, z, entity);
		return InteractionResult.SUCCESS;
	}
}