package com.crystal.mixin;

import com.crystal.gamerules.ModGameRules;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEgg;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin extends ThrowableItemProjectile {

    public ThrownEnderpearlMixin(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    private static boolean canToTeleportOwner(final Entity owner, final Level newLevel) {
        if (owner.level().dimension() == newLevel.dimension()) {
            if (!(owner instanceof LivingEntity)) {
                return owner.isAlive();
            } else {
                LivingEntity livingOwner = (LivingEntity)owner;
                return livingOwner.isAlive() && !livingOwner.isSleeping();
            }
        } else {
            return owner.canUsePortal(true);
        }
    }

    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void onHit(HitResult hitResult, CallbackInfo info){
        super.onHit(hitResult);

        for(int i = 0; i < 32; ++i) {
            this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * (double)2.0F, this.getZ(), this.random.nextGaussian(), (double)0.0F, this.random.nextGaussian());
        }

        Level var3 = this.level();
        if (var3 instanceof ServerLevel level) {
            if (!this.isRemoved()) {
                Entity owner = this.getOwner();
                if (owner != null && canToTeleportOwner(owner, level)) {
                    Vec3 teleportPos = this.oldPosition();
                    if (owner instanceof ServerPlayer) {
                        ServerPlayer player = (ServerPlayer)owner;
                        if (player.connection.isAcceptingMessages()) {
                            if (this.random.nextFloat() < (float) level.getGameRules().get(ModGameRules.ENDERMITE_SPAWN_CHANCE) / 100 && level.isSpawningMonsters() && level.getGameRules().get(ModGameRules.SPAWN_MOBS_FROM_PROJECTILES)) {
                                Endermite endermite = (Endermite)EntityTypes.ENDERMITE.create(level, EntitySpawnReason.TRIGGERED);
                                if (endermite != null) {
                                    endermite.snapTo(owner.getX(), owner.getY(), owner.getZ(), owner.getYRot(), owner.getXRot());
                                    level.addFreshEntity(endermite);
                                }
                            }

                            if (this.isOnPortalCooldown()) {
                                owner.setPortalCooldown();
                            }

                            ServerPlayer newOwner = player.teleport(new TeleportTransition(level, teleportPos, Vec3.ZERO, 0.0F, 0.0F, Relative.union(new Set[]{Relative.ROTATION, Relative.DELTA}), TeleportTransition.DO_NOTHING));
                            if (newOwner != null) {
                                newOwner.resetFallDistance();
                                newOwner.resetCurrentImpulseContext();
                                newOwner.hurtServer(player.level(), this.damageSources().enderPearl(), 5.0F);
                            }

                            this.playTeleportSound(level, teleportPos);
                        }
                    } else {
                        Entity newOwner = owner.teleport(new TeleportTransition(level, teleportPos, owner.getDeltaMovement(), owner.getYRot(), owner.getXRot(), TeleportTransition.DO_NOTHING));
                        if (newOwner != null) {
                            newOwner.resetFallDistance();
                        }

                        if (newOwner instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity)newOwner;
                            livingEntity.resetCurrentImpulseContext();
                        }

                        this.playTeleportSound(level, teleportPos);
                    }

                    this.discard();
                    info.cancel();
                }

                this.discard();
                info.cancel();
            }
        }
    }

    private void playTeleportSound(final Level level, final Vec3 position) {
        level.playSound((Entity)null, position.x, position.y, position.z, SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS);
    }

    @Overwrite
    public Item getDefaultItem() {
        return Items.ENDER_PEARL;
    }
}
