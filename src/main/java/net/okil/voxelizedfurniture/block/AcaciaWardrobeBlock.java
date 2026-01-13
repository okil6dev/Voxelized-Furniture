package net.okil.voxelizedfurniture.block;

import org.checkerframework.checker.units.qual.s;

import net.okil.voxelizedfurniture.world.inventory.WardrobeGuiMenu;
import net.okil.voxelizedfurniture.procedures.OakWardrobeStackProcedure;
import net.okil.voxelizedfurniture.block.entity.AcaciaWardrobeBlockEntity;

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

public class AcaciaWardrobeBlock extends Block implements EntityBlock {
	public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 1);
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SHAPE_1_NORTH = Shapes.or(box(0, 0, 0, 16, 2, 16), box(16, 0, 0, 32, 2, 16), box(15, 9, 0, 16, 16, 15), box(16, 15, 0, 31, 16, 15), box(16, 9, 0, 31, 15, 15), box(17, 13, -1, 20, 14, 0), box(16, 2, 0, 31, 8, 15),
			box(17, 6, -1, 20, 7, 0), box(15, 2, 0, 16, 8, 15), box(0, 2, 0, 1, 16, 16), box(31, 2, 0, 32, 16, 16), box(1, 2, 15, 17, 16, 16), box(17, 2, 15, 31, 16, 16), box(1, 8, 0, 16, 9, 15), box(16, 8, 0, 31, 9, 15), box(1, 15, 0, 15, 16, 15));
	private static final VoxelShape SHAPE_1_SOUTH = Shapes.or(box(0, 0, 0, 16, 2, 16), box(-16, 0, 0, 0, 2, 16), box(0, 9, 1, 1, 16, 16), box(-15, 15, 1, 0, 16, 16), box(-15, 9, 1, 0, 15, 16), box(-4, 13, 16, -1, 14, 17), box(-15, 2, 1, 0, 8, 16),
			box(-4, 6, 16, -1, 7, 17), box(0, 2, 1, 1, 8, 16), box(15, 2, 0, 16, 16, 16), box(-16, 2, 0, -15, 16, 16), box(-1, 2, 0, 15, 16, 1), box(-15, 2, 0, -1, 16, 1), box(0, 8, 1, 15, 9, 16), box(-15, 8, 1, 0, 9, 16), box(1, 15, 1, 15, 16, 16));
	private static final VoxelShape SHAPE_1_EAST = Shapes.or(box(0, 0, 0, 16, 2, 16), box(0, 0, 16, 16, 2, 32), box(1, 9, 15, 16, 16, 16), box(1, 15, 16, 16, 16, 31), box(1, 9, 16, 16, 15, 31), box(16, 13, 17, 17, 14, 20), box(1, 2, 16, 16, 8, 31),
			box(16, 6, 17, 17, 7, 20), box(1, 2, 15, 16, 8, 16), box(0, 2, 0, 16, 16, 1), box(0, 2, 31, 16, 16, 32), box(0, 2, 1, 1, 16, 17), box(0, 2, 17, 1, 16, 31), box(1, 8, 1, 16, 9, 16), box(1, 8, 16, 16, 9, 31), box(1, 15, 1, 16, 16, 15));
	private static final VoxelShape SHAPE_1_WEST = Shapes.or(box(0, 0, 0, 16, 2, 16), box(0, 0, -16, 16, 2, 0), box(0, 9, 0, 15, 16, 1), box(0, 15, -15, 15, 16, 0), box(0, 9, -15, 15, 15, 0), box(-1, 13, -4, 0, 14, -1), box(0, 2, -15, 15, 8, 0),
			box(-1, 6, -4, 0, 7, -1), box(0, 2, 0, 15, 8, 1), box(0, 2, 15, 16, 16, 16), box(0, 2, -16, 16, 16, -15), box(15, 2, -1, 16, 16, 15), box(15, 2, -15, 16, 16, -1), box(0, 8, 0, 15, 9, 15), box(0, 8, -15, 15, 9, 0),
			box(0, 15, 1, 15, 16, 15));
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(0, 0, 0, 16, 2, 16), box(16, 0, 0, 32, 2, 16), box(15, 8, 0, 16, 16, 15), box(16, 15, 0, 31, 16, 15), box(16, 8, 0, 31, 15, 15), box(17, 13, -1, 20, 14, 0), box(0, 2, 0, 1, 16, 16),
			box(31, 2, 0, 32, 16, 16), box(1, 2, 15, 17, 16, 16), box(17, 2, 15, 31, 16, 16), box(1, 2, 8, 15, 7, 15), box(1, 4, 2, 15, 7, 10), box(15, 4, 2, 31, 7, 10), box(15, 2, 8, 31, 7, 15), box(1, 7, 0, 16, 8, 15), box(16, 7, 0, 31, 8, 15),
			box(1, 15, 0, 15, 16, 15));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(0, 0, 0, 16, 2, 16), box(-16, 0, 0, 0, 2, 16), box(0, 8, 1, 1, 16, 16), box(-15, 15, 1, 0, 16, 16), box(-15, 8, 1, 0, 15, 16), box(-4, 13, 16, -1, 14, 17), box(15, 2, 0, 16, 16, 16),
			box(-16, 2, 0, -15, 16, 16), box(-1, 2, 0, 15, 16, 1), box(-15, 2, 0, -1, 16, 1), box(1, 2, 1, 15, 7, 8), box(1, 4, 6, 15, 7, 14), box(-15, 4, 6, 1, 7, 14), box(-15, 2, 1, 1, 7, 8), box(0, 7, 1, 15, 8, 16), box(-15, 7, 1, 0, 8, 16),
			box(1, 15, 1, 15, 16, 16));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(0, 0, 0, 16, 2, 16), box(0, 0, 16, 16, 2, 32), box(1, 8, 15, 16, 16, 16), box(1, 15, 16, 16, 16, 31), box(1, 8, 16, 16, 15, 31), box(16, 13, 17, 17, 14, 20), box(0, 2, 0, 16, 16, 1),
			box(0, 2, 31, 16, 16, 32), box(0, 2, 1, 1, 16, 17), box(0, 2, 17, 1, 16, 31), box(1, 2, 1, 8, 7, 15), box(6, 4, 1, 14, 7, 15), box(6, 4, 15, 14, 7, 31), box(1, 2, 15, 8, 7, 31), box(1, 7, 1, 16, 8, 16), box(1, 7, 16, 16, 8, 31),
			box(1, 15, 1, 16, 16, 15));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(0, 0, 0, 16, 2, 16), box(0, 0, -16, 16, 2, 0), box(0, 8, 0, 15, 16, 1), box(0, 15, -15, 15, 16, 0), box(0, 8, -15, 15, 15, 0), box(-1, 13, -4, 0, 14, -1), box(0, 2, 15, 16, 16, 16),
			box(0, 2, -16, 16, 16, -15), box(15, 2, -1, 16, 16, 15), box(15, 2, -15, 16, 16, -1), box(8, 2, 1, 15, 7, 15), box(2, 4, 1, 10, 7, 15), box(2, 4, -15, 10, 7, 1), box(8, 2, -15, 15, 7, 1), box(0, 7, 0, 15, 8, 15), box(0, 7, -15, 15, 8, 0),
			box(0, 15, 1, 15, 16, 15));

	public AcaciaWardrobeBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(3f, 5f).lightLevel(s -> (new Object() {
			public int getLightLevel() {
				if (s.getValue(BLOCKSTATE) == 1)
					return 0;
				return 0;
			}
		}.getLightLevel())).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
		if (entity instanceof ServerPlayer player) {
			player.openMenu(new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return Component.literal("Acacia Wardrobe");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					return new WardrobeGuiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
				}
			}, pos);
		}
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		double hitX = hit.getLocation().x;
		double hitY = hit.getLocation().y;
		double hitZ = hit.getLocation().z;
		Direction direction = hit.getDirection();
		OakWardrobeStackProcedure.execute(world, x, y, z);
		return InteractionResult.SUCCESS;
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AcaciaWardrobeBlockEntity(pos, state);
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
		if (tileentity instanceof AcaciaWardrobeBlockEntity be)
			return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
		else
			return 0;
	}
}