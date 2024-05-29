package com.kmek.minecafe.item.custom;

import com.kmek.minecafe.item.ModItemsInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MilkTeaItem extends CustomDrinkItem {
    public MilkTeaItem(Item.Properties properties) {
        super(properties);
        this.toReturnAfterUse = ModItemsInit.CLEAR_CUP.get();
    }

    public MilkTeaItem(Item.Properties properties, Component tooltip) {
        super(properties, tooltip);
        this.toReturnAfterUse = ModItemsInit.CLEAR_CUP.get();
    }

    public MilkTeaItem(int nutrition, float saturation) {
        super(nutrition, saturation);
        this.toReturnAfterUse = ModItemsInit.CLEAR_CUP.get();
    }

    public MilkTeaItem(int nutrition, float saturation, Component tooltip) {
        super(nutrition, saturation, tooltip);
        this.toReturnAfterUse = ModItemsInit.CLEAR_CUP.get();
    }

    // Cure all harmful potions the same way milk does
    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        livingEntity.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}
