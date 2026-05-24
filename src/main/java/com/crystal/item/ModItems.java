package com.crystal.item;

import com.crystal.ImprovedCrystals;
import com.crystal.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.InstrumentComponent;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Function;

public class ModItems {

    public static final Item RESONATING_ECHO_SHARD = register("resonating_echo_shard", ResonatingEchoShard::new, new Item.Properties().component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(BlockStateProperties.POWER, 0)).component(ModItemDataComponenets.ITEM_USE_SOUND, new ItemUseSoundComponent(null, null)).component(DataComponents.RARITY, Rarity.UNCOMMON));
    public static final Item PRISMARINE_SPYGLASS = register("prismarine_spyglass", p -> new EffectSpyglassItem(p, MobEffects.NIGHT_VISION), new Item.Properties().stacksTo(1));
    public static final Item SEA_TORCH = register("sea_torch", createWallBlockItem(ModBlocks.SEA_TORCH, ModBlocks.SEA_WALL_TORCH), new Item.Properties());
    public static final Item EXPERIENCE_FLASK = register("experience_flask", ExpFlaskItem::new, new Item.Properties().durability(1395).component(DataComponents.DAMAGE, 1395).rarity(Rarity.UNCOMMON));
    public static final Item ECHOING_GOAT_HORN = register("echoing_goat_horn", EchoingGoatHornItem::new, new Item.Properties().rarity(Rarity.UNCOMMON).durability(192).repairable(Items.ECHO_SHARD).enchantable(22).delayedComponent(DataComponents.INSTRUMENT, (context) -> new InstrumentComponent(context.getOrThrow(Instruments.PONDER_GOAT_HORN))));

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, name));

        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
    }

    private static Function<Item.Properties, Item> createWallBlockItem(final Block block, final  Block wallBlock)
    {
        return p -> new StandingAndWallBlockItem(block, wallBlock, Direction.DOWN, p.useItemDescriptionPrefix());
    }
}
