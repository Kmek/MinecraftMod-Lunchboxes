package com.kmek.klunchbox.item;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.fluid.ModFluids;
import com.kmek.klunchbox.item.custom.*;
import com.kmek.klunchbox.item.registery.CropsEnums;
import com.kmek.klunchbox.datagen.FileDataLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.kmek.klunchbox.datagen.FileDataLoader.*;

public class ModItemsInit {
    /**
     * Registry
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KLunchboxMod.MODID);
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final Map<RegistryObject<Item>, Float> compostableItems = new HashMap<>();
    public static RegistryObject<Item> compostable(float rate, RegistryObject<Item> reg) {
        compostableItems.put(reg, rate);
        return reg;
    }

    /**
     * Fluid Block Item
     */
    public static final RegistryObject<Item> COFFEE_BUCKET = ITEMS.register("coffee_bucket",
            () -> new BucketItem(ModFluids.SOURCE_COFFEE_FLUID, new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

    /**
     * Tools
     */
    public static final RegistryObject<Item> CROP_INSPECTOR_ITEM = ITEMS.register("crop_inspector",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_FORTUNE_COOKIE_PICKAXE = ITEMS.register("golden_fortune_cookie_pickaxe",
            () -> new PickaxeItem(ModItemTiers.GOLDEN_FORTUNE_COOKIE, 1, -2.8F, new Item.Properties()));

    /**
     * Raw Crops
     */
    public static final List<RegistryObject<Item>> CROP_ITEMS = Arrays.stream(CropsEnums.fruits)
            .filter(fruit -> fruit != CropsEnums.APPLE)
            .map(fruit -> compostable(0.65F, ITEMS.register(fruit.toString(), () -> new Item(new Item.Properties().food(
                new FoodProperties.Builder().nutrition(3).saturationMod(1f).build())))))
            .toList();
    public static final List<RegistryObject<Item>> NUTS = Arrays.stream(CropsEnums.nuts)
            .map(nut -> compostable(0.65F, ITEMS.register(nut.toString(), () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(2).saturationMod(1f).build())))))
            .toList();


    /*******************************************************************************************************************
     * INGREDIENTS
     ******************************************************************************************************************/
    public static final List<RegistryObject<Item>> INGREDIENTS =
            new FileDataLoader("registration_data/ingredients.txt").read().stream()
                    .map(args -> {
                        if (args.size() == 1)
                            return ITEMS.register(args.get(0), () -> new Item(new Item.Properties()));
                        else //if (args.size() == 3)
                            return ITEMS.register(args.get(0), () -> new Item(new Item.Properties().food(
                                    new FoodProperties.Builder()
                                            .nutrition(Integer.parseInt(args.get(1)))
                                            .saturationMod(Float.parseFloat(args.get(2))).build())));
                    }).toList();
    public static final List<RegistryObject<Item>> CREAMS = regListItems("creams.txt", "", "_cream", ITEMS, foodFixedArgs.apply(0, 0.3f));
    public static final List<RegistryObject<Item>> JAMS = regListItems("jams.txt", "", "_jam", ITEMS, foodFixedArgs.apply(1, 0.4f));

    public static final RegistryObject<Item> BATTER_MESS = ITEMS.register("batter_mess", () -> new Item(new Item.Properties().food(
        new FoodProperties.Builder().nutrition(0).saturationMod(0)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 200, 0), 0.8f)
            .build())) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    components.add(Component.literal("Consume raw eggs at your own risk").withStyle(ChatFormatting.YELLOW));
                    super.appendHoverText(stack, level, components, flag);
            }});
    public static final RegistryObject<Item> BURNT_CRISP = ITEMS.register("burnt_crisp", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder().nutrition(1).saturationMod(0.5f)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 0), 1.0f)
                    .build())));


    /*******************************************************************************************************************
     * DISHES
     ******************************************************************************************************************/
    public static final RegistryObject<Item> CLEAR_CUP = ITEMS.register("clear_cup", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MUG = ITEMS.register("mug", () -> new Item(new Item.Properties()));
//    public static final RegistryObject<Item> ICE_TRAY = ITEMS.register("ice_tray", () -> new Item(new Item.Properties().stacksTo(1)));
//    public static final RegistryObject<Item> ICE_TRAY_WATER = ITEMS.register("ice_tray_water", () -> new Item(new Item.Properties().stacksTo(1)));
//    public static final RegistryObject<Item> ICE_TRAY_ICE = ITEMS.register("ice_tray_ice", () -> new Item(new Item.Properties().stacksTo(1).craftRemainder(ICE_TRAY.get())));


    /*******************************************************************************************************************
     * FOODS
     ******************************************************************************************************************/
    public static final List<RegistryObject<Item>> YOGURTS = regListItems("yogurts.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> PUDDINGS = regListItems("puddings.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> CANDY = regListItems("candy.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> BROWNIES = regListItems("brownies.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> BARS = regListItems("bars.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> MUFFINS = regListItems("muffins.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> CANNOLIS = regListItems("creams.txt", "", "_cannoli", ITEMS, foodFixedArgs.apply(2, 0.5f));

    // Cakes
    public static final List<RegistryObject<Item>> CAKES = regListItems("cakes.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> CHEESECAKES = regListItems("cheesecakes.txt", ITEMS, food2Args);

    // Breads
    public static final List<RegistryObject<Item>> BREADS_PASTRIES = regListItems("breads_pastries.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> BREADS_LOAVES = regListItems("breads_loaves.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> BREADS_TOASTS = regListItems("breads_toasts.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> BREADS = Stream.of(BREADS_PASTRIES, BREADS_LOAVES, BREADS_TOASTS).flatMap(Collection::stream).toList();

    // Pies
    public static final List<RegistryObject<Item>> PIES_MISC = regListItems("pies.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> MEAT_PIES = regListItems("pies_meat.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> FRUIT_PIES = regListItems("pies_fruit.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> CREAM_PIES = regListItems("pies_cream.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> PIES = Stream.of(PIES_MISC, MEAT_PIES, FRUIT_PIES, CREAM_PIES).flatMap(Collection::stream).toList();

    // Cookies
    public static final List<RegistryObject<Item>> COOKIES = regListItems("cookies.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> CRINKLE_COOKIES = regListItems("cookies_crinkle.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> THUMBPRINT_COOKIES = regListItems("cookies_thumbprint.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> MACARONS = regListItems("macarons.txt", ITEMS, food2Args);
    public static final List<RegistryObject<Item>> COOKIES_ALL = Stream.of(COOKIES, CRINKLE_COOKIES, THUMBPRINT_COOKIES, MACARONS).flatMap(Collection::stream).toList();
    public static final RegistryObject<Item> FORTUNE_COOKIE = ITEMS.register("fortune_cookie", () -> new FortuneCookieItem(false));
    public static final RegistryObject<Item> GOLDEN_FORTUNE_COOKIE = ITEMS.register("golden_fortune_cookie", () -> new FortuneCookieItem(true));
    // todo make golden and diamond macarons have special effects?

    /**
     * Waffles
     */
    public static final RegistryObject<Item> RAW_WAFFLE_BATTER = ITEMS.register("raw_waffle_batter", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder().nutrition(0).saturationMod(0)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 300, 0), 0.6f)
                    .build())) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
            components.add(Component.literal("Consume raw eggs at your own risk").withStyle(ChatFormatting.YELLOW));
            super.appendHoverText(stack, level, components, flag);
        }
    });
    public static final RegistryObject<Item> TAIYAKI = ITEMS.register("taiyaki", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> EGG_WAFFLE = ITEMS.register("egg_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> PAW_WAFFLE = ITEMS.register("paw_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> CLASSIC_WAFFLE = ITEMS.register("classic_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> CREEPER_WAFFLE = ITEMS.register("creeper_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> HEART_WAFFLE = ITEMS.register("heart_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> PUMPKIN_WAFFLE = ITEMS.register("pumpkin_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> MUSHROOM_WAFFLE = ITEMS.register("mushroom_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> FLOWER_WAFFLE = ITEMS.register("flower_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> DIAMOND_WAFFLE = ITEMS.register("diamond_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> PICKAXE_WAFFLE = ITEMS.register("pickaxe_waffle", () -> new WaffleItem(2, 0.8f));
    public static final RegistryObject<Item> SWORD_WAFFLE = ITEMS.register("sword_waffle", () -> new WaffleItem(2, 0.8f));
    public static final List<RegistryObject<Item>> WAFFLES = List.of(
            TAIYAKI, EGG_WAFFLE, PAW_WAFFLE, CLASSIC_WAFFLE, CREEPER_WAFFLE, HEART_WAFFLE, PUMPKIN_WAFFLE,
            MUSHROOM_WAFFLE, FLOWER_WAFFLE, DIAMOND_WAFFLE, PICKAXE_WAFFLE, SWORD_WAFFLE);
    public static final List<RegistryObject<Item>> WAFFLE_MOLDS = List.of(
            ITEMS.register("taiyaki_mold", () -> new WaffleMoldItem(TAIYAKI.get())),
            ITEMS.register("egg_waffle_mold", () -> new WaffleMoldItem(EGG_WAFFLE.get())),
            ITEMS.register("paw_waffle_mold", () -> new WaffleMoldItem(PAW_WAFFLE.get())),
            ITEMS.register("classic_waffle_mold", () -> new WaffleMoldItem(CLASSIC_WAFFLE.get())),
            ITEMS.register("creeper_waffle_mold", () -> new WaffleMoldItem(CREEPER_WAFFLE.get())),
            ITEMS.register("heart_waffle_mold", () -> new WaffleMoldItem(HEART_WAFFLE.get())),
            ITEMS.register("pumpkin_waffle_mold", () -> new WaffleMoldItem(PUMPKIN_WAFFLE.get())),
            ITEMS.register("mushroom_waffle_mold", () -> new WaffleMoldItem(MUSHROOM_WAFFLE.get())),
            ITEMS.register("flower_waffle_mold", () -> new WaffleMoldItem(FLOWER_WAFFLE.get())),
            ITEMS.register("diamond_waffle_mold", () -> new WaffleMoldItem(DIAMOND_WAFFLE.get())),
            ITEMS.register("pickaxe_waffle_mold", () -> new WaffleMoldItem(PICKAXE_WAFFLE.get())),
            ITEMS.register("sword_waffle_mold", () -> new WaffleMoldItem(SWORD_WAFFLE.get()))
        );

    /**
     * Smores & Marshmallows
     */
    public static final RegistryObject<Item> MARSHMALLOW = ITEMS.register("marshmallow", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).build())));
    public static final RegistryObject<Item> MARSHMALLOWS = ITEMS.register("marshmallows", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).alwaysEat()
                    .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 60, 0), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0), 1.0f)
                    .build())));
    public static final RegistryObject<Item> MARSHMALLOW_PUFF = ITEMS.register("marshmallow_puff", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder().nutrition(1).saturationMod(0.5f).build())));
    public static final RegistryObject<Item> MARSHMALLOW_ON_STICK = ITEMS.register("marshmallow_on_stick",
            () -> new MarshmallowStickItem());
//    public static final RegistryObject<Item> MARSHMALLOW_ON_STICK_SCORCHED = ITEMS.register("marshmallow_on_stick_scorched",
//            () -> new MarshmallowStickItem(5, BURNT_CRISP.get()));
//    public static final RegistryObject<Item> MARSHMALLOW_ON_STICK_BURNT = ITEMS.register("marshmallow_on_stick_burnt",
//            () -> new MarshmallowStickItem(5, MARSHMALLOW_ON_STICK_SCORCHED.get()));
//    public static final RegistryObject<Item> MARSHMALLOW_ON_STICK_WELL_DONE = ITEMS.register("marshmallow_on_stick_well_done",
//            () -> new MarshmallowStickItem(5, MARSHMALLOW_ON_STICK_BURNT.get()));
//    public static final RegistryObject<Item> MARSHMALLOW_ON_STICK_GOLDEN = ITEMS.register("marshmallow_on_stick_golden",
//            () -> new MarshmallowStickItem(10, MARSHMALLOW_ON_STICK_WELL_DONE.get()));
//    public static final RegistryObject<Item> MARSHMALLOW_ON_STICK_TOASTED = ITEMS.register("marshmallow_on_stick_toasted",
//            () -> new MarshmallowStickItem(10, MARSHMALLOW_ON_STICK_GOLDEN.get()));
//    public static final RegistryObject<Item> MARSHMALLOW_ON_STICK_WARM = ITEMS.register("marshmallow_on_stick_warm",
//            () -> new MarshmallowStickItem(10, MARSHMALLOW_ON_STICK_TOASTED.get()));
//    public static final RegistryObject<Item> MARSHMALLOW_ON_STICK = ITEMS.register("marshmallow_on_stick",
//            () -> new MarshmallowStickItem(5, MARSHMALLOW_ON_STICK_WARM.get()));
    public static final List<RegistryObject<Item>> SMORES = Arrays.stream(new String[]{"warm", "toasted", "golden", "well_done", "burnt", "scorched"})
            .map(str -> ITEMS.register("smore_" + str, () -> new Item(new Item.Properties().food(
                        new FoodProperties.Builder().nutrition(5).saturationMod(2f).build())))
            ).toList();


    /*******************************************************************************************************************
     * DRINKS
     ******************************************************************************************************************/
    public static final Function<ArrayList<String>, Supplier<Item>> drink2Args = args ->
            () -> (Item) new CustomDrinkItem(
                    Integer.parseInt(args.get(1)),
                    Float.parseFloat(args.get(2)), CLEAR_CUP.get());

    public static final List<RegistryObject<Item>> FRUIT_JUICES = regListItems("juices.txt", ITEMS, drink2Args);
    public static final List<RegistryObject<Item>> LEMONADES = regListItems("lemonades.txt", ITEMS, drink2Args);
    public static final List<RegistryObject<Item>> MISC_DRINKS = regListItems("misc_drinks.txt", ITEMS, drink2Args);
    public static final List<RegistryObject<Item>> FLAVORED_MILKS = regListItems("flavored_milks.txt", ITEMS, drink2Args);
    public static final List<RegistryObject<Item>> FLAVORED_WATERS = regListItems("flavored_waters.txt", ITEMS, drink2Args);

    // Teas
    public static final RegistryObject<Item> MATCHA_TEA = ITEMS.register("matcha_tea",
            () -> new CoffeeItem(1, 0.5f, 1, null));

    /**
     * Bubble Milk Teas
     */
    public static final List<RegistryObject<Item>> BOBA_MILK_TEAS =
        new FileDataLoader("registration_data/boba_milk_teas.txt").read().stream().map(
            args -> ITEMS.register(args.get(0) + "_milk_tea",
                    () -> (Item) new MilkTeaItem(Integer.parseInt(args.get(1)), Float.parseFloat(args.get(2))))
        ).toList();
    // Funky tea flavors with effects
    public static final List<RegistryObject<Item>> SPECIAL_BOBA_MILK_TEAS = List.of(
    ITEMS.register("glow_berry_milk_tea",
            () -> new MilkTeaItem(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(2).saturationMod(0.5f)
                            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 600, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0), 1.0f)
                            .alwaysEat().build()),
                    Component.literal("Glowing!").withStyle(ChatFormatting.YELLOW))),
    ITEMS.register("phantom_milk_tea",
            () -> new MilkTeaItem(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(1).saturationMod(0.4f)
                            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 700, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 1000, 0), 1.0f)
                            .alwaysEat().build()),
                    Component.literal("A good pick-me-up in the morning").withStyle(ChatFormatting.YELLOW))),
    ITEMS.register("blazing_milk_tea",
            () -> new MilkTeaItem(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(0).saturationMod(0f)
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0f)
                            .alwaysEat().build()),
                    Component.literal("Now with authentic firey taste").withStyle(ChatFormatting.YELLOW))),
    ITEMS.register("darkside_milk_tea",
            () -> new MilkTeaItem(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(0).saturationMod(0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DARKNESS, 800, 0), 1.0f)
                            .alwaysEat().build()),
                    Component.literal("Join the dark side").withStyle(ChatFormatting.YELLOW))),
    ITEMS.register("wet_milk_tea",
            () -> new MilkTeaItem(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(0).saturationMod(0f)
                            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 800, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 800, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0), 1.0f)
                            .alwaysEat().build()),
                    Component.literal("Underwater vibes").withStyle(ChatFormatting.YELLOW))),
    ITEMS.register("imaginary_milk_tea",
        () -> new MilkTeaItem(new Item.Properties().food(
                new FoodProperties.Builder().nutrition(0).saturationMod(0f)
                        .effect(() -> new MobEffectInstance(MobEffects.INVISIBILITY, 800, 0), 1.0f)
                        .alwaysEat().build()),
                Component.literal("It's there, I promise!").withStyle(ChatFormatting.YELLOW))));

    /**
     * Coffee Stuff
     */
    public static final RegistryObject<Item> COFFEE_BEANS_ROASTED = compostable(0.5F, ITEMS.register("coffee_beans_roasted", () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> COFFEE_GROUNDS = compostable(0.6F, ITEMS.register("coffee_grounds", () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> COFFEE_FILTER = ITEMS.register("coffee_filter", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COFFEE_FILTER_USED = compostable(0.5F, ITEMS.register("coffee_filter_used", () -> new Item(new Item.Properties())));
    public static final RegistryObject<Item> COFFEE_POT = ITEMS.register("coffee_pot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COFFEE_POT_FULL = ITEMS.register("coffee_pot_full",
            () -> new Item(new Item.Properties().craftRemainder(COFFEE_POT.get())));
    public static final RegistryObject<Item> ESPRESSO_SHOT = ITEMS.register("espresso_shot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEAMED_MILK = ITEMS.register("steamed_milk", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MILK_FOAM = ITEMS.register("milk_foam", () -> new Item(new Item.Properties()));

    public static final List<RegistryObject<Item>> COFFEES =
            new FileDataLoader("registration_data/coffees.txt").read().stream().map(
                    args -> ITEMS.register(args.get(0),
                            () -> (Item) new CoffeeItem(
                                    Integer.parseInt(args.get(1)),
                                    Float.parseFloat(args.get(2)),
                                    Integer.parseInt(args.get(3)),
                                    Integer.parseInt(args.get(4)) == 1 ? CLEAR_CUP.get() : null))
            ).toList();
    public static final List<RegistryObject<Item>> SPECIAL_COFFEES = List.of(
        ITEMS.register("red_eye_coffee", () -> new CoffeeItem(0, 0, 3, null,
                    Component.literal("Careful there!").withStyle(ChatFormatting.DARK_RED))),
        ITEMS.register("black_eye_coffee", () -> new CoffeeItem(0, 0, 4, null,
                    Component.literal("Has a lot of caffeine...").withStyle(ChatFormatting.DARK_RED))),
        ITEMS.register("dead_eye_coffee", () -> new CoffeeItem(0, 0, 5, null,
                    Component.literal("Too much caffeine...").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.OBFUSCATED))));
}
