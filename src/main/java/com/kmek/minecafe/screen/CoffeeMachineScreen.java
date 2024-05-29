package com.kmek.minecafe.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CoffeeMachineScreen extends CustomBaseScreen<CoffeeMachineMenu> {
    public CoffeeMachineScreen(CoffeeMachineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, "textures/gui/coffee_machine_gui.png");
    }

    @Override
    protected void renderBgExtra(PoseStack pPoseStack, int x, int y) {
        super.renderBgExtra(pPoseStack, x, y);
        renderProgressArrow(pPoseStack, x, y);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting()) {
            int k = menu.getScaledProgressWater();
            blit(pPoseStack, x + 38, y + 35 + 14 - k, 177, 14 - k, 18, k + 1);

            k = menu.getScaledProgressFilter();
            blit(pPoseStack, x + 99, y + 25, 177, 17, 5, k);
        }
    }
}
