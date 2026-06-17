package com.crystal.client.datagen;

import com.crystal.ImprovedCrystals;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DatagenAdvancements extends FabricAdvancementProvider {
    protected DatagenAdvancements(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        Advancement.Builder.advancement()
                .rewards(
                        new AdvancementRewards.Builder()
                                .addRecipe(RecipeBuilder.getDefaultRecipeId(new ItemStackTemplate(Items.BEACON)))
                                .runs(Identifier.fromNamespaceAndPath(ImprovedCrystals.MOD_ID, "got_nether_star"))
                );
    }
}
