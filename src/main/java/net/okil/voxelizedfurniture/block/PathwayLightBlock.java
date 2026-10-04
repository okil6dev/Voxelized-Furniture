package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.RightClicklightProcedure;
import net.okil.voxelizedfurniture.procedures.Lightlevel3stateProcedure;

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

public class PathwayLightBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 1);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public PathwayLightBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.METAL).strength(0.5f, 2f).lightLevel(blockstate -> (int) Lightlevel3stateProcedure.execute(blockstate)).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(STATE, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(6, 0, 8, 10, 12, 10), box(9, 10, 4.5, 10, 12, 8), box(6, 10, 4.5, 7, 12, 8), box(7, 10, 4.5, 9, 12, 5.5), box(7, 10, 7, 9, 12, 8), box(7, 10, 5.5, 9, 11, 7), box(7, 11, 5.5, 9, 12, 7));
				case EAST -> Shapes.or(box(6, 0, 6, 8, 12, 10), box(8, 10, 9, 11.5, 12, 10), box(8, 10, 6, 11.5, 12, 7), box(10.5, 10, 7, 11.5, 12, 9), box(8, 10, 7, 9, 12, 9), box(9, 10, 7, 10.5, 11, 9), box(9, 11, 7, 10.5, 12, 9));
				case WEST -> Shapes.or(box(8, 0, 6, 10, 12, 10), box(4.5, 10, 6, 8, 12, 7), box(4.5, 10, 9, 8, 12, 10), box(4.5, 10, 7, 5.5, 12, 9), box(7, 10, 7, 8, 12, 9), box(5.5, 10, 7, 7, 11, 9), box(5.5, 11, 7, 7, 12, 9));
				default -> Shapes.or(box(6, 0, 6, 10, 12, 8), box(6, 10, 8, 7, 12, 11.5), box(9, 10, 8, 10, 12, 11.5), box(7, 10, 10.5, 9, 12, 11.5), box(7, 10, 8, 9, 12, 9), box(7, 10, 9, 9, 11, 10.5), box(7, 11, 9, 9, 12, 10.5));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state) {
		return true;
	}

	@Override
	public int getLightDampening(BlockState state) {
		return 0;
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
		RightClicklightProcedure.execute(world, x, y, z);
		return InteractionResult.SUCCESS;
	}
}