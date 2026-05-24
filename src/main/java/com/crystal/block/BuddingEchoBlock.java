package com.crystal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class BuddingEchoBlock extends BuddingAmethystBlock {
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingEchoBlock(Properties properties) {
        super(properties);
    }


    protected void randomTick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        if (random.nextInt(10) == 0) {
            Direction growDirection = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos growPos = pos.relative(growDirection);
            BlockState relativeState = level.getBlockState(growPos);
            Block nextStage = null;
            if (canClusterGrowAtState(relativeState)) {
                nextStage = ModBlocks.SMALL_ECHO_BUD;
            } else if (relativeState.is(ModBlocks.SMALL_ECHO_BUD) && relativeState.getValue(AmethystClusterBlock.FACING) == growDirection) {
                nextStage = ModBlocks.MEDIUM_ECHO_BUD;
            } else if (relativeState.is(ModBlocks.MEDIUM_ECHO_BUD) && relativeState.getValue(AmethystClusterBlock.FACING) == growDirection) {
                nextStage = ModBlocks.LARGE_ECHO_BUD;
            } else if (relativeState.is(ModBlocks.LARGE_ECHO_BUD) && relativeState.getValue(AmethystClusterBlock.FACING) == growDirection) {
                nextStage = ModBlocks.ECHO_CLUSTER;
            }

            if (nextStage != null) {
                BlockState targetState = (BlockState)((BlockState)nextStage.defaultBlockState().setValue(AmethystClusterBlock.FACING, growDirection)).setValue(AmethystClusterBlock.WATERLOGGED, relativeState.getFluidState().is(Fluids.WATER));
                level.setBlockAndUpdate(growPos, targetState);
            }

        }
    }
}
