package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.LightonandoffProcedure;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

public class LightSwitchButtonBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 1);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public LightSwitchButtonBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(1f, 2f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BLOCKSTATE, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(BLOCKSTATE) == 1) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(10.25, 0, 14, 11, 6, 16), box(5, 0, 14, 5.75, 6, 16), box(5.75, 0.75, 14.1, 10.25, 5.25, 16), box(5.75, 0, 14, 10.25, 0.75, 16), box(5.75, 5.25, 14, 10.25, 6, 16), box(6, 0.9, 13.25, 10, 4.8, 15.25));
					case EAST -> Shapes.or(box(0, 0, 10.25, 2, 6, 11), box(0, 0, 5, 2, 6, 5.75), box(0, 0.75, 5.75, 1.9, 5.25, 10.25), box(0, 0, 5.75, 2, 0.75, 10.25), box(0, 5.25, 5.75, 2, 6, 10.25), box(0.75, 0.9, 6, 2.75, 4.8, 10));
					case WEST -> Shapes.or(box(14, 0, 5, 16, 6, 5.75), box(14, 0, 10.25, 16, 6, 11), box(14.1, 0.75, 5.75, 16, 5.25, 10.25), box(14, 0, 5.75, 16, 0.75, 10.25), box(14, 5.25, 5.75, 16, 6, 10.25), box(13.25, 0.9, 6, 15.25, 4.8, 10));
					default -> Shapes.or(box(5, 0, 0, 5.75, 6, 2), box(10.25, 0, 0, 11, 6, 2), box(5.75, 0.75, 0, 10.25, 5.25, 1.9), box(5.75, 0, 0, 10.25, 0.75, 2), box(5.75, 5.25, 0, 10.25, 6, 2), box(6, 0.9, 0.75, 10, 4.8, 2.75));
				};
			}
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(10.25, 0, 14, 11, 6, 16), box(5, 0, 14, 5.75, 6, 16), box(5.75, 0.75, 14.1, 10.25, 5.25, 16), box(5.75, 0, 14, 10.25, 0.75, 16), box(5.75, 5.25, 14, 10.25, 6, 16), box(6, 1.05, 13.25, 10, 5, 15.25));
				case EAST -> Shapes.or(box(0, 0, 10.25, 2, 6, 11), box(0, 0, 5, 2, 6, 5.75), box(0, 0.75, 5.75, 1.9, 5.25, 10.25), box(0, 0, 5.75, 2, 0.75, 10.25), box(0, 5.25, 5.75, 2, 6, 10.25), box(0.75, 1.05, 6, 2.75, 5, 10));
				case WEST -> Shapes.or(box(14, 0, 5, 16, 6, 5.75), box(14, 0, 10.25, 16, 6, 11), box(14.1, 0.75, 5.75, 16, 5.25, 10.25), box(14, 0, 5.75, 16, 0.75, 10.25), box(14, 5.25, 5.75, 16, 6, 10.25), box(13.25, 1.05, 6, 15.25, 5, 10));
				default -> Shapes.or(box(5, 0, 0, 5.75, 6, 2), box(10.25, 0, 0, 11, 6, 2), box(5.75, 0.75, 0, 10.25, 5.25, 1.9), box(5.75, 0, 0, 10.25, 0.75, 2), box(5.75, 5.25, 0, 10.25, 6, 2), box(6, 1.05, 0.75, 10, 5, 2.75));
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
		builder.add(FACING, BLOCKSTATE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		return state.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BLOCKSTATE, 0);
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
		LightonandoffProcedure.execute(world, x, y, z);
		return InteractionResult.SUCCESS;
	}
}