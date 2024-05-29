package com.kmek.klunchbox.block.entity;

import com.kmek.klunchbox.networking.ModMessages;
import com.kmek.klunchbox.networking.packet.ItemStackSyncS2CPacket;
import com.kmek.klunchbox.screen.LunchboxMenu;
import com.kmek.klunchbox.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class LunchboxBlockEntity extends CustomBaseBlockEntity {
    public LunchboxBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState, ModBlockEntities.LUNCHBOX.get(), 12, 0, "Lunchbox");
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
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.isEdible() || stack.is(ModTags.Items.CAKE_BLOCKS);
            }
        };
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new LunchboxMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public String getInventoryString() {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            ItemStack iStack = this.itemHandler.getStackInSlot(i);
            if (!iStack.isEmpty()) {
                String itemName = iStack.getItem().getDescription().getString();
                int itemCount = iStack.getCount();
                items.add(itemCount + "x " + itemName);
            }
        }

        String delimiter = "$$";
        return String.join(delimiter, items);
    }
}
