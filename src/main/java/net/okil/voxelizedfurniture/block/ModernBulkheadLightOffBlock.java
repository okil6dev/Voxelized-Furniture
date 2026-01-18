package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.LightSwitchMDHLProcedure;

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
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class ModernBulkheadLightOffBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(4.5, 1.5, 14, 11.5, 14.5, 16), box(4.5, 1.5, 11, 11.5, 3.5, 14), box(4.5, 12.5, 11, 11.5, 14.5, 14), box(4.5, 10.5, 11, 11.5, 11.5, 14), box(4.5, 8.5, 11, 11.5, 9.5, 14),
			box(4.5, 6.5, 11, 11.5, 7.5, 14), box(4.5, 4.5, 11, 11.5, 5.5, 14), box(5.25, 3.5, 11.75, 10.75, 12.5, 14));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(4.5, 1.5, 0, 11.5, 14.5, 2), box(4.5, 1.5, 2, 11.5, 3.5, 5), box(4.5, 12.5, 2, 11.5, 14.5, 5), box(4.5, 10.5, 2, 11.5, 11.5, 5), box(4.5, 8.5, 2, 11.5, 9.5, 5),
			box(4.5, 6.5, 2, 11.5, 7.5, 5), box(4.5, 4.5, 2, 11.5, 5.5, 5), box(5.25, 3.5, 2, 10.75, 12.5, 4.25));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 1.5, 4.5, 2, 14.5, 11.5), box(2, 1.5, 4.5, 5, 3.5, 11.5), box(2, 12.5, 4.5, 5, 14.5, 11.5), box(2, 10.5, 4.5, 5, 11.5, 11.5), box(2, 8.5, 4.5, 5, 9.5, 11.5),
			box(2, 6.5, 4.5, 5, 7.5, 11.5), box(2, 4.5, 4.5, 5, 5.5, 11.5), box(2, 3.5, 5.25, 4.25, 12.5, 10.75));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(14, 1.5, 4.5, 16, 14.5, 11.5), box(11, 1.5, 4.5, 14, 3.5, 11.5), box(11, 12.5, 4.5, 14, 14.5, 11.5), box(11, 10.5, 4.5, 14, 11.5, 11.5), box(11, 8.5, 4.5, 14, 9.5, 11.5),
			box(11, 6.5, 4.5, 14, 7.5, 11.5), box(11, 4.5, 4.5, 14, 5.5, 11.5), box(11.75, 3.5, 5.25, 14, 12.5, 10.75));

	public ModernBulkheadLightOffBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.GLASS).strength(2f, 3f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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

	@Override
	public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(blockstate, world, pos, random);
		LightSwitchMDHLProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}