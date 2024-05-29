package com.kmek.minecafe.screen;

import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.block.entity.CakeStandBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CakeStandMenu extends CustomBaseMenu {
    public CakeStandMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(/*CakeStandBlockEntity.dataFieldsCount*/0));
    }

    public CakeStandMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(id, inv, (CakeStandBlockEntity) entity, data, ModBlocksInit.CAKE_STAND.get(), ModMenuTypes.CAKE_STAND_MENU.get());
    }

    @Override
    protected void addSlots(IItemHandler handler) {
        this.addSlot(new SlotItemHandler(handler, 0, 80, 27));
    }
}
