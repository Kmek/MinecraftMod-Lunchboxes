package com.kmek.minecafe.item;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.ModBlocksInit;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModCreativeTabInit {
    public static CreativeModeTab minecafeTab;

    /**
     * Registering the event bus
     */
    public void register(IEventBus modEventBus) {
        modEventBus.addListener(this::registerCustomCreativeTab);
    }

    private void registerCustomCreativeTab(CreativeModeTabEvent.Register event) {
        minecafeTab = event.registerCreativeModeTab(new ResourceLocation(MineCafeMod.MODID, "minecafe_tab1"), builder -> builder
            .title(Component.translatable("itemGroup.minecafe_blocks"))
            .icon(() -> new ItemStack(ModBlocksInit.ESPRESSO_MACHINE.get()))
            .displayItems((featureFlags, output, hasOp) -> {
                // Add categories
                acceptBlocks(output);
                acceptCropsAndSeeds(output);
            })
            .build()
        );
        event.registerCreativeModeTab(new ResourceLocation(MineCafeMod.MODID, "minecafe_tab2"), builder -> builder
                .title(Component.translatable("itemGroup.minecafe_ingredients"))
                .icon(() -> new ItemStack(ModItemsInit.INGREDIENTS.get(3).get()))
                .displayItems((featureFlags, output, hasOp) -> {
                    // Add categories
                    acceptIngredients(output);
                    acceptDishes(output);
                })
                .build()
        );
        event.registerCreativeModeTab(new ResourceLocation(MineCafeMod.MODID, "minecafe_tab3"), builder -> builder
                .title(Component.translatable("itemGroup.minecafe_food"))
                .icon(() -> new ItemStack(ModItemsInit.BREADS_PASTRIES.get(0).get()))
                .displayItems((featureFlags, output, hasOp) -> {
                    // Add categories
                    acceptFoods(output);
                })
                .build()
        );
        event.registerCreativeModeTab(new ResourceLocation(MineCafeMod.MODID, "minecafe_tab4"), builder -> builder
                .title(Component.translatable("itemGroup.minecafe_drinks"))
                .icon(() -> new ItemStack(ModItemsInit.BOBA_MILK_TEAS.get(ModItemsInit.BOBA_MILK_TEAS.size() - 1).get()))
                .displayItems((featureFlags, output, hasOp) -> {
                    // Add categories
                    acceptDrinks(output);
                })
                .build()
        );
    }

    /*****************************************************************************************************************
     * Blocks
     *****************************************************************************************************************/

    private void acceptBlocks(CreativeModeTab.Output output) {
        ModBlocksInit.CRAFTING_BLOCKS.forEach(reg -> output.accept(reg.get()));
        ModBlocksInit.DECORATIVE_BLOCKS.forEach(reg -> output.accept(reg.get()));
        ModBlocksInit.LUNCHBOXES.forEach(reg -> output.accept(reg.get()));
//        ModBlocksInit.TO_GO_CONTAINERS.forEach(reg -> output.accept(reg.get()));
        ModBlocksInit.WALL_SHELVES.forEach(reg -> output.accept(reg.get()));

        // Fun Blocks
        output.accept(ModItemsInit.COFFEE_BUCKET.get());
    }

    private void acceptCropsAndSeeds(CreativeModeTab.Output output) {
        output.accept(ModItemsInit.GOLDEN_FORTUNE_COOKIE_PICKAXE.get());
        output.accept(ModItemsInit.CROP_INSPECTOR_ITEM.get());

        acceptFlowerSeeds(output);

        // Seeds
        output.accept(ModBlocksInit.APPLE_CROP_BOTTOM.get());
        ModBlocksInit.FRUIT_TREE_CROPS.stream().forEach(reg -> output.accept(reg.get()));
        output.accept(ModBlocksInit.COFFEE_CROP_BOTTOM.get());
        ModBlocksInit.FRUIT_BUSH_CROPS.stream().forEach(reg -> output.accept(reg.get()));
        output.accept(ModBlocksInit.CASSAVA_CROP.get());
        ModBlocksInit.TILLED_CROPS.stream().forEach(reg -> output.accept(reg.get()));
    }
    private void acceptFlowerSeeds(CreativeModeTab.Output output) {
        ModBlocksInit.FLOWER_CROPS.forEach(reg -> output.accept(reg.get()));
        output.accept(ModBlocksInit.VANILLA_CROP.get());
        output.accept(ModBlocksInit.VANILLA_FLOWER.get());
    }

    /*****************************************************************************************************************
     * Ingredients
     *****************************************************************************************************************/

    private void acceptIngredients(CreativeModeTab.Output output) {
        acceptFruit(output);

        acceptCoreIngreidents(output);

        acceptCreams(output);
        acceptJams(output);
        acceptMarshmallows(output);
        acceptCoffeeIngredients(output);
        acceptMiscIngredients(output);
    }

    private void acceptFruit(CreativeModeTab.Output output) {
        // Fruit
        ModItemsInit.CROP_ITEMS.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptCoreIngreidents(CreativeModeTab.Output output) {
        ModItemsInit.NUTS.forEach(reg -> output.accept(reg.get()));

        ModItemsInit.INGREDIENTS.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptJams(CreativeModeTab.Output output) {
        ModItemsInit.JAMS.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptCreams(CreativeModeTab.Output output) {
        ModItemsInit.CREAMS.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptMarshmallows(CreativeModeTab.Output output) {
        output.accept(ModItemsInit.MARSHMALLOW.get());
        output.accept(ModItemsInit.MARSHMALLOWS.get());
        output.accept(ModItemsInit.MARSHMALLOW_PUFF.get());

        output.accept(ModItemsInit.MARSHMALLOW_ON_STICK.get());
    }

    private void acceptMiscIngredients(CreativeModeTab.Output output) {
        output.accept(ModItemsInit.RAW_WAFFLE_BATTER.get());
        output.accept(ModItemsInit.BATTER_MESS.get());
        output.accept(ModItemsInit.BURNT_CRISP.get());
    }

    private void acceptCoffeeIngredients(CreativeModeTab.Output output) {
        output.accept(ModItemsInit.COFFEE_BEANS_ROASTED.get());
        output.accept(ModItemsInit.COFFEE_GROUNDS.get());
        output.accept(ModItemsInit.COFFEE_POT_FULL.get());
        output.accept(ModItemsInit.COFFEE_FILTER.get());
        output.accept(ModItemsInit.COFFEE_FILTER_USED.get());
        output.accept(ModItemsInit.ESPRESSO_SHOT.get());
        output.accept(ModItemsInit.STEAMED_MILK.get());
        output.accept(ModItemsInit.MILK_FOAM.get());
    }

    private void acceptDishes(CreativeModeTab.Output output) {
        output.accept(ModItemsInit.COFFEE_POT.get());

        output.accept(ModItemsInit.MUG.get());
        output.accept(ModItemsInit.CLEAR_CUP.get());

//        output.accept(ModItemsInit.ICE_TRAY.get());
//        output.accept(ModItemsInit.ICE_TRAY_WATER.get());
//        output.accept(ModItemsInit.ICE_TRAY_ICE.get());

        ModItemsInit.WAFFLE_MOLDS.forEach(reg -> output.accept(reg.get()));
    }

    /*****************************************************************************************************************
     * Foods
     *****************************************************************************************************************/

    private void acceptFoods(CreativeModeTab.Output output) {
        acceptBreads(output);
        acceptWaffles(output);
        acceptCookies(output);
        acceptMiscFoodItems(output);
        acceptCake(output);
        acceptPie(output);
        acceptPudding(output);
        acceptCannolis(output);
        acceptYogurts(output);
        acceptCandy(output);
        acceptSmores(output);
    }

    private void acceptBreads(CreativeModeTab.Output output) {
        ModItemsInit.BREADS_PASTRIES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.BREADS_LOAVES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.BREADS_TOASTS.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptWaffles(CreativeModeTab.Output output) {
        ModItemsInit.WAFFLES.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptCake(CreativeModeTab.Output output) {
        ModBlocksInit.CAKE_BLOCKS.forEach(reg -> output.accept(reg.get()));
        ModBlocksInit.CAKE_BLOCKS_VELVET.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.CAKES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.CHEESECAKES.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptPie(CreativeModeTab.Output output) {
        ModItemsInit.PIES_MISC.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.FRUIT_PIES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.CREAM_PIES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.MEAT_PIES.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptPudding(CreativeModeTab.Output output) {
        ModItemsInit.PUDDINGS.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptYogurts(CreativeModeTab.Output output) {
        ModItemsInit.YOGURTS.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptCookies(CreativeModeTab.Output output) {
        ModItemsInit.COOKIES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.CRINKLE_COOKIES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.THUMBPRINT_COOKIES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.MACARONS.forEach(reg -> output.accept(reg.get()));
        output.accept(ModItemsInit.FORTUNE_COOKIE.get());
        output.accept(ModItemsInit.GOLDEN_FORTUNE_COOKIE.get());
    }

    private void acceptMiscFoodItems(CreativeModeTab.Output output) {
        ModItemsInit.BROWNIES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.BARS.forEach(reg -> output.accept(reg.get()));

        ModItemsInit.MUFFINS.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptCandy(CreativeModeTab.Output output) {
        ModItemsInit.CANDY.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptSmores(CreativeModeTab.Output output) {
        ModItemsInit.SMORES.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptCannolis(CreativeModeTab.Output output) {
        ModItemsInit.CANNOLIS.forEach(reg -> output.accept(reg.get()));
    }

    /*****************************************************************************************************************
     * Drinks
     *****************************************************************************************************************/

    private void acceptDrinks(CreativeModeTab.Output output) {
        acceptFruitDrinks(output);
        acceptCoffeeDrinks(output);
        acceptMilkTeaDrinks(output);
        acceptMiscDrinks(output);
    }

    private void acceptMilkTeaDrinks(CreativeModeTab.Output output) {
        output.accept(ModItemsInit.MATCHA_TEA.get());

        ModItemsInit.BOBA_MILK_TEAS.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.SPECIAL_BOBA_MILK_TEAS.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptCoffeeDrinks(CreativeModeTab.Output output) {
        ModItemsInit.COFFEES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.SPECIAL_COFFEES.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptFruitDrinks(CreativeModeTab.Output output) {
        ModItemsInit.FRUIT_JUICES.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.LEMONADES.forEach(reg -> output.accept(reg.get()));
    }

    private void acceptMiscDrinks(CreativeModeTab.Output output) {
        ModItemsInit.FLAVORED_MILKS.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.FLAVORED_WATERS.forEach(reg -> output.accept(reg.get()));
        ModItemsInit.MISC_DRINKS.forEach(reg -> output.accept(reg.get()));
    }
}
