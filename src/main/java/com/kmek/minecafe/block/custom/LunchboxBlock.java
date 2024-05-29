package com.kmek.minecafe.block.custom;

import com.kmek.minecafe.block.CustomVoxelMenuEntityBlock;
import com.kmek.minecafe.block.entity.LunchboxBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LunchboxBlock extends CustomVoxelMenuEntityBlock<LunchboxBlockEntity> {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public LunchboxBlock(BlockBehaviour.Properties properties) {
        super(properties, true);
        registerDefaultState(defaultBlockState().setValue(OPEN, false));
    }

    @Override
    public VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.1875, 0.875, 0.3125, 0.8125), BooleanOp.OR);
        return shape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    /**
     * Block Entity Stuff
     */

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LunchboxBlockEntity(pos, state);
    }

    @Override
    protected boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof LunchboxBlockEntity;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide() && Minecraft.getInstance().options.keyShift.isDown()) {
            BlockState updated = pState.cycle(OPEN);
            pLevel.setBlock(pPos, updated, 3);
        } else if (Minecraft.getInstance().options.keySprint.isDown()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (isBlockEntity(blockEntity)) {
                if (!pLevel.isClientSide()) {
                    // Create item stack
                    ItemStack stack = new ItemStack(this);
                    blockEntity.saveToItem(stack);
                    CompoundTag invTag = stack.getTag();
                    invTag.putString("InventoryString", ((LunchboxBlockEntity) blockEntity).getInventoryString());

                    // Place item in player inventory
                    pPlayer.addItem(stack);

                    // Remove block
                    pLevel.removeBlockEntity(pPos);
                    pLevel.removeBlock(pPos, false);
                }
            }
        } else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    // Save inventory to an item stack
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (isBlockEntity(blockEntity)) {
                // Create item stack
                ItemStack stack = new ItemStack(this);
                blockEntity.saveToItem(stack);
                CompoundTag invTag = stack.getTag();
                invTag.putString("InventoryString", ((LunchboxBlockEntity) blockEntity).getInventoryString());
                // Drop item stack
                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), stack);
            }
        }
        super.removeWithoutDrops(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    // Get saved inventory from an item stack
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pLevel.isClientSide()) {
            CompoundTag tag = pStack.getTag();
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (isBlockEntity(blockEntity) && tag != null) {
                blockEntity.load(tag);
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }
}
