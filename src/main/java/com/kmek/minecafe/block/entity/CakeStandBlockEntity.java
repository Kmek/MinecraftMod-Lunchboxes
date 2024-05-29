package com.kmek.minecafe.block.entity;

import com.kmek.minecafe.screen.CakeStandMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CakeStandBlockEntity extends CustomBaseBlockEntity {
    public CakeStandBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState, ModBlockEntities.CAKE_STAND.get(), 1, 0, "Cake Stand");
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return 0;
            }

            @Override
            public void set(int pIndex, int pValue) {
            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CakeStandMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    // For block entity rendering
    @Override
    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(0);
    }
}
