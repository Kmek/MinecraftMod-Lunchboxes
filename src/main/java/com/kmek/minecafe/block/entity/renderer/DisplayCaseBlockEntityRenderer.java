package com.kmek.minecafe.block.entity.renderer;

import com.kmek.minecafe.block.entity.DisplayCaseBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraftforge.items.ItemStackHandler;

public class DisplayCaseBlockEntityRenderer extends CustomBaseBlockEntityRenderer<DisplayCaseBlockEntity> {
    public DisplayCaseBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderInner(ItemRenderer itemRenderer, DisplayCaseBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        centerAndRotateToFacing(pBlockEntity, pPoseStack);
        pPoseStack.translate(0f, 0.6f, 0f);
        pPoseStack.scale(0.2f, 0.2f, 0.2f);
        pPoseStack.translate(0f, 0f, -1f);

        ItemStackHandler items = pBlockEntity.getRenderItemStackHandler();

        // Render rows of 4
        renderRowOf4Slots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource, 0);
        pPoseStack.translate(0.0f, -2.15f, 0f);
        renderRowOf4Slots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource, 4);
        pPoseStack.translate(0.0f, -1.3f, 0.8f);
        renderRowOf4Slots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource, 8);
        pPoseStack.translate(0.0f, -1.2f, 1.25f);
        renderRowOf4Slots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource, 12);
    }
}
