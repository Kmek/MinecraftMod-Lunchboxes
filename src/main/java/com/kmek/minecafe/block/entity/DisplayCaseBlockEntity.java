package com.kmek.minecafe.block.entity;

import com.kmek.minecafe.block.custom.DisplayCaseBlock;
import com.kmek.minecafe.screen.DisplayCaseMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DisplayCaseBlockEntity extends CustomBaseBlockEntity {
    public DisplayCaseBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState, ModBlockEntities.DISPLAY_CASE.get(), 16, 1, "Display Case");
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                if (pIndex == 0) {
                    return getBlockState().getValue(DisplayCaseBlock.LOCKED) ? 1 : 0;
                }
                return 0;
            }

            @Override
            public void set(int pIndex, int pValue) {
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DisplayCaseMenu(pContainerId, pPlayerInventory, this, this.data);
    }
}
