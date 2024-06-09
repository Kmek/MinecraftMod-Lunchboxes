package com.kmek.klunchbox.item;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.ModBlocksInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            KLunchboxMod.MODID);

    /**
     * Registering the event bus
     */
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static RegistryObject<CreativeModeTab> klunchboxTab = CREATIVE_MODE_TABS.register("klunchbox_tab1", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.klunchbox_blocks"))
                    .icon(() -> new ItemStack(ModBlocksInit.CREEPER_LUNCHBOX.get()))
                    .displayItems((params, output) -> {
                        acceptBlocks(output);
                    })
                    .build());

    /*****************************************************************************************************************
     * Blocks
     *****************************************************************************************************************/

    private static void acceptBlocks(CreativeModeTab.Output output) {
        ModBlocksInit.LUNCHBOXES.forEach(reg -> output.accept(reg.get()));

    }
}
