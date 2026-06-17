package com.crystal.entity;

import com.crystal.ImprovedCrystals;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEgg;

public class ModEntityTypes {

    public static final EntityType<ThrownSilverfishEgg> SILVERFISH_EGG = register("sliverfish_egg", EntityType.Builder.<ThrownSilverfishEgg>of(ThrownSilverfishEgg::new, MobCategory.MISC).noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntityTypes() {
        ImprovedCrystals.LOGGER.info("Registering EntityTypes for " + ImprovedCrystals.MOD_ID);
    }

}
