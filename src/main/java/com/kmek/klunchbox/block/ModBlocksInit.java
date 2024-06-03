package com.kmek.klunchbox.block;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.custom.LunchboxBlock;
import com.kmek.klunchbox.item.ModItemsInit;
import com.kmek.klunchbox.item.custom.LunchboxItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocksInit {
    public static final DeferredRegister<Item> ITEMS = ModItemsInit.ITEMS;
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KLunchboxMod.MODID);
    // Register
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    // Will be rendered as cutout later
    public static final List<RegistryObject<Block>> renderAsCutout = new ArrayList<>();
    public static RegistryObject<Block> asCutout(RegistryObject<Block> reg) {
        renderAsCutout.add(reg);
        return reg;
    }

    // Will be rendered as translucent later
    public static final List<RegistryObject<Block>> renderAsTranslucent = new ArrayList<>();
    public static RegistryObject<Block> asTranslucent(RegistryObject<Block> reg) {
        renderAsTranslucent.add(reg);
        return reg;
    }

    /**
     * Setup Helper Functions
     */
    // Same name block and item
    private static <T extends Block> RegistryObject<T> registerBlockItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }
    // Different name for block and item
    private static <T extends Block> RegistryObject<T> registerItemNameBlockItem(String blockName, String itemName, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(blockName, block);
        ITEMS.register(itemName, () -> new ItemNameBlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    /**
     * Lunchboxes
     */
    public static RegistryObject<Block> registerLunchboxBlockItem(String name) {
        RegistryObject<Block> toReturn = BLOCKS.register(name,
                () -> new LunchboxBlock(BlockBehaviour.Properties.of(Material.PLANT).noOcclusion().instabreak().sound(SoundType.METAL)));
        ITEMS.register(name,
                () -> new LunchboxItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    // Color Lunchboxes
    public static final RegistryObject<Block> RED_LUNCHBOX = registerLunchboxBlockItem("red_lunchbox");
    // Wool Lunchboxes
    public static final RegistryObject<Block> WHITE_WOOL_LUNCHBOX = registerLunchboxBlockItem("white_wool_lunchbox");

    // Entity Collectible Lunchboxes
    public static final RegistryObject<Block> CREEPER_LUNCHBOX = registerLunchboxBlockItem("creeper_lunchbox");

    // Dimension Collectible Lunchboxes
    public static final RegistryObject<Block> OVERWORLD_LUNCHBOX = registerLunchboxBlockItem("overworld_lunchbox");
    public static final RegistryObject<Block> NETHER_LUNCHBOX = registerLunchboxBlockItem("nether_lunchbox");
    public static final RegistryObject<Block> END_LUNCHBOX = registerLunchboxBlockItem("end_lunchbox");

    public static final List<RegistryObject<Block>> LUNCHBOXES = List.of(RED_LUNCHBOX, WHITE_WOOL_LUNCHBOX, CREEPER_LUNCHBOX, OVERWORLD_LUNCHBOX, NETHER_LUNCHBOX, END_LUNCHBOX);
}
