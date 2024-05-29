package com.kmek.minecafe.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DisplayCaseScreen extends CustomBaseScreen<DisplayCaseMenu> {
    public DisplayCaseScreen(DisplayCaseMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/display_case_gui.png");
    }

    @Override
    protected void renderBgExtra(PoseStack pPoseStack, int x, int y) {
        super.renderBgExtra(pPoseStack, x, y);

        // Render lock
        if (menu.isLocked())
            blit(pPoseStack, x + 133, y + 33, 177, 1, 5, 5);
    }
}
