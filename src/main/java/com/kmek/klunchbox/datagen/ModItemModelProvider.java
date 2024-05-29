package com.kmek.klunchbox.datagen;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.ModBlocksInit;
import com.kmek.klunchbox.item.ModItemsInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, KLunchboxMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        /* ********* ITEMS for BLOCKS ********* */

        // Block items through parent block model
        ModBlocksInit.LUNCHBOXES.forEach(w -> blockItemFromBlockParent(w, "lunchbox/"));
    }

    /**
     * Helper Functions
     */

    private ItemModelBuilder itemFromExplicitPath(String path, String textureFolder, String parent) {
        return withExistingParent(path, new ResourceLocation(parent))
                .texture("layer0", new ResourceLocation(KLunchboxMod.MODID, textureFolder + path));
    }

    private ItemModelBuilder itemFromItemPath(String path, String textureFolder, String parent) {
        return withExistingParent(path, new ResourceLocation(parent))
                .texture("layer0", new ResourceLocation(KLunchboxMod.MODID, "item/" + textureFolder + path));
    }

    private ItemModelBuilder simpleItemFromPath(String path, String textureFolder) {
        return itemFromItemPath(path, textureFolder, "item/generated");
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item, String textureFolder) {
        return simpleItemFromPath(item.getId().getPath(), textureFolder);
    }

    private ItemModelBuilder simpleItemFromBlock(RegistryObject<Block> block, String textureFolder) {
        return simpleItemFromPath(block.getId().getPath(), textureFolder);
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item, String textureFolder) {
        return itemFromItemPath(item.getId().getPath(), textureFolder, "item/handheld");
    }

     private ItemModelBuilder blockItemFromBlockParent(RegistryObject<Block> block, String parentBlockModelFolder) {
         String path = block.getId().getPath();
         return withExistingParent(path, new ResourceLocation(KLunchboxMod.MODID, "block/" + parentBlockModelFolder + path));
     }
}
