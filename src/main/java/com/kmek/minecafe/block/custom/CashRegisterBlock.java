package com.kmek.minecafe.block.custom;

import com.kmek.minecafe.block.CustomMenuEntityBlock;
import com.kmek.minecafe.block.CustomVoxelMenuEntityBlock;
import com.kmek.minecafe.block.entity.CashRegisterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CashRegisterBlock extends CustomVoxelMenuEntityBlock<CashRegisterBlockEntity> {
    public CashRegisterBlock(BlockBehaviour.Properties properties) {
        super(properties, true);
    }

    @Override
    public VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.25, 0.875, 0.375, 1), BooleanOp.OR);
        return shape;
    }

    /**
     * Block Entity Stuff
     */

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CashRegisterBlockEntity(pos, state);
    }

    @Override
    protected boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof CashRegisterBlockEntity;
    }

    // Only open menu from back of block
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.getDirection().getOpposite().equals(pState.getValue(CustomMenuEntityBlock.FACING))) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        return InteractionResult.PASS;
    }
}
