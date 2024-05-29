package com.kmek.minecafe.fluid;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.item.ModItemsInit;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, MineCafeMod.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_COFFEE_FLUID = FLUIDS.register("coffee_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.COFFEE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_COFFEE_FLUID = FLUIDS.register("flowing_coffee_water",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.COFFEE_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties COFFEE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.COFFEE_FLUID_TYPE, SOURCE_COFFEE_FLUID, FLOWING_COFFEE_FLUID)
            .slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(ModBlocksInit.COFFEE_FLUID_BLOCK).bucket(ModItemsInit.COFFEE_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
