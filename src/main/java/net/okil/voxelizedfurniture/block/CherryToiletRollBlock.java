package net.okil.voxelizedfurniture.block;

import org.checkerframework.checker.units.qual.s;

import net.okil.voxelizedfurniture.procedures.ToiletrollwoolProcedure;
import net.okil.voxelizedfurniture.block.entity.CherryToiletRollBlockEntity;

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

public class CherryToiletRollBlock extends Block implements EntityBlock {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 1);
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_1_NORTH = Shapes.or(box(4, 6, 15, 12, 11, 16), box(4, 8.25, 12.75, 12, 8.75, 13.25), box(11.5, 8.25, 13.25, 12, 8.75, 15), box(4, 8.25, 13.25, 4.5, 8.75, 15), box(5.5, 6.75, 13.5, 10.5, 9.75, 14.5),
			box(5.5, 6.75, 11.5, 10.5, 9.75, 12.5), box(5.5, 8.75, 12.5, 10.5, 9.75, 13.5), box(5.5, 6.75, 12.5, 10.5, 7.75, 13.5));
	private static final VoxelShape SHAPE_1_SOUTH = Shapes.or(box(4, 6, 0, 12, 11, 1), box(4, 8.25, 2.75, 12, 8.75, 3.25), box(4, 8.25, 1, 4.5, 8.75, 2.75), box(11.5, 8.25, 1, 12, 8.75, 2.75), box(5.5, 6.75, 1.5, 10.5, 9.75, 2.5),
			box(5.5, 6.75, 3.5, 10.5, 9.75, 4.5), box(5.5, 8.75, 2.5, 10.5, 9.75, 3.5), box(5.5, 6.75, 2.5, 10.5, 7.75, 3.5));
	private static final VoxelShape SHAPE_1_EAST = Shapes.or(box(0, 6, 4, 1, 11, 12), box(2.75, 8.25, 4, 3.25, 8.75, 12), box(1, 8.25, 11.5, 2.75, 8.75, 12), box(1, 8.25, 4, 2.75, 8.75, 4.5), box(1.5, 6.75, 5.5, 2.5, 9.75, 10.5),
			box(3.5, 6.75, 5.5, 4.5, 9.75, 10.5), box(2.5, 8.75, 5.5, 3.5, 9.75, 10.5), box(2.5, 6.75, 5.5, 3.5, 7.75, 10.5));
	private static final VoxelShape SHAPE_1_WEST = Shapes.or(box(15, 6, 4, 16, 11, 12), box(12.75, 8.25, 4, 13.25, 8.75, 12), box(13.25, 8.25, 4, 15, 8.75, 4.5), box(13.25, 8.25, 11.5, 15, 8.75, 12), box(13.5, 6.75, 5.5, 14.5, 9.75, 10.5),
			box(11.5, 6.75, 5.5, 12.5, 9.75, 10.5), box(12.5, 8.75, 5.5, 13.5, 9.75, 10.5), box(12.5, 6.75, 5.5, 13.5, 7.75, 10.5));
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(4, 6, 15, 12, 11, 16), box(4, 8.25, 12.75, 12, 8.75, 13.25), box(11.5, 8.25, 13.25, 12, 8.75, 15), box(4, 8.25, 13.25, 4.5, 8.75, 15));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(4, 6, 0, 12, 11, 1), box(4, 8.25, 2.75, 12, 8.75, 3.25), box(4, 8.25, 1, 4.5, 8.75, 2.75), box(11.5, 8.25, 1, 12, 8.75, 2.75));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 6, 4, 1, 11, 12), box(2.75, 8.25, 4, 3.25, 8.75, 12), box(1, 8.25, 11.5, 2.75, 8.75, 12), box(1, 8.25, 4, 2.75, 8.75, 4.5));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(15, 6, 4, 16, 11, 12), box(12.75, 8.25, 4, 13.25, 8.75, 12), box(13.25, 8.25, 4, 15, 8.75, 4.5), box(13.25, 8.25, 11.5, 15, 8.75, 12));

	public CherryToiletRollBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(0.5f, 1f).lightLevel(s -> (new Object() {
			public int getLightLevel() {
				if (s.getValue(BLOCKSTATE) == 1)
					return 0;
				return 0;
			}
		}.getLightLevel())).noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
		if (state.getValue(BLOCKSTATE) == 1) {
			return (switch (state.getValue(FACING)) {
				case NORTH -> SHAPE_1_NORTH;
				case SOUTH -> SHAPE_1_SOUTH;
				case EAST -> SHAPE_1_EAST;
				case WEST -> SHAPE_1_WEST;
				default -> SHAPE_1_NORTH;
			});
		}
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
		builder.add(FACING, BLOCKSTATE);
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
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		double hitX = hit.getLocation().x;
		double hitY = hit.getLocation().y;
		double hitZ = hit.getLocation().z;
		Direction direction = hit.getDirection();
		ToiletrollwoolProcedure.execute(world, x, y, z, blockstate, entity);
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CherryToiletRollBlockEntity(pos, state);
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
		if (tileentity instanceof CherryToiletRollBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}