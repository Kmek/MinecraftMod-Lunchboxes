package com.kmek.minecafe.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class JuicerScreen extends CustomBaseScreen<JuicerMenu> {
    public JuicerScreen(JuicerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/juicer_gui.png");
    }

    @Override
    protected void renderBgExtra(PoseStack pPoseStack, int x, int y) {
        super.renderBgExtra(pPoseStack, x, y);
        renderProgressArrow(pPoseStack, x, y);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting()) {
            int k = menu.getScaledProgress();
            blit(pPoseStack, x + 121, y + 19, 177, 1, 5, k);
        }
    }
}
