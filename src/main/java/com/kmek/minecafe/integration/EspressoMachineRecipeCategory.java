package com.kmek.minecafe.integration;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.recipe.EspressoMachineRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EspressoMachineRecipeCategory implements IRecipeCategory<EspressoMachineRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(MineCafeMod.MODID, "espresso_machine");
    public final static ResourceLocation TEXTURE = new ResourceLocation(MineCafeMod.MODID, "textures/gui/espresso_machine_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public EspressoMachineRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 48, 16, 79, 54);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksInit.ESPRESSO_MACHINE.get()));
    }

    @Override
    public RecipeType<EspressoMachineRecipe> getRecipeType() {
        return JEIMineCafePlugin.ESPRESSO_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Espresso Machine");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EspressoMachineRecipe recipe, IFocusGroup focuses) {
        // Change as needed for recipe
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 1).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 11, 19).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 53, 19).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 32, 37).addItemStack(recipe.getResultItem());
    }
}
