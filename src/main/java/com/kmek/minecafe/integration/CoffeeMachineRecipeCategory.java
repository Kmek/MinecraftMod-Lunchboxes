package com.kmek.minecafe.integration;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.recipe.CoffeeMachineRecipe;
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

public class CoffeeMachineRecipeCategory implements IRecipeCategory<CoffeeMachineRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(MineCafeMod.MODID, "coffee_machine");
    public final static ResourceLocation TEXTURE = new ResourceLocation(MineCafeMod.MODID, "textures/gui/coffee_machine_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CoffeeMachineRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 38, 16, 100, 55);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksInit.COFFEE_MACHINE.get()));
    }

    @Override
    public RecipeType<CoffeeMachineRecipe> getRecipeType() {
        return JEIMineCafePlugin.COFFEE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Coffee Machine");
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
    public void setRecipe(IRecipeLayoutBuilder builder, CoffeeMachineRecipe recipe, IFocusGroup focuses) {
        // Change as needed for recipe
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 42, 8).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 42, 30).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 83, 21).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 83, 38).addItemStack(recipe.getResultItem());
    }
}
