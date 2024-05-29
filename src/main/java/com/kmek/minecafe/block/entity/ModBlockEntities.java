package com.kmek.minecafe.block.entity;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.ModBlocksInit;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MineCafeMod.MODID);

    /**
     * Decor / Display Block Entities
     */
    public static final RegistryObject<BlockEntityType<DisplayCaseBlockEntity>> DISPLAY_CASE =
            BLOCK_ENTITIES.register("display_case", () ->
                    BlockEntityType.Builder.of(DisplayCaseBlockEntity::new,
                            ModBlocksInit.DISPLAY_CASE_CURVED.get()).build(null));

    public static final RegistryObject<BlockEntityType<CakeStandBlockEntity>> CAKE_STAND =
            BLOCK_ENTITIES.register("cake_stand", () ->
                    BlockEntityType.Builder.of(CakeStandBlockEntity::new,
                            ModBlocksInit.CAKE_STAND.get()).build(null));

    public static final RegistryObject<BlockEntityType<VaseBlockEntity>> VASE =
            BLOCK_ENTITIES.register("vase", () ->
                    BlockEntityType.Builder.of(VaseBlockEntity::new,
                            ModBlocksInit.VASE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CashRegisterBlockEntity>> CASH_REGISTER =
            BLOCK_ENTITIES.register("cash_register", () ->
                    BlockEntityType.Builder.of(CashRegisterBlockEntity::new,
                            ModBlocksInit.CASH_REGISTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<WallShelfBlockEntity>> WALL_SHELF =
            BLOCK_ENTITIES.register("wall_shelf", () ->
                    BlockEntityType.Builder.of(WallShelfBlockEntity::new,
                            ModBlocksInit.OAK_WALL_SHELF.get(),
                            ModBlocksInit.SPRUCE_WALL_SHELF.get(),
                            ModBlocksInit.BIRCH_WALL_SHELF.get(),
                            ModBlocksInit.JUNGLE_WALL_SHELF.get(),
                            ModBlocksInit.ACACIA_WALL_SHELF.get(),
                            ModBlocksInit.DARK_OAK_WALL_SHELF.get(),
                            ModBlocksInit.MANGROVE_WALL_SHELF.get(),
                            ModBlocksInit.CRIMSON_WALL_SHELF.get(),
                            ModBlocksInit.WARPED_WALL_SHELF.get()).build(null));

    public static final RegistryObject<BlockEntityType<LunchboxBlockEntity>> LUNCHBOX =
            BLOCK_ENTITIES.register("lunchbox", () ->
                    BlockEntityType.Builder.of(LunchboxBlockEntity::new,
                            ModBlocksInit.RED_LUNCHBOX.get(),
                            ModBlocksInit.CREEPER_LUNCHBOX.get(),
                            ModBlocksInit.END_LUNCHBOX.get()).build(null));

    /**
     * Crafting Station Block Entities
     */
    public static final RegistryObject<BlockEntityType<WaffleIronBlockEntity>> WAFFLE_IRON =
            BLOCK_ENTITIES.register("waffle_iron", () ->
                    BlockEntityType.Builder.of(WaffleIronBlockEntity::new,
                            ModBlocksInit.WAFFLE_IRON.get()).build(null));

    public static final RegistryObject<BlockEntityType<CoffeeMachineBlockEntity>> COFFEE_MACHINE =
            BLOCK_ENTITIES.register("coffee_machine", () ->
                    BlockEntityType.Builder.of(CoffeeMachineBlockEntity::new,
                            ModBlocksInit.COFFEE_MACHINE.get()).build(null));

    public static final RegistryObject<BlockEntityType<EspressoMachineBlockEntity>> ESPRESSO_MACHINE =
            BLOCK_ENTITIES.register("espresso_machine", () ->
                    BlockEntityType.Builder.of(EspressoMachineBlockEntity::new,
                            ModBlocksInit.ESPRESSO_MACHINE.get()).build(null));

    public static final RegistryObject<BlockEntityType<JuicerBlockEntity>> JUICER =
            BLOCK_ENTITIES.register("juicer", () ->
                    BlockEntityType.Builder.of(JuicerBlockEntity::new,
                            ModBlocksInit.JUICER.get()).build(null));

    /**
     * Registering to the Event Bus
     */
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
