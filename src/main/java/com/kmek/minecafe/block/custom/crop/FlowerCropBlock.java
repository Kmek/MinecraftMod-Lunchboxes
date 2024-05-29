package com.kmek.minecafe.block.custom.crop;

import com.kmek.minecafe.item.ModItemsInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class FlowerCropBlock extends CropBlock {
    private static final int flowerAge = 6;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, flowerAge);

    private final String seeds;
    private final Block flower;

    public FlowerCropBlock(String seeds, Block flower, Properties properties) {
        super(properties);
        this.seeds = "item.minecafe." + seeds;
        this.flower = flower;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        Optional<RegistryObject<Item>> opt = ModItemsInit.ITEMS.getEntries().stream()
//                .peek(entry -> System.out.println(entry.get().getDescriptionId()))
                .filter(entry -> entry.get().getDescriptionId().equals(seeds))
                .findFirst();
        return opt.isPresent() ? opt.get().get() : super.getBaseSeedId();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(AGE) < flowerAge) {
            super.randomTick(pState, pLevel, pPos, pRandom);
        } else {
            growIntoFlower(pState, pLevel, pPos);
        }
    }

    // Replaces crop with flower block
    private void growIntoFlower(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        pLevel.setBlock(pPos, flower.defaultBlockState(), 2);
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        growIntoFlower(pState, pLevel, pPos);
    }
}
