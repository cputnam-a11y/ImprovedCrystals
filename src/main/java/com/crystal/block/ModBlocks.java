package com.crystal.block;

import com.crystal.ImprovedCrystals;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.awt.*;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import static net.minecraft.world.level.block.Blocks.litBlockEmission;

public class ModBlocks {

    public static final Block AMETHYST_CHIMES = register("amethyst_chimes", p -> new ChimeBlock(p, SoundEvents.AMETHYST_BLOCK_CHIME, SoundEvents.AMETHYST_BLOCK_HIT), BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true);
    public static final Block PRISMARINE_CRYSTAL_CHIMES = register("prismarine_crystal_chimes", p -> new ChimeBlock(p, SoundEvents.WATER_AMBIENT, SoundEvents.PLAYER_SPLASH), BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true);
    public static final Block ECHO_CHIMES = register("echo_chimes", p -> new EchoChimeBlock(p, SoundEvents.AMETHYST_BLOCK_CHIME, SoundEvents.AMETHYST_BLOCK_HIT), BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true, Rarity.UNCOMMON);
    public static final Block ECHO_BLOCK = register("echo_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true, Rarity.UNCOMMON);
    public static final Block PRISMARINE_CRYSTAL_BLOCK = register("prismarine_crystal_block", Block::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(stateex -> 15).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().strength(1.5F), true);
    public static final Block SEA_GLASS = register("sea_glass", TransparentBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel(stateex -> 5).destroyTime(0.3F).noOcclusion().sound(SoundType.GLASS).isValidSpawn(Blocks::never).isRedstoneConductor(Blocks::never).isSuffocating(Blocks::never).isViewBlocking(Blocks::never).liquid(), true);
    public static final Block SMALL_PRISMARINE_CRYSTAL_BUD = register("small_prismarine_crystal_bud", p -> new PrismarineClusterBlock(1, 9, p), BlockBehaviour.Properties.of().sound(SoundType.AMETHYST).requiresCorrectToolForDrops().strength(1.5F).lightLevel(stateex -> 1).mapColor(MapColor.COLOR_LIGHT_BLUE).pushReaction(PushReaction.DESTROY), true);
    public static final Block MEDIUM_PRISMARINE_CRYSTAL_BUD = register("medium_prismarine_crystal_bud", p -> new PrismarineClusterBlock(2, 9, p), BlockBehaviour.Properties.of().sound(SoundType.AMETHYST).requiresCorrectToolForDrops().strength(1.5F).lightLevel(stateex -> 2).mapColor(MapColor.COLOR_LIGHT_BLUE).pushReaction(PushReaction.DESTROY), true);
    public static final Block LARGE_PRISMARINE_CRYSTAL_BUD = register("large_prismarine_crystal_bud", p -> new PrismarineClusterBlock(3, 9, p), BlockBehaviour.Properties.of().sound(SoundType.AMETHYST).requiresCorrectToolForDrops().strength(1.5F).lightLevel(stateex -> 3).mapColor(MapColor.COLOR_LIGHT_BLUE).pushReaction(PushReaction.DESTROY), true);
    public static final Block PRISMARINE_CRYSTAL_CLUSTER = register("prismarine_crystal_cluster", p -> new PrismarineClusterBlock(4, 9, p), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().sound(SoundType.AMETHYST).strength(1.5F).lightLevel(stateex -> 4).mapColor(MapColor.COLOR_LIGHT_BLUE).pushReaction(PushReaction.DESTROY), true);
    public static final Block ECHO_ROD = register("echo_rod", EchoRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).lightLevel(litBlockEmission(4)), true, Rarity.UNCOMMON);
    public static final Block CALIBRATED_ECHO_ROD = register("calibrated_echo_rod", CalibratedEchoRodBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.ECHO_ROD), true, Rarity.UNCOMMON);
    public static final Block SMOOTH_AMETHYST = register("smooth_amethyst", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true);
    public static final Block SMOOTH_AMETHYST_SLAB = register("smooth_amethyst_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST), true);
    public static final Block SMOOTH_AMETHYST_STAIRS = register("smooth_amethyst_stairs", p -> new StairBlock(ModBlocks.SMOOTH_AMETHYST.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST), true);
    public static final Block SMOOTH_AMETHYST_WALL = register("smooth_amethyst_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST).forceSolidOn(), true);
    public static final Block SMOOTH_PRISMARINE_CRYSTALS = register("smooth_prismarine_crystals", Block::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.PRISMARINE_CRYSTAL_BLOCK).mapColor(DyeColor.LIGHT_BLUE), true);
    public static final Block SMOOTH_PRISMARINE_CRYSTAL_SLAB = register("smooth_prismarine_crystal_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.PRISMARINE_CRYSTAL_BLOCK), true);
    public static final Block SMOOTH_PRISMARINE_CRYSTAL_STAIRS = register("smooth_prismarine_crystal_stairs", p -> new StairBlock(ModBlocks.SMOOTH_AMETHYST.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.PRISMARINE_CRYSTAL_BLOCK), true);
    public static final Block SMOOTH_PRISMARINE_CRYSTAL_WALL = register("smooth_prismarine_crystal_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.PRISMARINE_CRYSTAL_BLOCK).forceSolidOn(), true);
    public static final Block SEA_TORCH = register("sea_torch", p -> new WaterloggedTorchBlock(ParticleTypes.FLAME, p), BlockBehaviour.Properties.of().noCollision().instabreak().lightLevel((statex) -> 14).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), false);
    public static final Block SEA_WALL_TORCH = register("sea_wall_torch", p -> new WallWaterloggedTorchBlock(ParticleTypes.FLAME, p), BlockBehaviour.Properties.of().noCollision().instabreak().lightLevel((statex) -> 14).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).overrideLootTable(ModBlocks.SEA_TORCH.getLootTable()), false);
    public static final Block SMOOTH_ECHO = register("smooth_echo", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).mapColor(MapColor.COLOR_BLUE), true, Rarity.UNCOMMON);
    public static final Block SMOOTH_ECHO_SLAB = register("smooth_echo_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO), true, Rarity.UNCOMMON);
    public static final Block SMOOTH_ECHO_STAIRS = register("smooth_echo_stairs", p -> new StairBlock(ModBlocks.SMOOTH_AMETHYST.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO), true, Rarity.UNCOMMON);
    public static final Block SMOOTH_ECHO_WALL = register("smooth_echo_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO).forceSolidOn(), true, Rarity.UNCOMMON);
    public static final Block SMALL_ECHO_BUD = register("small_echo_bud", p -> new AmethystClusterBlock(1, 8, p), BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.5F).sound(SoundType.AMETHYST).mapColor(MapColor.COLOR_BLUE), true, Rarity.UNCOMMON);
    public static final Block MEDIUM_ECHO_BUD = register("medium_echo_bud", p -> new AmethystClusterBlock(2, 8, p), BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.5F).sound(SoundType.AMETHYST).mapColor(MapColor.COLOR_BLUE), true, Rarity.UNCOMMON);
    public static final Block LARGE_ECHO_BUD = register("large_echo_bud", p -> new AmethystClusterBlock(3, 8, p), BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.5F).sound(SoundType.AMETHYST).mapColor(MapColor.COLOR_BLUE), true, Rarity.UNCOMMON);
    public static final Block ECHO_CLUSTER = register("echo_cluster", p -> new AmethystClusterBlock(4, 8, p), BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.5F).sound(SoundType.AMETHYST).mapColor(MapColor.COLOR_BLUE), true, Rarity.UNCOMMON);
    public static final Block BUDDING_PRISMARINE = register("budding_prismarine", BuddingPrismarineBlock::new, BlockBehaviour.Properties.ofFullCopy(PRISMARINE_CRYSTAL_BLOCK).randomTicks(), true);
    public static final Block BUDDING_ECHO = register("budding_echo", BuddingEchoBlock::new, BlockBehaviour.Properties.ofFullCopy(ECHO_BLOCK).strength(1.5F).randomTicks(), true, Rarity.UNCOMMON);
    public static final Block SMOOTH_PRISMARINE_CRYSTAL_BRICKS = register("smooth_prismarine_crystal_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(SMOOTH_PRISMARINE_CRYSTALS), true);
    public static final Block SMOOTH_ECHO_BRICKS = register("smooth_echo_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO), true, Rarity.UNCOMMON);
    public static final Block SMOOTH_AMETHYST_BRICKS = register("smooth_amethyst_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST), true);
    public static final Block POLISHED_PRISMARINE_CRYSTALS = register("polished_prismarine_crystals", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(SMOOTH_PRISMARINE_CRYSTALS).noOcclusion(), true);
    public static final Block POLISHED_ECHO = register("polished_echo", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO).noOcclusion(), true, Rarity.UNCOMMON);
    public static final Block POLISHED_AMETHYST = register("polished_amethyst", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST).noOcclusion(), true);
    public static final Block POLISHED_PRISMARINE_CRYSTAL_BRICKS = register("polished_prismarine_crystal_bricks", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(SMOOTH_PRISMARINE_CRYSTALS).noOcclusion(), true);
    public static final Block POLISHED_ECHO_BRICKS = register("polished_echo_bricks", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO).noOcclusion(), true, Rarity.UNCOMMON);
    public static final Block POLISHED_AMETHYST_BRICKS = register("polished_amethyst_bricks", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST).noOcclusion(), true);
    public static final Block SMOOTH_PRISMARINE_CRYSTAL_BRICK_SLAB = register("smooth_prismarine_crystal_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_PRISMARINE_CRYSTAL_BRICKS), true);
    public static final Block SMOOTH_AMETHYST_BRICK_SLAB = register("smooth_amethyst_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST_BRICKS), true);
    public static final Block SMOOTH_ECHO_BRICK_SLAB = register("smooth_echo_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO_BRICKS), true, Rarity.UNCOMMON);
    public static final Block SMOOTH_PRISMARINE_CRYSTAL_BRICK_STAIRS = register("smooth_prismarine_crystal_brick_stairs", p -> new StairBlock(ModBlocks.SMOOTH_PRISMARINE_CRYSTALS.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_PRISMARINE_CRYSTAL_BRICKS), true);
    public static final Block SMOOTH_AMETHYST_BRICK_STAIRS = register("smooth_amethyst_brick_stairs", p -> new StairBlock(ModBlocks.SMOOTH_AMETHYST_BRICKS.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST_BRICKS), true);
    public static final Block SMOOTH_ECHO_BRICK_STAIRS = register("smooth_echo_brick_stairs", p -> new StairBlock(ModBlocks.POLISHED_ECHO_BRICKS.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO_BRICKS), true, Rarity.UNCOMMON);
    public static final Block SMOOTH_PRISMARINE_CRYSTAL_BRICK_WALL = register("smooth_prismarine_crystal_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_PRISMARINE_CRYSTAL_BRICKS).forceSolidOn(), true);
    public static final Block SMOOTH_AMETHYST_BRICK_WALL = register("smooth_amethyst_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST_BRICKS).forceSolidOn(), true);
    public static final Block SMOOTH_ECHO_BRICK_WALL = register("smooth_echo_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO_BRICKS).forceSolidOn(), true, Rarity.UNCOMMON);
    public static final Block POLISHED_AMETHYST_BRICK_WALL = register("polished_amethyst_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_AMETHYST).forceSolidOn(), true);
    public static final Block POLISHED_AMETHYST_BRICK_SLAB = register("polished_amethyst_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_AMETHYST), true);
    public static final Block POLISHED_AMETHYST_BRICK_STAIRS = register("polished_amethyst_brick_stairs", p -> new StairBlock(POLISHED_AMETHYST.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_AMETHYST), true);
    public static final Block POLISHED_AMETHYST_WALL = register("polished_amethyst_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_AMETHYST).forceSolidOn(), true);
    public static final Block POLISHED_AMETHYST_SLAB = register("polished_amethyst_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_AMETHYST), true);
    public static final Block POLISHED_AMETHYST_STAIRS = register("polished_amethyst_stairs", p -> new StairBlock(POLISHED_AMETHYST.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_AMETHYST), true);
    public static final Block POLISHED_ECHO_BRICK_WALL = register("polished_echo_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_ECHO).forceSolidOn(), true, Rarity.UNCOMMON);
    public static final Block POLISHED_ECHO_BRICK_SLAB = register("polished_echo_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_ECHO), true, Rarity.UNCOMMON);
    public static final Block POLISHED_ECHO_BRICK_STAIRS = register("polished_echo_brick_stairs", p -> new StairBlock(POLISHED_ECHO.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_ECHO), true, Rarity.UNCOMMON);
    public static final Block POLISHED_ECHO_WALL = register("polished_echo_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_ECHO).forceSolidOn(), true, Rarity.UNCOMMON);
    public static final Block POLISHED_ECHO_SLAB = register("polished_echo_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_ECHO), true, Rarity.UNCOMMON);
    public static final Block POLISHED_ECHO_STAIRS = register("polished_echo_stairs", p -> new StairBlock(POLISHED_ECHO.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_ECHO), true, Rarity.UNCOMMON);
    public static final Block POLISHED_PRISMARINE_CRYSTAL_BRICK_WALL = register("polished_prismarine_crystal_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_PRISMARINE_CRYSTALS).forceSolidOn(), true);
    public static final Block POLISHED_PRISMARINE_CRYSTAL_BRICK_SLAB = register("polished_prismarine_crystal_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_PRISMARINE_CRYSTALS), true);
    public static final Block POLISHED_PRISMARINE_CRYSTAL_BRICK_STAIRS = register("polished_prismarine_crystal_brick_stairs", p -> new StairBlock(POLISHED_PRISMARINE_CRYSTALS.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_PRISMARINE_CRYSTALS), true);
    public static final Block POLISHED_PRISMARINE_CRYSTAL_WALL = register("polished_prismarine_crystal_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_PRISMARINE_CRYSTALS).forceSolidOn(), true);
    public static final Block POLISHED_PRISMARINE_CRYSTAL_SLAB = register("polished_prismarine_crystal_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_PRISMARINE_CRYSTALS), true);
    public static final Block POLISHED_PRISMARINE_CRYSTAL_STAIRS = register("polished_prismarine_crystal_stairs", p -> new StairBlock(POLISHED_PRISMARINE_CRYSTALS.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(ModBlocks.POLISHED_PRISMARINE_CRYSTALS), true);
    public static final Block QUARTZ_WALL = register("quartz_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).forceSolidOn(), true);
    public static final Block SMOOTH_QUARTZ_WALL = register("smooth_quartz_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).forceSolidOn(), true);
    public static final Block PRISMARINE_BRICK_WALL = register("prismarine_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE_BRICKS).forceSolidOn(), true);
    public static final Block DARK_PRISMARINE_WALL = register("dark_prismarine_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_PRISMARINE).forceSolidOn(), true);
    public static final Block CHISELED_POLISHED_ECHO = register("chiseled_polished_echo", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_ECHO).noOcclusion(), true, Rarity.UNCOMMON);
    public static final Block CHISELED_POLISHED_AMETHYST = register("chiseled_polished_amethyst", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_AMETHYST).noOcclusion(), true);
    public static final Block CHISELED_POLISHED_PRISMARINE_CRYSTALS = register("chiseled_polished_prismarine_crystals", TransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.SMOOTH_PRISMARINE_CRYSTALS).noOcclusion(), true);
    public static final Block CHISELED_SMOOTH_ECHO = register("chiseled_smooth_echo", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true, Rarity.UNCOMMON);
    public static final Block CHISELED_SMOOTH_AMETHYST = register("chiseled_smooth_amethyst", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK), true);
    public static final Block CHISELED_SMOOTH_PRISMARINE_CRYSTALS = register("chiseled_smooth_prismarine_crystals", Block::new, BlockBehaviour.Properties.ofFullCopy(ModBlocks.PRISMARINE_CRYSTAL_BLOCK), true);
    public static final Block QUARTZ_BRICK_WALL = register("quartz_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).forceSolidOn(), true);
    public static final Block QUARTZ_BRICK_SLAB = register("quartz_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).forceSolidOn(), true);
    public static final Block QUARTZ_BRICK_STAIRS = register("quartz_brick_stairs", p -> new StairBlock(Blocks.QUARTZ_BLOCK.defaultBlockState(), p), BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).forceSolidOn(), true);
    public static final Block QUARTZ_CHIMES = register("quartz_chimes", p -> new ChimeBlock(p, SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS.value(), SoundEvents.AMBIENT_NETHER_WASTES_MOOD.value()), BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK), true);

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        // Create a registry key for the block
        ResourceKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.setId(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            ResourceKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem, Rarity rarity) {
        // Create a registry key for the block
        ResourceKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance
        Block block = blockFactory.apply(settings.setId(blockKey));

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            ResourceKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix().component(DataComponents.RARITY, rarity));
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, name));
    }

    public static void initialize() {}

}
