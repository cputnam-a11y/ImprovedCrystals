package com.crystal.mixin;

import com.crystal.attributes.ModAttributes;
import com.crystal.item.EffectSpyglassItem;
import com.crystal.item.ModItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "isScoping", at = @At("HEAD"), cancellable = true)
    public void isScoping(CallbackInfoReturnable<Boolean> info) {
        if(this.isUsingItem() && this.getUseItem().is(ModItems.PRISMARINE_SPYGLASS)) {
            info.setReturnValue(true);
        }
        else if(!this.getActiveItem().is(ModItems.PRISMARINE_SPYGLASS) && !this.getActiveEffects().isEmpty())
        {
            EffectSpyglassItem.removeEffect(this, MobEffects.NIGHT_VISION.value());
        }
    }

    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> info){
                info.getReturnValue().add(ModAttributes.COOLDOWN_TIME, 5)
                .add(ModAttributes.FIRE_BREATH, 0)
                .add(ModAttributes.POTION_BREATH, 0)
                .add(ModAttributes.INSTRUMENT_SOUND_RANGE, 0)
                .add(ModAttributes.BREATH_KNOCKBACK, 1)
                .add(ModAttributes.BREATH_POWER, 10);
    }

    @WrapOperation(method = "blockUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/BlocksAttacks;disable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;FLnet/minecraft/world/item/ItemStack;)V"))
    private void blockUsingItem(BlocksAttacks instance, ServerLevel level, LivingEntity user, float baseSeconds, ItemStack blockingWith, Operation<Void> original) {
        original.call(instance, level, user, (float)user.getAttributeValue(ModAttributes.COOLDOWN_TIME), blockingWith);
    }
}
