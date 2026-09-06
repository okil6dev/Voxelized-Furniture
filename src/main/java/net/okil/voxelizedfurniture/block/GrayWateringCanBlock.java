package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.WateringcanwaterProcedure;

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

public class GrayWateringCanBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 1);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public GrayWateringCanBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.SCAFFOLDING).strength(0.8f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(STATE, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(STATE) == 1) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(6, 0, 5, 7, 7, 6), box(6, 0, 10, 7, 7, 11), box(5, 0, 6, 6, 7, 10), box(2.03806, 2.19134, 7, 7.03806, 4.19134, 9), box(1.03806, 1.69134, 6.5, 2.03806, 4.69134, 9.5), box(12, 0, 6, 13, 7, 10),
							box(6, 9, 7, 12, 10, 9), box(12, 7, 7, 13, 9, 9), box(5, 7, 7, 6, 9, 9), box(11, 0, 10, 12, 7, 11), box(11, 0, 5, 12, 7, 6), box(7, 0, 5, 11, 1, 11), box(6, 0, 6, 7, 1, 10), box(11, 0, 6, 12, 1, 10),
							box(7, 0, 4, 11, 7, 5), box(7, 0, 11, 11, 7, 12), box(7, 0, 5, 11, 6, 6), box(6, 0, 6, 12, 6, 10), box(7, 0, 10, 11, 6, 11));
					case EAST -> Shapes.or(box(10, 0, 6, 11, 7, 7), box(5, 0, 6, 6, 7, 7), box(6, 0, 5, 10, 7, 6), box(7, 2.19134, 2.03806, 9, 4.19134, 7.03806), box(6.5, 1.69134, 1.03806, 9.5, 4.69134, 2.03806), box(6, 0, 12, 10, 7, 13),
							box(7, 9, 6, 9, 10, 12), box(7, 7, 12, 9, 9, 13), box(7, 7, 5, 9, 9, 6), box(5, 0, 11, 6, 7, 12), box(10, 0, 11, 11, 7, 12), box(5, 0, 7, 11, 1, 11), box(6, 0, 6, 10, 1, 7), box(6, 0, 11, 10, 1, 12),
							box(11, 0, 7, 12, 7, 11), box(4, 0, 7, 5, 7, 11), box(10, 0, 7, 11, 6, 11), box(6, 0, 6, 10, 6, 12), box(5, 0, 7, 6, 6, 11));
					case WEST -> Shapes.or(box(5, 0, 9, 6, 7, 10), box(10, 0, 9, 11, 7, 10), box(6, 0, 10, 10, 7, 11), box(7, 2.19134, 8.96194, 9, 4.19134, 13.96194), box(6.5, 1.69134, 13.96194, 9.5, 4.69134, 14.96194), box(6, 0, 3, 10, 7, 4),
							box(7, 9, 4, 9, 10, 10), box(7, 7, 3, 9, 9, 4), box(7, 7, 10, 9, 9, 11), box(10, 0, 4, 11, 7, 5), box(5, 0, 4, 6, 7, 5), box(5, 0, 5, 11, 1, 9), box(6, 0, 9, 10, 1, 10), box(6, 0, 4, 10, 1, 5), box(4, 0, 5, 5, 7, 9),
							box(11, 0, 5, 12, 7, 9), box(5, 0, 5, 6, 6, 9), box(6, 0, 4, 10, 6, 10), box(10, 0, 5, 11, 6, 9));
					default -> Shapes.or(box(9, 0, 10, 10, 7, 11), box(9, 0, 5, 10, 7, 6), box(10, 0, 6, 11, 7, 10), box(8.96194, 2.19134, 7, 13.96194, 4.19134, 9), box(13.96194, 1.69134, 6.5, 14.96194, 4.69134, 9.5), box(3, 0, 6, 4, 7, 10),
							box(4, 9, 7, 10, 10, 9), box(3, 7, 7, 4, 9, 9), box(10, 7, 7, 11, 9, 9), box(4, 0, 5, 5, 7, 6), box(4, 0, 10, 5, 7, 11), box(5, 0, 5, 9, 1, 11), box(9, 0, 6, 10, 1, 10), box(4, 0, 6, 5, 1, 10), box(5, 0, 11, 9, 7, 12),
							box(5, 0, 4, 9, 7, 5), box(5, 0, 10, 9, 6, 11), box(4, 0, 6, 10, 6, 10), box(5, 0, 5, 9, 6, 6));
				};
			}
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(6, 0, 5, 7, 7, 6), box(6, 0, 10, 7, 7, 11), box(5, 0, 6, 6, 7, 10), box(2.03806, 2.19134, 7, 7.03806, 4.19134, 9), box(1.03806, 1.69134, 6.5, 2.03806, 4.69134, 9.5), box(12, 0, 6, 13, 7, 10),
						box(6, 9, 7, 12, 10, 9), box(12, 7, 7, 13, 9, 9), box(5, 7, 7, 6, 9, 9), box(11, 0, 10, 12, 7, 11), box(11, 0, 5, 12, 7, 6), box(7, 0, 5, 11, 1, 11), box(6, 0, 6, 7, 1, 10), box(11, 0, 6, 12, 1, 10), box(7, 0, 4, 11, 7, 5),
						box(7, 0, 11, 11, 7, 12));
				case EAST -> Shapes.or(box(10, 0, 6, 11, 7, 7), box(5, 0, 6, 6, 7, 7), box(6, 0, 5, 10, 7, 6), box(7, 2.19134, 2.03806, 9, 4.19134, 7.03806), box(6.5, 1.69134, 1.03806, 9.5, 4.69134, 2.03806), box(6, 0, 12, 10, 7, 13),
						box(7, 9, 6, 9, 10, 12), box(7, 7, 12, 9, 9, 13), box(7, 7, 5, 9, 9, 6), box(5, 0, 11, 6, 7, 12), box(10, 0, 11, 11, 7, 12), box(5, 0, 7, 11, 1, 11), box(6, 0, 6, 10, 1, 7), box(6, 0, 11, 10, 1, 12), box(11, 0, 7, 12, 7, 11),
						box(4, 0, 7, 5, 7, 11));
				case WEST -> Shapes.or(box(5, 0, 9, 6, 7, 10), box(10, 0, 9, 11, 7, 10), box(6, 0, 10, 10, 7, 11), box(7, 2.19134, 8.96194, 9, 4.19134, 13.96194), box(6.5, 1.69134, 13.96194, 9.5, 4.69134, 14.96194), box(6, 0, 3, 10, 7, 4),
						box(7, 9, 4, 9, 10, 10), box(7, 7, 3, 9, 9, 4), box(7, 7, 10, 9, 9, 11), box(10, 0, 4, 11, 7, 5), box(5, 0, 4, 6, 7, 5), box(5, 0, 5, 11, 1, 9), box(6, 0, 9, 10, 1, 10), box(6, 0, 4, 10, 1, 5), box(4, 0, 5, 5, 7, 9),
						box(11, 0, 5, 12, 7, 9));
				default -> Shapes.or(box(9, 0, 10, 10, 7, 11), box(9, 0, 5, 10, 7, 6), box(10, 0, 6, 11, 7, 10), box(8.96194, 2.19134, 7, 13.96194, 4.19134, 9), box(13.96194, 1.69134, 6.5, 14.96194, 4.69134, 9.5), box(3, 0, 6, 4, 7, 10),
						box(4, 9, 7, 10, 10, 9), box(3, 7, 7, 4, 9, 9), box(10, 7, 7, 11, 9, 9), box(4, 0, 5, 5, 7, 6), box(4, 0, 10, 5, 7, 11), box(5, 0, 5, 9, 1, 11), box(9, 0, 6, 10, 1, 10), box(4, 0, 6, 5, 1, 10), box(5, 0, 11, 9, 7, 12),
						box(5, 0, 4, 9, 7, 5));
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
		WateringcanwaterProcedure.execute(world, x, y, z, entity);
		return InteractionResult.SUCCESS;
	}
}