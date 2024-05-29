package com.kmek.klunchbox.item;

import com.kmek.klunchbox.KLunchboxMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemsInit {
    /**
     * Registry
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KLunchboxMod.MODID);
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
