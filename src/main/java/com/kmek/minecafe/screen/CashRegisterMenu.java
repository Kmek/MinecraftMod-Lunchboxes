package com.kmek.minecafe.screen;

import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.block.entity.CashRegisterBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;

public class CashRegisterMenu extends CustomBaseMenu {
    public CashRegisterMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(/*BlockEntity.dataFieldsCount*/0));
    }

    public CashRegisterMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, (CashRegisterBlockEntity) entity, data, ModBlocksInit.CASH_REGISTER.get(), ModMenuTypes.CASH_REGISTER_MENU.get());
    }

    @Override
    protected void addSlots(IItemHandler handler) {
        addNSlots(handler, 4, 0, 46, 25, 23, 0);
        addNSlots(handler, 4, 4, 46, 45, 23, 0);
    }
}
