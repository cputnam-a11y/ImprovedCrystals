package com.crystal.mixin;

import com.crystal.attributes.ModAttributes;
import com.crystal.item.EffectSpyglassItem;
import com.crystal.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Inject(method = "isScoping", at = @At("HEAD"), cancellable = true)
    public void isScoping(CallbackInfoReturnable info) {
        if(this.isUsingItem() && this.getUseItem().is(ModItems.PRISMARINE_SPYGLASS)) {
            info.setReturnValue(true);
        }
        else if(!this.getActiveItem().is(ModItems.PRISMARINE_SPYGLASS) && !this.getActiveEffects().isEmpty())
        {
            EffectSpyglassItem.removeEffect(this, MobEffects.NIGHT_VISION.value());
        }
    }

    @Inject(method = "createAttributes", at = @At("HEAD"), cancellable = true)
    private static void createAttributes(CallbackInfoReturnable info){
        var att = LivingEntity.createLivingAttributes().add(ModAttributes.BREATH_ATTACK_DISTANCE, 0F).add(Attributes.ATTACK_DAMAGE, (double)1.0F).add(Attributes.MOVEMENT_SPEED, (double)0.1F).add(Attributes.ATTACK_SPEED).add(Attributes.LUCK).add(Attributes.BLOCK_INTERACTION_RANGE).add(Attributes.BLOCK_BREAK_SPEED).add(Attributes.SUBMERGED_MINING_SPEED).add(Attributes.SNEAKING_SPEED).add(Attributes.MINING_EFFICIENCY).add(Attributes.SWEEPING_DAMAGE_RATIO).add(Attributes.WAYPOINT_TRANSMIT_RANGE, (double)6.0E7F).add(Attributes.WAYPOINT_RECEIVE_RANGE, (double)6.0E7F).add(ModAttributes.COOLDOWN_TIME, 5).add(ModAttributes.FIRE_BREATH, 0).add(ModAttributes.POTION_BREATH, 0).add(ModAttributes.INSTRUMENT_SOUND_RANGE, 0).add(ModAttributes.BREATH_KNOCKBACK, 1).add(ModAttributes.BREATH_POWER, 10);
        info.setReturnValue(att);
    }

    @Inject(method = "blockUsingItem", at = @At("HEAD"), cancellable = true)
    private void blockUsingItem(final ServerLevel level, final LivingEntity attacker, final DamageSource source, final float damage, CallbackInfo info) {
        super.blockUsingItem(level, attacker, source, damage);
        ItemStack itemBlockingWith = this.getItemBlockingWith();
        BlocksAttacks blocksAttacks = itemBlockingWith != null ? (BlocksAttacks)itemBlockingWith.get(DataComponents.BLOCKS_ATTACKS) : null;
        float secondsToDisableBlocking = attacker.getSecondsToDisableBlocking();

        if (secondsToDisableBlocking > 0.0F && blocksAttacks != null) {
            Holder<Attribute> cooldown = ModAttributes.COOLDOWN_TIME;
            blocksAttacks.disable(level, this, (float)this.getAttributeValue(cooldown), itemBlockingWith);
            info.cancel();
        }

    }
}
