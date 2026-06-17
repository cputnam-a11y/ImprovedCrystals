package com.crystal.util;

import com.crystal.ImprovedCrystals;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks{

        public static final TagKey<Block> ADDITIONAL_CHIME_SUPPORT_BLOCKS = createTag("additional_chime_support_blocks");
        public static final TagKey<Block> ECHO_DRIED_GHAST_NOISES = createTag("echo_dried_ghast_noises");
        public static final TagKey<Block> ECHO_PISTON_EXTEND_NOISES = createTag("echo_piston_extend_noises");
        public static final TagKey<Block> ECHO_PISTON_RETRACT_NOISES = createTag("echo_piston_retract_noises");
        public static final TagKey<Block> ECHO_WARDEN_NOISES = createTag("echo_warden_noises");
        public static final TagKey<Block> ECHO_TNT_NOISES = createTag("echo_tnt_noises");
        public static final TagKey<Block> ECHO_SOUL_NOISES = createTag("echo_soul_noises");
        public static final TagKey<Block> ECHO_SNIFFER_EGG_NOISES = createTag("echo_sniffer_egg_noises");
        public static final TagKey<Block> ECHO_SNIFFER_NOISES = createTag("echo_sniffer_noises");
        public static final TagKey<Block> ECHO_SHULKER_NOISES = createTag("echo_shulker_noises");
        public static final TagKey<Block> ECHO_BEE_NOISES = createTag("echo_bee_noises");
        public static final TagKey<Block> ECHO_CAVE_NOISES = createTag("echo_cave_noises");
        public static final TagKey<Block> ECHO_ARROW_NOISES = createTag("echo_arrow_noises");
        public static final TagKey<Block> ECHO_UNDERWATER_NOISES = createTag("echo_underwater_noises");
        public static final TagKey<Block> ECHO_RARE_UNDERWATER_NOISES = createTag("echo_rare_underwater_noises");
        public static final TagKey<Block> ECHO_ULTRA_RARE_UNDERWATER_NOISES = createTag("echo_ultra_rare_underwater_noises");
        public static final TagKey<Block> ECHO_SPONGE_NOISES = createTag("echo_sponge_noises");
        public static final TagKey<Block> ECHO_BASALT_DELTA_NOISES = createTag("echo_basalt_delta_noises");
        public static final TagKey<Block> ECHO_CRIMSON_FOREST_NOISES = createTag("echo_crimson_forest_noises");
        public static final TagKey<Block> ECHO_CRIMSON_FOREST_MOOD_NOISES = createTag("echo_crimson_forest_mood_noises");
        public static final TagKey<Block> ECHO_SOUL_SAND_VALLEY_NOISES = createTag("echo_soul_sand_valley_noises");
        public static final TagKey<Block> ECHO_SOUL_SAND_VALLEY_MOOD_NOISES = createTag("echo_soul_sand_valley_mood_noises");
        public static final TagKey<Block> ECHO_DEAD_BUSH_NOISES = createTag("echo_dead_bush_noises");
        public static final TagKey<Block> ECHO_EYEBLOSSOM_NOISES = createTag("echo_eyeblossom_noises");
        public static final TagKey<Block> ECHO_PALE_MOSS_NOISES = createTag("echo_pale_moss_noises");
        public static final TagKey<Block> ECHO_SAND_NOISES = createTag("echo_sand_noises");
        public static final TagKey<Block> ECHO_DRY_GRASS_NOISES = createTag("echo_dry_grass_noises");
        public static final TagKey<Block> ECHO_INFESTED_BLOCK_NOISES = createTag("echo_infested_block_noises");
        public static final TagKey<Block> PRISMARINE_CRYSTAL_BLOCKS = createTag("prismarine_crystal_blocks");
        public static final TagKey<Block> AMETHYST_BLOCKS = createTag("amethyst_blocks");
        public static final TagKey<Block> ECHO_BLOCKS = createTag("echo_blocks");
        public static final TagKey<Block> ECHO_WARPED_FOREST_NOISES = createTag("echo_warped_forest_noises");
        public static final TagKey<Block> ECHO_WARPED_FOREST_MOOD_NOISES = createTag("echo_warped_forest_mood_noises");
        public static final TagKey<Block> ECHO_VILLAGER_NOISES = createTag("echo_villager_noises");
        public static final TagKey<Block> ECHO_COPPER_GOLEM_NOISES = createTag("echo_copper_golem_noises");
        public static final TagKey<Block> SEA_GLASS_BLOCKS = createTag("sea_glass_blocks");


        private static TagKey<Block> createTag(String name)
        {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, name));
        }
    }
}
