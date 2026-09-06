package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.block.entity.WoodenKitchenDrawerBlockEntity;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Containers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class WoodenKitchenDrawerBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public WoodenKitchenDrawerBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(2f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(0, 0, 2, 16, 14, 16), box(13, 0, 1, 15, 6, 2), box(13, 7, 1, 15, 14, 2), box(1, 7, 1, 3, 14, 2), box(1, 0, 1, 3, 6, 2), box(3, 12, 1, 13, 14, 2), box(4, 9, 1, 12, 11, 2), box(4, 2, 1, 12, 4, 2),
						box(3, 0, 1, 13, 1, 2), box(3, 5, 1, 13, 6, 2), box(7, 5, 0, 9, 6, 1), box(7, 12, 0, 9, 13, 1), box(3, 7, 1, 13, 8, 2), box(15, 0, 1, 16, 14, 2), box(4, 11, 1, 12, 12, 2), box(12, 8, 1, 13, 12, 2), box(4, 8, 1, 12, 9, 2),
						box(3, 8, 1, 4, 12, 2), box(0, 0, 1, 1, 14, 2), box(1, 6, 1, 15, 7, 2), box(4, 4, 1, 12, 5, 2), box(3, 1, 1, 4, 5, 2), box(4, 1, 1, 12, 2, 2), box(12, 1, 1, 13, 5, 2), box(0, 14, 0, 16, 16, 16));
				case EAST -> Shapes.or(box(0, 0, 0, 14, 14, 16), box(14, 0, 13, 15, 6, 15), box(14, 7, 13, 15, 14, 15), box(14, 7, 1, 15, 14, 3), box(14, 0, 1, 15, 6, 3), box(14, 12, 3, 15, 14, 13), box(14, 9, 4, 15, 11, 12),
						box(14, 2, 4, 15, 4, 12), box(14, 0, 3, 15, 1, 13), box(14, 5, 3, 15, 6, 13), box(15, 5, 7, 16, 6, 9), box(15, 12, 7, 16, 13, 9), box(14, 7, 3, 15, 8, 13), box(14, 0, 15, 15, 14, 16), box(14, 11, 4, 15, 12, 12),
						box(14, 8, 12, 15, 12, 13), box(14, 8, 4, 15, 9, 12), box(14, 8, 3, 15, 12, 4), box(14, 0, 0, 15, 14, 1), box(14, 6, 1, 15, 7, 15), box(14, 4, 4, 15, 5, 12), box(14, 1, 3, 15, 5, 4), box(14, 1, 4, 15, 2, 12),
						box(14, 1, 12, 15, 5, 13), box(0, 14, 0, 16, 16, 16));
				case WEST -> Shapes.or(box(2, 0, 0, 16, 14, 16), box(1, 0, 1, 2, 6, 3), box(1, 7, 1, 2, 14, 3), box(1, 7, 13, 2, 14, 15), box(1, 0, 13, 2, 6, 15), box(1, 12, 3, 2, 14, 13), box(1, 9, 4, 2, 11, 12), box(1, 2, 4, 2, 4, 12),
						box(1, 0, 3, 2, 1, 13), box(1, 5, 3, 2, 6, 13), box(0, 5, 7, 1, 6, 9), box(0, 12, 7, 1, 13, 9), box(1, 7, 3, 2, 8, 13), box(1, 0, 0, 2, 14, 1), box(1, 11, 4, 2, 12, 12), box(1, 8, 3, 2, 12, 4), box(1, 8, 4, 2, 9, 12),
						box(1, 8, 12, 2, 12, 13), box(1, 0, 15, 2, 14, 16), box(1, 6, 1, 2, 7, 15), box(1, 4, 4, 2, 5, 12), box(1, 1, 12, 2, 5, 13), box(1, 1, 4, 2, 2, 12), box(1, 1, 3, 2, 5, 4), box(0, 14, 0, 16, 16, 16));
				default -> Shapes.or(box(0, 0, 0, 16, 14, 14), box(1, 0, 14, 3, 6, 15), box(1, 7, 14, 3, 14, 15), box(13, 7, 14, 15, 14, 15), box(13, 0, 14, 15, 6, 15), box(3, 12, 14, 13, 14, 15), box(4, 9, 14, 12, 11, 15), box(4, 2, 14, 12, 4, 15),
						box(3, 0, 14, 13, 1, 15), box(3, 5, 14, 13, 6, 15), box(7, 5, 15, 9, 6, 16), box(7, 12, 15, 9, 13, 16), box(3, 7, 14, 13, 8, 15), box(0, 0, 14, 1, 14, 15), box(4, 11, 14, 12, 12, 15), box(3, 8, 14, 4, 12, 15),
						box(4, 8, 14, 12, 9, 15), box(12, 8, 14, 13, 12, 15), box(15, 0, 14, 16, 14, 15), box(1, 6, 14, 15, 7, 15), box(4, 4, 14, 12, 5, 15), box(12, 1, 14, 13, 5, 15), box(4, 1, 14, 12, 2, 15), box(3, 1, 14, 4, 5, 15),
						box(0, 14, 0, 16, 16, 16));
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
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		return state.setValue(FACING, context.getHorizontalDirection().getOpposite());
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
		if (entity instanceof ServerPlayer player)
			player.openMenu(world.getBlockEntity(pos) instanceof MenuProvider menuProvider ? menuProvider : null);
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new WoodenKitchenDrawerBlockEntity(pos, state);
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
		if (tileentity instanceof WoodenKitchenDrawerBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}