package com.kmek.minecafe.loot;

import com.kmek.minecafe.MineCafeMod;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MineCafeMod.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("add_item_tenth_chance", AddItemModifierTenthChance.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_TAG =
            LOOT_MODIFIER_SERIALIZERS.register("add_tag_chance", AddTagModifierChance.CODEC);
    // Don't forget to also add loot_modifier json file to data/forge/loot_modifiers/global_loot_modifiers.json!

    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
