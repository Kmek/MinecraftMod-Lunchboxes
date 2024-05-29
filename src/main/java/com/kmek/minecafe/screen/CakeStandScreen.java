package com.kmek.minecafe.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CakeStandScreen extends CustomBaseScreen<CakeStandMenu> {
    public CakeStandScreen(CakeStandMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/cake_stand_gui.png");
    }
}
