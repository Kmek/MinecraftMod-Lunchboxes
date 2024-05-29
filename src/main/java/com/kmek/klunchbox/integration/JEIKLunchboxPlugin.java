package com.kmek.klunchbox.integration;

import com.kmek.klunchbox.KLunchboxMod;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIKLunchboxPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(KLunchboxMod.MODID, "jei_plugin");
    }

}
