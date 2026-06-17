package com.crystal.mixin;

import com.crystal.gamerules.ModGameRules;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEgg;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrownEgg.class)
public abstract class ThrownEggMixin extends ThrowableItemProjectile {

    public ThrownEggMixin(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    @Definition(id = "random", field = "Lnet/minecraft/world/entity/projectile/throwableitemprojectile/ThrownEgg;random:Lnet/minecraft/util/RandomSource;")
    @Definition(id = "nextInt", method = "Lnet/minecraft/util/RandomSource;nextInt(I)I")
    @Expression("this.random.nextInt(8) == 0")
    @ModifyExpressionValue(
            method = "onHit",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private boolean cancelChickens(boolean original) {
        if (this.level() instanceof ServerLevel serverLevel && !serverLevel.getGameRules().get(ModGameRules.SPAWN_MOBS_FROM_PROJECTILES)) {
            return false;
        }
        return original;
    }
}
