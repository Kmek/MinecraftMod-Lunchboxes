package com.kmek.minecafe.block.custom;

import com.kmek.minecafe.block.CustomMenuEntityBlock;
import com.kmek.minecafe.block.entity.DisplayCaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class DisplayCaseBlock extends CustomMenuEntityBlock<DisplayCaseBlockEntity> {
    public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;

    public DisplayCaseBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(LOCKED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LOCKED);
    }

    /**
     * Block Entity Stuff
     */

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DisplayCaseBlockEntity(pos, state);
    }

    @Override
    protected boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof DisplayCaseBlockEntity;
    }

    // Only open menu from back of block
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        boolean isLocked = pState.getValue(LOCKED);
        if (pPlayer.getDirection().equals(pState.getValue(CustomMenuEntityBlock.FACING))) {
            if (pPlayer.isCrouching()) {
                BlockState updated = pState.cycle(LOCKED);
                if (!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND) {
                    pLevel.setBlock(pPos, updated, 3);
                }
            }
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        } else if (!isLocked) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        return InteractionResult.PASS;
    }
}
