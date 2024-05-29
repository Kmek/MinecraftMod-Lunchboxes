package com.kmek.minecafe.tags;

import com.kmek.minecafe.MineCafeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
//        public static final TagKey<Block> NEEDS_ZIRCON_TOOL = tag("needs_zircon_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(MineCafeMod.MODID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> WAFFLE = tag("waffle");
        public static final TagKey<Item> WAFFLE_FILLING = tag("waffle_filling");
        public static final TagKey<Item> WAFFLE_MOLD = tag("waffle_mold");
        public static final TagKey<Item> SEEDS_FROM_GRASS = tag("seeds_from_grass");
        public static final TagKey<Item> SEEDS_FROM_LEAVES = tag("seeds_from_leaves");
        public static final TagKey<Item> CAKE_BLOCKS = tag("cake_blocks");

        // Item tags for advancements
        public static final TagKey<Item> CHEESECAKES = tag("cheesecakes");
        public static final TagKey<Item> CREAMS = tag("creams");
        public static final TagKey<Item> CREAM_PIES = tag("cream_pies");
        public static final TagKey<Item> FRUIT_PIES = tag("fruit_pies");
        public static final TagKey<Item> MEAT_PIES = tag("meat_pies");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(MineCafeMod.MODID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}
