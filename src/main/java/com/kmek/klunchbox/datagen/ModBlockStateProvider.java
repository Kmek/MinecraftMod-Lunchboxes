package com.kmek.klunchbox.datagen;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.ModBlocksInit;
import com.kmek.klunchbox.block.custom.LunchboxBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, KLunchboxMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocksInit.LUNCHBOXES.forEach(reg -> lunchboxBlockState(reg));
    }

    private VariantBlockStateBuilder lunchboxBlockState(RegistryObject<Block> block) {
        VariantBlockStateBuilder builder = getVariantBuilder((LunchboxBlock) block.get());
        return builder.forAllStates(state -> {
            boolean open = state.getValue(BlockStateProperties.OPEN);
            Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            String modelName = "block/lunchbox/" + block.getId().getPath();
            if (open) {
                modelName += "_open";
            }
            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc(modelName)))
                    .rotationY(directionToRotationY(direction))
                    .build();
        });
    }

    private int directionToRotationY(Direction dir) {
        switch(dir) {
            case EAST -> {
                return 90;
            }
            case SOUTH -> {
                return 180;
            }
            case WEST -> {
                return 270;
            }
            default -> {
                return 0;
            }
        }
    }
}
