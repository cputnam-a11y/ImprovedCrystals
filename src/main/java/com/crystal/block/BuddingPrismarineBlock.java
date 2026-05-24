package com.crystal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.material.Fluids;

public class BuddingPrismarineBlock extends Block {
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingPrismarineBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (rand.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[rand.nextInt(DIRECTIONS.length)];
            BlockPos blockpos = pos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            Block block = null;
            if (canClusterGrowAtState(blockstate)) {
                block = ModBlocks.SMALL_PRISMARINE_CRYSTAL_BUD;
            } else if (blockstate.is(ModBlocks.SMALL_PRISMARINE_CRYSTAL_BUD) && blockstate.getValue(PrismarineClusterBlock.FACING) == direction) {
                block = ModBlocks.MEDIUM_PRISMARINE_CRYSTAL_BUD;
            } else if (blockstate.is(ModBlocks.MEDIUM_PRISMARINE_CRYSTAL_BUD) && blockstate.getValue(PrismarineClusterBlock.FACING) == direction) {
                block = ModBlocks.LARGE_PRISMARINE_CRYSTAL_BUD;
            } else if (blockstate.is(ModBlocks.LARGE_PRISMARINE_CRYSTAL_BUD) && blockstate.getValue(PrismarineClusterBlock.FACING) == direction) {
                block = ModBlocks.PRISMARINE_CRYSTAL_CLUSTER;
            }

            if (block != null) {
                boolean waterlogged = blockstate.getFluidState().getType() == Fluids.WATER;
                Direction dir = direction;
                AttachFace af = AttachFace.WALL;

                if(direction == Direction.UP)
                {
                    dir = Direction.NORTH;
                    af = AttachFace.FLOOR;
                }
                else if(direction == Direction.DOWN)
                {
                    dir = Direction.NORTH;
                    af = AttachFace.CEILING;
                }


                BlockState blockstate1 = block.defaultBlockState()
                        .setValue(PrismarineClusterBlock.WATERLOGGED, waterlogged)
                        .setValue(PrismarineClusterBlock.FACING, dir)
                        .setValue(PrismarineClusterBlock.FACE, af);

                level.setBlockAndUpdate(blockpos, blockstate1);
            }
        }
    }

    public static boolean canClusterGrowAtState(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }
}
