package com.kmek.klunchbox.item;

import com.kmek.klunchbox.block.ModBlocksInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabInit {

    /**
     * Registering the event bus
     */

    public static final CreativeModeTab klunchboxTab = new CreativeModeTab("klunchbox_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocksInit.CREEPER_LUNCHBOX.get());
        }
    };
}
