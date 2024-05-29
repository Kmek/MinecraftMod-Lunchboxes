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
        ModBlocksInit.CAKE_BLOCKS.forEach(reg -> cakeModels(reg.getId().getPath(), "block/cake/"));
        ModBlocksInit.CAKE_BLOCKS_VELVET.forEach(reg -> cakeModels(reg.getId().getPath(), "block/cake/"));
        ModBlocksInit.LUNCHBOXES.forEach(reg -> lunchboxModels(reg.getId().getPath(), "block/lunchbox/"));
    }

    private BlockModelBuilder cakeModelSingleTextureFile(String name, String appendFileName, String textureFolder, String parent) {
        return withExistingParent("block/cake/" + name + appendFileName, new ResourceLocation(KLunchboxMod.MODID, parent))
                .texture("texture", new ResourceLocation(KLunchboxMod.MODID, textureFolder + name));
    }

    private void cakeModels(String name, String textureFolder) {
        cakeModelSingleTextureFile(name, "", textureFolder, "block/cake/cake");
        cakeModelSingleTextureFile(name, "_slice1", textureFolder, "block/cake/cake_slice1");
        cakeModelSingleTextureFile(name, "_slice2", textureFolder, "block/cake/cake_slice2");
        cakeModelSingleTextureFile(name, "_slice3", textureFolder, "block/cake/cake_slice3");
        cakeModelSingleTextureFile(name, "_slice4", textureFolder, "block/cake/cake_slice4");
        cakeModelSingleTextureFile(name, "_slice5", textureFolder, "block/cake/cake_slice5");
        cakeModelSingleTextureFile(name, "_slice6", textureFolder, "block/cake/cake_slice6");
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