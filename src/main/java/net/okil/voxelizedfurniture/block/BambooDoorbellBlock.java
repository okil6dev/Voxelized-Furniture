package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.OakdoorbellPressedProcedure;
import net.okil.voxelizedfurniture.block.entity.BambooDoorbellBlockEntity;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class BambooDoorbellBlock extends Block implements EntityBlock {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 1);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public BambooDoorbellBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(2f, 5f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BLOCKSTATE, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(BLOCKSTATE) == 1) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(4, 2, 14, 12, 14, 16), box(6, 6, 13.5, 10, 10, 14));
					case EAST -> Shapes.or(box(0, 2, 4, 2, 14, 12), box(2, 6, 6, 2.5, 10, 10));
					case WEST -> Shapes.or(box(14, 2, 4, 16, 14, 12), box(13.5, 6, 6, 14, 10, 10));
					default -> Shapes.or(box(4, 2, 0, 12, 14, 2), box(6, 6, 2, 10, 10, 2.5));
				};
			}
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(4, 2, 14, 12, 14, 16), box(6, 6, 12.5, 10, 10, 14));
				case EAST -> Shapes.or(box(0, 2, 4, 2, 14, 12), box(2, 6, 6, 3.5, 10, 10));
				case WEST -> Shapes.or(box(14, 2, 4, 16, 14, 12), box(12.5, 6, 6, 14, 10, 10));
				default -> Shapes.or(box(4, 2, 0, 12, 14, 2), box(6, 6, 2, 10, 10, 3.5));
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
		builder.add(FACING, BLOCKSTATE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		return state.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BLOCKSTATE, 0);
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
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		double hitX = hit.getLocation().x;
		double hitY = hit.getLocation().y;
		double hitZ = hit.getLocation().z;
		Direction direction = hit.getDirection();
		OakdoorbellPressedProcedure.execute(world, x, y, z);
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BambooDoorbellBlockEntity(pos, state);
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
		if (tileentity instanceof BambooDoorbellBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}