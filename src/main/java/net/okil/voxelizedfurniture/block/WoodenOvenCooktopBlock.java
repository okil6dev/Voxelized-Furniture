package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.block.entity.WoodenOvenCooktopBlockEntity;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.material.PushReaction;
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

public class WoodenOvenCooktopBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public WoodenOvenCooktopBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(3f, 2f).noOcclusion().pushReaction(PushReaction.BLOCK).isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(0, 14, 0, 16, 16, 15), box(0, 0, 2, 16, 4, 16), box(0, 4, 1, 16, 14, 15), box(1.5, 10, 0, 14.5, 11, 0.25), box(1, 10, 0.25, 2, 11, 1), box(14, 10, 0.25, 15, 11, 1), box(5, 11.5, 0.9, 11, 13.5, 1),
						box(2, 11.5, 0.9, 4, 13.5, 1), box(12, 11.5, 0.9, 14, 13.5, 1), box(7, 2, 0, 9, 3, 1), box(0, 4, 15, 16, 16, 16), box(0, 3, 1, 16, 4, 2), box(0, 0, 1, 16, 1, 2), box(15, 1, 1, 16, 3, 2), box(0, 1, 1, 1, 3, 2),
						box(1, 1, 1, 15, 3, 2));
				case EAST -> Shapes.or(box(1, 14, 0, 16, 16, 16), box(0, 0, 0, 14, 4, 16), box(1, 4, 0, 15, 14, 16), box(15.75, 10, 1.5, 16, 11, 14.5), box(15, 10, 1, 15.75, 11, 2), box(15, 10, 14, 15.75, 11, 15), box(15, 11.5, 5, 15.1, 13.5, 11),
						box(15, 11.5, 2, 15.1, 13.5, 4), box(15, 11.5, 12, 15.1, 13.5, 14), box(15, 2, 7, 16, 3, 9), box(0, 4, 0, 1, 16, 16), box(14, 3, 0, 15, 4, 16), box(14, 0, 0, 15, 1, 16), box(14, 1, 15, 15, 3, 16), box(14, 1, 0, 15, 3, 1),
						box(14, 1, 1, 15, 3, 15));
				case WEST -> Shapes.or(box(0, 14, 0, 15, 16, 16), box(2, 0, 0, 16, 4, 16), box(1, 4, 0, 15, 14, 16), box(0, 10, 1.5, 0.25, 11, 14.5), box(0.25, 10, 14, 1, 11, 15), box(0.25, 10, 1, 1, 11, 2), box(0.9, 11.5, 5, 1, 13.5, 11),
						box(0.9, 11.5, 12, 1, 13.5, 14), box(0.9, 11.5, 2, 1, 13.5, 4), box(0, 2, 7, 1, 3, 9), box(15, 4, 0, 16, 16, 16), box(1, 3, 0, 2, 4, 16), box(1, 0, 0, 2, 1, 16), box(1, 1, 0, 2, 3, 1), box(1, 1, 15, 2, 3, 16),
						box(1, 1, 1, 2, 3, 15));
				default -> Shapes.or(box(0, 14, 1, 16, 16, 16), box(0, 0, 0, 16, 4, 14), box(0, 4, 1, 16, 14, 15), box(1.5, 10, 15.75, 14.5, 11, 16), box(14, 10, 15, 15, 11, 15.75), box(1, 10, 15, 2, 11, 15.75), box(5, 11.5, 15, 11, 13.5, 15.1),
						box(12, 11.5, 15, 14, 13.5, 15.1), box(2, 11.5, 15, 4, 13.5, 15.1), box(7, 2, 15, 9, 3, 16), box(0, 4, 0, 16, 16, 1), box(0, 3, 14, 16, 4, 15), box(0, 0, 14, 16, 1, 15), box(0, 1, 14, 1, 3, 15), box(15, 1, 14, 16, 3, 15),
						box(1, 1, 14, 15, 3, 15));
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
		return new WoodenOvenCooktopBlockEntity(pos, state);
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
		if (tileentity instanceof WoodenOvenCooktopBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}