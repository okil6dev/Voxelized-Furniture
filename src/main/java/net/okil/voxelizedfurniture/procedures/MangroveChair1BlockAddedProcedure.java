package net.okil.voxelizedfurniture.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

public class MangroveChair1BlockAddedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (Direction.EAST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3((0.5 + x), y, (0.5 + z)), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						"/summon voxelized_furniture:mangrove_chair ~ ~ ~ {Rotation:[-90.0f, 0.0f]}");
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		} else if (Direction.SOUTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3((0.5 + x), y, (0.5 + z)), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						"/summon voxelized_furniture:mangrove_chair ~ ~ ~ {Rotation:[0.0f, 0.0f]}");
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		} else if (Direction.WEST == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3((0.5 + x), y, (0.5 + z)), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						"/summon voxelized_furniture:mangrove_chair ~ ~ ~ {Rotation:[90.0f, 0.0f]}");
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		} else if (Direction.NORTH == (getDirectionFromBlockState((world.getBlockState(BlockPos.containing(x, y, z)))))) {
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3((0.5 + x), y, (0.5 + z)), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
						"/summon voxelized_furniture:mangrove_chair ~ ~ ~ {Rotation:[180.0f, 0.0f]}");
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		}
	}

	private static Direction getDirectionFromBlockState(BlockState blockState) {
		if (blockState.getBlock().getStateDefinition().getProperty("facing") instanceof EnumProperty ep && ep.getValueClass() == Direction.class)
			return (Direction) blockState.getValue(ep);
		if (blockState.getBlock().getStateDefinition().getProperty("axis") instanceof EnumProperty ep && ep.getValueClass() == Direction.Axis.class)
			return Direction.fromAxisAndDirection((Direction.Axis) blockState.getValue(ep), Direction.AxisDirection.POSITIVE);
		return Direction.NORTH;
	}
}