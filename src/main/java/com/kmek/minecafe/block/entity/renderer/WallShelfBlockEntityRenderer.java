package com.kmek.minecafe.block.entity.renderer;

import com.kmek.minecafe.block.entity.WallShelfBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraftforge.items.ItemStackHandler;

public class WallShelfBlockEntityRenderer extends CustomBaseBlockEntityRenderer<WallShelfBlockEntity> {
    public WallShelfBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderInner(ItemRenderer itemRenderer, WallShelfBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        centerAndRotateToFacing(pBlockEntity, pPoseStack);
        pPoseStack.translate(0f, 0.28f, 0f);
        pPoseStack.scale(0.2f, 0.2f, 0.2f);
        pPoseStack.translate(0f, 0f, -1.5f);

        ItemStackHandler items = pBlockEntity.getRenderItemStackHandler();

        // Render front rows of 4
        renderRowOf4Slots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource, 0);
        pPoseStack.translate(0f, -1.55f, 0f);
        renderRowOf4Slots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource, 4);
        pPoseStack.translate(0f, -1.55f, 0f);
        renderRowOf4Slots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource, 8);
    }
}
