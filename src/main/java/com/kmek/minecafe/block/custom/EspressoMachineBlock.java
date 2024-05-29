package com.kmek.minecafe.block.custom;

import com.kmek.minecafe.block.CustomVoxelMenuEntityBlock;
import com.kmek.minecafe.block.entity.EspressoMachineBlockEntity;
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

public class EspressoMachineBlock extends CustomVoxelMenuEntityBlock<EspressoMachineBlockEntity> {
//    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
//    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
//    private static final VoxelShape SHAPE = makeShape();

    public EspressoMachineBlock(BlockBehaviour.Properties properties) {
        super(properties, true);
//        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
//        if (SHAPES.size() == 0)
//            runCalculation(SHAPE);
    }

    @Override
    public VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.0625, 0.875, 0.75, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.75, 0.5625, 0.8125, 1, 0.875), BooleanOp.OR);
        return shape;
    }

//    protected void runCalculation(VoxelShape shape) {
//        for (Direction direction : Direction.values())
//            SHAPES.put(direction, MineCafeMod.calculateShapes(direction, shape));
//    }
//
//    @Override
//    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
//    }
//
//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
//        super.createBlockStateDefinition(builder);
//        builder.add(FACING);
//    }
//
//    @Override
//    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
//        return SHAPES.get(state.getValue(FACING));
//    }

    /**
     * Block Entity Stuff
     */

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EspressoMachineBlockEntity(pos, state);
    }

    @Override
    protected boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof EspressoMachineBlockEntity;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ESPRESSO_MACHINE.get(), EspressoMachineBlockEntity::tick);
    }
}
