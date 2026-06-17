package com.crystal.block.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WeatherDetectorBlockEntity extends BlockEntity {

    public WeatherDetectorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntityTypes.WEATHER_DETECTOR_ENTITY, worldPosition, blockState);
    }
}
