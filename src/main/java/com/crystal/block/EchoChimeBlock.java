package com.crystal.block;

import com.crystal.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class EchoChimeBlock extends ChimeBlock {

    public EchoChimeBlock(Properties properties, SoundEvent ambientSound, SoundEvent interactSound) {
        super(properties, ambientSound, interactSound);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        var aboveBlock = level.getBlockState(pos.above());

        if(random.nextInt(24) == 0)
        {
            level.playLocalSound((double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, getSoundToPlay(aboveBlock), SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        var aboveBlock = level.getBlockState(pos.above());

        RandomSource random = level.getRandom();

        level.playLocalSound((double) pos.getX() + (double) 0.5F, (double) pos.getY() + (double) 0.5F, (double) pos.getZ() + (double) 0.5F, getSoundToPlay(aboveBlock), SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);

        return InteractionResult.SUCCESS;
    }

    private SoundEvent getSoundToPlay(BlockState block)
    {
        var soundToPlay = block.getSoundType().getHitSound();

        if(block.is(ModTags.Blocks.ECHO_DRIED_GHAST_NOISES))
        {
            soundToPlay = SoundEvents.DRIED_GHAST_TRANSITION;
        }
        else if(block.is(ModTags.Blocks.ECHO_WARDEN_NOISES))
        {
            soundToPlay = SoundEvents.WARDEN_HEARTBEAT;
        }
        else if(block.is(ModTags.Blocks.ECHO_TNT_NOISES))
        {
            soundToPlay = SoundEvents.GENERIC_EXPLODE.value();
        }
        else if(block.is(ModTags.Blocks.ECHO_BEE_NOISES))
        {
            soundToPlay = SoundEvents.BEEHIVE_WORK;
        }
        else if(block.is(ModTags.Blocks.ECHO_PISTON_NOISES))
        {
            soundToPlay = SoundEvents.PISTON_EXTEND;
        }
        else if(block.is(ModTags.Blocks.ECHO_SOUL_NOISES))
        {
            soundToPlay = SoundEvents.SOUL_ESCAPE.value();
        }
        else if(block.is(ModTags.Blocks.ECHO_SHULKER_NOISES))
        {
            soundToPlay = SoundEvents.SHULKER_AMBIENT;
        }
        else if(block.is(ModTags.Blocks.ECHO_SNIFFER_EGG_NOISES))
        {
            soundToPlay = SoundEvents.SNIFFER_EGG_CRACK;
        }
        else if(block.is(ModTags.Blocks.ECHO_SNIFFER_NOISES))
        {
            soundToPlay = SoundEvents.SNIFFER_SCENTING;
        }
        else if(block.is(ModTags.Blocks.ECHO_ARROW_NOISES))
        {
            soundToPlay = SoundEvents.ARROW_SHOOT;
        }
        else if(block.is(ModTags.Blocks.ECHO_CAVE_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_CAVE.value();
        }
        else if(block.is(ModTags.Blocks.ECHO_UNDERWATER_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS;
        }
        else if(block.is(ModTags.Blocks.ECHO_RARE_UNDERWATER_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE;
        }
        else if(block.is(ModTags.Blocks.ECHO_ULTRA_RARE_UNDERWATER_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE;
        }
        else if(block.is(ModTags.Blocks.ECHO_SPONGE_NOISES))
        {
            soundToPlay = SoundEvents.SPONGE_ABSORB;
        }
        else if(block.is(ModTags.Blocks.ECHO_BASALT_DELTA_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS.value();
        }
        else if(block.is(ModTags.Blocks.ECHO_SOUL_SAND_VALLEY_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS.value();
        }
        else if(block.is(ModTags.Blocks.ECHO_SOUL_SAND_VALLEY_MOOD_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD.value();
        }
        else if(block.is(ModTags.Blocks.ECHO_CRIMSON_FOREST_MOOD_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD.value();
        }
        else if(block.is(ModTags.Blocks.ECHO_CRIMSON_FOREST_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS.value();
        }
        else if(block.is(ModTags.Blocks.ECHO_WARPED_FOREST_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS.value();
        }
        else if(block.is(ModTags.Blocks.ECHO_WARPED_FOREST_MOOD_NOISES))
        {
            soundToPlay = SoundEvents.AMBIENT_WARPED_FOREST_MOOD.value();
        }

        return soundToPlay;
    }
}
