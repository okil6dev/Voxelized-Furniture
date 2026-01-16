package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.world.inventory.KitchenCounterGuiMenu;
import net.okil.voxelizedfurniture.block.entity.KitchenDrawer1BlockEntity;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Containers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import io.netty.buffer.Unpooled;

public class KitchenDrawer1Block extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(0, 0, 3, 16, 14, 16), box(1, 12.25, 2.5, 15, 13.25, 3), box(1, 10.25, 2.5, 15, 11.25, 3), box(14, 11.25, 2.5, 15, 12.25, 3), box(1, 11.25, 2.5, 2, 12.25, 3),
			box(1, 2.75, 2.5, 2, 5.75, 3), box(1, 1.75, 2.5, 15, 2.75, 3), box(1, 5.75, 2.5, 15, 6.75, 3), box(7, 5.75, 2, 9, 6.75, 2.5), box(7, 12.25, 2, 9, 13.25, 2.5), box(1, 7, 2.5, 15, 8, 3), box(14, 8, 2.5, 15, 9, 3), box(1, 9, 2.5, 15, 10, 3),
			box(7, 9, 2, 9, 10, 2.5), box(1, 8, 2.5, 2, 9, 3), box(14, 2.75, 2.5, 15, 5.75, 3), box(0, 14, 7, 16, 16, 10), box(0, 14, 11, 16, 16, 14), box(0, 14, 15, 16, 16, 16), box(0, 14, 14, 16, 16, 15), box(0, 14, 10, 16, 16, 11),
			box(0, 14, 6, 16, 16, 7), box(0, 14, 2.25, 16, 16, 6));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 0, 16, 14, 13), box(1, 12.25, 13, 15, 13.25, 13.5), box(1, 10.25, 13, 15, 11.25, 13.5), box(1, 11.25, 13, 2, 12.25, 13.5), box(14, 11.25, 13, 15, 12.25, 13.5),
			box(14, 2.75, 13, 15, 5.75, 13.5), box(1, 1.75, 13, 15, 2.75, 13.5), box(1, 5.75, 13, 15, 6.75, 13.5), box(7, 5.75, 13.5, 9, 6.75, 14), box(7, 12.25, 13.5, 9, 13.25, 14), box(1, 7, 13, 15, 8, 13.5), box(1, 8, 13, 2, 9, 13.5),
			box(1, 9, 13, 15, 10, 13.5), box(7, 9, 13.5, 9, 10, 14), box(14, 8, 13, 15, 9, 13.5), box(1, 2.75, 13, 2, 5.75, 13.5), box(0, 14, 6, 16, 16, 9), box(0, 14, 2, 16, 16, 5), box(0, 14, 0, 16, 16, 1), box(0, 14, 1, 16, 16, 2),
			box(0, 14, 5, 16, 16, 6), box(0, 14, 9, 16, 16, 10), box(0, 14, 10, 16, 16, 13.75));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 0, 0, 13, 14, 16), box(13, 12.25, 1, 13.5, 13.25, 15), box(13, 10.25, 1, 13.5, 11.25, 15), box(13, 11.25, 14, 13.5, 12.25, 15), box(13, 11.25, 1, 13.5, 12.25, 2),
			box(13, 2.75, 1, 13.5, 5.75, 2), box(13, 1.75, 1, 13.5, 2.75, 15), box(13, 5.75, 1, 13.5, 6.75, 15), box(13.5, 5.75, 7, 14, 6.75, 9), box(13.5, 12.25, 7, 14, 13.25, 9), box(13, 7, 1, 13.5, 8, 15), box(13, 8, 14, 13.5, 9, 15),
			box(13, 9, 1, 13.5, 10, 15), box(13.5, 9, 7, 14, 10, 9), box(13, 8, 1, 13.5, 9, 2), box(13, 2.75, 14, 13.5, 5.75, 15), box(6, 14, 0, 9, 16, 16), box(2, 14, 0, 5, 16, 16), box(0, 14, 0, 1, 16, 16), box(1, 14, 0, 2, 16, 16),
			box(5, 14, 0, 6, 16, 16), box(9, 14, 0, 10, 16, 16), box(10, 14, 0, 13.75, 16, 16));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(3, 0, 0, 16, 14, 16), box(2.5, 12.25, 1, 3, 13.25, 15), box(2.5, 10.25, 1, 3, 11.25, 15), box(2.5, 11.25, 1, 3, 12.25, 2), box(2.5, 11.25, 14, 3, 12.25, 15),
			box(2.5, 2.75, 14, 3, 5.75, 15), box(2.5, 1.75, 1, 3, 2.75, 15), box(2.5, 5.75, 1, 3, 6.75, 15), box(2, 5.75, 7, 2.5, 6.75, 9), box(2, 12.25, 7, 2.5, 13.25, 9), box(2.5, 7, 1, 3, 8, 15), box(2.5, 8, 1, 3, 9, 2), box(2.5, 9, 1, 3, 10, 15),
			box(2, 9, 7, 2.5, 10, 9), box(2.5, 8, 14, 3, 9, 15), box(2.5, 2.75, 1, 3, 5.75, 2), box(7, 14, 0, 10, 16, 16), box(11, 14, 0, 14, 16, 16), box(15, 14, 0, 16, 16, 16), box(14, 14, 0, 15, 16, 16), box(10, 14, 0, 11, 16, 16),
			box(6, 14, 0, 7, 16, 16), box(2.25, 14, 0, 6, 16, 16));

	public KitchenDrawer1Block(BlockBehaviour.Properties properties) {
		super(properties.strength(5f, 9f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
	public InteractionResult useWithoutItem(BlockState blockstate, Level world, BlockPos pos, Player entity, BlockHitResult hit) {
		super.useWithoutItem(blockstate, world, pos, entity, hit);
		if (entity instanceof ServerPlayer player) {
			player.openMenu(new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return Component.literal("Kitchen Drawer");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					return new KitchenCounterGuiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
				}
			}, pos);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new KitchenDrawer1BlockEntity(pos, state);
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
		if (tileentity instanceof KitchenDrawer1BlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}