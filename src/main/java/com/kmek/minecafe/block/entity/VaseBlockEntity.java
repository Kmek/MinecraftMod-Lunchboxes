package com.kmek.minecafe.block.entity;

import com.kmek.minecafe.networking.ModMessages;
import com.kmek.minecafe.networking.packet.ItemStackSyncS2CPacket;
import com.kmek.minecafe.screen.VaseMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class VaseBlockEntity extends CustomBaseBlockEntity {
    public VaseBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState, ModBlockEntities.VASE.get(), 9, 0, "Vase");
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
        this.itemHandler = new ItemStackHandler(menuSlotCount) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (!level.isClientSide) {
                    ModMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new VaseMenu(pContainerId, pPlayerInventory, this, this.data);
    }
}
