package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.block.entity.KitchenFridgeBlockEntity;

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
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Containers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class KitchenFridgeBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public KitchenFridgeBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(6f, 9f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(0, 0, 3, 16, 16, 16), box(0, 16, 3, 16, 32, 16), box(0.25, 16, 2, 15.75, 31.5, 3), box(14, 12, 1, 15, 24, 2), box(14, 4, 1, 15, 9, 2), box(0.25, 11, 2, 15.75, 16, 3), box(0.25, 1, 2, 15.75, 10, 3),
						box(3, 26, 1.9, 7, 30, 2));
				case EAST -> Shapes.or(box(0, 0, 0, 13, 16, 16), box(0, 16, 0, 13, 32, 16), box(13, 16, 0.25, 14, 31.5, 15.75), box(14, 12, 14, 15, 24, 15), box(14, 4, 14, 15, 9, 15), box(13, 11, 0.25, 14, 16, 15.75), box(13, 1, 0.25, 14, 10, 15.75),
						box(14, 26, 3, 14.1, 30, 7));
				case WEST -> Shapes.or(box(3, 0, 0, 16, 16, 16), box(3, 16, 0, 16, 32, 16), box(2, 16, 0.25, 3, 31.5, 15.75), box(1, 12, 1, 2, 24, 2), box(1, 4, 1, 2, 9, 2), box(2, 11, 0.25, 3, 16, 15.75), box(2, 1, 0.25, 3, 10, 15.75),
						box(1.9, 26, 9, 2, 30, 13));
				default -> Shapes.or(box(0, 0, 0, 16, 16, 13), box(0, 16, 0, 16, 32, 13), box(0.25, 16, 13, 15.75, 31.5, 14), box(1, 12, 14, 2, 24, 15), box(1, 4, 14, 2, 9, 15), box(0.25, 11, 13, 15.75, 16, 14), box(0.25, 1, 13, 15.75, 10, 14),
						box(9, 26, 14, 13, 30, 14.1));
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
		return new KitchenFridgeBlockEntity(pos, state);
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
		if (tileentity instanceof KitchenFridgeBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}