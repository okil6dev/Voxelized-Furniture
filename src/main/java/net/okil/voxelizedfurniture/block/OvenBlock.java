package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.world.inventory.CookingGuiMenu;
import net.okil.voxelizedfurniture.block.entity.OvenBlockEntity;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import io.netty.buffer.Unpooled;

import com.google.common.collect.ImmutableMap;

public class OvenBlock extends Block implements EntityBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public OvenBlock() {
		super(BlockBehaviour.Properties.of().strength(7f, 9f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(0, 0, 2, 16, 16, 3), box(2, 11, 1, 3, 12, 2), box(13, 11, 1, 14, 12, 2), box(2, 11, 0, 14, 12, 1), box(6, 13, 1.75, 10, 15, 2), box(0, 15, 3, 16, 16, 16), box(0, 0, 3, 16, 15, 16),
						box(1.25, 13.25, 1.75, 2.75, 14.75, 2), box(3.25, 13.25, 1.75, 4.75, 14.75, 2), box(11.25, 13.25, 1.75, 12.75, 14.75, 2), box(13.25, 13.25, 1.75, 14.75, 14.75, 2));
				case EAST -> Shapes.or(box(13, 0, 0, 14, 16, 16), box(14, 11, 2, 15, 12, 3), box(14, 11, 13, 15, 12, 14), box(15, 11, 2, 16, 12, 14), box(14, 13, 6, 14.25, 15, 10), box(0, 15, 0, 13, 16, 16), box(0, 0, 0, 13, 15, 16),
						box(14, 13.25, 1.25, 14.25, 14.75, 2.75), box(14, 13.25, 3.25, 14.25, 14.75, 4.75), box(14, 13.25, 11.25, 14.25, 14.75, 12.75), box(14, 13.25, 13.25, 14.25, 14.75, 14.75));
				case WEST -> Shapes.or(box(2, 0, 0, 3, 16, 16), box(1, 11, 13, 2, 12, 14), box(1, 11, 2, 2, 12, 3), box(0, 11, 2, 1, 12, 14), box(1.75, 13, 6, 2, 15, 10), box(3, 15, 0, 16, 16, 16), box(3, 0, 0, 16, 15, 16),
						box(1.75, 13.25, 13.25, 2, 14.75, 14.75), box(1.75, 13.25, 11.25, 2, 14.75, 12.75), box(1.75, 13.25, 3.25, 2, 14.75, 4.75), box(1.75, 13.25, 1.25, 2, 14.75, 2.75));
				default -> Shapes.or(box(0, 0, 13, 16, 16, 14), box(13, 11, 14, 14, 12, 15), box(2, 11, 14, 3, 12, 15), box(2, 11, 15, 14, 12, 16), box(6, 13, 14, 10, 15, 14.25), box(0, 15, 0, 16, 16, 13), box(0, 0, 0, 16, 15, 13),
						box(13.25, 13.25, 14, 14.75, 14.75, 14.25), box(11.25, 13.25, 14, 12.75, 14.75, 14.25), box(3.25, 13.25, 14, 4.75, 14.75, 14.25), box(1.25, 13.25, 14, 2.75, 14.75, 14.25));
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
		if (entity instanceof ServerPlayer player) {
			player.openMenu(new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return Component.literal("Oven");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					return new CookingGuiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
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
		return new OvenBlockEntity(pos, state);
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
			if (blockEntity instanceof OvenBlockEntity be) {
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
		if (tileentity instanceof OvenBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}