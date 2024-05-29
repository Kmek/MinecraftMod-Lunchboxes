package com.kmek.minecafe.recipe;

import com.kmek.minecafe.MineCafeMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MineCafeMod.MODID);

    public static final RegistryObject<RecipeSerializer<EspressoMachineRecipe>> ESPRESSO_MACHINE_SERIALIZER =
            SERIALIZERS.register("espresso_machine", () -> EspressoMachineRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CoffeeMachineRecipe>> COFFEE_MACHINE_SERIALIZER =
            SERIALIZERS.register("coffee_machine", () -> CoffeeMachineRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<WaffleIronRecipe>> WAFFLE_IRON_SERIALIZER =
            SERIALIZERS.register("waffle_iron", () -> WaffleIronRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<JuicerRecipe>> JUICER_SERIALIZER =
            SERIALIZERS.register("juicer", () -> JuicerRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
