package com.kmek.minecafe.block;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.entity.CustomBaseBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.EnumMap;
import java.util.Map;

public class CustomVoxelMenuEntityBlock <E extends CustomBaseBlockEntity> extends CustomMenuEntityBlock<E> {
    protected final VoxelShape SHAPE = makeShape();
    private final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    public CustomVoxelMenuEntityBlock(Properties properties, boolean runCalcOnVoxelShape) {
        super(properties);
        if (runCalcOnVoxelShape && SHAPES.size() == 0) {
            runCalculation(SHAPE);
        }
    }

    public VoxelShape makeShape() {
        // for child class to override
        return Shapes.empty();
    }

    protected void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values())
            SHAPES.put(direction, MineCafeMod.calculateShapes(direction, shape));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // for child class to override (if necessary)
        return SHAPES.get(state.getValue(FACING));
    }
}
