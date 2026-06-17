package com.crystal.mixin;

import com.crystal.gamerules.ModGameRules;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrownEnderpearl.class)
public abstract class ThrownEnderpearlMixin extends ThrowableItemProjectile {

    public ThrownEnderpearlMixin(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    @Definition(id = "random", field = "Lnet/minecraft/world/entity/projectile/throwableitemprojectile/ThrownEnderpearl;random:Lnet/minecraft/util/RandomSource;")
    @Definition(id = "nextFloat", method = "Lnet/minecraft/util/RandomSource;nextFloat()F")
    @Expression("this.random.nextFloat() < @(0.05)")
    @ModifyExpressionValue(
            method = "onHit",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private float modifySpawnChance(float original, @Local ServerLevel level) {
        return (float)level.getGameRules().get(ModGameRules.ENDERMITE_SPAWN_CHANCE) / 100;
    }

    @Definition(id = "level", local = @Local(type = ServerLevel.class, name = "level"))
    @Definition(id = "getLevelData", method = "Lnet/minecraft/server/level/ServerLevel;getLevelData()Lnet/minecraft/world/level/storage/LevelData;")
    @Definition(id = "getDifficulty", method = "Lnet/minecraft/world/level/storage/LevelData;getDifficulty()Lnet/minecraft/world/Difficulty;")
    @Definition(id = "PEACEFUL", field = "Lnet/minecraft/world/Difficulty;PEACEFUL:Lnet/minecraft/world/Difficulty;")
    @Expression("level.getLevelData().getDifficulty() != PEACEFUL")
    @ModifyExpressionValue(
            method = "onHit",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private boolean modifySpawnAllowed(boolean original, @Local ServerLevel level) {
        return original && level.getGameRules().get(ModGameRules.SPAWN_MOBS_FROM_PROJECTILES);
    }
}
