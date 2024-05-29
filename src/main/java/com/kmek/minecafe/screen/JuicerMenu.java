package com.kmek.minecafe.screen;

import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.block.entity.JuicerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class JuicerMenu extends CustomBaseMenu {
    public JuicerMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(/*CoffeeMachineBlockEntity.dataFieldsCount*/1));
    }

    public JuicerMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, (JuicerBlockEntity) entity, data, ModBlocksInit.JUICER.get(), ModMenuTypes.JUICER_MENU.get());
    }

    @Override
    protected void addSlots(IItemHandler handler) {
        this.addNSlots(handler, 3, 0, 62, 20, 18, 0);
        this.addNSlots(handler, 3, 3, 62, 38, 18, 0);
        this.addSlot(new SlotItemHandler(handler, JuicerBlockEntity.SLOT_CUPS, 147, 61));
        this.addSlot(new SlotItemHandler(handler, JuicerBlockEntity.SLOT_OUTPUT, 126, 61));
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = JuicerBlockEntity.maxProgress;
        int progressArrowSize = 36; // This is the height in pixels of your arrow
        return progress != 0 ? progress > maxProgress ? progressArrowSize : progress * progressArrowSize / maxProgress : 0;
    }
}
