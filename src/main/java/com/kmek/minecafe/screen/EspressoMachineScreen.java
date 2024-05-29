package com.kmek.minecafe.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class EspressoMachineScreen extends CustomBaseScreen<EspressoMachineMenu> {
    public EspressoMachineScreen(EspressoMachineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/espresso_machine_gui.png");
    }

    @Override
    protected void renderBgExtra(PoseStack pPoseStack, int x, int y) {
        super.renderBgExtra(pPoseStack, x, y);
        renderProgressArrow(pPoseStack, x, y);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting()) {
            int k = menu.getScaledProgress();
            blit(pPoseStack, x + 79, y + 35 + 14 - k, 177, 14 - k, 18, k + 1);
        }
    }
}
