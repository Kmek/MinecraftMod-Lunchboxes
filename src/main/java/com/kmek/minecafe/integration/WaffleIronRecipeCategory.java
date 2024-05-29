package com.kmek.minecafe.integration;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.recipe.WaffleIronRecipe;
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

public class WaffleIronRecipeCategory implements IRecipeCategory<WaffleIronRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(MineCafeMod.MODID, "waffle_iron");
    public final static ResourceLocation TEXTURE = new ResourceLocation(MineCafeMod.MODID, "textures/gui/waffle_iron_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public WaffleIronRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 41, 20, 93, 48);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksInit.WAFFLE_IRON.get()));
    }

    @Override
    public RecipeType<WaffleIronRecipe> getRecipeType() {
        return JEIMineCafePlugin.WAFFLE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Waffle Iron");
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
    public void setRecipe(IRecipeLayoutBuilder builder, WaffleIronRecipe recipe, IFocusGroup focuses) {
        // Change as needed for recipe
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 16).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 47, 30).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 75, 2).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 47, 2).addItemStack(recipe.getResultItem());
    }
}
