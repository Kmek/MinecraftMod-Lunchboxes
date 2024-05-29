package com.kmek.minecafe.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class VaseScreen extends CustomBaseScreen<VaseMenu> {
    public VaseScreen(VaseMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/vase_gui.png");
    }
}
