package com.kmek.minecafe.block.custom.crop;

import com.kmek.minecafe.block.ModBlocksInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class CropTreeBottomBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    private final String topBlockName;
    private Block topBlock = null;

    public CropTreeBottomBlock(String topBlockName, BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.topBlockName = "block.minecafe." + topBlockName; // this.getDescriptionId()
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    private Block getTopBlock() {
        if (topBlock == null) {
            Optional<RegistryObject<Block>> opt = ModBlocksInit.BLOCKS.getEntries().stream()
                    .filter(entry -> entry.get().getDescriptionId().equals(topBlockName))
                    .findFirst();
            topBlock = opt.isPresent() ? opt.get().get() : Blocks.AIR;
        }
        return topBlock;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    // Tree crops can catch on fire
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 15;
    }
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < 7 || pLevel.getBlockState(pPos.above()).isAir();
    }
    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }
    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        this.grow(pLevel, pPos, pState, 2 + pLevel.random.nextInt(2));
    }

    // Grow the crop
    private void grow(ServerLevel pLevel, BlockPos pPos, BlockState pState, int toAdd) {
        int age = pState.getValue(AGE);
        if (age == 7) {
            growTopBlock(pLevel, pPos, pState);
        } else {
            pLevel.setBlock(pPos, this.defaultBlockState().setValue(AGE, Math.min(7, age + toAdd)), 2);
        }
    }

    // Grow the top block
    private void growTopBlock(ServerLevel pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.getBlockState(pPos.above()).isAir()) {
            pLevel.setBlock(pPos.above(), getTopBlock().defaultBlockState(), 3);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(AGE) <= 7;
    }
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt(5) == 0)) {
            grow(pLevel, pPos, pState, 1);
        }
    }

    // Makes this bottom sustain the top crop
    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlant(world, pos).is(getTopBlock());
    }

    // Makes this block slow to walk through
    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity && pEntity.getType() != EntityType.FOX && pEntity.getType() != EntityType.BEE) {
            pEntity.makeStuckInBlock(pState, new Vec3(1F, 1D, 1F));
        }
    }
}
