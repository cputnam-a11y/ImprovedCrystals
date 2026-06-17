package com.crystal;

import com.crystal.attributes.ModAttributes;
import com.crystal.block.DispenserModRegistry;
import com.crystal.block.ModBlocks;
import com.crystal.block.entities.ModBlockEntityTypes;
import com.crystal.entity.ModEntityCallbackEvents;
import com.crystal.entity.ModEntityTypes;
import com.crystal.gamerules.ModGameRules;
import com.crystal.item.ModFuelItems;
import com.crystal.item.ModItems;
import com.crystal.ui.ModCreativeInventory;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImprovedCrystals implements ModInitializer {
	public static final String MOD_ID = "improved-crystals";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModBlocks.initialize();
		ModItems.initialize();
		ModEntityTypes.registerModEntityTypes();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ModCreativeInventory.IMPROVED_CRYSTALS_TAB_KEY, ModCreativeInventory.IMPROVED_CRYSTALS_TAB);
		ModBlockEntityTypes.initalize();
		ModAttributes.initialize();
		DispenserModRegistry.register();
		ModEntityCallbackEvents.register();
		ModGameRules.register();
		ModFuelItems.initialize();
	}
}