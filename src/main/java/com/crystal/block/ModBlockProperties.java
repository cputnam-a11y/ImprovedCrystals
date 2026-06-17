package com.crystal.block;

import com.crystal.util.WeatherDetectionCycleEnum;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockProperties {
    public static BooleanProperty IN_SNOW_BIOME = BooleanProperty.create("in_snow_biome");
    public static BooleanProperty IN_HOT_BIOME = BooleanProperty.create("in_hot_biome");
    public static BooleanProperty IN_VALID_BIOME = BooleanProperty.create("in_valid_biome");
    public static BooleanProperty IN_VALID_DIMENSION = BooleanProperty.create("in_valid_dimension");
    public static EnumProperty<WeatherDetectionCycleEnum> WEATHER_DETECTION_CYCLE = EnumProperty.create("weather_detection_cycle", WeatherDetectionCycleEnum.class);
    public static EnumProperty<MoonPhase> MOON_PHASE = EnumProperty.create("moon_phase", MoonPhase.class);

}
