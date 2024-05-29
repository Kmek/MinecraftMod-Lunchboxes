package com.kmek.minecafe.screen;

import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.block.entity.DisplayCaseBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;

public class DisplayCaseMenu extends CustomBaseMenu {
    public DisplayCaseMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(/*DisplayCaseBlockEntity.dataFieldsCount*/1));
    }

    public DisplayCaseMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, (DisplayCaseBlockEntity) entity, data, ModBlocksInit.DISPLAY_CASE_CURVED.get(), ModMenuTypes.DISPLAY_CASE_MENU.get());
    }

    @Override
    protected void addSlots(IItemHandler handler) {
        add4Slots(handler, 0, 99, 7);
        add4Slots(handler, 4, 25, 17);
        add4Slots(handler, 8, 17, 35);
        add4Slots(handler, 12, 8, 53);
    }

    public boolean isLocked() {
        return data.get(0) == 1;
    }
}
