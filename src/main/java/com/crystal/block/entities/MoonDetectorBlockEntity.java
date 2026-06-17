package com.crystal.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MoonDetectorBlockEntity extends BlockEntity {

    public MoonDetectorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntityTypes.MOON_DETECTOR_ENTITY, worldPosition, blockState);
    }
}
