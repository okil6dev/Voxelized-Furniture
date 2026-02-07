package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModBlocks;

import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.BiomeColors;

public class PottedGreenPlantBlock extends Block implements SimpleWaterloggedBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE_NORTH = Shapes.or(box(4.5, 0, 4.5, 11.5, 1, 11.5), box(3.5, 1, 4.5, 4.5, 7, 11.5), box(2.5, 7, 4.5, 3.5, 13, 11.5), box(11.5, 1, 4.5, 12.5, 7, 11.5), box(12.5, 7, 4.5, 13.5, 13, 11.5),
			box(4.5, 1, 11.5, 11.5, 7, 12.5), box(4.5, 7, 12.5, 11.5, 13, 13.5), box(4.5, 1, 3.5, 11.5, 7, 4.5), box(4.5, 7, 2.5, 11.5, 13, 3.5), box(4.5, 7, 3.5, 11.5, 11, 12.5), box(11.5, 7, 4.5, 12.5, 11, 11.5), box(3.5, 7, 4.5, 4.5, 11, 11.5),
			box(11.5, 7, 3.5, 12.5, 13, 4.5), box(3.5, 7, 3.5, 4.5, 13, 4.5), box(3.5, 7, 11.5, 4.5, 13, 12.5), box(11.5, 7, 11.5, 12.5, 13, 12.5), box(7.5, 11, 7.5, 8.5, 22, 8.5), box(5.5, 21, 5.5, 12.5, 27, 12.5), box(2.5, 22, 2.5, 9.5, 28, 9.5),
			box(5.5, 19, 2.5, 12.5, 25, 9.5), box(3.5, 23, 6.5, 9.5, 29, 10.5), box(4.5, 20, 4.5, 11.5, 26, 11.5));
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(box(4.5, 0, 4.5, 11.5, 1, 11.5), box(11.5, 1, 4.5, 12.5, 7, 11.5), box(12.5, 7, 4.5, 13.5, 13, 11.5), box(3.5, 1, 4.5, 4.5, 7, 11.5), box(2.5, 7, 4.5, 3.5, 13, 11.5),
			box(4.5, 1, 3.5, 11.5, 7, 4.5), box(4.5, 7, 2.5, 11.5, 13, 3.5), box(4.5, 1, 11.5, 11.5, 7, 12.5), box(4.5, 7, 12.5, 11.5, 13, 13.5), box(4.5, 7, 3.5, 11.5, 11, 12.5), box(3.5, 7, 4.5, 4.5, 11, 11.5), box(11.5, 7, 4.5, 12.5, 11, 11.5),
			box(3.5, 7, 11.5, 4.5, 13, 12.5), box(11.5, 7, 11.5, 12.5, 13, 12.5), box(11.5, 7, 3.5, 12.5, 13, 4.5), box(3.5, 7, 3.5, 4.5, 13, 4.5), box(7.5, 11, 7.5, 8.5, 22, 8.5), box(3.5, 21, 3.5, 10.5, 27, 10.5), box(6.5, 22, 6.5, 13.5, 28, 13.5),
			box(3.5, 19, 6.5, 10.5, 25, 13.5), box(6.5, 23, 5.5, 12.5, 29, 9.5), box(4.5, 20, 4.5, 11.5, 26, 11.5));
	private static final VoxelShape SHAPE_EAST = Shapes.or(box(4.5, 0, 4.5, 11.5, 1, 11.5), box(4.5, 1, 3.5, 11.5, 7, 4.5), box(4.5, 7, 2.5, 11.5, 13, 3.5), box(4.5, 1, 11.5, 11.5, 7, 12.5), box(4.5, 7, 12.5, 11.5, 13, 13.5),
			box(3.5, 1, 4.5, 4.5, 7, 11.5), box(2.5, 7, 4.5, 3.5, 13, 11.5), box(11.5, 1, 4.5, 12.5, 7, 11.5), box(12.5, 7, 4.5, 13.5, 13, 11.5), box(3.5, 7, 4.5, 12.5, 11, 11.5), box(4.5, 7, 11.5, 11.5, 11, 12.5), box(4.5, 7, 3.5, 11.5, 11, 4.5),
			box(11.5, 7, 11.5, 12.5, 13, 12.5), box(11.5, 7, 3.5, 12.5, 13, 4.5), box(3.5, 7, 3.5, 4.5, 13, 4.5), box(3.5, 7, 11.5, 4.5, 13, 12.5), box(7.5, 11, 7.5, 8.5, 22, 8.5), box(3.5, 21, 5.5, 10.5, 27, 12.5), box(6.5, 22, 2.5, 13.5, 28, 9.5),
			box(6.5, 19, 5.5, 13.5, 25, 12.5), box(5.5, 23, 3.5, 9.5, 29, 9.5), box(4.5, 20, 4.5, 11.5, 26, 11.5));
	private static final VoxelShape SHAPE_WEST = Shapes.or(box(4.5, 0, 4.5, 11.5, 1, 11.5), box(4.5, 1, 11.5, 11.5, 7, 12.5), box(4.5, 7, 12.5, 11.5, 13, 13.5), box(4.5, 1, 3.5, 11.5, 7, 4.5), box(4.5, 7, 2.5, 11.5, 13, 3.5),
			box(11.5, 1, 4.5, 12.5, 7, 11.5), box(12.5, 7, 4.5, 13.5, 13, 11.5), box(3.5, 1, 4.5, 4.5, 7, 11.5), box(2.5, 7, 4.5, 3.5, 13, 11.5), box(3.5, 7, 4.5, 12.5, 11, 11.5), box(4.5, 7, 3.5, 11.5, 11, 4.5), box(4.5, 7, 11.5, 11.5, 11, 12.5),
			box(3.5, 7, 3.5, 4.5, 13, 4.5), box(3.5, 7, 11.5, 4.5, 13, 12.5), box(11.5, 7, 11.5, 12.5, 13, 12.5), box(11.5, 7, 3.5, 12.5, 13, 4.5), box(7.5, 11, 7.5, 8.5, 22, 8.5), box(5.5, 21, 3.5, 12.5, 27, 10.5), box(2.5, 22, 6.5, 9.5, 28, 13.5),
			box(2.5, 19, 3.5, 9.5, 25, 10.5), box(6.5, 23, 6.5, 10.5, 29, 12.5), box(4.5, 20, 4.5, 11.5, 26, 11.5));

	public PottedGreenPlantBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1.5f, 6f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
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
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@OnlyIn(Dist.CLIENT)
	public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
		event.getBlockColors().register((bs, world, pos, index) -> {
			return world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor();
		}, VoxelizedFurnitureModBlocks.POTTED_GREEN_PLANT.get());
	}

	@OnlyIn(Dist.CLIENT)
	public static void itemColorLoad(RegisterColorHandlersEvent.Item event) {
		event.getItemColors().register((stack, index) -> {
			return FoliageColor.getDefaultColor();
		}, VoxelizedFurnitureModBlocks.POTTED_GREEN_PLANT.get());
	}
}