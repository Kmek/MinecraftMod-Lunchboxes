package com.kmek.minecafe.screen;

import com.kmek.minecafe.MineCafeMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MineCafeMod.MODID);

    /**
     * Crafting Station Menus
     */
    public static final RegistryObject<MenuType<WaffleIronMenu>> WAFFLE_IRON_MENU =
            registerMenuType(WaffleIronMenu::new, "waffle_iron_menu");
    public static final RegistryObject<MenuType<CoffeeMachineMenu>> COFFEE_MACHINE_MENU =
            registerMenuType(CoffeeMachineMenu::new, "coffee_machine_menu");
    public static final RegistryObject<MenuType<EspressoMachineMenu>> ESPRESSO_MACHINE_MENU =
            registerMenuType(EspressoMachineMenu::new, "espresso_machine_menu");
    public static final RegistryObject<MenuType<JuicerMenu>> JUICER_MENU =
            registerMenuType(JuicerMenu::new, "juicer_menu");

    /**
     * Display / Decor Menus
     */
    public static final RegistryObject<MenuType<DisplayCaseMenu>> DISPLAY_CASE_MENU =
            registerMenuType(DisplayCaseMenu::new, "display_case_menu");
    public static final RegistryObject<MenuType<CakeStandMenu>> CAKE_STAND_MENU =
            registerMenuType(CakeStandMenu::new, "cake_stand_menu");
    public static final RegistryObject<MenuType<VaseMenu>> VASE_MENU =
            registerMenuType(VaseMenu::new, "vase_menu");
    public static final RegistryObject<MenuType<CashRegisterMenu>> CASH_REGISTER_MENU =
            registerMenuType(CashRegisterMenu::new, "cash_register_menu");
    public static final RegistryObject<MenuType<WallShelfMenu>> WALL_SHELF_MENU =
            registerMenuType(WallShelfMenu::new, "wall_shelf_menu");
    public static final RegistryObject<MenuType<LunchboxMenu>> LUNCHBOX_MENU =
            registerMenuType(LunchboxMenu::new, "lunchbox_menu");

    /**
     * Setup for menu registering
     */
    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>>
        registerMenuType(IContainerFactory<T> factory, String name) {
            return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
