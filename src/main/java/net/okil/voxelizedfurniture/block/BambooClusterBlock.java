package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.BambooClusterHitProcedure;
import net.okil.voxelizedfurniture.procedures.BambooClusterBlockValidPlacementConditionProcedure;
import net.okil.voxelizedfurniture.block.entity.BambooClusterBlockEntity;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Containers;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class BambooClusterBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty TIMESHIT = IntegerProperty.create("timeshit", 0, 100);
	public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 2);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public BambooClusterBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.BAMBOO).strength(-1, 3600000).noOcclusion().isRedstoneConductor((bs, br, bp) -> false).dynamicShape().offsetType(Block.OffsetType.XZ));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TIMESHIT, 0).setValue(VARIANT, 0).setValue(WATERLOGGED, false));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(VARIANT) == 1) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(3, 0, 10.5, 5, 9, 12.5), box(4, 0, 0.5, 6, 10, 2.5), box(-0.5, 0, 7.5, 1.5, 14, 9.5), box(6, 0, 4.5, 8, 15, 6.5), box(13.5, 0, 6.5, 15.5, 7, 8.5));
					case EAST -> Shapes.or(box(3.5, 0, 3, 5.5, 9, 5), box(13.5, 0, 4, 15.5, 10, 6), box(6.5, 0, -0.5, 8.5, 14, 1.5), box(9.5, 0, 6, 11.5, 15, 8), box(7.5, 0, 13.5, 9.5, 7, 15.5));
					case WEST -> Shapes.or(box(10.5, 0, 11, 12.5, 9, 13), box(0.5, 0, 10, 2.5, 10, 12), box(7.5, 0, 14.5, 9.5, 14, 16.5), box(4.5, 0, 8, 6.5, 15, 10), box(6.5, 0, 0.5, 8.5, 7, 2.5));
					default -> Shapes.or(box(11, 0, 3.5, 13, 9, 5.5), box(10, 0, 13.5, 12, 10, 15.5), box(14.5, 0, 6.5, 16.5, 14, 8.5), box(8, 0, 9.5, 10, 15, 11.5), box(0.5, 0, 7.5, 2.5, 7, 9.5));
				};
			}
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(13, 0, 5, 15, 9, 7), box(10, 0, 9.5, 12, 10, 11.5), box(6, 0, 10, 8, 14, 12), box(5, 0, 2.5, 7, 15, 4.5), box(2.5, 0, 14, 4.5, 7, 16));
				case EAST -> Shapes.or(box(9, 0, 13, 11, 9, 15), box(4.5, 0, 10, 6.5, 10, 12), box(4, 0, 6, 6, 14, 8), box(11.5, 0, 5, 13.5, 15, 7), box(0, 0, 2.5, 2, 7, 4.5));
				case WEST -> Shapes.or(box(5, 0, 1, 7, 9, 3), box(9.5, 0, 4, 11.5, 10, 6), box(10, 0, 8, 12, 14, 10), box(2.5, 0, 9, 4.5, 15, 11), box(14, 0, 11.5, 16, 7, 13.5));
				default -> Shapes.or(box(1, 0, 9, 3, 9, 11), box(4, 0, 4.5, 6, 10, 6.5), box(8, 0, 4, 10, 14, 6), box(9, 0, 11.5, 11, 15, 13.5), box(11.5, 0, 0, 13.5, 7, 2));
			};
		}, WATERLOGGED, TIMESHIT);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state).move(state.getOffset(pos));
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
		builder.add(FACING, TIMESHIT, VARIANT, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		return state.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(TIMESHIT, 0).setValue(VARIANT, 0).setValue(WATERLOGGED, flag);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public boolean canSurvive(BlockState blockstate, LevelReader worldIn, BlockPos pos) {
		if (worldIn instanceof LevelAccessor world) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return BambooClusterBlockValidPlacementConditionProcedure.execute(world, x, y, z);
		}
		return super.canSurvive(blockstate, worldIn, pos);
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
		return !state.canSurvive(world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, world, scheduledTickAccess, currentPos, facing, facingPos, facingState, random);
	}

	@Override
	public void attack(BlockState blockstate, Level world, BlockPos pos, Player entity) {
		super.attack(blockstate, world, pos, entity);
		BambooClusterHitProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BambooClusterBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState blockstate, ServerLevel world, BlockPos blockpos, boolean flag) {
		Containers.updateNeighboursAfterDestroy(blockstate, world, blockpos);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos, Direction direction) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof BambooClusterBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}