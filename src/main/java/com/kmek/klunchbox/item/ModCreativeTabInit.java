package com.kmek.klunchbox.item;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.ModBlocksInit;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModCreativeTabInit {
    public static CreativeModeTab klunchboxTab;

    /**
     * Registering the event bus
     */
    public void register(IEventBus modEventBus) {
        modEventBus.addListener(this::registerCustomCreativeTab);
    }

    private void registerCustomCreativeTab(CreativeModeTabEvent.Register event) {
        klunchboxTab = event.registerCreativeModeTab(new ResourceLocation(KLunchboxMod.MODID, "klunchbox_tab1"),
            builder -> builder
                .title(Component.translatable("itemGroup.klunchbox_blocks"))
                .icon(() -> new ItemStack(ModBlocksInit.CREEPER_LUNCHBOX.get()))
                .displayItems((params, output) -> {
                    acceptBlocks(output);
                })
                .build()
        );
    }

    /*****************************************************************************************************************
     * Blocks
     *****************************************************************************************************************/

    private void acceptBlocks(CreativeModeTab.Output output) {
        ModBlocksInit.LUNCHBOXES.forEach(reg -> output.accept(reg.get()));

    }
}
