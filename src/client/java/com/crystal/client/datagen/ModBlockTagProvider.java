package com.crystal.client.datagen;

import com.crystal.ImprovedCrystals;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

    public static final TagKey<Block> ADDITIONAL_CHIME_SUPPORT_BLOCKS = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "additional_chime_support_blocks"));
    public static final TagKey<Block> ECHO_DRIED_GHAST_NOISES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "echo_dried_ghast_noises"));
    public static final TagKey<Block> ECHO_PISTON_NOISES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "echo_piston_noises"));
    public static final TagKey<Block> ECHO_WARDEN_NOISES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "echo_warden_noises"));
    public static final TagKey<Block> ECHO_TNT_NOISES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "echo_tnt_noises"));
    public static final TagKey<Block> ECHO_SOUL_NOISES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "echo_soul_noises"));
    public static final TagKey<Block> ECHO_SNIFFER_EGG_NOISES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "echo_sniffer_egg_noises"));
    public static final TagKey<Block> ECHO_SNIFFER_NOISES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "echo_sniffer_noises"));
    public static final TagKey<Block> ECHO_SHULKER_NOISES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "echo_shulker_noises"));
    public static final TagKey<Block> ECHO_BEE_NOISES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "echo_bee_noises"));

    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        valueLookupBuilder(ADDITIONAL_CHIME_SUPPORT_BLOCKS).add(Blocks.HONEY_BLOCK).forceAddTag(BlockTags.FENCES).forceAddTag(BlockTags.WALLS).add(Blocks.DRIED_GHAST).add(Blocks.CHEST).add(Blocks.ENDER_CHEST).add(Blocks.TRAPPED_CHEST).forceAddTag(BlockTags.BARS).forceAddTag(BlockTags.COPPER_CHESTS).forceAddTag(BlockTags.LIGHTNING_RODS).add(Blocks.SNIFFER_EGG).forceAddTag(BlockTags.FLOWER_POTS).add(Blocks.FLOWER_POT).setReplace(true);
        valueLookupBuilder(ECHO_SHULKER_NOISES).forceAddTag(BlockTags.SHULKER_BOXES).setReplace(true);
        valueLookupBuilder(ECHO_BEE_NOISES).forceAddTag(BlockTags.BEEHIVES).setReplace(true);
        valueLookupBuilder(ECHO_SNIFFER_EGG_NOISES).add(Blocks.SNIFFER_EGG).setReplace(true);
        valueLookupBuilder(ECHO_PISTON_NOISES).add(Blocks.PISTON).add(Blocks.STICKY_PISTON).setReplace(true);
        valueLookupBuilder(ECHO_TNT_NOISES).add(Blocks.TNT).setReplace(true);
        valueLookupBuilder(ECHO_SOUL_NOISES).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL).setReplace(true);
        valueLookupBuilder(ECHO_WARDEN_NOISES).add(Blocks.SCULK_SHRIEKER).setReplace(true);
        valueLookupBuilder(ECHO_DRIED_GHAST_NOISES).add(Blocks.DRIED_GHAST).setReplace(true);
        valueLookupBuilder(ECHO_SNIFFER_NOISES).add(Blocks.POTTED_TORCHFLOWER).setReplace(true);
    }
}
