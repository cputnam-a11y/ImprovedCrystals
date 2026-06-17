package com.crystal.item;

import com.crystal.block.ModBlocks;
import net.fabricmc.fabric.api.registry.FuelValueEvents;

public class ModFuelItems {

    public static void initialize() {
        FuelValueEvents.BUILD.register((builder, context) -> {
            builder.add(ModBlocks.WEATHER_DETECTOR, 300);
        });
        FuelValueEvents.BUILD.register((builder, context) -> {
            builder.add(ModBlocks.MOON_DETECTOR, 300);
        });
    }

}
