package com.kmek.minecafe.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CashRegisterScreen extends CustomBaseScreen<CashRegisterMenu> {
    public CashRegisterScreen(CashRegisterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/cash_register_gui.png");
    }
}
