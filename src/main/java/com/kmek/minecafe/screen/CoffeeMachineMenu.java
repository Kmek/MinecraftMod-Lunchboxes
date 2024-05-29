package com.kmek.minecafe.screen;

import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.block.entity.CoffeeMachineBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CoffeeMachineMenu extends CustomBaseMenu {
    public CoffeeMachineMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(/*CoffeeMachineBlockEntity.dataFieldsCount*/1));
    }

    public CoffeeMachineMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, (CoffeeMachineBlockEntity) entity, data, ModBlocksInit.COFFEE_MACHINE.get(), ModMenuTypes.COFFEE_MACHINE_MENU.get());
    }

    @Override
    protected void addSlots(IItemHandler handler) {
            this.addSlot(new SlotItemHandler(handler, 0, 39, 17));
            this.addSlot(new SlotItemHandler(handler, 1, 80, 24));
            this.addSlot(new SlotItemHandler(handler, 2, 80, 46));
            this.addSlot(new SlotItemHandler(handler, 3, 121, 37));
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgressWater() {
        int progress = this.data.get(0);
        int maxProgress = CoffeeMachineBlockEntity.waterTime;
        int progressArrowSize = 14; //19; // This is the height in pixels of your arrow
        return progress != 0 ? progress > maxProgress ? progressArrowSize : progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledProgressFilter() {
        int progress = this.data.get(0) - CoffeeMachineBlockEntity.waterTime;
        int maxProgress = CoffeeMachineBlockEntity.brewTime;
        int progressArrowSize = 37; // This is the height in pixels of your arrow
        return progress > 0 ? progress * progressArrowSize / maxProgress : 0;
    }
}
