package com.crystal.mixin;

import com.crystal.ImprovedCrystals;
import com.crystal.attributes.ModAttributes;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InstrumentItem.class)
public class InstrumentItemMixin {
    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/ItemStack;I)V"))
    private void handleUseCooldown(ItemCooldowns instance, ItemStack item, int time, Operation<Void> original, final Level level, final Player player) {
        var cooldown = (player.getAttributeValue(ModAttributes.COOLDOWN_TIME) + 2) * 20.0F;
        ImprovedCrystals.LOGGER.info("Cooldown: " + cooldown);
        original.call(instance, item, Mth.floor(cooldown));
    }
}
