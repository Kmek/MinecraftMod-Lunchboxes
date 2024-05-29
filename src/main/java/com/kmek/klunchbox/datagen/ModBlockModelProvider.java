package com.kmek.klunchbox.datagen;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.ModBlocksInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider {
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, KLunchboxMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModBlocksInit.LUNCHBOXES.forEach(reg -> lunchboxModels(reg.getId().getPath(), "block/lunchbox/"));
    }

    private void lunchboxModels(String name, String textureFolder) {
        withExistingParent("block/lunchbox/" + name,
                new ResourceLocation(KLunchboxMod.MODID, "block/lunchbox/lunchbox"))
                .texture("outer", new ResourceLocation(KLunchboxMod.MODID, textureFolder + name));
        withExistingParent("block/lunchbox/" + name + "_open",
                new ResourceLocation(KLunchboxMod.MODID, "block/lunchbox/lunchbox_open"))
                .texture("outer", new ResourceLocation(KLunchboxMod.MODID, textureFolder + name + "_open"));
    }
}