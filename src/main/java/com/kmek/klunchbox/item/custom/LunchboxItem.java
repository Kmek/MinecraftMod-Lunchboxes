package com.kmek.klunchbox.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class LunchboxItem extends BlockItem {

    public LunchboxItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        try {
            if (stack.hasTag()) {
                CompoundTag tag = stack.getTag();
                String inv = tag.getString("InventoryString");
                if (inv.length() > 0) {
                    Arrays.stream(inv.split((String)"\\$\\$")).forEach(s -> {
                        components.add(Component.literal(s).withStyle(ChatFormatting.GRAY));
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Error, tag exception");
        }
        super.appendHoverText(stack, level, components, flag);
    }
}
