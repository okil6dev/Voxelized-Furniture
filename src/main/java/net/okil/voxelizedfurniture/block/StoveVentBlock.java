package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.block.entity.StoveVentBlockEntity;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Containers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class StoveVentBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(1, 0, 5.5, 15, 2, 7), box(1, 0, 7, 2, 2, 16), box(2, 0, 15, 14, 2, 16), box(14, 0, 7, 15, 2, 16), box(2, 2, 7, 14, 3, 9), box(2, 2, 15, 14, 3, 16), box(13, 2, 9, 14, 3, 15),
			box(2, 2, 9, 3, 3, 15), box(3, 3, 8.5, 13, 4, 11), box(3, 3, 11, 4, 4, 15), box(12, 3, 11, 13, 4, 15), box(3, 3, 15, 13, 4, 16), box(4, 4, 15, 12, 5, 16), box(4, 4, 10, 12, 5, 12), box(11, 4, 12, 12, 5, 15), box(4, 4, 12, 5, 5, 15),
			box(5, 5, 11.25, 11, 6, 12), box(5, 5, 15.25, 11, 6, 16), box(5, 5, 12, 6, 6, 15.25), box(10, 5, 12, 11, 6, 15.25), box(6, 6, 15, 10, 16, 16), box(6, 6, 12, 10, 16, 13), box(7, 15, 13, 9, 16, 15), box(6, 6, 13, 7, 16, 15),
			box(9, 6, 13, 10, 16, 15));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(1, 0, 9, 15, 2, 10.5), box(14, 0, 0, 15, 2, 9), box(2, 0, 0, 14, 2, 1), box(1, 0, 0, 2, 2, 9), box(2, 2, 7, 14, 3, 9), box(2, 2, 0, 14, 3, 1), box(2, 2, 1, 3, 3, 7),
			box(13, 2, 1, 14, 3, 7), box(3, 3, 5, 13, 4, 7.5), box(12, 3, 1, 13, 4, 5), box(3, 3, 1, 4, 4, 5), box(3, 3, 0, 13, 4, 1), box(4, 4, 0, 12, 5, 1), box(4, 4, 4, 12, 5, 6), box(4, 4, 1, 5, 5, 4), box(11, 4, 1, 12, 5, 4),
			box(5, 5, 4, 11, 6, 4.75), box(5, 5, 0, 11, 6, 0.75), box(10, 5, 0.75, 11, 6, 4), box(5, 5, 0.75, 6, 6, 4), box(6, 6, 0, 10, 16, 1), box(6, 6, 3, 10, 16, 4), box(7, 15, 1, 9, 16, 3), box(9, 6, 1, 10, 16, 3), box(6, 6, 1, 7, 16, 3));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(9, 0, 1, 10.5, 2, 15), box(0, 0, 1, 9, 2, 2), box(0, 0, 2, 1, 2, 14), box(0, 0, 14, 9, 2, 15), box(7, 2, 2, 9, 3, 14), box(0, 2, 2, 1, 3, 14), box(1, 2, 13, 7, 3, 14),
			box(1, 2, 2, 7, 3, 3), box(5, 3, 3, 7.5, 4, 13), box(1, 3, 3, 5, 4, 4), box(1, 3, 12, 5, 4, 13), box(0, 3, 3, 1, 4, 13), box(0, 4, 4, 1, 5, 12), box(4, 4, 4, 6, 5, 12), box(1, 4, 11, 4, 5, 12), box(1, 4, 4, 4, 5, 5),
			box(4, 5, 5, 4.75, 6, 11), box(0, 5, 5, 0.75, 6, 11), box(0.75, 5, 5, 4, 6, 6), box(0.75, 5, 10, 4, 6, 11), box(0, 6, 6, 1, 16, 10), box(3, 6, 6, 4, 16, 10), box(1, 15, 7, 3, 16, 9), box(1, 6, 6, 3, 16, 7), box(1, 6, 9, 3, 16, 10));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(5.5, 0, 1, 7, 2, 15), box(7, 0, 14, 16, 2, 15), box(15, 0, 2, 16, 2, 14), box(7, 0, 1, 16, 2, 2), box(7, 2, 2, 9, 3, 14), box(15, 2, 2, 16, 3, 14), box(9, 2, 2, 15, 3, 3),
			box(9, 2, 13, 15, 3, 14), box(8.5, 3, 3, 11, 4, 13), box(11, 3, 12, 15, 4, 13), box(11, 3, 3, 15, 4, 4), box(15, 3, 3, 16, 4, 13), box(15, 4, 4, 16, 5, 12), box(10, 4, 4, 12, 5, 12), box(12, 4, 4, 15, 5, 5), box(12, 4, 11, 15, 5, 12),
			box(11.25, 5, 5, 12, 6, 11), box(15.25, 5, 5, 16, 6, 11), box(12, 5, 10, 15.25, 6, 11), box(12, 5, 5, 15.25, 6, 6), box(15, 6, 6, 16, 16, 10), box(12, 6, 6, 13, 16, 10), box(13, 15, 7, 15, 16, 9), box(13, 6, 9, 15, 16, 10),
			box(13, 6, 6, 15, 16, 7));

	public StoveVentBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.METAL).strength(5f, 7f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new StoveVentBlockEntity(pos, state);
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
	public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
		BlockEntity tileentity = world.getBlockEntity(pos);
		if (tileentity instanceof StoveVentBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}