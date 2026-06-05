package com.crystal.entity;

import com.crystal.ImprovedCrystals;
import com.crystal.item.ItemUseSoundComponent;
import com.crystal.item.ModItemDataComponenets;
import com.crystal.item.ModItems;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.camel.CamelHusk;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
import net.minecraft.world.entity.animal.equine.Donkey;
import net.minecraft.world.entity.animal.equine.Mule;
import net.minecraft.world.entity.animal.equine.SkeletonHorse;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import net.minecraft.world.entity.animal.golem.CopperGolemState;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.animal.panda.Panda;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.pig.PigSoundVariant;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.entity.monster.illager.Vindicator;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.skeleton.Bogged;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.WeatheringCopper;

import java.util.Random;

public class ModEntityCallbackEvents {

    public static void silenceMobs()
    {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            Random random = new Random();
            ItemStack item = player.getItemInHand(hand);

            if(item.is(Items.ECHO_SHARD) && !entity.isSilent()) {
                var rEchoShard = new ItemStack(ModItems.RESONATING_ECHO_SHARD);
                SoundEvent sound = getMobSound(entity);
                if(sound != null) {
                    rEchoShard.set(ModItemDataComponenets.ITEM_USE_SOUND, new ItemUseSoundComponent(sound, entity.getType()));
                    rEchoShard.applyComponents(item.getComponentsPatch());
                    entity.setSilent(true);
                    item.shrink(1);
                    player.addItem(rEchoShard);
                    player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 1F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
            else if(item.is(ModItems.RESONATING_ECHO_SHARD) && entity.isSilent() && item.get(ModItemDataComponenets.ITEM_USE_SOUND).entityType() != null && item.get(ModItemDataComponenets.ITEM_USE_SOUND).entityType() == entity.getType())
            {
                item.shrink(1);
                entity.setSilent(false);
                ItemStack stack = new ItemStack(Items.ECHO_SHARD);
                stack.applyComponents(item.getComponentsPatch());
                player.addItem(stack);
                player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 1F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        });
    }

    public static void register()
    {
        silenceMobs();
    }


    private static SoundEvent getMobSound(Entity mob)
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
            Armadillo armadillo = (Armadillo) mob;

            if(armadillo.isScared())
            {
                return SoundEvents.ARMADILLO_ROLL;
            }

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
            Bat bat = (Bat)mob;

            if(bat.isResting())
            {
                return SoundEvents.BAT_TAKEOFF;
            }

            return SoundEvents.BAT_AMBIENT;
        }
        else if(mob.is(EntityType.CAMEL))
        {
            Camel camel = (Camel)mob;

            if(camel.isDashing())
            {
                return SoundEvents.CAMEL_DASH;
            }
            else if(camel.isCamelSitting())
            {
                return SoundEvents.CAMEL_SIT;
            }

            return SoundEvents.CAMEL_AMBIENT;
        }
        else if(mob.is(EntityType.CAMEL_HUSK))
        {
            CamelHusk camelHusk = (CamelHusk)mob;

            if(camelHusk.isDashing())
            {
                return SoundEvents.CAMEL_HUSK_DASH;
            }
            else if(camelHusk.isCamelSitting())
            {
                return SoundEvents.CAMEL_HUSK_SIT;
            }

            return SoundEvents.CAMEL_HUSK_AMBIENT;
        }
        else if(mob.is(EntityType.CAT))
        {
            Cat cat = (Cat) mob;

            if(!cat.isBaby()) {
                return cat.get(DataComponents.CAT_SOUND_VARIANT).value().adultSounds().ambientSound().value();
            }
            else
            {
                return cat.get(DataComponents.CAT_SOUND_VARIANT).value().babySounds().ambientSound().value();
            }
        }
        else if(mob.is(EntityType.CHICKEN))
        {
            Chicken chicken = (Chicken) mob;
            if(!chicken.isBaby()) {
                return chicken.get(DataComponents.CHICKEN_SOUND_VARIANT).value().adultSounds().ambientSound().value();
            }
            else
            {
                return chicken.get(DataComponents.CHICKEN_SOUND_VARIANT).value().babySounds().ambientSound().value();
            }
        }
        else if(mob.is(EntityType.COD))
        {
            return SoundEvents.COD_FLOP;
        }
        else if(mob.is(EntityType.COPPER_GOLEM))
        {
            CopperGolem copperGolem = (CopperGolem) mob;

            if(copperGolem.getState() == CopperGolemState.DROPPING_NO_ITEM)
            {
                return SoundEvents.COPPER_GOLEM_ITEM_NO_DROP;
            }
            else if(copperGolem.getState() == CopperGolemState.DROPPING_ITEM)
            {
                return SoundEvents.COPPER_GOLEM_ITEM_DROP;
            }
            else if(copperGolem.getState() == CopperGolemState.GETTING_ITEM)
            {
                return SoundEvents.COPPER_GOLEM_ITEM_GET;
            }
            else if(copperGolem.getState() == CopperGolemState.GETTING_NO_ITEM)
            {
                return SoundEvents.COPPER_GOLEM_ITEM_NO_GET;
            }

            if(copperGolem.getWeatherState() == WeatheringCopper.WeatherState.OXIDIZED)
            {
                return SoundEvents.COPPER_GOLEM_OXIDIZED_SPIN;
            }
            else if(copperGolem.getWeatherState() == WeatheringCopper.WeatherState.WEATHERED)
            {
                return SoundEvents.COPPER_GOLEM_WEATHERED_SPIN;
            }

            return SoundEvents.COPPER_GOLEM_SPIN;
        }
        else if(mob.is(EntityType.COW))
        {
            Cow cow = (Cow)mob;
            return cow.get(DataComponents.COW_SOUND_VARIANT).value().ambientSound().value();
        }
        else if(mob.is(EntityType.MOOSHROOM))
        {
            return SoundEvents.MOOSHROOM_MILK;
        }
        else if(mob.is(EntityType.DONKEY))
        {
            Donkey donkey = (Donkey) mob;

            if(donkey.hasChest())
            {
                return SoundEvents.DONKEY_CHEST;
            }

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
            HappyGhast happyGhast = (HappyGhast) mob;
            if(happyGhast.isBaby())
            {
                return SoundEvents.GHASTLING_AMBIENT;
            }

            return SoundEvents.HAPPY_GHAST_AMBIENT;
        }
        else if(mob.is(EntityType.HORSE))
        {
            return SoundEvents.HORSE_AMBIENT;
        }
        else if(mob.is(EntityType.MULE))
        {
            Mule mule = (Mule) mob;

            if(mule.hasChest())
            {
                return SoundEvents.MULE_CHEST;
            }

            return SoundEvents.MULE_AMBIENT;
        }
        else if(mob.is(EntityType.OCELOT))
        {
            return SoundEvents.OCELOT_AMBIENT;
        }
        else if(mob.is(EntityType.PARROT))
        {
            Parrot parrot = (Parrot) mob;
            return parrot.getAmbientSound();
        }
        else if(mob.is(EntityType.PIG))
        {
            Pig pig = (Pig) mob;
            if(!pig.isBaby()) {
                return pig.get(DataComponents.PIG_SOUND_VARIANT).value().adultSounds().ambientSound().value();
            }
            else
            {
                return pig.get(DataComponents.PIG_SOUND_VARIANT).value().babySounds().ambientSound().value();
            }
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
            if(mob.isUnderWater())
            {
                return SoundEvents.SKELETON_HORSE_GALLOP_WATER;
            }

            return SoundEvents.SKELETON_HORSE_AMBIENT;
        }
        else if(mob.is(EntityType.SNIFFER))
        {
            Sniffer sniffer = (Sniffer)mob;

            if(sniffer.isSearching())
            {
                return SoundEvents.SNIFFER_SEARCHING;
            }

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
            Dolphin dolphin = (Dolphin) mob;

            if(dolphin.gotFish())
            {
                return SoundEvents.DOLPHIN_EAT;
            }
            else if(dolphin.getMoistnessLevel() <= 0)
            {
                return SoundEvents.DOLPHIN_HURT;
            }
            else if(dolphin.isUnderWater())
            {
                return SoundEvents.DOLPHIN_AMBIENT_WATER;
            }

            return SoundEvents.DOLPHIN_AMBIENT;
        }
        else if(mob.is(EntityType.DROWNED))
        {
            return SoundEvents.DROWNED_AMBIENT;
        }
        else if(mob.is(EntityType.ENDERMAN))
        {
            EnderMan enderman = (EnderMan) mob;

            if(enderman.isCreepy())
            {
                return SoundEvents.ENDERMAN_SCREAM;
            }
            if(enderman.getCarriedBlock() != null)
            {
                return SoundEvents.ENDERMAN_TELEPORT;
            }

            return SoundEvents.ENDERMAN_AMBIENT;
        }
        else if(mob.is(EntityType.FOX))
        {
            Fox fox = (Fox)mob;

            if(fox.isPouncing())
            {
                return SoundEvents.FOX_SCREECH;
            }
            else if(fox.isInterested())
            {
                return SoundEvents.FOX_SNIFF;
            }
            else if(fox.isSleeping())
            {
                return SoundEvents.FOX_SLEEP;
            }
            else if(fox.isSitting())
            {
                return SoundEvents.FOX_EAT;
            }
            else if(fox.isCrouching())
            {
                return SoundEvents.FOX_AGGRO;
            }
            else if(fox.isFullyCrouched())
            {
                return SoundEvents.FOX_BITE;
            }
            else if(fox.isFaceplanted())
            {
                return SoundEvents.FOX_TELEPORT;
            }

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
            PolarBear polarBear = (PolarBear)mob;

            if(polarBear.isStanding())
            {
                return SoundEvents.POLAR_BEAR_WARNING;
            }
            else if(polarBear.isBaby())
            {
                return SoundEvents.POLAR_BEAR_AMBIENT_BABY;
            }

            return SoundEvents.POLAR_BEAR_AMBIENT;
        }
        else if(mob.is(EntityType.PUFFERFISH))
        {
            return SoundEvents.PUFFER_FISH_BLOW_UP;
        }
        else if(mob.is(EntityType.WOLF))
        {
            Wolf wolf = (Wolf) mob;
            if(!wolf.isBaby()) {
                return wolf.get(DataComponents.WOLF_SOUND_VARIANT).value().adultSounds().ambientSound().value();
            }
            else
            {
                return wolf.get(DataComponents.WOLF_SOUND_VARIANT).value().babySounds().ambientSound().value();
            }
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
            if(!mob.isUnderWater())
            {
                return SoundEvents.ELDER_GUARDIAN_FLOP;
            }

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
            Ghast ghast = (Ghast)mob;

            if(ghast.isCharging())
            {
                return SoundEvents.GHAST_SCREAM;
            }

            return SoundEvents.GHAST_AMBIENT;
        }
        else if(mob.is(EntityType.GUARDIAN))
        {
            if(!mob.isUnderWater())
            {
                return SoundEvents.GUARDIAN_FLOP;
            }

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
        else if(mob instanceof EnderDragonPart)
        {
            return SoundEvents.ENDER_DRAGON_GROWL;
        }
        else if(mob.is(EntityType.WITHER))
        {
            WitherBoss wither = (WitherBoss) mob;
            if(wither.getInvulnerableTicks() > 0)
            {
                return SoundEvents.WITHER_SPAWN;
            }
            else if(wither.isPowered())
            {
                return SoundEvents.WITHER_HURT;
            }

            return SoundEvents.WITHER_AMBIENT;
        }
        else if(mob.is(EntityType.ILLUSIONER))
        {
            return SoundEvents.ILLUSIONER_AMBIENT;
        }


        return null;
    }
}
