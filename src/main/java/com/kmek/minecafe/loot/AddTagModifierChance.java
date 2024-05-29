package com.kmek.minecafe.loot;

import com.google.common.base.Suppliers;
import com.kmek.minecafe.tags.ModTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class AddTagModifierChance extends LootModifier {
    private static final Map<Item, TagKey<Item>> ITEMS_TO_TAGS = Map.of(
            Items.GRASS, ModTags.Items.SEEDS_FROM_GRASS,
            Items.OAK_LEAVES, ModTags.Items.SEEDS_FROM_LEAVES
    );
    private static final Map<Item, Double> ITEMS_TO_CHANCE = Map.of(
            Items.GRASS, 0.05,
            Items.OAK_LEAVES, 0.005
    );

    public static final Supplier<Codec<AddTagModifierChance>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec()
            .fieldOf("item").forGetter(m -> m.item)).apply(inst, AddTagModifierChance::new)));
    private final Item item;
    private final TagKey<Item> tagKey;
    private final Double chance;


    protected AddTagModifierChance(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
        tagKey = ITEMS_TO_TAGS.getOrDefault(item, ModTags.Items.WAFFLE);
        chance = ITEMS_TO_CHANCE.getOrDefault(item, 1.0);
        // If it starts dropping waffles all the time, something in the setup is wrong
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if(context.getRandom().nextFloat() <= chance) {
            if (tagKey != null) {
                ITag<Item> itag = ForgeRegistries.ITEMS.tags().getTag(tagKey);
                if (itag.size() > 0) {
                    generatedLoot.add(new ItemStack(itag.getRandomElement(context.getRandom()).get()));
                }
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
