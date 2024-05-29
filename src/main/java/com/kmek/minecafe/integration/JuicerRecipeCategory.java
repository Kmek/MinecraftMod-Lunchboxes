package com.kmek.minecafe.integration;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.recipe.JuicerRecipe;
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

public class JuicerRecipeCategory implements IRecipeCategory<JuicerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(MineCafeMod.MODID, "juicer");
    public final static ResourceLocation TEXTURE = new ResourceLocation(MineCafeMod.MODID, "textures/gui/juicer_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public JuicerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 58, 4, 106, 74);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocksInit.JUICER.get()));
    }

    @Override
    public RecipeType<JuicerRecipe> getRecipeType() {
        return JEIMineCafePlugin.JUICER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Juicer");
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
    public void setRecipe(IRecipeLayoutBuilder builder, JuicerRecipe recipe, IFocusGroup focuses) {
        // Change as needed for recipe
        for (int i = 0; i < 3; i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, 4 + (i * 18), 16).addIngredients(recipe.getIngredients().get(i));
            builder.addSlot(RecipeIngredientRole.INPUT, 4 + (i * 18), 16 + 18).addIngredients(recipe.getIngredients().get(i + 3));
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 89, 57).addIngredients(recipe.getIngredients().get(6));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 68, 57).addItemStack(recipe.getResultItem());
    }
}
