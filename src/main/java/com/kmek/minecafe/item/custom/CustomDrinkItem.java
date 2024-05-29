package com.kmek.minecafe.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomDrinkItem extends Item {
    protected Item toReturnAfterUse = null;
    protected Component tooltip = null;

    public CustomDrinkItem(Item.Properties pProperties) {
        super(pProperties);
    }

    public CustomDrinkItem(Item.Properties properties, Component tooltip) {
        super(properties);
        this.tooltip = tooltip;
    }

    public CustomDrinkItem(int nutrition, float saturation) {
        super(new Item.Properties().food(
                new FoodProperties.Builder()
                        .nutrition(nutrition)
                        .saturationMod(saturation)
                        .build()));
    }

    public CustomDrinkItem(int nutrition, float saturation, Item remainder) {
        super(new Item.Properties().food(
                new FoodProperties.Builder()
                        .nutrition(nutrition)
                        .saturationMod(saturation)
                        .build())
                .craftRemainder(remainder));
    }

    public CustomDrinkItem(int nutrition, float saturation, Component tooltip) {
        super(new Item.Properties().food(
                new FoodProperties.Builder()
                        .nutrition(nutrition)
                        .saturationMod(saturation)
                        .build()));
        this.tooltip = tooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (tooltip != null) {
            components.add(tooltip);
        }

        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            if (toReturnAfterUse != null) {
//                System.out.println("\n\n" + toReturnAfterUse.getDescriptionId());
                player.addItem(new ItemStack(toReturnAfterUse));
            }
        }

        return livingEntity.eat(level, itemStack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }
}
