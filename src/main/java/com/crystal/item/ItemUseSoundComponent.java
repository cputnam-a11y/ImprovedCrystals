package com.crystal.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record ItemUseSoundComponent(SoundEvent soundEvent, EntityType entityType) {


    public static final Codec<ItemUseSoundComponent> CODEC = RecordCodecBuilder.create(
            builder -> {
                return builder.group(
                        SoundEvent.DIRECT_CODEC.fieldOf("soundEvent").forGetter(ItemUseSoundComponent::soundEvent),
                        EntityType.CODEC.fieldOf("entityType").forGetter(ItemUseSoundComponent::entityType)
                ).apply(builder, ItemUseSoundComponent::new);});
}
