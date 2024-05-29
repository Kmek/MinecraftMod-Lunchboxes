package com.kmek.minecafe.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kmek.minecafe.MineCafeMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class JuicerRecipe implements Recipe<SimpleContainer> {
    public final ResourceLocation id;
    public final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public JuicerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;

        // If all 7 ingredients match
        for (int i = 0; i < 7; i++) {
            if (!recipeItems.get(i).test(pContainer.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<JuicerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "juicer";
    }

    public static class Serializer implements RecipeSerializer<JuicerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(MineCafeMod.MODID, "juicer");

        @Override
        public JuicerRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(7, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                try {
                    inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
                } catch (Exception e) {
                    inputs.set(i, Ingredient.EMPTY);
                }
            }

            return new JuicerRecipe(pRecipeId, output, inputs);
        }

        @Override
        public @Nullable JuicerRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new JuicerRecipe(pRecipeId, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, JuicerRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient in : pRecipe.getIngredients()) {
                in.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(), false);
        }
    }
}
