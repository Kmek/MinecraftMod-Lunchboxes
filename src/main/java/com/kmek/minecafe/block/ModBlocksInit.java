package com.kmek.minecafe.block;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.custom.*;
import com.kmek.minecafe.block.custom.crop.*;
import com.kmek.minecafe.datagen.FileDataLoader;
import com.kmek.minecafe.fluid.ModFluids;
import com.kmek.minecafe.item.ModItemsInit;
import com.kmek.minecafe.item.custom.LunchboxItem;
import com.kmek.minecafe.item.registery.CropsEnums;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModBlocksInit {
    public static final DeferredRegister<Item> ITEMS = ModItemsInit.ITEMS;
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MineCafeMod.MODID);
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

    // Will be marked as compostable later
    public static final Map<RegistryObject<Block>, Float> compostableItems = new HashMap<>();
    public static RegistryObject<Block> compostable(float rate, RegistryObject<Block> reg) {
        compostableItems.put(reg, rate);
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
    // Create two block crop with seed block
    private static RegistryObject<Block> registerDoubleCropBlockItem(String cropName, String seedName, String fruitName, int resetAge) {
        RegistryObject<Block> bottom = registerItemNameBlockItem(cropName + "_crop_bottom", seedName,
                () -> new CropTreeBottomBlock(cropName + "_crop_top",
                        BlockBehaviour.Properties.of(Material.PLANT).noCollission().destroyTime(1f).sound(SoundType.GRASS)));
        RegistryObject<Block> top = BLOCKS.register(cropName + "_crop_top",
                () -> new CropTreeTopBlock(bottom.get(), fruitName, resetAge,
                        BlockBehaviour.Properties.of(Material.PLANT).noCollission().destroyTime(0.5f).sound(SoundType.GRASS)));
        return bottom;
    }
    private static RegistryObject<Block> registerDoubleCropBlockItem(String cropName, String seedName, Item fruitItem, int resetAge) {
        RegistryObject<Block> bottom = registerItemNameBlockItem(cropName + "_crop_bottom", seedName,
                () -> new CropTreeBottomBlock(cropName + "_crop_top",
                        BlockBehaviour.Properties.of(Material.PLANT).noCollission().destroyTime(1f).sound(SoundType.GRASS)));
        RegistryObject<Block> top = BLOCKS.register(cropName + "_crop_top",
                () -> new CropTreeTopBlock(bottom.get(), fruitItem, resetAge,
                        BlockBehaviour.Properties.of(Material.PLANT).noCollission().destroyTime(0.5f).sound(SoundType.GRASS)));
        return bottom;
    }
    // Create fruit tree blocks and seed item
    private static RegistryObject<Block> registerFruitTree(String fruitName) {
        return registerDoubleCropBlockItem(fruitName, fruitName + "_seeds", "item.minecafe." + fruitName, 5);
    }

    // Create fruit bush block and seed item
    private static RegistryObject<Block> registerFruitBush(String fruitName, int resetAge) {
        return registerItemNameBlockItem(fruitName + "_bush_crop", fruitName + "_seeds",
                () -> new BushCrop("item.minecafe." + fruitName, resetAge,
                        BlockBehaviour.Properties.of(Material.PLANT).noCollission().destroyTime(0.5f).sound(SoundType.GRASS)));
    }

    /**
     * Flower Crop Blocks (+ Seed Items)
     */
    public static final List<RegistryObject<Block>> FLOWER_CROPS = Arrays.stream(new Block[]{Blocks.DANDELION,
                    Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP,
                    Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY})
            .map(block -> {
                String flowerName = block.getDescriptionId().split("minecraft")[1].substring(1);
                RegistryObject<Block> flowerCropBlock = compostable(0.6F, registerItemNameBlockItem(flowerName + "_crop", flowerName + "_seeds",
                        () -> new FlowerCropBlock(flowerName + "_seeds", block, BlockBehaviour.Properties.copy(Blocks.WHEAT).offsetType(BlockBehaviour.OffsetType.XZ))));
                return flowerCropBlock;
            }).toList();
    // Vanilla Flower + Crop
    public static final RegistryObject<Block> VANILLA_FLOWER = compostable(0.5F, registerBlockItem("vanilla_flower",
            () -> new FlowerBlock(MobEffects.SATURATION, 3, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ))));
    public static final RegistryObject<Block> VANILLA_CROP = compostable(0.6F, registerItemNameBlockItem("vanilla_crop", "vanilla_seeds",
            () -> new FlowerCropBlock("vanilla_seeds", VANILLA_FLOWER.get(), BlockBehaviour.Properties.copy(Blocks.WHEAT).offsetType(BlockBehaviour.OffsetType.XZ))));
    // todo make vanilla orchid potted
    // todo follow adding a flower youtube guide? making it spawnable with bonemeal possibly? adding to tags?

    /**
     * Single Block Crops
     */
    // Cassava
    public static final RegistryObject<Block> CASSAVA_CROP = compostable(0.5F, registerItemNameBlockItem("cassava_crop", "cassava_cutting",
            () -> new TilledCropBlock("item.minecafe.cassava_cutting", BlockBehaviour.Properties.copy(Blocks.WHEAT))));
    // Tilled Crops
    public static final List<RegistryObject<Block>> TILLED_CROPS = Arrays.stream(CropsEnums.tilledCrops)
            .map(fruit -> asCutout(compostable(0.65F, registerItemNameBlockItem(fruit + "_crop", fruit + "_seeds",
                    () -> new TilledCropBlock("item.minecafe." + fruit + "_seeds", BlockBehaviour.Properties.copy(Blocks.WHEAT))))))
            .toList();
    // Bush Fruit Crops
    public static final List<RegistryObject<Block>> FRUIT_BUSH_CROPS = Arrays.stream(CropsEnums.bushCrops)
            .map(fruit -> compostable(0.65F, registerFruitBush(fruit.toString(), 3)))
            .toList();

    /**
     * Double Block Crops (+ Seed Items)
     */
    public static final RegistryObject<Block> COFFEE_CROP_BOTTOM = compostable(0.5F,
            registerDoubleCropBlockItem("coffee", "coffee_beans_unroasted", "item.minecafe.coffee_cherries", 7));
    // Fruits
    public static final RegistryObject<Block> APPLE_CROP_BOTTOM = compostable(0.5F, registerDoubleCropBlockItem("apple", "apple_seeds", Items.APPLE, 5));
    public static final List<RegistryObject<Block>> FRUIT_TREE_CROPS = Arrays.stream(CropsEnums.treeCrops)
            .filter(fruit -> fruit != CropsEnums.APPLE)
            .map(fruit -> compostable(0.65F, registerFruitTree(fruit.toString())))
            .toList();

    /**
     * Crafting Blocks (+ Item Versions)
     */
    public static final RegistryObject<Block> WAFFLE_IRON = registerBlockItem("waffle_iron",
            () -> new WaffleIronBlock(BlockBehaviour.Properties.of(Material.DIRT).destroyTime(0.5f).dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> COFFEE_MACHINE = registerBlockItem("coffee_machine",
            () -> new CoffeeMachineBlock(BlockBehaviour.Properties.of(Material.DIRT).destroyTime(0.5f).dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> ESPRESSO_MACHINE = registerBlockItem("espresso_machine",
            () -> new EspressoMachineBlock(BlockBehaviour.Properties.of(Material.DIRT).destroyTime(0.5f).dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> JUICER = asTranslucent(registerBlockItem("juicer",
            () -> new JuicerBlock(BlockBehaviour.Properties.of(Material.DIRT).destroyTime(0.5f).dynamicShape().noOcclusion())));

    public static final List<RegistryObject<Block>> CRAFTING_BLOCKS = List.of(WAFFLE_IRON, COFFEE_MACHINE,
            ESPRESSO_MACHINE, JUICER);

    /**
     * Decorative Blocks
     */
    public static final RegistryObject<Block> DISPLAY_CASE_CURVED = asTranslucent(registerBlockItem("display_case_curved",
            () -> new DisplayCaseBlock(BlockBehaviour.Properties.of(Material.DIRT).destroyTime(0.5f).dynamicShape().noOcclusion())));
    public static final RegistryObject<Block> CAKE_STAND = registerBlockItem("cake_stand",
            () -> new CakeStandBlock(BlockBehaviour.Properties.of(Material.DIRT).destroyTime(0.5f).dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> VASE = asTranslucent(registerBlockItem("vase",
            () -> new VaseBlock(BlockBehaviour.Properties.of(Material.DIRT).destroyTime(0.5f).dynamicShape().noOcclusion())));
    public static final RegistryObject<Block> CASH_REGISTER = asTranslucent(registerBlockItem("cash_register",
            () -> new CashRegisterBlock(BlockBehaviour.Properties.of(Material.DIRT).destroyTime(3.5f).dynamicShape().requiresCorrectToolForDrops().noOcclusion())));

    public static final List<RegistryObject<Block>> DECORATIVE_BLOCKS = List.of(CAKE_STAND, VASE, DISPLAY_CASE_CURVED,
            CASH_REGISTER);

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

    public static final RegistryObject<Block> RED_LUNCHBOX = registerLunchboxBlockItem("red_lunchbox");
    public static final RegistryObject<Block> CREEPER_LUNCHBOX = registerLunchboxBlockItem("creeper_lunchbox");
    public static final RegistryObject<Block> END_LUNCHBOX = registerLunchboxBlockItem("end_lunchbox");

    public static final List<RegistryObject<Block>> LUNCHBOXES = List.of(RED_LUNCHBOX, CREEPER_LUNCHBOX, END_LUNCHBOX);

    /**
     * To-Go Boxes & Containers
     */
//    public static final RegistryObject<Block> TREAT_BOX = registerBlockItem("treat_box",
//            () -> new LunchboxBlock(BlockBehaviour.Properties.of(Material.PLANT).noOcclusion().instabreak().sound(SoundType.WOOD))); // TODO create new class
//    // TODO cake (or pie) box
//    // TODO macaron box
//    // TODO gift bag
//    // TODO drink bag
//
//    public static final List<RegistryObject<Block>> TO_GO_CONTAINERS = List.of(TREAT_BOX);

    /**
     * Wall Shelves
     */
    public static RegistryObject<Block> registerWallShelfBlockItem(String name) {
        return registerBlockItem(name, () -> new WallShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).destroyTime(0.75f).dynamicShape().noOcclusion()));
    }

    public static final RegistryObject<Block> OAK_WALL_SHELF = registerWallShelfBlockItem("oak_wall_shelf");
    public static final RegistryObject<Block> SPRUCE_WALL_SHELF = registerWallShelfBlockItem("spruce_wall_shelf");
    public static final RegistryObject<Block> BIRCH_WALL_SHELF = registerWallShelfBlockItem("birch_wall_shelf");
    public static final RegistryObject<Block> JUNGLE_WALL_SHELF = registerWallShelfBlockItem("jungle_wall_shelf");
    public static final RegistryObject<Block> ACACIA_WALL_SHELF = registerWallShelfBlockItem("acacia_wall_shelf");
    public static final RegistryObject<Block> DARK_OAK_WALL_SHELF = registerWallShelfBlockItem("dark_oak_wall_shelf");
    public static final RegistryObject<Block> MANGROVE_WALL_SHELF = registerWallShelfBlockItem("mangrove_wall_shelf");
    public static final RegistryObject<Block> CRIMSON_WALL_SHELF = registerWallShelfBlockItem("crimson_wall_shelf");
    public static final RegistryObject<Block> WARPED_WALL_SHELF = registerWallShelfBlockItem("warped_wall_shelf");

    public static final List<RegistryObject<Block>> WALL_SHELVES = List.of(OAK_WALL_SHELF, SPRUCE_WALL_SHELF,
            BIRCH_WALL_SHELF, JUNGLE_WALL_SHELF, ACACIA_WALL_SHELF, DARK_OAK_WALL_SHELF, MANGROVE_WALL_SHELF,
            CRIMSON_WALL_SHELF, WARPED_WALL_SHELF);

    /**
     * Cake Blocks
     */
    public static final List<RegistryObject<Block>> CAKE_BLOCKS =
            new FileDataLoader("registration_data/cake_blocks.txt").read().stream()
                    .map(args -> registerBlockItem(args.get(0), () -> (Block) new MyCakeBlock())).toList();
    public static final List<RegistryObject<Block>> CAKE_BLOCKS_VELVET =
            new FileDataLoader("registration_data/cake_blocks_velvet.txt").read().stream()
                    .map(args -> registerBlockItem(args.get(0), () -> (Block) new MyCakeBlock())).toList();
    public static final List<RegistryObject<Block>> CAKE_BLOCKS_ALL = Stream.of(CAKE_BLOCKS, CAKE_BLOCKS_VELVET).flatMap(Collection::stream).toList();

    /**
     * Fluids
     */
    public static final RegistryObject<LiquidBlock> COFFEE_FLUID_BLOCK = BLOCKS.register("coffee_fluid_block",
            () -> new LiquidBlock(ModFluids.SOURCE_COFFEE_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
}
