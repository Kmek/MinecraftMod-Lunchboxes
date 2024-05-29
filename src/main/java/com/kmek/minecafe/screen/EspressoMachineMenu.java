package com.kmek.minecafe.screen;

import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.block.entity.EspressoMachineBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class EspressoMachineMenu extends CustomBaseMenu {
    public EspressoMachineMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(/*BlockEntity.dataFieldsCount*/1));
    }

    public EspressoMachineMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, (EspressoMachineBlockEntity) entity, data, ModBlocksInit.ESPRESSO_MACHINE.get(), ModMenuTypes.ESPRESSO_MACHINE_MENU.get());
    }

    @Override
    protected void addSlots(IItemHandler handler) {
        this.addSlot(new SlotItemHandler(handler, EspressoMachineBlockEntity.SLOT_WATER, 80, 17));
        this.addSlot(new SlotItemHandler(handler, EspressoMachineBlockEntity.SLOT_GROUNDS, 59, 35));
        this.addSlot(new SlotItemHandler(handler, EspressoMachineBlockEntity.SLOT_MILK, 101, 35));
        this.addSlot(new SlotItemHandler(handler, EspressoMachineBlockEntity.SLOT_OUTPUT, 80, 53));
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = EspressoMachineBlockEntity.maxProgress;
        int progressArrowSize = 14; // This is the height in pixels of your arrow
        return progress != 0 ? progress > maxProgress ? progressArrowSize : progress * progressArrowSize / maxProgress : 0;
    }
}
