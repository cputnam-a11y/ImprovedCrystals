package com.crystal.block;

import com.crystal.ImprovedCrystals;
import com.crystal.item.ModItemDataComponenets;
import com.crystal.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.Random;

public class DispenserModRegistry {

    public static void register()
    {
        DispenserBlock.registerBehavior(ModItems.EXPERIENCE_FLASK, new OptionalDispenseItemBehavior(){

            @Override
            protected ItemStack execute(BlockSource source, ItemStack dispensed) {
                if(dispensed.getDamageValue() >= 0 && dispensed.getDamageValue() < dispensed.getMaxDamage())
                {
                    ServerLevel level = source.level();
                    ExperienceOrb.award(level, source.center(), 1);
                    dispensed.setDamageValue(dispensed.getDamageValue() + 1);
                    return dispensed;
                }

                return super.execute(source, dispensed);
            }
        });

        DispenserBlock.registerBehavior(ModItems.RESONATING_ECHO_SHARD, new OptionalDispenseItemBehavior(){

            @Override
            protected ItemStack execute(BlockSource source, ItemStack dispensed) {
                if(dispensed.get(ModItemDataComponenets.ITEM_USE_SOUND).soundEvent() != null)
                {
                    ServerLevel level = source.level();
                    Random random = new Random();
                    level.playSound(null, source.pos(), dispensed.get(ModItemDataComponenets.ITEM_USE_SOUND).soundEvent(), SoundSource.BLOCKS, 1f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    return dispensed;
                }

                return super.execute(source, dispensed);
            }
        });

        DispenserBlock.registerProjectileBehavior(ModItems.SILVERFISH_EGG);
    }

}
