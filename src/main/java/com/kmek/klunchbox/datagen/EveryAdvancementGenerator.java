package com.kmek.klunchbox.datagen;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.ModBlocksInit;
import com.kmek.klunchbox.item.ModItemsInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Consumer;

public class EveryAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> writer, ExistingFileHelper existingFileHelper) {

        /**
         * DISHES
         */


        /**
         * DRINKS
         */
        final String rootDrinks = "root_drinks";

        anyAndEveryAchievementFromArrayReg(ModItemsInit.COFFEES,
                2, "Any Coffee", "Craft a coffee",
                10, "Every Coffee", "Craft every type of coffee",
                rootDrinks, "coffee", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.FRUIT_JUICES,
                20, "Any Juice", "Craft a juice",
                26, "Every Juice", "Craft every type of juice",
                rootDrinks, "juice", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.LEMONADES,
                4, "Any Lemonade", "Craft a lemonade",
                6, "Every Lemonade", "Craft every type of lemonade",
                rootDrinks, "lemonade", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.BOBA_MILK_TEAS,
                13, "Any Boba Milk Tea", "Craft a boba milk tea",
                32, "Every Boba Milk Tea", "Craft every type of boba milk tea",
                rootDrinks, "boba_milk_tea", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.FLAVORED_MILKS,
                0, "Any Flavored Milk", "Craft a glass of flavored milk",
                5, "Every Flavored Milk", "Craft every type of flavored milk",
                rootDrinks, "flavored_milk", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.FLAVORED_WATERS,
                0, "Any Flavored Water", "Craft a glass of flavored water",
                1, "Every Flavored Water", "Craft every type of flavored water",
                rootDrinks, "flavored_water", writer, existingFileHelper);

        /**
         * FOOD
         */
        final String rootFood = "root_food";

        anyAndEveryAchievementFromArrayReg(ModItemsInit.CANNOLIS,
                37, "Holy Cannoli!", "Craft a cannoli",
                25, "Every Cannoli", "Craft every type of cannoli",
                rootFood, "cannoli", writer, existingFileHelper);

        Advancement any_cake_block = anyAndEveryAchievementFromArrayReg(ModBlocksInit.CAKE_BLOCKS_ALL,
                0, "Any Cake", "Craft a cake",
                16, "Every Cake", "Craft every type of cake",
                rootFood, "cake_blocks", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModBlocksInit.CAKE_BLOCKS_VELVET,
                0, "Any Velvet Cake", "Craft a velvet cake",
                8, "Every Velvet Cake", "Craft every type of velvet cake",
                any_cake_block, "cake_blocks_velvet", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.CAKES,
                0, "Any Snack Cake", "Craft a snack cake",
                3, "Every Snack Cake", "Craft every type of snack cake",
                rootFood, "cake", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.CHEESECAKES,
                0, "Cheese In Cake Form", "Craft a cheesecake",
                10, "Every Cheesecake", "Craft every type of cheesecake",
                rootFood, "cheesecake", writer, existingFileHelper);

        Advancement any_pies = anyAndEveryAchievementFromArrayReg(ModItemsInit.PIES,
                0, "Any Pie", "Craft a pie",
                20, "Every Pie", "Craft every type of pie",
                rootFood, "pies", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.MEAT_PIES,
                1, "A Meat Pie", "Craft a meat pie",
                0, "Every Meat Pie", "Craft every type of meat pie",
                any_pies, "meat_pies", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.FRUIT_PIES,
                0, "A Fruit Pie", "Craft a fruit pie",
                7, "Every Fruit Pie", "Craft every type of fruit pie",
                any_pies, "fruit_pies", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.CREAM_PIES,
                0, "A Cream Pie", "Craft a cream pie",
                4, "Every Cream Pie", "Craft every type of cream pie",
                any_pies, "cream_pies", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.PUDDINGS,
                37, "Any Pudding", "Craft a pudding",
                2, "Every Pudding", "Craft every type of pudding",
                rootFood, "pudding", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.YOGURTS,
                40, "Any Yogurt", "Craft a yogurt",
                2, "Every Yogurt", "Craft every type of yogurt",
                rootFood, "yogurt", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.CANDY,
                0, "Any Candy", "Craft a candy",
                7, "Every Candy", "Craft every type of candy",
                rootFood, "candy", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.MUFFINS,
                0, "Any Muffin", "Craft a muffin",
                17, "Every Muffin", "Craft every type of muffin",
                rootFood, "muffin", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.BROWNIES,
                0, "Any Brownie", "Craft a brownie",
                3, "Every Brownie", "Craft every type of brownie",
                rootFood, "brownie", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.BARS,
                2, "Any Baked Bar", "Craft a baked bar",
                1, "Every Baked Bar", "Craft every type of baked bar",
                rootFood, "bar", writer, existingFileHelper);

        Advancement any_cookies = anyAndEveryAchievementFromArrayReg(ModItemsInit.COOKIES_ALL,
                9, "Any Cookie", "Craft a cookie",
                13, "Every Cookie", "Craft every type of cookie",
                rootFood, "cookie", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.MACARONS,
                42, "Any Macaron", "Craft a macaron",
                53, "Every Macaron", "Craft every type of macaron",
                any_cookies, "macaron", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.THUMBPRINT_COOKIES,
                9, "Any Thumbprint Cookie", "Craft a thumbprint cookie",
                13, "Every Thumbprint Cookie", "Craft every type of thumbprint cookie",
                any_cookies, "thumbprint_cookie", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.CRINKLE_COOKIES,
                3, "Any Crinkle Cookie", "Craft a crinkle cookie",
                5, "Every Crinkle Cookie", "Craft every type of crinkle cookie",
                any_cookies, "crinkle_cookie", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.BREADS_PASTRIES,
                0, "A Pastry", "Craft a bread pastry",
                3, "Every Pastry", "Craft every type of bread pastry",
                rootFood, "bread", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.BREADS_LOAVES,
                1, "Aloaf (Of Bread)", "Craft a bread loaf",
                11, "Every Bread Loaf", "Craft every type of bread loaf",
                rootFood, "loaf", writer, existingFileHelper);
        anyAndEveryAchievementFromArrayReg(ModItemsInit.BREADS_TOASTS,
                0, "A Toast", "Craft toast",
                8, "Every Toast", "Craft every type of toast",
                rootFood, "toast", writer, existingFileHelper);

        /**
         * INGREDIENTS
         */
        final String rootIngredients = "root_ingredients";

        anyAndEveryAchievementFromArrayReg(ModItemsInit.CREAMS,
                0, "Any Flavored Cream", "Craft a flavored cream",
                17, "Every Flavored Cream", "Craft every type of flavored cream",
                rootIngredients, "flavored_cream", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.JAMS,
                2, "That's My Jam!", "Craft a jam",
                24, "Every Jam", "Craft every type of jam",
                rootIngredients, "jam", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.CROP_ITEMS,
                1, "Any Fruit", "Find a fruit",
                7, "Every Fruit", "Find every type of fruit",
                rootIngredients, "fruit", writer, existingFileHelper);

        anyAndEveryAchievementFromArrayReg(ModItemsInit.NUTS,
                0, "A Nut!", "Find a nut",
                2, "Aw Nuts!", "Find every type of nut",
                rootIngredients, "nuts", writer, existingFileHelper);
    }

    private <T extends ItemLike> Advancement anyAndEveryAchievementFromArrayReg(List<RegistryObject<T>> itemList,
                                       int iconIndexAny, String titleAny, String descriptionAny,
                                       int iconIndexEvery, String titleEvery, String descriptionEvery,
                                       String parentOfAny, String fileName, Consumer<Advancement> writer, ExistingFileHelper existingFileHelper) {
        Advancement any = anyAchievementFromArrayReg(itemList, iconIndexAny, titleAny, descriptionAny,
                parentOfAny, "any_" + fileName, writer, existingFileHelper);
        Advancement every = everyAchievementFromArrayReg(itemList, iconIndexEvery, titleEvery, descriptionEvery,
                any, "every_" + fileName, writer, existingFileHelper);
        return any;
    }
    private <T extends ItemLike> Advancement anyAndEveryAchievementFromArrayReg(List<RegistryObject<T>> itemList,
                                        int iconIndexAny, String titleAny, String descriptionAny,
                                        int iconIndexEvery, String titleEvery, String descriptionEvery,
                                        Advancement parentOfAny, String fileName, Consumer<Advancement> writer, ExistingFileHelper existingFileHelper) {
        Advancement any = anyAchievementFromArrayReg(itemList, iconIndexAny, titleAny, descriptionAny,
                parentOfAny, "any_" + fileName, writer, existingFileHelper);
        Advancement every = everyAchievementFromArrayReg(itemList, iconIndexEvery, titleEvery, descriptionEvery,
                any, "every_" + fileName, writer, existingFileHelper);
        return any;
    }

    private <T extends ItemLike> Advancement anyAchievementFromArrayReg(List<RegistryObject<T>> itemList,
                                       int iconIndex, String title, String description, String parentAdv,
                                       String fileName, Consumer<Advancement> writer, ExistingFileHelper existingFileHelper) {
        return criterionFromArrayRegs(itemList, Advancement.Builder.advancement()
                .parent(new ResourceLocation(KLunchboxMod.MODID, parentAdv))
                .display(itemList.get(iconIndex).get(),
                        Component.literal(title),
                        Component.literal(description),
                        null, FrameType.GOAL, true, true, false))
                .requirements(new String [][]{itemList.stream().map(reg -> "trigger_" + reg.get().toString()).toArray(String[]::new)})
                .save(writer, new ResourceLocation(KLunchboxMod.MODID, fileName), existingFileHelper);
    }
    private <T extends ItemLike> Advancement anyAchievementFromArrayReg(List<RegistryObject<T>> itemList,
                                        int iconIndex, String title, String description, Advancement parentAdv,
                                        String fileName, Consumer<Advancement> writer, ExistingFileHelper existingFileHelper) {
        return criterionFromArrayRegs(itemList, Advancement.Builder.advancement()
                .parent(parentAdv)
                .display(itemList.get(iconIndex).get(),
                        Component.literal(title),
                        Component.literal(description),
                        null, FrameType.GOAL, true, true, false))
                .requirements(new String [][]{itemList.stream().map(reg -> "trigger_" + reg.get().toString()).toArray(String[]::new)})
                .save(writer, new ResourceLocation(KLunchboxMod.MODID, fileName), existingFileHelper);
    }

    private <T extends ItemLike> Advancement everyAchievementFromArrayReg(List<RegistryObject<T>> itemList,
                                       int iconIndex, String title, String description, Advancement parentAdv,
                                       String fileName, Consumer<Advancement> writer, ExistingFileHelper existingFileHelper) {
        return criterionFromArrayRegs(itemList, Advancement.Builder.advancement()
                .parent(parentAdv)
                .display(itemList.get(iconIndex).get(),
                        Component.literal(title),
                        Component.literal(description),
                        null, FrameType.CHALLENGE, true, true, false))
                .save(writer, new ResourceLocation(KLunchboxMod.MODID, fileName), existingFileHelper);
    }
    private <T extends ItemLike> Advancement everyAchievementFromArrayReg(List<RegistryObject<T>> itemList,
                                       int iconIndex, String title, String description, String parentAdv,
                                       String fileName, Consumer<Advancement> writer, ExistingFileHelper existingFileHelper) {
        return criterionFromArrayRegs(itemList, Advancement.Builder.advancement()
                .parent(new ResourceLocation(KLunchboxMod.MODID, parentAdv))
                .display(itemList.get(iconIndex).get(),
                        Component.literal(title),
                        Component.literal(description),
                        null, FrameType.CHALLENGE, true, true, false))
                .save(writer, new ResourceLocation(KLunchboxMod.MODID, fileName), existingFileHelper);
    }

    private <T extends ItemLike> Advancement.Builder criterionFromArrayRegs(List<RegistryObject<T>> itemList, Advancement.Builder builder) {
        itemList.forEach(reg -> {
            builder.addCriterion("trigger_" + reg.get().toString(), triggerForItem(reg));
        });

        return builder;
    }

    private <T extends ItemLike> CriterionTriggerInstance triggerForItem(RegistryObject<T> item) {
        return triggerForItem(item.get());
    }
    private CriterionTriggerInstance triggerForItem(ItemLike item) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY,
                new ItemPredicate[]{
                        ItemPredicate.Builder.item().of(item).build()
                });
    }
}
