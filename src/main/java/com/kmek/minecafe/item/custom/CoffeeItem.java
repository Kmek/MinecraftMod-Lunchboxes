package com.kmek.minecafe.item.custom;

import com.kmek.minecafe.item.ModItemsInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.Objects;

public class CoffeeItem extends CustomDrinkItem {
    public CoffeeItem(int nutrition, float saturation, int caffeineLevel, Item itemToDrop) {
        super(new Item.Properties().food(
                new FoodProperties.Builder()
                        .alwaysEat()
                        .nutrition(nutrition)
                        .saturationMod(saturation)
                        .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200 * caffeineLevel, caffeineLevel - 1), 0.75f)
                        .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 100 * caffeineLevel, caffeineLevel - 1), caffeineLevel > 1 ? 0.5f: 0)
                        .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 140 * caffeineLevel, caffeineLevel - 1), caffeineLevel > 2 ? 0.15f * caffeineLevel: 0)
                        .effect(() -> new MobEffectInstance(MobEffects.HARM, 200 * caffeineLevel, caffeineLevel - 1), caffeineLevel == 5 ? 0.1f * caffeineLevel: 0)
                        .build()));
        toReturnAfterUse = Objects.requireNonNullElseGet(itemToDrop, ModItemsInit.MUG::get);
    }

    public CoffeeItem(int nutrition, float saturation, int caffeineLevel, Item itemToDrop, Component tooltip) {
        this(nutrition, saturation, caffeineLevel, itemToDrop);
        this.tooltip = tooltip;
    }
}
