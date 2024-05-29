package com.kmek.klunchbox.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LunchboxScreen extends CustomBaseScreen<LunchboxMenu> {
    public LunchboxScreen(LunchboxMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/lunchbox_gui.png");
    }
}
