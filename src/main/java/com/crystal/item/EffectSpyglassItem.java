package com.crystal.item;

import com.crystal.ImprovedCrystals;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class EffectSpyglassItem extends SpyglassItem {
    public Holder<MobEffect> effectToApply;

    public EffectSpyglassItem(Properties properties, Holder<MobEffect> effectToApply) {
        super(properties);
        this.effectToApply = effectToApply;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.addEffect(new MobEffectInstance(effectToApply, -1, 0, true, false, false));
        return super.use(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        removeEffect(entity, effectToApply.value());
        return super.finishUsingItem(itemStack, level, entity);
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int remainingTime) {
        removeEffect(entity, effectToApply.value());
        return super.releaseUsing(itemStack, level, entity, remainingTime);
    }

    public static void removeEffect(LivingEntity entity, MobEffect effectToRemove)
    {
        var effects = new ArrayList<>(entity.getActiveEffects());

        for(int i = 0; i < effects.size(); i++)
        {
            if(effects.get(i).isAmbient() && effects.get(i).getEffect().value() == effectToRemove)
            {
                entity.removeEffect(effects.get(i).getEffect());
                break;
            }
        }
    }
}
