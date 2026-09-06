package net.okil.voxelizedfurniture.block;

import net.okil.voxelizedfurniture.procedures.CratestatesProcedure;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.function.Function;

public class WoodenCrateBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 4);
	private final Function<BlockState, VoxelShape> shapes = this.makeShapes();

	public WoodenCrateBlock(BlockBehaviour.Properties properties) {
		super(properties.sound(SoundType.WOOD).strength(1.75f, 2.5f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(STATE, 0));
	}

	private Function<BlockState, VoxelShape> makeShapes() {
		return this.getShapeForEachState(state -> {
			if (state.getValue(STATE) == 1) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(3, 4.5, 4, 13, 6, 4.25), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(3, 0, 4, 13, 0.5, 5.5), box(3, 0, 10.5, 13, 0.5, 12),
							box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 4, 13, 2.5, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 4.5, 11.75, 13, 6, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 1, 11.75, 13, 2.5, 12),
							box(1.5, 4.5, 5.5, 1.75, 6, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5),
							box(14.25, 4.5, 5.5, 14.5, 6, 10.5), box(1.75, 0.5, 4.25, 14.25, 4.5, 11.75));
					case EAST -> Shapes.or(box(11.75, 4.5, 3, 12, 6, 13), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 3, 12, 0.5, 13), box(4, 0, 3, 5.5, 0.5, 13),
							box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(11.75, 1, 3, 12, 2.5, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(4, 4.5, 3, 4.25, 6, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(4, 1, 3, 4.25, 2.5, 13),
							box(5.5, 4.5, 1.5, 10.5, 6, 1.75), box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5),
							box(5.5, 4.5, 14.25, 10.5, 6, 14.5), box(4.25, 0.5, 1.75, 11.75, 4.5, 14.25));
					case WEST -> Shapes.or(box(4, 4.5, 3, 4.25, 6, 13), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 3, 5.5, 0.5, 13), box(10.5, 0, 3, 12, 0.5, 13),
							box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(4, 1, 3, 4.25, 2.5, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(11.75, 4.5, 3, 12, 6, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(11.75, 1, 3, 12, 2.5, 13),
							box(5.5, 4.5, 14.25, 10.5, 6, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75),
							box(5.5, 4.5, 1.5, 10.5, 6, 1.75), box(4.25, 0.5, 1.75, 11.75, 4.5, 14.25));
					default -> Shapes.or(box(3, 4.5, 11.75, 13, 6, 12), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(3, 0, 10.5, 13, 0.5, 12), box(3, 0, 4, 13, 0.5, 5.5),
							box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 11.75, 13, 2.5, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 4.5, 4, 13, 6, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 1, 4, 13, 2.5, 4.25),
							box(14.25, 4.5, 5.5, 14.5, 6, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5),
							box(1.5, 4.5, 5.5, 1.75, 6, 10.5), box(1.75, 0.5, 4.25, 14.25, 4.5, 11.75));
				};
			} else if (state.getValue(STATE) == 2) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(3, 4.5, 4, 13, 6, 4.25), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(3, 0, 4, 13, 0.5, 5.5), box(3, 0, 10.5, 13, 0.5, 12),
							box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 4, 13, 2.5, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 4.5, 11.75, 13, 6, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 1, 11.75, 13, 2.5, 12),
							box(1.5, 4.5, 5.5, 1.75, 6, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5),
							box(14.25, 4.5, 5.5, 14.5, 6, 10.5), box(1.75, 0.5, 4.25, 14.25, 4.5, 11.75));
					case EAST -> Shapes.or(box(11.75, 4.5, 3, 12, 6, 13), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 3, 12, 0.5, 13), box(4, 0, 3, 5.5, 0.5, 13),
							box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(11.75, 1, 3, 12, 2.5, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(4, 4.5, 3, 4.25, 6, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(4, 1, 3, 4.25, 2.5, 13),
							box(5.5, 4.5, 1.5, 10.5, 6, 1.75), box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5),
							box(5.5, 4.5, 14.25, 10.5, 6, 14.5), box(4.25, 0.5, 1.75, 11.75, 4.5, 14.25));
					case WEST -> Shapes.or(box(4, 4.5, 3, 4.25, 6, 13), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 3, 5.5, 0.5, 13), box(10.5, 0, 3, 12, 0.5, 13),
							box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(4, 1, 3, 4.25, 2.5, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(11.75, 4.5, 3, 12, 6, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(11.75, 1, 3, 12, 2.5, 13),
							box(5.5, 4.5, 14.25, 10.5, 6, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75),
							box(5.5, 4.5, 1.5, 10.5, 6, 1.75), box(4.25, 0.5, 1.75, 11.75, 4.5, 14.25));
					default -> Shapes.or(box(3, 4.5, 11.75, 13, 6, 12), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(3, 0, 10.5, 13, 0.5, 12), box(3, 0, 4, 13, 0.5, 5.5),
							box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 11.75, 13, 2.5, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 4.5, 4, 13, 6, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 1, 4, 13, 2.5, 4.25),
							box(14.25, 4.5, 5.5, 14.5, 6, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5),
							box(1.5, 4.5, 5.5, 1.75, 6, 10.5), box(1.75, 0.5, 4.25, 14.25, 4.5, 11.75));
				};
			} else if (state.getValue(STATE) == 3) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(3, 4.5, 4, 13, 6, 4.25), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(3, 0, 4, 13, 0.5, 5.5), box(3, 0, 10.5, 13, 0.5, 12),
							box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 4, 13, 2.5, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 4.5, 11.75, 13, 6, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 1, 11.75, 13, 2.5, 12),
							box(1.5, 4.5, 5.5, 1.75, 6, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5),
							box(14.25, 4.5, 5.5, 14.5, 6, 10.5), box(1.75, 0.5, 4.25, 14.25, 4.5, 11.75), box(3, 4.5, 6, 5, 12.5, 8), box(11, 4.5, 7, 13, 11.5, 9), box(6, 4.5, 9, 8, 9.5, 11), box(8, 4.5, 5, 10, 10.5, 7));
					case EAST -> Shapes.or(box(11.75, 4.5, 3, 12, 6, 13), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 3, 12, 0.5, 13), box(4, 0, 3, 5.5, 0.5, 13),
							box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(11.75, 1, 3, 12, 2.5, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(4, 4.5, 3, 4.25, 6, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(4, 1, 3, 4.25, 2.5, 13),
							box(5.5, 4.5, 1.5, 10.5, 6, 1.75), box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5),
							box(5.5, 4.5, 14.25, 10.5, 6, 14.5), box(4.25, 0.5, 1.75, 11.75, 4.5, 14.25), box(8, 4.5, 3, 10, 12.5, 5), box(7, 4.5, 11, 9, 11.5, 13), box(5, 4.5, 6, 7, 9.5, 8), box(9, 4.5, 8, 11, 10.5, 10));
					case WEST -> Shapes.or(box(4, 4.5, 3, 4.25, 6, 13), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 3, 5.5, 0.5, 13), box(10.5, 0, 3, 12, 0.5, 13),
							box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(4, 1, 3, 4.25, 2.5, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(11.75, 4.5, 3, 12, 6, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(11.75, 1, 3, 12, 2.5, 13),
							box(5.5, 4.5, 14.25, 10.5, 6, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75),
							box(5.5, 4.5, 1.5, 10.5, 6, 1.75), box(4.25, 0.5, 1.75, 11.75, 4.5, 14.25), box(6, 4.5, 11, 8, 12.5, 13), box(7, 4.5, 3, 9, 11.5, 5), box(9, 4.5, 8, 11, 9.5, 10), box(5, 4.5, 6, 7, 10.5, 8));
					default -> Shapes.or(box(3, 4.5, 11.75, 13, 6, 12), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(3, 0, 10.5, 13, 0.5, 12), box(3, 0, 4, 13, 0.5, 5.5),
							box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 11.75, 13, 2.5, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 4.5, 4, 13, 6, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 1, 4, 13, 2.5, 4.25),
							box(14.25, 4.5, 5.5, 14.5, 6, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5),
							box(1.5, 4.5, 5.5, 1.75, 6, 10.5), box(1.75, 0.5, 4.25, 14.25, 4.5, 11.75), box(11, 4.5, 8, 13, 12.5, 10), box(3, 4.5, 7, 5, 11.5, 9), box(8, 4.5, 5, 10, 9.5, 7), box(6, 4.5, 9, 8, 10.5, 11));
				};
			} else if (state.getValue(STATE) == 4) {
				return switch (state.getValue(FACING)) {
					case NORTH -> Shapes.or(box(3, 4.5, 4, 13, 6, 4.25), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(3, 0, 4, 13, 0.5, 5.5), box(3, 0, 10.5, 13, 0.5, 12),
							box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 4, 13, 2.5, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 4.5, 11.75, 13, 6, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 1, 11.75, 13, 2.5, 12),
							box(1.5, 4.5, 5.5, 1.75, 6, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5),
							box(14.25, 4.5, 5.5, 14.5, 6, 10.5), box(1.75, 0.5, 4.25, 14.25, 4.5, 11.75), box(3, 4.5, 6, 5, 12.5, 8), box(11, 4.5, 7, 13, 11.5, 9), box(6, 4.5, 9, 8, 9.5, 11), box(8, 4.5, 5, 10, 10.5, 7));
					case EAST -> Shapes.or(box(11.75, 4.5, 3, 12, 6, 13), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 3, 12, 0.5, 13), box(4, 0, 3, 5.5, 0.5, 13),
							box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(11.75, 1, 3, 12, 2.5, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(4, 4.5, 3, 4.25, 6, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(4, 1, 3, 4.25, 2.5, 13),
							box(5.5, 4.5, 1.5, 10.5, 6, 1.75), box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5),
							box(5.5, 4.5, 14.25, 10.5, 6, 14.5), box(4.25, 0.5, 1.75, 11.75, 4.5, 14.25), box(8, 4.5, 3, 10, 12.5, 5), box(7, 4.5, 11, 9, 11.5, 13), box(5, 4.5, 6, 7, 9.5, 8), box(9, 4.5, 8, 11, 10.5, 10));
					case WEST -> Shapes.or(box(4, 4.5, 3, 4.25, 6, 13), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 3, 5.5, 0.5, 13), box(10.5, 0, 3, 12, 0.5, 13),
							box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(4, 1, 3, 4.25, 2.5, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(11.75, 4.5, 3, 12, 6, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(11.75, 1, 3, 12, 2.5, 13),
							box(5.5, 4.5, 14.25, 10.5, 6, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75),
							box(5.5, 4.5, 1.5, 10.5, 6, 1.75), box(4.25, 0.5, 1.75, 11.75, 4.5, 14.25), box(6, 4.5, 11, 8, 12.5, 13), box(7, 4.5, 3, 9, 11.5, 5), box(9, 4.5, 8, 11, 9.5, 10), box(5, 4.5, 6, 7, 10.5, 8));
					default -> Shapes.or(box(3, 4.5, 11.75, 13, 6, 12), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(3, 0, 10.5, 13, 0.5, 12), box(3, 0, 4, 13, 0.5, 5.5),
							box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 11.75, 13, 2.5, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 4.5, 4, 13, 6, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 1, 4, 13, 2.5, 4.25),
							box(14.25, 4.5, 5.5, 14.5, 6, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5),
							box(1.5, 4.5, 5.5, 1.75, 6, 10.5), box(1.75, 0.5, 4.25, 14.25, 4.5, 11.75), box(11, 4.5, 8, 13, 12.5, 10), box(3, 4.5, 7, 5, 11.5, 9), box(8, 4.5, 5, 10, 9.5, 7), box(6, 4.5, 9, 8, 10.5, 11));
				};
			}
			return switch (state.getValue(FACING)) {
				case NORTH -> Shapes.or(box(3, 4.5, 4, 13, 6, 4.25), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(3, 0, 4, 13, 0.5, 5.5), box(3, 0, 10.5, 13, 0.5, 12),
						box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 4, 13, 2.5, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 4.5, 11.75, 13, 6, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 1, 11.75, 13, 2.5, 12),
						box(1.5, 4.5, 5.5, 1.75, 6, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5), box(14.25, 4.5, 5.5, 14.5, 6, 10.5));
				case EAST -> Shapes.or(box(11.75, 4.5, 3, 12, 6, 13), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 3, 12, 0.5, 13), box(4, 0, 3, 5.5, 0.5, 13),
						box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(11.75, 1, 3, 12, 2.5, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(4, 4.5, 3, 4.25, 6, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(4, 1, 3, 4.25, 2.5, 13), box(5.5, 4.5, 1.5, 10.5, 6, 1.75),
						box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5), box(5.5, 4.5, 14.25, 10.5, 6, 14.5));
				case WEST -> Shapes.or(box(4, 4.5, 3, 4.25, 6, 13), box(4, 0, 13, 5.5, 6, 14.5), box(4, 0, 1.5, 5.5, 6, 3), box(10.5, 0, 1.5, 12, 6, 3), box(10.5, 0, 13, 12, 6, 14.5), box(4, 0, 3, 5.5, 0.5, 13), box(10.5, 0, 3, 12, 0.5, 13),
						box(5.5, 0, 1.5, 10.5, 0.5, 14.5), box(4, 1, 3, 4.25, 2.5, 13), box(4, 2.75, 3, 4.25, 4.25, 13), box(11.75, 4.5, 3, 12, 6, 13), box(11.75, 2.75, 3, 12, 4.25, 13), box(11.75, 1, 3, 12, 2.5, 13),
						box(5.5, 4.5, 14.25, 10.5, 6, 14.5), box(5.5, 2.75, 14.25, 10.5, 4.25, 14.5), box(5.5, 1, 14.25, 10.5, 2.5, 14.5), box(5.5, 1, 1.5, 10.5, 2.5, 1.75), box(5.5, 2.75, 1.5, 10.5, 4.25, 1.75), box(5.5, 4.5, 1.5, 10.5, 6, 1.75));
				default -> Shapes.or(box(3, 4.5, 11.75, 13, 6, 12), box(13, 0, 10.5, 14.5, 6, 12), box(1.5, 0, 10.5, 3, 6, 12), box(1.5, 0, 4, 3, 6, 5.5), box(13, 0, 4, 14.5, 6, 5.5), box(3, 0, 10.5, 13, 0.5, 12), box(3, 0, 4, 13, 0.5, 5.5),
						box(1.5, 0, 5.5, 14.5, 0.5, 10.5), box(3, 1, 11.75, 13, 2.5, 12), box(3, 2.75, 11.75, 13, 4.25, 12), box(3, 4.5, 4, 13, 6, 4.25), box(3, 2.75, 4, 13, 4.25, 4.25), box(3, 1, 4, 13, 2.5, 4.25),
						box(14.25, 4.5, 5.5, 14.5, 6, 10.5), box(14.25, 2.75, 5.5, 14.5, 4.25, 10.5), box(14.25, 1, 5.5, 14.5, 2.5, 10.5), box(1.5, 1, 5.5, 1.75, 2.5, 10.5), box(1.5, 2.75, 5.5, 1.75, 4.25, 10.5), box(1.5, 4.5, 5.5, 1.75, 6, 10.5));
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
		builder.add(FACING, STATE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state == null)
			return null;
		return state.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(STATE, 0);
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
		CratestatesProcedure.execute(world, x, y, z, entity);
		return InteractionResult.SUCCESS;
	}
}