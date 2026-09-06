package net.okil.voxelizedfurniture.block;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.EnumProperty;
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

import java.util.function.Function;

public class TvBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public TvBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(1.5f, 6f).requiresCorrectToolForDrops().noOcclusion().postProcess((bs, br, bp) -> bp).emissiveRendering(bs -> true).isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(17, 2, 14, 17.5, 14, 16), box(-1.5, 2, 14, -1, 14, 16), box(-1, 2, 14, 17, 2.5, 16), box(-1, 13.5, 14, 17, 14, 16), box(-1, 2.5, 15, 17, 13.5, 16), box(-1, 2.5, 14.2, 17, 13.5, 15));
				case EAST -> Shapes.or(box(0, 2, 17, 2, 14, 17.5), box(0, 2, -1.5, 2, 14, -1), box(0, 2, -1, 2, 2.5, 17), box(0, 13.5, -1, 2, 14, 17), box(0, 2.5, -1, 1, 13.5, 17), box(1, 2.5, -1, 1.8, 13.5, 17));
				case WEST -> Shapes.or(box(14, 2, -1.5, 16, 14, -1), box(14, 2, 17, 16, 14, 17.5), box(14, 2, -1, 16, 2.5, 17), box(14, 13.5, -1, 16, 14, 17), box(15, 2.5, -1, 16, 13.5, 17), box(14.2, 2.5, -1, 15, 13.5, 17));
				default -> Shapes.or(box(-1.5, 2, 0, -1, 14, 2), box(17, 2, 0, 17.5, 14, 2), box(-1, 2, 0, 17, 2.5, 2), box(-1, 13.5, 0, 17, 14, 2), box(-1, 2.5, 0, 17, 13.5, 1), box(-1, 2.5, 1, 17, 13.5, 1.8));
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
}