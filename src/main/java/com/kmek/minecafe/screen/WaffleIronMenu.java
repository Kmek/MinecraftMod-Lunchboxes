package com.kmek.minecafe.screen;

import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.block.entity.WaffleIronBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WaffleIronMenu extends CustomBaseMenu {
    public WaffleIronMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(/*WaffleIronBlockEntity.dataFieldsCount*/2));
    }

    public WaffleIronMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, (WaffleIronBlockEntity) entity, data, ModBlocksInit.WAFFLE_IRON.get(), ModMenuTypes.WAFFLE_IRON_MENU.get());
    }

    @Override
    protected void addSlots(IItemHandler handler) {
        this.addSlot(new SlotItemHandler(handler, 0, 43, 36));
        this.addSlot(new SlotItemHandler(handler, 1, 88, 22));
        this.addSlot(new SlotItemHandler(handler, 2, 88, 50));
        this.addSlot(new SlotItemHandler(handler, 3, 116, 22));
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 46; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
}
