package com.kmek.minecafe.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WaffleIronScreen extends CustomBaseScreen<WaffleIronMenu> {
    public WaffleIronScreen(WaffleIronMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/waffle_iron_gui.png");
    }

    @Override
    protected void renderBgExtra(PoseStack pPoseStack, int x, int y) {
        super.renderBgExtra(pPoseStack, x, y);
        renderProgressArrow(pPoseStack, x, y);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting()) {
            int k = menu.getScaledProgress();
            blit(pPoseStack, x + 71, y + 21 + 46 - k, 177, 46 - k, 5, k + 1);
        }
    }
}
