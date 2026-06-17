package com.crystal.gamerules;

import com.crystal.ImprovedCrystals;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class ModGameRules implements ModInitializer {

    public static final GameRule<Boolean> SPAWN_MOBS_FROM_PROJECTILES = GameRuleBuilder
            .forBoolean(true)
            .category(GameRuleCategory.SPAWNING)
            .buildAndRegister(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "spawn_mobs_from_projectiles"));

    public static final GameRule<Integer> ENDERMITE_SPAWN_CHANCE = GameRuleBuilder
            .forInteger(5).range(0, 100)
            .category(GameRuleCategory.SPAWNING)
            .buildAndRegister(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "endermite_spawn_chance"));

    @Override
    public void onInitialize() {

    }

    public static void register(){

    }
}
