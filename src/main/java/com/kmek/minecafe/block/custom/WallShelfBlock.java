package com.kmek.minecafe.block.custom;

import com.kmek.minecafe.block.CustomVoxelMenuEntityBlock;
import com.kmek.minecafe.block.entity.WallShelfBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class WallShelfBlock extends CustomVoxelMenuEntityBlock<WallShelfBlockEntity> {
    public WallShelfBlock(BlockBehaviour.Properties properties) {
        super(properties, true);
    }

    @Override
    public VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.625, 1, 1, 1), BooleanOp.OR);
        return shape;
    }

    // Tree crops can catch on fire
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 20;
    }
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    /**
     * Block Entity Stuff
     */

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WallShelfBlockEntity(pos, state);
    }

    @Override
    protected boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof WallShelfBlockEntity;
    }
}
