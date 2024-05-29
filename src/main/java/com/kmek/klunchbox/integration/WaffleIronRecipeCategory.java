package com.kmek.klunchbox.integration;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.ModBlocksInit;
import com.kmek.klunchbox.recipe.WaffleIronRecipe;
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
    public final static ResourceLocation UID = new ResourceLocation(KLunchboxMod.MODID, "waffle_iron");
    public final static ResourceLocation TEXTURE = new ResourceLocation(KLunchboxMod.MODID, "textures/gui/waffle_iron_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public WaffleIronRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 41, 20, 93, 48);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksInit.WAFFLE_IRON.get()));
    }

    @Override
    public RecipeType<WaffleIronRecipe> getRecipeType() {
        return JEIKLunchboxPlugin.WAFFLE_TYPE;
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
