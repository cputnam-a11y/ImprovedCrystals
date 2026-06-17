package com.crystal.mixin;

import com.crystal.ImprovedCrystals;
import com.crystal.attributes.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.InstrumentComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(InstrumentItem.class)
public class InstrumentItemMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(final Level level, final Player player, final InteractionHand hand, CallbackInfoReturnable info) {
        ItemStack itemStack = player.getItemInHand(hand);
        Optional<? extends Holder<Instrument>> instrumentHolder = getInstrument(itemStack);
        if (instrumentHolder.isPresent()) {
            Instrument instrument = (Instrument)((Holder)instrumentHolder.get()).value();
            player.startUsingItem(hand);
            play(level, player, instrument);
            Holder<Attribute> cooldown = ModAttributes.COOLDOWN_TIME;
            ImprovedCrystals.LOGGER.info("Cooldown: " + (player.getAttributeValue(cooldown) + 2) * 20.0F);
            player.getCooldowns().addCooldown(itemStack, Mth.floor((player.getAttributeValue(cooldown) + 2) * 20.0F));
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            info.setReturnValue(InteractionResult.CONSUME);
        } else {
            info.setReturnValue(InteractionResult.FAIL);
        }
    }

    @Overwrite
    private static Optional<Holder<Instrument>> getInstrument(final ItemStack itemStack) {
        InstrumentComponent instrument = (InstrumentComponent)itemStack.get(DataComponents.INSTRUMENT);
        return instrument != null ? Optional.of(instrument.instrument()) : Optional.empty();
    }

    @Overwrite
    private static void play(final Level level, final Player player, final Instrument instrument) {
        SoundEvent soundEvent = (SoundEvent)instrument.soundEvent().value();
        Holder<Attribute> extraRange = ModAttributes.INSTRUMENT_SOUND_RANGE;
        float volume = instrument.range() + (float)player.getAttributeValue(extraRange) / 16.0F;
        level.playSound(player, player, soundEvent, SoundSource.RECORDS, volume, 1.0F);
        level.gameEvent(GameEvent.INSTRUMENT_PLAY, player.position(), GameEvent.Context.of(player));
    }
}
