package com.crystal.entity;

import com.crystal.gamerules.ModGameRules;
import com.crystal.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Objects;
import java.util.Optional;

public class ThrownSilverfishEgg extends ThrowableItemProjectile {
    private static final EntityDimensions ZERO_SIZED_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);

    public ThrownSilverfishEgg(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public ThrownSilverfishEgg(final Level level, final LivingEntity mob, final ItemStack itemStack) {
        super(ModEntityTypes.SILVERFISH_EGG, mob, level, itemStack);
    }

    public ThrownSilverfishEgg(final Level level, final double x, final double y, final double z, final ItemStack itemStack) {
        super(ModEntityTypes.SILVERFISH_EGG, x, y, z, level, itemStack);
    }

    public void handleEntityEvent(final byte id) {
        if (id == 3) {
            ItemStack item = this.getItem();
            if (!item.isEmpty()) {
                ItemParticleOption breakParticle = new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item));

                for(int i = 0; i < 8; ++i) {
                    this.level().addParticle(breakParticle, this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08);
                }
            }
        }

    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        BlockState state = this.level().getBlockState(hitResult.getBlockPos());

        if(!this.level().isClientSide() && this.level() instanceof ServerLevel sl && sl.getGameRules().get(GameRules.MOB_GRIEFING) && InfestedBlock.isCompatibleHostBlock(state))
        {
            this.level().setBlock(hitResult.getBlockPos(), InfestedBlock.infestedStateByHost(state), 3);
        }

        super.onHitBlock(hitResult);
    }

    protected void onHitEntity(final EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        hitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);

        if(hitResult.getEntity() instanceof LivingEntity le)
        {
            var effect = new MobEffectInstance(MobEffects.INFESTED, 180 * 20);
            le.addEffect(effect, this);
        }
    }

    protected void onHit(final HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide() && this.level() instanceof ServerLevel sl && sl.isSpawningMonsters() && sl.getGameRules().get(ModGameRules.SPAWN_MOBS_FROM_PROJECTILES)) {
            Silverfish silverfish = (Silverfish) EntityTypes.SILVERFISH.create(this.level(), EntitySpawnReason.TRIGGERED);
            if (silverfish != null) {
                silverfish.snapTo(this.oldPosition().x, this.oldPosition().y, this.oldPosition().z, this.getYRot(), 0.0F);
                Objects.requireNonNull(silverfish);

                this.level().addFreshEntity(silverfish);
            }
        }

        this.level().broadcastEntityEvent(this, (byte)3);
        this.discard();

    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SILVERFISH_EGG;
    }
}
