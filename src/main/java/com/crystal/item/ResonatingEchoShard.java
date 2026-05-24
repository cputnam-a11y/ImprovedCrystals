package com.crystal.item;

import com.crystal.ImprovedCrystals;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

public class ResonatingEchoShard extends Item {

    public ResonatingEchoShard(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        Random random = new Random();
        ItemStack stack =  player.getItemInHand(hand);
        if(stack.get(DataComponents.BLOCK_STATE).get(BlockStateProperties.POWER) > 0) {
            createSonicBoom(level, player, player.getItemInHand(hand));
            player.getItemInHand(hand).shrink(1);
            return InteractionResult.SUCCESS;
        }
        else if(stack.get(ModItemDataComponenets.ITEM_USE_SOUND).soundEvent() != null)
        {
            player.playSound(stack.get(ModItemDataComponenets.ITEM_USE_SOUND).soundEvent(), 1F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("item.improved-crystals.resonating_echo_shard.power_info", itemStack.get(DataComponents.BLOCK_STATE).get(BlockStateProperties.POWER)));
        if(itemStack.get(ModItemDataComponenets.ITEM_USE_SOUND).entityType() != null) {
            builder.accept(Component.translatable("item.improved-crystals.resonating_echo_shard.sound_info", itemStack.get(ModItemDataComponenets.ITEM_USE_SOUND).entityType().getDescription()));
        }
    }

    private void createSonicBoom(Level level, Player player, ItemStack itemStack) {
        float dist = 7f;
        Vec3 target = player.position().add(player.getLookAngle().scale(dist));
        Vec3 source = player.position().add(0.0, 1.6f, 0.0);
        Vec3 offset = target.subtract(source);
        Vec3 normalize = offset.normalize();

        int steps = Mth.floor(offset.length()) + 7;
        Set<Entity> hit = new HashSet<>();

        for (int i = 1; i < steps; ++i) {
            Vec3 particlePos = source.add(normalize.scale(i));
            level.addParticle(ParticleTypes.SONIC_BOOM, particlePos.x, particlePos.y, particlePos.z, 0f, 0f, 0f);
            hit.addAll(level.getEntitiesOfClass(LivingEntity.class, new AABB(source, particlePos)));
        }

        hit.remove(player);
        player.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
        for (Entity hitTarget : hit) {
            if (hitTarget instanceof LivingEntity living) {
                living.hurt(level.damageSources().sonicBoom(player), itemStack.get(DataComponents.BLOCK_STATE).get(BlockStateProperties.POWER));
                double knockbackVertical = (double) 0.5F * 1 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                double knockbackHorizontal = (double) 2.5F * 1 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                living.push(normalize.x() * knockbackHorizontal, normalize.y() * knockbackVertical, normalize.z() * knockbackHorizontal);

            }
        }
    }
}
