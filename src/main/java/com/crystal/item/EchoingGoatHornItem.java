package com.crystal.item;

import com.crystal.ImprovedCrystals;
import com.crystal.attributes.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.InstrumentComponent;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.swing.*;
import java.util.*;

public class EchoingGoatHornItem extends InstrumentItem {

    private final Holder<Attribute> cooldown = ModAttributes.COOLDOWN_TIME;
    private final Holder<Attribute> flameBreath = ModAttributes.FIRE_BREATH;
    private final Holder<Attribute> potionBreath = ModAttributes.POTION_BREATH;
    private final Holder<Attribute> breathPower = ModAttributes.BREATH_POWER;
    private final Holder<Attribute> breathKnockback = ModAttributes.BREATH_KNOCKBACK;
    private final Holder<Attribute> breathAttackDistance = ModAttributes.BREATH_ATTACK_DISTANCE;


    public EchoingGoatHornItem(Properties properties) {
        properties.attributes(ItemAttributeModifiers.builder().add(ModAttributes.BREATH_POWER, new AttributeModifier(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "breath_power"), 10, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build());
        properties.attributes(ItemAttributeModifiers.builder().add(ModAttributes.COOLDOWN_TIME, new AttributeModifier(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "cooldown_time"), 7, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build());
        properties.attributes(ItemAttributeModifiers.builder().add(ModAttributes.BREATH_KNOCKBACK, new AttributeModifier(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "breath_knockback"), 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build());
        properties.attributes(ItemAttributeModifiers.builder().add(ModAttributes.FIRE_BREATH, new AttributeModifier(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "fire_breath"), 0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build());
        properties.attributes(ItemAttributeModifiers.builder().add(ModAttributes.POTION_BREATH, new AttributeModifier(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "potion_breath"), 0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build());
        properties.attributes(ItemAttributeModifiers.builder().add(ModAttributes.INSTRUMENT_SOUND_RANGE, new AttributeModifier(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "instrument_sound_range"), 0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build());
        properties.attributes(ItemAttributeModifiers.builder().add(ModAttributes.BREATH_ATTACK_DISTANCE, new AttributeModifier(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "breath_attack_distance"), 0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build());

        super(properties);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Optional<? extends Holder<Instrument>> instrumentHolder = getInstrument(itemStack);
        if (instrumentHolder.isPresent()) {
            Instrument instrument = (Instrument)((Holder)instrumentHolder.get()).value();
            player.startUsingItem(hand);
            play(level, player, instrument);
            createSonicBoom(level, player, itemStack);
            itemStack.hurtAndBreak(1, player, hand);
            player.getCooldowns().addCooldown(itemStack, Mth.floor(player.getAttributeValue(cooldown) * 20.0F));
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.FAIL;
        }
    }

    private static Optional<Holder<Instrument>> getInstrument(final ItemStack itemStack) {
        InstrumentComponent instrument = (InstrumentComponent)itemStack.get(DataComponents.INSTRUMENT);
        return instrument != null ? Optional.of(instrument.instrument()) : Optional.empty();
    }

    private static void play(final Level level, final Player player, final Instrument instrument) {
        SoundEvent soundEvent = (SoundEvent)instrument.soundEvent().value();
        Holder<Attribute> extraRange = ModAttributes.INSTRUMENT_SOUND_RANGE;
        float volume = instrument.range() + (float)player.getAttributeValue(extraRange) / 16.0F;
        level.playSound(player, player, soundEvent, SoundSource.RECORDS, volume, 1.0F);
        level.gameEvent(GameEvent.INSTRUMENT_PLAY, player.position(), GameEvent.Context.of(player));
    }

    private void createSonicBoom(Level level, Player player, ItemStack itemStack)
    {
        float dist = 7f;
        Vec3 target = player.position().add(player.getLookAngle().scale(dist));
        Vec3 source = player.position().add(0.0, 1.6f, 0.0);;
        Vec3 offset = target.subtract(source);
        Vec3 normalize = offset.normalize();

        int steps = Mth.floor(offset.length()) + 7 + (int)player.getAttributeValue(breathAttackDistance);
        Set<Entity> hit = new HashSet<>();

        for(int i = 1; i < steps; ++i) {
            Vec3 particlePos = source.add(normalize.scale(i));
            level.addParticle(ParticleTypes.SONIC_BOOM, particlePos.x, particlePos.y, particlePos.z, 0f, 0f, 0f);
            hit.addAll(level.getEntitiesOfClass(LivingEntity.class, new AABB(source, particlePos)));
        }

        hit.remove(player);
        player.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
        for (Entity hitTarget : hit) {
            if(hitTarget instanceof LivingEntity living) {
                living.hurt(level.damageSources().sonicBoom(player), (float)player.getAttributeValue(breathPower));
                double knockbackVertical = (double)0.5F * player.getAttributeValue(breathKnockback) - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                double knockbackHorizontal = (double)2.5F * player.getAttributeValue(breathKnockback) - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                living.push(normalize.x() * knockbackHorizontal, normalize.y() * knockbackVertical, normalize.z() * knockbackHorizontal);

                if(player.getAttributeValue(flameBreath) > 0)
                {
                    living.igniteForSeconds((float)player.getAttributeValue(flameBreath) + 2);
                }
                else if(player.getAttributeValue(potionBreath) > 0)
                {
                    int numberOfEffects = (int)player.getAttributeValue(potionBreath);

                    for(int i = 0; i < numberOfEffects; i++)
                    {
                        var effects = player.getActiveEffects();
                        if(!effects.isEmpty())
                        {
                            var effect = effects.stream().toList().get(i);
                            living.addEffect(effect);
                            player.removeEffect(effect.getEffect());
                        }
                    }
                }
            }
        }
    }
}
