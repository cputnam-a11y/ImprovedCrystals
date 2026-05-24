package com.crystal.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.Random;
import java.util.function.Consumer;

public class ExpFlaskItem extends Item {

    public ExpFlaskItem(Properties properties) {
        super(properties);
    }


    @Override
    public int getUseDuration(final ItemStack itemStack, final LivingEntity user) {
        return 1200;
    }


    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BLOCK;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int ticksRemaining) {
        Random random = new Random();
        if(livingEntity instanceof Player player)
        {
            if(!player.isShiftKeyDown() && player.totalExperience > 0 && itemStack.getDamageValue() <= itemStack.getMaxDamage() && itemStack.getDamageValue() > 0)
            {
                player.giveExperiencePoints(-1);
                itemStack.setDamageValue(itemStack.getDamageValue() - 1);
                if(random.nextInt(100) <= 15) {
                    player.playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 0.1F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                }
            }
            else if(player.isShiftKeyDown() && itemStack.getDamageValue() < itemStack.getMaxDamage() && itemStack.getDamageValue() >= 0)
            {
                player.giveExperiencePoints(1);
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.1F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                itemStack.setDamageValue(itemStack.getDamageValue() + 1);
            }
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getDamageValue() > 0 || stack.getDamageValue() == stack.getMaxDamage();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("item.improved-crystals.experience_bottle_xp", new Object[]{itemStack.getMaxDamage() - itemStack.getDamageValue(), itemStack.getMaxDamage()}));
    }

    public int getBarColor(final ItemStack stack) {
        return Color.GREEN.getRGB();
    }
}
