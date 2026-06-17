package com.crystal.util;

import net.minecraft.util.StringRepresentable;

public enum WeatherDetectionCycleEnum implements StringRepresentable {
    CLEAR("clear"),
    RAIN("rain"),
    THUNDER("thunder");

    private final String name;

    WeatherDetectionCycleEnum(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getSerializedName();
    }

    public String getSerializedName() {
        return this.name;
    }
}
