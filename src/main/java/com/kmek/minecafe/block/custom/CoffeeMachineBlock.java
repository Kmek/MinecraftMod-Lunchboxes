package com.kmek.minecafe.block.custom;

import com.kmek.minecafe.block.CustomVoxelMenuEntityBlock;
import com.kmek.minecafe.block.entity.CoffeeMachineBlockEntity;
import com.kmek.minecafe.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CoffeeMachineBlock extends CustomVoxelMenuEntityBlock<CoffeeMachineBlockEntity> {
    public CoffeeMachineBlock(BlockBehaviour.Properties properties) {
        super(properties, true);
    }

    @Override
    public VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.125, 0.6875, 0.8125, 0.8125), BooleanOp.OR);
        return shape;
    }

    /**
     * Block Entity Stuff
     */

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CoffeeMachineBlockEntity(pos, state);
    }

    @Override
    protected boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof CoffeeMachineBlockEntity;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.COFFEE_MACHINE.get(), CoffeeMachineBlockEntity::tick);
    }
}
