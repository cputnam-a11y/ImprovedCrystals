package com.crystal.attributes;

import com.crystal.ImprovedCrystals;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ModAttributes {

    public static final Holder<Attribute> COOLDOWN_TIME = register("cooldown_time", 7.0, 0.0, Double.MAX_VALUE, false);
    public static final Holder<Attribute> FIRE_BREATH = register("fire_breath", 0.0, 0.0, Double.MAX_VALUE, false);
    public static final Holder<Attribute> POTION_BREATH = register("potion_breath", 0.0, 0.0, Double.MAX_VALUE, false);
    public static final Holder<Attribute> BREATH_POWER = register("breath_power", 10.0, 0.0, Double.MAX_VALUE, false);
    public static final Holder<Attribute> BREATH_KNOCKBACK = register("breath_knockback", 1, 0.0, Double.MAX_VALUE, false);
    public static final Holder<Attribute> INSTRUMENT_SOUND_RANGE = register("instrument_sound_range", 0.0, 0.0, Double.MAX_VALUE, false);
    public static final Holder<Attribute> BREATH_ATTACK_DISTANCE = register("breath_attack_distance", 0.0, 0.0, Double.MAX_VALUE, false);

    private static Holder<Attribute> register(String name, double defaultValue, double minValue, double maxValue, boolean syncedWithClient) {
        Identifier identifier = Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, name);
        Attribute entityAttribute = new RangedAttribute(
                identifier.toLanguageKey(),
                defaultValue,
                minValue,
                maxValue
        ).setSyncable(syncedWithClient);

        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, identifier, entityAttribute);
    }

    public static void initialize() {
    }
}
