package com.kmek.minecafe.fluid;

import com.kmek.minecafe.MineCafeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation SOAP_OVERLAY_RL = new ResourceLocation(MineCafeMod.MODID, "misc/in_soap_water"); // Unsure what this is used for

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MineCafeMod.MODID);

    public static final RegistryObject<FluidType> COFFEE_FLUID_TYPE = register("coffee_fluid",
            FluidType.Properties.create().lightLevel(0).density(1000).viscosity(6000) // todo figure out of density is actually changing
                    .supportsBoating(true).motionScale(0.01));
//                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK);
//                    .sound(SoundAction.get("ambient"), SoundEvents.WATER_AMBIENT));
//                    .sound(SoundAction.get("swim"), SoundEvents.PLAYER_SWIM));


    /**
     * Registering
     */

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, SOAP_OVERLAY_RL,
                0xFF40250B, new Vector3f(64f / 255f, 37f / 255f, 11f / 255f), properties));
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
