package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.AirfryerOnBlockRightclickedProcedure;
import net.okil.voxelizedfurniture.block.entity.AirfryerBlockEntity;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Containers;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class AirfryerBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 1);
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public AirfryerBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(2.5f, 3.5f).noOcclusion().randomTicks().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(STATE, 0).setValue(WATERLOGGED, false));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(STATE) == 1) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(4, 0, 7.9, 12, 10, 13), box(4, 6, 6.9, 12, 10, 7.9), box(4, 0, 6.9, 5, 6, 7.9), box(5, 0, 6.9, 11, 1, 7.9), box(11, 0, 6.9, 12, 6, 7.9), box(3.9, 5.5, 6.8, 4.5, 6, 7.9),
							box(11.5, 5.5, 6.8, 12.1, 6, 7.9), box(3.9, 5.5, 7.9, 12.1, 6, 13.15), box(4.5, 0.5, 2, 11.5, 6, 3), box(4.5, 0.5, 7.85, 11.5, 6, 8), box(10.5, 0.5, 3, 11.5, 6, 8), box(4.5, 0.5, 3, 5.5, 6, 8),
							box(5.5, 0.5, 3, 10.5, 1.5, 9), box(7.5, 4.75, 0.25, 8.5, 5.75, 2.25), box(7.5, 2.75, 0.25, 8.5, 4.75, 1.25), box(7.5, 7, 6.75, 8.5, 8, 7));
					case EAST -> Shapes.or(box(3, 0, 4, 8.1, 10, 12), box(8.1, 6, 4, 9.1, 10, 12), box(8.1, 0, 4, 9.1, 6, 5), box(8.1, 0, 5, 9.1, 1, 11), box(8.1, 0, 11, 9.1, 6, 12), box(8.1, 5.5, 3.9, 9.2, 6, 4.5), box(8.1, 5.5, 11.5, 9.2, 6, 12.1),
							box(2.85, 5.5, 3.9, 8.1, 6, 12.1), box(13, 0.5, 4.5, 14, 6, 11.5), box(8, 0.5, 4.5, 8.15, 6, 11.5), box(8, 0.5, 10.5, 13, 6, 11.5), box(8, 0.5, 4.5, 13, 6, 5.5), box(7, 0.5, 5.5, 13, 1.5, 10.5),
							box(13.75, 4.75, 7.5, 15.75, 5.75, 8.5), box(14.75, 2.75, 7.5, 15.75, 4.75, 8.5), box(9, 7, 7.5, 9.25, 8, 8.5));
					case WEST -> Shapes.or(box(7.9, 0, 4, 13, 10, 12), box(6.9, 6, 4, 7.9, 10, 12), box(6.9, 0, 11, 7.9, 6, 12), box(6.9, 0, 5, 7.9, 1, 11), box(6.9, 0, 4, 7.9, 6, 5), box(6.8, 5.5, 11.5, 7.9, 6, 12.1),
							box(6.8, 5.5, 3.9, 7.9, 6, 4.5), box(7.9, 5.5, 3.9, 13.15, 6, 12.1), box(2, 0.5, 4.5, 3, 6, 11.5), box(7.85, 0.5, 4.5, 8, 6, 11.5), box(3, 0.5, 4.5, 8, 6, 5.5), box(3, 0.5, 10.5, 8, 6, 11.5),
							box(3, 0.5, 5.5, 9, 1.5, 10.5), box(0.25, 4.75, 7.5, 2.25, 5.75, 8.5), box(0.25, 2.75, 7.5, 1.25, 4.75, 8.5), box(6.75, 7, 7.5, 7, 8, 8.5));
					default -> Shapes.or(box(4, 0, 3, 12, 10, 8.1), box(4, 6, 8.1, 12, 10, 9.1), box(11, 0, 8.1, 12, 6, 9.1), box(5, 0, 8.1, 11, 1, 9.1), box(4, 0, 8.1, 5, 6, 9.1), box(11.5, 5.5, 8.1, 12.1, 6, 9.2), box(3.9, 5.5, 8.1, 4.5, 6, 9.2),
							box(3.9, 5.5, 2.85, 12.1, 6, 8.1), box(4.5, 0.5, 13, 11.5, 6, 14), box(4.5, 0.5, 8, 11.5, 6, 8.15), box(4.5, 0.5, 8, 5.5, 6, 13), box(10.5, 0.5, 8, 11.5, 6, 13), box(5.5, 0.5, 7, 10.5, 1.5, 13),
							box(7.5, 4.75, 13.75, 8.5, 5.75, 15.75), box(7.5, 2.75, 14.75, 8.5, 4.75, 15.75), box(7.5, 7, 9, 8.5, 8, 9.25));
				};
			}
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(4, 0, 6.9, 12, 10, 13), box(3.9, 5.5, 6.8, 4.5, 6, 7.9), box(11.5, 5.5, 6.8, 12.1, 6, 7.9), box(3.9, 5.5, 7.9, 12.1, 6, 13.15), box(4.5, 0.5, 6.75, 11.5, 6, 7), box(7.5, 4.75, 5, 8.5, 5.75, 7),
						box(7.5, 2.75, 5, 8.5, 4.75, 6), box(7.5, 7, 6.75, 8.5, 8, 7));
				case EAST -> Shapes.or(box(3, 0, 4, 9.1, 10, 12), box(8.1, 5.5, 3.9, 9.2, 6, 4.5), box(8.1, 5.5, 11.5, 9.2, 6, 12.1), box(2.85, 5.5, 3.9, 8.1, 6, 12.1), box(9, 0.5, 4.5, 9.25, 6, 11.5), box(9, 4.75, 7.5, 11, 5.75, 8.5),
						box(10, 2.75, 7.5, 11, 4.75, 8.5), box(9, 7, 7.5, 9.25, 8, 8.5));
				case WEST -> Shapes.or(box(6.9, 0, 4, 13, 10, 12), box(6.8, 5.5, 11.5, 7.9, 6, 12.1), box(6.8, 5.5, 3.9, 7.9, 6, 4.5), box(7.9, 5.5, 3.9, 13.15, 6, 12.1), box(6.75, 0.5, 4.5, 7, 6, 11.5), box(5, 4.75, 7.5, 7, 5.75, 8.5),
						box(5, 2.75, 7.5, 6, 4.75, 8.5), box(6.75, 7, 7.5, 7, 8, 8.5));
				default -> Shapes.or(box(4, 0, 3, 12, 10, 9.1), box(11.5, 5.5, 8.1, 12.1, 6, 9.2), box(3.9, 5.5, 8.1, 4.5, 6, 9.2), box(3.9, 5.5, 2.85, 12.1, 6, 8.1), box(4.5, 0.5, 9, 11.5, 6, 9.25), box(7.5, 4.75, 9, 8.5, 5.75, 11),
						box(7.5, 2.75, 10, 8.5, 4.75, 11), box(7.5, 7, 9, 8.5, 8, 9.25));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return propagatesSkylightDown(state, worldIn, pos) ? 0 : 1;
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
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
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

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AirfryerBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity != null && blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof AirfryerBlockEntity be) {
				Containers.dropContents(world, pos, be);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof AirfryerBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}