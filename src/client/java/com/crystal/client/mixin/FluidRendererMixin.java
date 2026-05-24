package com.crystal.client.mixin;

import com.crystal.ImprovedCrystals;
import com.crystal.block.ModBlocks;
import com.crystal.item.ModItems;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

    @Inject(method = "isFaceOccludedByNeighbor", at = @At("HEAD"), cancellable = true)
    private static void isFaceOccludedByNeighbor(final Direction direction, final float height, final BlockState neighborState, CallbackInfoReturnable info)
    {
        if(neighborState.is(ModBlocks.SEA_GLASS))
        {
            info.setReturnValue(true);
        }
    }
}
