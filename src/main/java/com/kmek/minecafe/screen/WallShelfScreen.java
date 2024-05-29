package com.kmek.minecafe.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WallShelfScreen extends CustomBaseScreen<WallShelfMenu> {
    public WallShelfScreen(WallShelfMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/wall_shelf_gui.png");
    }
}
