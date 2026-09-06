package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.block.entity.WoodenKitchenExhausthoodBlockEntity;

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

public class WoodenKitchenExhausthoodBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public WoodenKitchenExhausthoodBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(3f, 2f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(0, 0, 5, 16, 3, 16), box(0, 3, 8, 16, 4, 16), box(0, 3, 7.5, 16, 3.35, 8.5), box(1, 15, 8, 15, 16, 9), box(3, 7, 8, 13, 14, 9), box(1, 4, 8, 15, 6, 9), box(2, 14, 8, 14, 15, 9), box(2, 6, 8, 14, 7, 9),
						box(1, 5, 7, 2, 6, 8), box(13, 7, 8, 14, 14, 9), box(2, 7, 8, 3, 14, 9), box(0, 4, 9, 16, 16, 16), box(15, 4, 8, 16, 16, 9), box(14, 6, 8, 15, 15, 9), box(1, 6, 8, 2, 15, 9), box(0, 4, 8, 1, 16, 9),
						box(0, 2.25, 6.15, 16, 3.25, 9.4));
				case EAST -> Shapes.or(box(0, 0, 0, 11, 3, 16), box(0, 3, 0, 8, 4, 16), box(7.5, 3, 0, 8.5, 3.35, 16), box(7, 15, 1, 8, 16, 15), box(7, 7, 3, 8, 14, 13), box(7, 4, 1, 8, 6, 15), box(7, 14, 2, 8, 15, 14), box(7, 6, 2, 8, 7, 14),
						box(8, 5, 1, 9, 6, 2), box(7, 7, 13, 8, 14, 14), box(7, 7, 2, 8, 14, 3), box(0, 4, 0, 7, 16, 16), box(7, 4, 15, 8, 16, 16), box(7, 6, 14, 8, 15, 15), box(7, 6, 1, 8, 15, 2), box(7, 4, 0, 8, 16, 1),
						box(6.6, 2.25, 0, 9.85, 3.25, 16));
				case WEST -> Shapes.or(box(5, 0, 0, 16, 3, 16), box(8, 3, 0, 16, 4, 16), box(7.5, 3, 0, 8.5, 3.35, 16), box(8, 15, 1, 9, 16, 15), box(8, 7, 3, 9, 14, 13), box(8, 4, 1, 9, 6, 15), box(8, 14, 2, 9, 15, 14), box(8, 6, 2, 9, 7, 14),
						box(7, 5, 14, 8, 6, 15), box(8, 7, 2, 9, 14, 3), box(8, 7, 13, 9, 14, 14), box(9, 4, 0, 16, 16, 16), box(8, 4, 0, 9, 16, 1), box(8, 6, 1, 9, 15, 2), box(8, 6, 14, 9, 15, 15), box(8, 4, 15, 9, 16, 16),
						box(6.15, 2.25, 0, 9.4, 3.25, 16));
				default -> Shapes.or(box(0, 0, 0, 16, 3, 11), box(0, 3, 0, 16, 4, 8), box(0, 3, 7.5, 16, 3.35, 8.5), box(1, 15, 7, 15, 16, 8), box(3, 7, 7, 13, 14, 8), box(1, 4, 7, 15, 6, 8), box(2, 14, 7, 14, 15, 8), box(2, 6, 7, 14, 7, 8),
						box(14, 5, 8, 15, 6, 9), box(2, 7, 7, 3, 14, 8), box(13, 7, 7, 14, 14, 8), box(0, 4, 0, 16, 16, 7), box(0, 4, 7, 1, 16, 8), box(1, 6, 7, 2, 15, 8), box(14, 6, 7, 15, 15, 8), box(15, 4, 7, 16, 16, 8),
						box(0, 2.25, 6.6, 16, 3.25, 9.85));
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
		return new WoodenKitchenExhausthoodBlockEntity(pos, state);
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
		if (tileentity instanceof WoodenKitchenExhausthoodBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}