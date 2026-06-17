package com.crystal.block.entities;

import com.crystal.ImprovedCrystals;
import com.crystal.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {
    public static final BlockEntityType<EchoRodBlockEntity> ECHO_ROD_ENTITY =
            register("echo_rod", EchoRodBlockEntity::new, ModBlocks.ECHO_ROD);

    public static final BlockEntityType<WeatherDetectorBlockEntity> WEATHER_DETECTOR_ENTITY =
            register("weather_detector", WeatherDetectorBlockEntity::new, ModBlocks.WEATHER_DETECTOR);

    public static final BlockEntityType<MoonDetectorBlockEntity> MOON_DETECTOR_ENTITY =
            register("moon_detector", MoonDetectorBlockEntity::new, ModBlocks.MOON_DETECTOR);

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block blocks) {
        Identifier id = Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void initalize(){};
}
