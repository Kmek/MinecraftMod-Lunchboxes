package com.kmek.minecafe.block.custom.crop;

import com.kmek.minecafe.item.ModItemsInit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class TilledCropBlock extends CropBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    private final String seedName;

    public TilledCropBlock(String seedName, BlockBehaviour.Properties properties) {
        super(properties);
        this.seedName = seedName;
    }

    public Item getSeed() {
        Optional<RegistryObject<Item>> opt = ModItemsInit.ITEMS.getEntries().stream()
//                    .peek(entry -> System.out.println(entry.get().getDescriptionId()))
                .filter(entry -> entry.get().getDescriptionId().equals(seedName))
                .findFirst();
        return opt.isPresent() ? opt.get().get() : Items.STICK;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return getSeed();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
