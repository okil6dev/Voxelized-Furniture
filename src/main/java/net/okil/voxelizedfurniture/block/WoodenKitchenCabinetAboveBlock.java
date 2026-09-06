package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.block.entity.WoodenKitchenCabinetAboveBlockEntity;

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

public class WoodenKitchenCabinetAboveBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public WoodenKitchenCabinetAboveBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(1.5f, 2f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(0, 0, 9, 16, 16, 16), box(15, 0, 8, 16, 16, 9), box(1, 0, 8, 3, 16, 9), box(13, 0, 8, 15, 16, 9), box(2, 1, 7, 3, 2, 8), box(4, 3, 8, 12, 13, 9), box(3, 0, 8, 13, 2, 9), box(3, 14, 8, 13, 16, 9),
						box(0, 0, 8, 1, 16, 9), box(3, 2, 8, 4, 14, 9), box(4, 13, 8, 12, 14, 9), box(4, 2, 8, 12, 3, 9), box(12, 2, 8, 13, 14, 9));
				case EAST -> Shapes.or(box(0, 0, 0, 7, 16, 16), box(7, 0, 15, 8, 16, 16), box(7, 0, 1, 8, 16, 3), box(7, 0, 13, 8, 16, 15), box(8, 1, 2, 9, 2, 3), box(7, 3, 4, 8, 13, 12), box(7, 0, 3, 8, 2, 13), box(7, 14, 3, 8, 16, 13),
						box(7, 0, 0, 8, 16, 1), box(7, 2, 3, 8, 14, 4), box(7, 13, 4, 8, 14, 12), box(7, 2, 4, 8, 3, 12), box(7, 2, 12, 8, 14, 13));
				case WEST -> Shapes.or(box(9, 0, 0, 16, 16, 16), box(8, 0, 0, 9, 16, 1), box(8, 0, 13, 9, 16, 15), box(8, 0, 1, 9, 16, 3), box(7, 1, 13, 8, 2, 14), box(8, 3, 4, 9, 13, 12), box(8, 0, 3, 9, 2, 13), box(8, 14, 3, 9, 16, 13),
						box(8, 0, 15, 9, 16, 16), box(8, 2, 12, 9, 14, 13), box(8, 13, 4, 9, 14, 12), box(8, 2, 4, 9, 3, 12), box(8, 2, 3, 9, 14, 4));
				default -> Shapes.or(box(0, 0, 0, 16, 16, 7), box(0, 0, 7, 1, 16, 8), box(13, 0, 7, 15, 16, 8), box(1, 0, 7, 3, 16, 8), box(13, 1, 8, 14, 2, 9), box(4, 3, 7, 12, 13, 8), box(3, 0, 7, 13, 2, 8), box(3, 14, 7, 13, 16, 8),
						box(15, 0, 7, 16, 16, 8), box(12, 2, 7, 13, 14, 8), box(4, 13, 7, 12, 14, 8), box(4, 2, 7, 12, 3, 8), box(3, 2, 7, 4, 14, 8));
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
		return new WoodenKitchenCabinetAboveBlockEntity(pos, state);
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
		if (tileentity instanceof WoodenKitchenCabinetAboveBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}