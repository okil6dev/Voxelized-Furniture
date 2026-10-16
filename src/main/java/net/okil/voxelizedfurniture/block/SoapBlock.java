package net.okil.voxelizedfurniture.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import com.google.common.collect.ImmutableMap;

public class SoapBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private final ImmutableMap<BlockState, VoxelShape> shapes = this.makeShapes();

	public SoapBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(0f, 10f).noCollission().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private ImmutableMap<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(11.5, 4.45, 12.5, 12.5, 5.45, 14.5), box(10.5, 0, 13, 13.5, 4, 15), box(11.25, 3.2, 13.25, 12.75, 4.2, 14.75), box(11.5, 3.45, 13.5, 12.5, 4.45, 14.5));
				case EAST -> Shapes.or(box(1.5, 4.45, 11.5, 3.5, 5.45, 12.5), box(1, 0, 10.5, 3, 4, 13.5), box(1.25, 3.2, 11.25, 2.75, 4.2, 12.75), box(1.5, 3.45, 11.5, 2.5, 4.45, 12.5));
				case WEST -> Shapes.or(box(12.5, 4.45, 3.5, 14.5, 5.45, 4.5), box(13, 0, 2.5, 15, 4, 5.5), box(13.25, 3.2, 3.25, 14.75, 4.2, 4.75), box(13.5, 3.45, 3.5, 14.5, 4.45, 4.5));
				default -> Shapes.or(box(3.5, 4.45, 1.5, 4.5, 5.45, 3.5), box(2.5, 0, 1, 5.5, 4, 3), box(3.25, 3.2, 1.25, 4.75, 4.2, 2.75), box(3.5, 3.45, 1.5, 4.5, 4.45, 2.5));
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
}