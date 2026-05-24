package com.crystal.item;

import com.crystal.ImprovedCrystals;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModItemDataComponenets {

    public static final DataComponentType<ItemUseSoundComponent> ITEM_USE_SOUND = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "item_use_sound"),
            DataComponentType.<ItemUseSoundComponent>builder().persistent(ItemUseSoundComponent.CODEC).build());

}
