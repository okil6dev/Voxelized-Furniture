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

public class DiamondGeodeBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public DiamondGeodeBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(3f, 5f).requiresCorrectToolForDrops().noOcclusion().postProcess((bs, br, bp) -> bp).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false).dynamicShape().offsetType(Block.OffsetType.XZ));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(1, 0, 9, 15, 9, 15), box(1, 0, 2, 8, 9, 9));
				case EAST -> Shapes.or(box(1, 0, 1, 7, 9, 15), box(7, 0, 1, 14, 9, 8));
				case WEST -> Shapes.or(box(9, 0, 1, 15, 9, 15), box(2, 0, 8, 9, 9, 15));
				default -> Shapes.or(box(1, 0, 1, 15, 9, 7), box(8, 0, 7, 15, 9, 14));
			};
		});
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.apply(state).move(state.getOffset(pos));
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