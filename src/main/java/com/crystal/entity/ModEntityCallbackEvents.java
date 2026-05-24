package com.crystal.entity;

import com.crystal.ImprovedCrystals;
import com.crystal.item.ItemUseSoundComponent;
import com.crystal.item.ModItemDataComponenets;
import com.crystal.item.ModItems;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.entity.animal.panda.Panda;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.pig.PigSoundVariant;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.entity.monster.illager.Vindicator;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Random;

public class ModEntityCallbackEvents {

    public static void silenceMobs()
    {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            Random random = new Random();
            ItemStack item = player.getItemInHand(hand);
            if(entity instanceof Mob mob && !mob.is(EntityType.PLAYER))
            {
                if(item.is(Items.ECHO_SHARD) && !mob.isSilent()) {
                    var rEchoShard = new ItemStack(ModItems.RESONATING_ECHO_SHARD);
                    rEchoShard.set(ModItemDataComponenets.ITEM_USE_SOUND, new ItemUseSoundComponent(getMobSound(mob), mob.getType()));
                    rEchoShard.applyComponents(item.getComponentsPatch());
                    mob.setSilent(true);
                    item.shrink(1);
                    player.addItem(rEchoShard);
                    player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 1F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    return InteractionResult.SUCCESS;
                }
                else if(item.is(ModItems.RESONATING_ECHO_SHARD) && mob.isSilent() && item.get(ModItemDataComponenets.ITEM_USE_SOUND).entityType() != null && item.get(ModItemDataComponenets.ITEM_USE_SOUND).entityType() == mob.getType())
                {
                    item.shrink(1);
                    mob.setSilent(false);
                    ItemStack stack = new ItemStack(Items.ECHO_SHARD);
                    stack.applyComponents(item.getComponentsPatch());
                    player.addItem(stack);
                    player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 1F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        });
    }

    public static void register()
    {
        silenceMobs();
    }


    private static SoundEvent getMobSound(Mob mob)
    {
        if(mob.is(EntityType.ALLAY))
        {
            Allay allay = (Allay) mob;

            if(allay.hasItemInHand())
            {
                return SoundEvents.ALLAY_AMBIENT_WITH_ITEM;
            }
            else
            {
                return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
            }
        }
        else if(mob.is(EntityType.ARMADILLO))
        {
            return SoundEvents.ARMADILLO_AMBIENT;
        }
        else if(mob.is(EntityType.AXOLOTL))
        {
            Axolotl axolotl = (Axolotl) mob;

            if(axolotl.isInWater())
            {
                return SoundEvents.AXOLOTL_IDLE_WATER;
            }
            else if(axolotl.isPlayingDead())
            {
                return SoundEvents.AXOLOTL_DEATH;
            }
            else
            {
                return SoundEvents.AXOLOTL_IDLE_AIR;
            }
        }
        else if(mob.is(EntityType.BAT))
        {
            return SoundEvents.BAT_AMBIENT;
        }
        else if(mob.is(EntityType.CAMEL))
        {
            return SoundEvents.CAMEL_AMBIENT;
        }
        else if(mob.is(EntityType.CAMEL_HUSK))
        {
            return SoundEvents.CAMEL_HUSK_AMBIENT;
        }
        else if(mob.is(EntityType.CAT))
        {
            return SoundEvents.CAT_SOUNDS.values().stream().findFirst().get().adultSounds().ambientSound().value();
        }
        else if(mob.is(EntityType.CHICKEN))
        {
            return SoundEvents.CHICKEN_SOUNDS.values().stream().findFirst().get().adultSounds().ambientSound().value();
        }
        else if(mob.is(EntityType.COD))
        {
            return SoundEvents.COD_FLOP;
        }
        else if(mob.is(EntityType.COPPER_GOLEM))
        {
            return SoundEvents.COPPER_GOLEM_SPIN;
        }
        else if(mob.is(EntityType.COW))
        {
            return SoundEvents.COW_SOUNDS.values().stream().findFirst().get().ambientSound().value();
        }
        else if(mob.is(EntityType.MOOSHROOM))
        {
            return SoundEvents.MOOSHROOM_MILK;
        }
        else if(mob.is(EntityType.DONKEY))
        {
            return SoundEvents.DONKEY_AMBIENT;
        }
        else if(mob.is(EntityType.FROG))
        {
            return SoundEvents.FROG_AMBIENT;
        }
        else if(mob.is(EntityType.GLOW_SQUID))
        {
            return SoundEvents.GLOW_SQUID_AMBIENT;
        }
        else if(mob.is(EntityType.HAPPY_GHAST))
        {
            return SoundEvents.HAPPY_GHAST_AMBIENT;
        }
        else if(mob.is(EntityType.HORSE))
        {
            return SoundEvents.HORSE_AMBIENT;
        }
        else if(mob.is(EntityType.MULE))
        {
            return SoundEvents.MULE_AMBIENT;
        }
        else if(mob.is(EntityType.OCELOT))
        {
            return SoundEvents.OCELOT_AMBIENT;
        }
        else if(mob.is(EntityType.PARROT))
        {
            return SoundEvents.PARROT_AMBIENT;
        }
        else if(mob.is(EntityType.PIG))
        {
            return SoundEvents.PIG_SOUNDS.values().stream().findFirst().get().adultSounds().ambientSound().value();
        }
        else if(mob.is(EntityType.RABBIT))
        {
            return SoundEvents.RABBIT_AMBIENT;
        }
        else if(mob.is(EntityType.SALMON))
        {
            return SoundEvents.SALMON_FLOP;
        }
        else if(mob.is(EntityType.SHEEP))
        {
            return SoundEvents.SHEEP_AMBIENT;
        }
        else if(mob.is(EntityType.SKELETON_HORSE))
        {
            return SoundEvents.SKELETON_HORSE_AMBIENT;
        }
        else if(mob.is(EntityType.SNIFFER))
        {
            return SoundEvents.SNIFFER_IDLE;
        }
        else if(mob.is(EntityType.SNOW_GOLEM))
        {
            var snowGolem = (SnowGolem)mob;

            if(snowGolem.hasPumpkin()) {
                return SoundEvents.SNOW_GOLEM_HURT;
            }
            else
            {
                return SoundEvents.SNOW_GOLEM_SHEAR;
            }
        }
        else if(mob.is(EntityType.SQUID))
        {
            return SoundEvents.SQUID_AMBIENT;
        }
        else if(mob.is(EntityType.STRIDER))
        {
            return SoundEvents.STRIDER_AMBIENT;
        }
        else if(mob.is(EntityType.TADPOLE))
        {
            return SoundEvents.TADPOLE_FLOP;
        }
        else if(mob.is(EntityType.TROPICAL_FISH))
        {
            return SoundEvents.TROPICAL_FISH_FLOP;
        }
        else if(mob.is(EntityType.TURTLE))
        {
            Turtle turtle = (Turtle) mob;

            if(turtle.isInWater())
            {
                return SoundEvents.TURTLE_SWIM;
            }
            else {
                return SoundEvents.TURTLE_AMBIENT_LAND;
            }
        }
        else if(mob.is(EntityType.VILLAGER))
        {
            return SoundEvents.VILLAGER_AMBIENT;
        }
        else if(mob.is(EntityType.WANDERING_TRADER))
        {
            return SoundEvents.WANDERING_TRADER_AMBIENT;
        }
        else if(mob.is(EntityType.ZOMBIE_HORSE))
        {
            return SoundEvents.ZOMBIE_HORSE_AMBIENT;
        }
        else if(mob.is(EntityType.BEE))
        {
            Bee bee = (Bee)mob;

            if(bee.isAngry())
            {
                return SoundEvents.BEE_STING;
            }
            else
            {
                return SoundEvents.BEE_LOOP;
            }
        }
        else if(mob.is(EntityType.SPIDER))
        {
            return SoundEvents.SPIDER_AMBIENT;
        }
        else if(mob.is(EntityType.CAVE_SPIDER))
        {
            return SoundEvents.SPIDER_HURT;
        }
        else if(mob.is(EntityType.DOLPHIN))
        {
            return SoundEvents.DOLPHIN_AMBIENT;
        }
        else if(mob.is(EntityType.DROWNED))
        {
            return SoundEvents.DROWNED_AMBIENT;
        }
        else if(mob.is(EntityType.ENDERMAN))
        {
            return SoundEvents.ENDERMAN_AMBIENT;
        }
        else if(mob.is(EntityType.FOX))
        {
            return SoundEvents.FOX_AMBIENT;
        }
        else if(mob.is(EntityType.GOAT))
        {
            Goat goat = (Goat)mob;

            if(goat.isScreamingGoat())
            {
                return SoundEvents.GOAT_SCREAMING_AMBIENT;
            }
            else
            {
                return SoundEvents.GOAT_AMBIENT;
            }
        }
        else if(mob.is(EntityType.IRON_GOLEM))
        {
            return SoundEvents.IRON_GOLEM_STEP;
        }
        else if(mob.is(EntityType.LLAMA))
        {
            return SoundEvents.LLAMA_AMBIENT;
        }
        else if(mob.is(EntityType.TRADER_LLAMA))
        {
            return SoundEvents.LLAMA_SPIT;
        }
        else if(mob.is(EntityType.NAUTILUS))
        {
            return SoundEvents.NAUTILUS_AMBIENT;
        }
        else if(mob.is(EntityType.PANDA))
        {
            Panda panda = (Panda) mob;

            if(panda.isAggressive())
            {
                return SoundEvents.PANDA_AGGRESSIVE_AMBIENT;
            }
            else if(panda.isEating())
            {
                return SoundEvents.PANDA_EAT;
            }
            else if(panda.isWorried())
            {
                return SoundEvents.PANDA_WORRIED_AMBIENT;
            }
            else if(panda.isSneezing())
            {
                return SoundEvents.PANDA_SNEEZE;
            }
            else
            {
                return SoundEvents.PANDA_AMBIENT;
            }
        }
        else if(mob.is(EntityType.PIGLIN))
        {
            Piglin piglin = (Piglin) mob;

            if(piglin.isDancing())
            {
                return SoundEvents.PIGLIN_CELEBRATE;
            }
            else
            {
                return SoundEvents.PIGLIN_AMBIENT;
            }
        }
        else if(mob.is(EntityType.POLAR_BEAR))
        {
            return SoundEvents.POLAR_BEAR_AMBIENT;
        }
        else if(mob.is(EntityType.PUFFERFISH))
        {
            return SoundEvents.PUFFER_FISH_BLOW_UP;
        }
        else if(mob.is(EntityType.WOLF))
        {
            return SoundEvents.WOLF_SOUNDS.values().stream().findFirst().get().adultSounds().ambientSound().value();
        }
        else if(mob.is(EntityType.ZOMBIE_NAUTILUS))
        {
            return SoundEvents.ZOMBIE_NAUTILUS_AMBIENT;
        }
        else if(mob.is(EntityType.ZOMBIFIED_PIGLIN))
        {
            return SoundEvents.ZOMBIFIED_PIGLIN_AMBIENT;
        }
        else if(mob.is(EntityType.BLAZE))
        {
            Blaze blaze = (Blaze)mob;
            if(blaze.isOnFire())
            {
                return SoundEvents.BLAZE_BURN;
            }
            else {
                return SoundEvents.BLAZE_AMBIENT;
            }
        }
        else if(mob.is(EntityType.BOGGED))
        {
            Bogged bogged = (Bogged) mob;

            if(bogged.isSheared())
            {
                return SoundEvents.BOGGED_SHEAR;
            }
            else
            {
                return SoundEvents.BOGGED_AMBIENT;
            }
        }
        else if(mob.is(EntityType.BREEZE))
        {
            return SoundEvents.BREEZE_WHIRL;
        }
        else if(mob.is(EntityType.CREAKING))
        {
            return SoundEvents.CREAKING_AMBIENT;
        }
        else if(mob.is(EntityType.CREEPER))
        {
            return SoundEvents.CREEPER_PRIMED;
        }
        else if(mob.is(EntityType.ELDER_GUARDIAN))
        {
            return SoundEvents.ELDER_GUARDIAN_CURSE;
        }
        else if(mob.is(EntityType.ENDERMITE))
        {
            return SoundEvents.ENDERMITE_AMBIENT;
        }
        else if(mob.is(EntityType.EVOKER))
        {
            Evoker evoker = (Evoker)mob;

            if(evoker.isCelebrating())
            {
                return SoundEvents.EVOKER_CELEBRATE;
            }
            else if(evoker.isCastingSpell())
            {
                return SoundEvents.EVOKER_CAST_SPELL;
            }
            else {
                return SoundEvents.EVOKER_AMBIENT;
            }
        }
        else if(mob.is(EntityType.GHAST))
        {
            return SoundEvents.GHAST_AMBIENT;
        }
        else if(mob.is(EntityType.GUARDIAN))
        {
            return SoundEvents.GUARDIAN_AMBIENT;
        }
        else if(mob.is(EntityType.HOGLIN))
        {
            return SoundEvents.HOGLIN_AMBIENT;
        }
        else if(mob.is(EntityType.HUSK))
        {
            return SoundEvents.HUSK_AMBIENT;
        }
        else if(mob.is(EntityType.MAGMA_CUBE))
        {
            return SoundEvents.MAGMA_CUBE_SQUISH;
        }
        else if(mob.is(EntityType.PARCHED))
        {
            return SoundEvents.PARCHED_AMBIENT;
        }
        else if(mob.is(EntityType.PHANTOM))
        {
            return SoundEvents.PHANTOM_AMBIENT;
        }
        else if(mob.is(EntityType.PIGLIN_BRUTE))
        {
            return SoundEvents.PIGLIN_BRUTE_AMBIENT;
        }
        else if(mob.is(EntityType.PILLAGER))
        {
            Pillager pillager = (Pillager) mob;

            if(pillager.isCelebrating())
            {
                return SoundEvents.PILLAGER_CELEBRATE;
            }
            else if(pillager.isChargingCrossbow())
            {
                return SoundEvents.CROSSBOW_SHOOT;
            }
            else {
                return SoundEvents.PILLAGER_AMBIENT;
            }
        }
        else if(mob.is(EntityType.RAVAGER))
        {
            Ravager ravager = (Ravager) mob;

            if(ravager.isCelebrating())
            {
                return SoundEvents.RAVAGER_CELEBRATE;
            }
            else if(ravager.getStunnedTick() > 0)
            {
                return SoundEvents.RAVAGER_STUNNED;
            }
            else if(ravager.getRoarTick() > 0)
            {
                return SoundEvents.RAVAGER_ROAR;
            }
            else {
                return SoundEvents.RAVAGER_AMBIENT;
            }
        }
        else if(mob.is(EntityType.SHULKER))
        {
            return SoundEvents.SHULKER_AMBIENT;
        }
        else if(mob.is(EntityType.SILVERFISH))
        {
            return SoundEvents.SILVERFISH_AMBIENT;
        }
        else if(mob.is(EntityType.SKELETON))
        {
            return SoundEvents.SKELETON_AMBIENT;
        }
        else if(mob.is(EntityType.SLIME))
        {
            return SoundEvents.SLIME_SQUISH;
        }
        else if(mob.is(EntityType.STRAY))
        {
            return SoundEvents.STRAY_AMBIENT;
        }
        else if(mob.is(EntityType.VEX))
        {
            Vex vex = (Vex)mob;

            if(vex.isCharging())
            {
                return SoundEvents.VEX_CHARGE;
            }
            else
            {
                return SoundEvents.VEX_AMBIENT;
            }
        }
        else if(mob.is(EntityType.VINDICATOR))
        {
            Vindicator vindicator = (Vindicator) mob;

            if(vindicator.isCelebrating())
            {
                return SoundEvents.VINDICATOR_CELEBRATE;
            }
            else {
                return SoundEvents.VINDICATOR_AMBIENT;
            }
        }
        else if(mob.is(EntityType.WARDEN))
        {
            return SoundEvents.WARDEN_AMBIENT;
        }
        else if(mob.is(EntityType.WITCH))
        {
            Witch witch = (Witch) mob;

            if(witch.isCelebrating())
            {
                return SoundEvents.WITCH_CELEBRATE;
            }
            else {
                return SoundEvents.WITCH_AMBIENT;
            }
        }
        else if(mob.is(EntityType.WITHER_SKELETON))
        {
            return SoundEvents.WITHER_SKELETON_AMBIENT;
        }
        else if(mob.is(EntityType.ZOGLIN))
        {
            return SoundEvents.ZOGLIN_AMBIENT;
        }
        else if(mob.is(EntityType.ZOMBIE) || mob.is(EntityType.GIANT))
        {
            return SoundEvents.ZOMBIE_AMBIENT;
        }
        else if(mob.is(EntityType.ZOMBIE_VILLAGER))
        {
            return SoundEvents.ZOMBIE_VILLAGER_AMBIENT;
        }
        else if(mob.is(EntityType.ENDER_DRAGON))
        {
            return SoundEvents.ENDER_DRAGON_GROWL;
        }
        else if(mob.is(EntityType.WITHER))
        {
            return SoundEvents.WITHER_SPAWN;
        }
        else if(mob.is(EntityType.ILLUSIONER))
        {
            return SoundEvents.ILLUSIONER_AMBIENT;
        }


        return null;
    }
}
