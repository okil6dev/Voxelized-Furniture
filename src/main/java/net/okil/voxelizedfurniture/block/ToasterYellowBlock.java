package net.okil.voxelizedfurniture.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class ToasterYellowBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public ToasterYellowBlock() {
		super(BlockBehaviour.Properties.of().strength(2.5f, 4f).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(3, 0, 5, 13, 1, 11), box(4, 1, 5, 12, 7, 6), box(3.5, 1, 7, 12, 7, 9), box(12, 1, 5, 13, 7, 11), box(3, 1, 9, 4, 7, 11), box(3.45, 3, 7.5, 3.5, 7, 8.5), box(2.25, 5, 7, 4, 6, 9),
						box(2.5, 1.25, 7.5, 3.5, 2.25, 8.5), box(3, 1, 7, 4, 3, 9), box(3, 1, 5, 4, 7, 7), box(4, 1, 10, 12, 7, 11));
				case EAST -> Shapes.or(box(5, 0, 3, 11, 1, 13), box(10, 1, 4, 11, 7, 12), box(7, 1, 3.5, 9, 7, 12), box(5, 1, 12, 11, 7, 13), box(5, 1, 3, 7, 7, 4), box(7.5, 3, 3.45, 8.5, 7, 3.5), box(7, 5, 2.25, 9, 6, 4),
						box(7.5, 1.25, 2.5, 8.5, 2.25, 3.5), box(7, 1, 3, 9, 3, 4), box(9, 1, 3, 11, 7, 4), box(5, 1, 4, 6, 7, 12));
				case WEST -> Shapes.or(box(5, 0, 3, 11, 1, 13), box(5, 1, 4, 6, 7, 12), box(7, 1, 4, 9, 7, 12.5), box(5, 1, 3, 11, 7, 4), box(9, 1, 12, 11, 7, 13), box(7.5, 3, 12.5, 8.5, 7, 12.55), box(7, 5, 12, 9, 6, 13.75),
						box(7.5, 1.25, 12.5, 8.5, 2.25, 13.5), box(7, 1, 12, 9, 3, 13), box(5, 1, 12, 7, 7, 13), box(10, 1, 4, 11, 7, 12));
				default -> Shapes.or(box(3, 0, 5, 13, 1, 11), box(4, 1, 10, 12, 7, 11), box(4, 1, 7, 12.5, 7, 9), box(3, 1, 5, 4, 7, 11), box(12, 1, 5, 13, 7, 7), box(12.5, 3, 7.5, 12.55, 7, 8.5), box(12, 5, 7, 13.75, 6, 9),
						box(12.5, 1.25, 7.5, 13.5, 2.25, 8.5), box(12, 1, 7, 13, 3, 9), box(12, 1, 9, 13, 7, 11), box(4, 1, 5, 12, 7, 6));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
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
}