package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.SinkStateSwitcherProcedure;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
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

import com.google.common.collect.ImmutableMap;

public class WoodenKitchenSinkBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 1);
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public WoodenKitchenSinkBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(3f, 2f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(STATE, 0));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(STATE) == 1) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(0, 0, 2, 16, 14, 3), box(13, 1, 1, 14, 12, 2), box(15, 0, 1, 16, 14, 2), box(1, 0, 1, 2, 14, 2), box(14, 0, 1, 15, 14, 2), box(7, 1, 1, 9, 12, 2), box(2, 0, 1, 14, 1, 2), box(2, 12, 1, 14, 14, 2),
							box(0, 0, 1, 1, 14, 2), box(9, 1, 1, 10, 12, 2), box(10, 11, 1, 13, 12, 2), box(10, 2, 1, 13, 11, 2), box(3, 2, 1, 6, 11, 2), box(6, 12, 0, 7, 13, 1), box(9, 12, 0, 10, 13, 1), box(10, 1, 1, 13, 2, 2),
							box(3, 11, 1, 6, 12, 2), box(3, 1, 1, 6, 2, 2), box(6, 1, 1, 7, 12, 2), box(2, 1, 1, 3, 12, 2), box(0, 14, 0, 16, 16, 3), box(0, 14, 12, 16, 16, 16), box(7, 16, 13.5, 9, 24, 15.5), box(4, 18, 13.5, 7, 20, 15.5),
							box(4.25, 20, 14.25, 4.75, 22, 14.75), box(7, 24, 8.3, 9, 26, 15.5), box(7.25, 15, 8.55, 8.75, 24, 10.25), box(2, 7, 2.3, 14, 15, 12.5), box(14, 14, 3, 16, 16, 12), box(14, 16, 3, 15, 16.25, 12),
							box(1, 16, 3, 2, 16.25, 12), box(1, 16, 2, 15, 16.25, 3), box(1, 16, 12, 15, 16.25, 13), box(0, 14, 3, 2, 16, 12), box(0, 0, 12, 16, 14, 16), box(14, 0, 3, 16, 14, 12), box(0, 0, 3, 2, 14, 12), box(2, 0, 3, 14, 7, 12),
							box(7, 0.5, 6.5, 9, 7.5, 8.5));
					case EAST -> Shapes.or(box(13, 0, 0, 14, 14, 16), box(14, 1, 13, 15, 12, 14), box(14, 0, 15, 15, 14, 16), box(14, 0, 1, 15, 14, 2), box(14, 0, 14, 15, 14, 15), box(14, 1, 7, 15, 12, 9), box(14, 0, 2, 15, 1, 14),
							box(14, 12, 2, 15, 14, 14), box(14, 0, 0, 15, 14, 1), box(14, 1, 9, 15, 12, 10), box(14, 11, 10, 15, 12, 13), box(14, 2, 10, 15, 11, 13), box(14, 2, 3, 15, 11, 6), box(15, 12, 6, 16, 13, 7), box(15, 12, 9, 16, 13, 10),
							box(14, 1, 10, 15, 2, 13), box(14, 11, 3, 15, 12, 6), box(14, 1, 3, 15, 2, 6), box(14, 1, 6, 15, 12, 7), box(14, 1, 2, 15, 12, 3), box(13, 14, 0, 16, 16, 16), box(0, 14, 0, 4, 16, 16), box(0.5, 16, 7, 2.5, 24, 9),
							box(0.5, 18, 4, 2.5, 20, 7), box(1.25, 20, 4.25, 1.75, 22, 4.75), box(0.5, 24, 7, 7.7, 26, 9), box(5.75, 15, 7.25, 7.45, 24, 8.75), box(3.5, 7, 2, 13.7, 15, 14), box(4, 14, 14, 13, 16, 16), box(4, 16, 14, 13, 16.25, 15),
							box(4, 16, 1, 13, 16.25, 2), box(13, 16, 1, 14, 16.25, 15), box(3, 16, 1, 4, 16.25, 15), box(4, 14, 0, 13, 16, 2), box(0, 0, 0, 4, 14, 16), box(4, 0, 14, 13, 14, 16), box(4, 0, 0, 13, 14, 2), box(4, 0, 2, 13, 7, 14),
							box(7.5, 0.5, 7, 9.5, 7.5, 9));
					case WEST -> Shapes.or(box(2, 0, 0, 3, 14, 16), box(1, 1, 2, 2, 12, 3), box(1, 0, 0, 2, 14, 1), box(1, 0, 14, 2, 14, 15), box(1, 0, 1, 2, 14, 2), box(1, 1, 7, 2, 12, 9), box(1, 0, 2, 2, 1, 14), box(1, 12, 2, 2, 14, 14),
							box(1, 0, 15, 2, 14, 16), box(1, 1, 6, 2, 12, 7), box(1, 11, 3, 2, 12, 6), box(1, 2, 3, 2, 11, 6), box(1, 2, 10, 2, 11, 13), box(0, 12, 9, 1, 13, 10), box(0, 12, 6, 1, 13, 7), box(1, 1, 3, 2, 2, 6),
							box(1, 11, 10, 2, 12, 13), box(1, 1, 10, 2, 2, 13), box(1, 1, 9, 2, 12, 10), box(1, 1, 13, 2, 12, 14), box(0, 14, 0, 3, 16, 16), box(12, 14, 0, 16, 16, 16), box(13.5, 16, 7, 15.5, 24, 9), box(13.5, 18, 9, 15.5, 20, 12),
							box(14.25, 20, 11.25, 14.75, 22, 11.75), box(8.3, 24, 7, 15.5, 26, 9), box(8.55, 15, 7.25, 10.25, 24, 8.75), box(2.3, 7, 2, 12.5, 15, 14), box(3, 14, 0, 12, 16, 2), box(3, 16, 1, 12, 16.25, 2),
							box(3, 16, 14, 12, 16.25, 15), box(2, 16, 1, 3, 16.25, 15), box(12, 16, 1, 13, 16.25, 15), box(3, 14, 14, 12, 16, 16), box(12, 0, 0, 16, 14, 16), box(3, 0, 0, 12, 14, 2), box(3, 0, 14, 12, 14, 16), box(3, 0, 2, 12, 7, 14),
							box(6.5, 0.5, 7, 8.5, 7.5, 9));
					default -> Shapes.or(box(0, 0, 13, 16, 14, 14), box(2, 1, 14, 3, 12, 15), box(0, 0, 14, 1, 14, 15), box(14, 0, 14, 15, 14, 15), box(1, 0, 14, 2, 14, 15), box(7, 1, 14, 9, 12, 15), box(2, 0, 14, 14, 1, 15),
							box(2, 12, 14, 14, 14, 15), box(15, 0, 14, 16, 14, 15), box(6, 1, 14, 7, 12, 15), box(3, 11, 14, 6, 12, 15), box(3, 2, 14, 6, 11, 15), box(10, 2, 14, 13, 11, 15), box(9, 12, 15, 10, 13, 16), box(6, 12, 15, 7, 13, 16),
							box(3, 1, 14, 6, 2, 15), box(10, 11, 14, 13, 12, 15), box(10, 1, 14, 13, 2, 15), box(9, 1, 14, 10, 12, 15), box(13, 1, 14, 14, 12, 15), box(0, 14, 13, 16, 16, 16), box(0, 14, 0, 16, 16, 4), box(7, 16, 0.5, 9, 24, 2.5),
							box(9, 18, 0.5, 12, 20, 2.5), box(11.25, 20, 1.25, 11.75, 22, 1.75), box(7, 24, 0.5, 9, 26, 7.7), box(7.25, 15, 5.75, 8.75, 24, 7.45), box(2, 7, 3.5, 14, 15, 13.7), box(0, 14, 4, 2, 16, 13), box(1, 16, 4, 2, 16.25, 13),
							box(14, 16, 4, 15, 16.25, 13), box(1, 16, 13, 15, 16.25, 14), box(1, 16, 3, 15, 16.25, 4), box(14, 14, 4, 16, 16, 13), box(0, 0, 0, 16, 14, 4), box(0, 0, 4, 2, 14, 13), box(14, 0, 4, 16, 14, 13), box(2, 0, 4, 14, 7, 13),
							box(7, 0.5, 7.5, 9, 7.5, 9.5));
				};
			}
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(0, 0, 2, 16, 14, 3), box(13, 1, 1, 14, 12, 2), box(15, 0, 1, 16, 14, 2), box(1, 0, 1, 2, 14, 2), box(14, 0, 1, 15, 14, 2), box(7, 1, 1, 9, 12, 2), box(2, 0, 1, 14, 1, 2), box(2, 12, 1, 14, 14, 2),
						box(0, 0, 1, 1, 14, 2), box(9, 1, 1, 10, 12, 2), box(10, 11, 1, 13, 12, 2), box(10, 2, 1, 13, 11, 2), box(3, 2, 1, 6, 11, 2), box(6, 12, 0, 7, 13, 1), box(9, 12, 0, 10, 13, 1), box(10, 1, 1, 13, 2, 2), box(3, 11, 1, 6, 12, 2),
						box(3, 1, 1, 6, 2, 2), box(6, 1, 1, 7, 12, 2), box(2, 1, 1, 3, 12, 2), box(0, 14, 0, 16, 16, 3), box(0, 14, 12, 16, 16, 16), box(7, 16, 13.5, 9, 24, 15.5), box(4, 18, 13.5, 7, 20, 15.5), box(4.25, 20, 14.25, 4.75, 22, 14.75),
						box(7, 24, 12.3, 9, 26, 15.5), box(7, 24, 10.3, 9, 26, 12.3), box(7, 24, 8.3, 9, 26, 10.3), box(14, 14, 3, 16, 16, 12), box(14, 16, 3, 15, 16.25, 12), box(1, 16, 3, 2, 16.25, 12), box(1, 16, 2, 15, 16.25, 3),
						box(1, 16, 12, 15, 16.25, 13), box(0, 14, 3, 2, 16, 12), box(0, 0, 12, 16, 14, 16), box(14, 0, 3, 16, 14, 12), box(0, 0, 3, 2, 14, 12), box(2, 0, 3, 14, 7, 12), box(7, 0.5, 6.5, 9, 7.5, 8.5));
				case EAST ->
					Shapes.or(box(13, 0, 0, 14, 14, 16), box(14, 1, 13, 15, 12, 14), box(14, 0, 15, 15, 14, 16), box(14, 0, 1, 15, 14, 2), box(14, 0, 14, 15, 14, 15), box(14, 1, 7, 15, 12, 9), box(14, 0, 2, 15, 1, 14), box(14, 12, 2, 15, 14, 14),
							box(14, 0, 0, 15, 14, 1), box(14, 1, 9, 15, 12, 10), box(14, 11, 10, 15, 12, 13), box(14, 2, 10, 15, 11, 13), box(14, 2, 3, 15, 11, 6), box(15, 12, 6, 16, 13, 7), box(15, 12, 9, 16, 13, 10), box(14, 1, 10, 15, 2, 13),
							box(14, 11, 3, 15, 12, 6), box(14, 1, 3, 15, 2, 6), box(14, 1, 6, 15, 12, 7), box(14, 1, 2, 15, 12, 3), box(13, 14, 0, 16, 16, 16), box(0, 14, 0, 4, 16, 16), box(0.5, 16, 7, 2.5, 24, 9), box(0.5, 18, 4, 2.5, 20, 7),
							box(1.25, 20, 4.25, 1.75, 22, 4.75), box(0.5, 24, 7, 3.7, 26, 9), box(3.7, 24, 7, 5.7, 26, 9), box(5.7, 24, 7, 7.7, 26, 9), box(4, 14, 14, 13, 16, 16), box(4, 16, 14, 13, 16.25, 15), box(4, 16, 1, 13, 16.25, 2),
							box(13, 16, 1, 14, 16.25, 15), box(3, 16, 1, 4, 16.25, 15), box(4, 14, 0, 13, 16, 2), box(0, 0, 0, 4, 14, 16), box(4, 0, 14, 13, 14, 16), box(4, 0, 0, 13, 14, 2), box(4, 0, 2, 13, 7, 14), box(7.5, 0.5, 7, 9.5, 7.5, 9));
				case WEST -> Shapes.or(box(2, 0, 0, 3, 14, 16), box(1, 1, 2, 2, 12, 3), box(1, 0, 0, 2, 14, 1), box(1, 0, 14, 2, 14, 15), box(1, 0, 1, 2, 14, 2), box(1, 1, 7, 2, 12, 9), box(1, 0, 2, 2, 1, 14), box(1, 12, 2, 2, 14, 14),
						box(1, 0, 15, 2, 14, 16), box(1, 1, 6, 2, 12, 7), box(1, 11, 3, 2, 12, 6), box(1, 2, 3, 2, 11, 6), box(1, 2, 10, 2, 11, 13), box(0, 12, 9, 1, 13, 10), box(0, 12, 6, 1, 13, 7), box(1, 1, 3, 2, 2, 6), box(1, 11, 10, 2, 12, 13),
						box(1, 1, 10, 2, 2, 13), box(1, 1, 9, 2, 12, 10), box(1, 1, 13, 2, 12, 14), box(0, 14, 0, 3, 16, 16), box(12, 14, 0, 16, 16, 16), box(13.5, 16, 7, 15.5, 24, 9), box(13.5, 18, 9, 15.5, 20, 12),
						box(14.25, 20, 11.25, 14.75, 22, 11.75), box(12.3, 24, 7, 15.5, 26, 9), box(10.3, 24, 7, 12.3, 26, 9), box(8.3, 24, 7, 10.3, 26, 9), box(3, 14, 0, 12, 16, 2), box(3, 16, 1, 12, 16.25, 2), box(3, 16, 14, 12, 16.25, 15),
						box(2, 16, 1, 3, 16.25, 15), box(12, 16, 1, 13, 16.25, 15), box(3, 14, 14, 12, 16, 16), box(12, 0, 0, 16, 14, 16), box(3, 0, 0, 12, 14, 2), box(3, 0, 14, 12, 14, 16), box(3, 0, 2, 12, 7, 14), box(6.5, 0.5, 7, 8.5, 7.5, 9));
				default -> Shapes.or(box(0, 0, 13, 16, 14, 14), box(2, 1, 14, 3, 12, 15), box(0, 0, 14, 1, 14, 15), box(14, 0, 14, 15, 14, 15), box(1, 0, 14, 2, 14, 15), box(7, 1, 14, 9, 12, 15), box(2, 0, 14, 14, 1, 15), box(2, 12, 14, 14, 14, 15),
						box(15, 0, 14, 16, 14, 15), box(6, 1, 14, 7, 12, 15), box(3, 11, 14, 6, 12, 15), box(3, 2, 14, 6, 11, 15), box(10, 2, 14, 13, 11, 15), box(9, 12, 15, 10, 13, 16), box(6, 12, 15, 7, 13, 16), box(3, 1, 14, 6, 2, 15),
						box(10, 11, 14, 13, 12, 15), box(10, 1, 14, 13, 2, 15), box(9, 1, 14, 10, 12, 15), box(13, 1, 14, 14, 12, 15), box(0, 14, 13, 16, 16, 16), box(0, 14, 0, 16, 16, 4), box(7, 16, 0.5, 9, 24, 2.5), box(9, 18, 0.5, 12, 20, 2.5),
						box(11.25, 20, 1.25, 11.75, 22, 1.75), box(7, 24, 0.5, 9, 26, 3.7), box(7, 24, 3.7, 9, 26, 5.7), box(7, 24, 5.7, 9, 26, 7.7), box(0, 14, 4, 2, 16, 13), box(1, 16, 4, 2, 16.25, 13), box(14, 16, 4, 15, 16.25, 13),
						box(1, 16, 13, 15, 16.25, 14), box(1, 16, 3, 15, 16.25, 4), box(14, 14, 4, 16, 16, 13), box(0, 0, 0, 16, 14, 4), box(0, 0, 4, 2, 14, 13), box(14, 0, 4, 16, 14, 13), box(2, 0, 4, 14, 7, 13), box(7, 0.5, 7.5, 9, 7.5, 9.5));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
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
		SinkStateSwitcherProcedure.execute(world, x, y, z, entity);
		return InteractionResult.SUCCESS;
	}
}