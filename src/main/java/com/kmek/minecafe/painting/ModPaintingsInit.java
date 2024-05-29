package com.kmek.minecafe.painting;

import com.kmek.minecafe.MineCafeMod;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintingsInit {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, MineCafeMod.MODID);

    public static final RegistryObject<PaintingVariant> TAIYAKI = PAINTING_VARIANTS.register("taiyaki", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> TAIYAKI_MOLD = PAINTING_VARIANTS.register("taiyaki_mold", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> BOBA_SIGN = PAINTING_VARIANTS.register("boba_sign", () -> new PaintingVariant(48, 16));
    public static final RegistryObject<PaintingVariant> BROWN_SUGAR_MILK_TEA = PAINTING_VARIANTS.register("brown_sugar_milk_tea", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> MILK_TEAS_TRIO = PAINTING_VARIANTS.register("milk_teas_trio", () -> new PaintingVariant(32, 32));

    public static void register(IEventBus eventBus) {
        PAINTING_VARIANTS.register(eventBus);
    }
}
