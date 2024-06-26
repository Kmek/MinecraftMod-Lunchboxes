package com.kmek.klunchbox.block.entity;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.ModBlocksInit;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, KLunchboxMod.MODID);

    /**
     * Lunchbox Block Entities
     */

    public static final RegistryObject<BlockEntityType<LunchboxBlockEntity>> LUNCHBOX =
            BLOCK_ENTITIES.register("lunchbox", () ->
                    BlockEntityType.Builder.of(LunchboxBlockEntity::new,
                            ModBlocksInit.BASE_LUNCHBOX.get(),
                            ModBlocksInit.WHITE_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.LIGHT_GRAY_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.GRAY_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.BLACK_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.BROWN_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.RED_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.ORANGE_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.YELLOW_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.LIME_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.GREEN_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.CYAN_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.LIGHT_BLUE_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.BLUE_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.PURPLE_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.MAGENTA_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.PINK_WOOL_LUNCHBOX.get(),
                            ModBlocksInit.CREEPER_LUNCHBOX.get(),
                            ModBlocksInit.OVERWORLD_LUNCHBOX.get(),
                            ModBlocksInit.NETHER_LUNCHBOX.get(),
                            ModBlocksInit.END_LUNCHBOX.get()
                    ).build(null));

    /**
     * Registering to the Event Bus
     */
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
