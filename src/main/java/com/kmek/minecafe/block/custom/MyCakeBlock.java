package com.kmek.minecafe.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class MyCakeBlock extends CakeBlock {
    public MyCakeBlock() {
        super(BlockBehaviour.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL));
    }

    public MyCakeBlock(Properties pProperties) {
        super(pProperties);
    }

    // To prevent placing of candles on cakes (might be added later)
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            if (eat(pLevel, pPos, pState, pPlayer).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
        }
        return eat(pLevel, pPos, pState, pPlayer);
    }
}
