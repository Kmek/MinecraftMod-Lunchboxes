package com.kmek.minecafe.block.entity.renderer;

import com.kmek.minecafe.block.entity.LunchboxBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.ItemStackHandler;

public class LunchboxBlockEntityRenderer extends CustomBaseBlockEntityRenderer<LunchboxBlockEntity> {
    public LunchboxBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LunchboxBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.getBlockState().getValue(BlockStateProperties.OPEN))
            super.render(pBlockEntity, pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
    }

    @Override
    protected void renderInner(ItemRenderer itemRenderer, LunchboxBlockEntity pBlockEntity, float pPartialTick,
                               PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        centerAndRotateToFacing(pBlockEntity, pPoseStack);
        pPoseStack.scale(0.2f, 0.2f, 0.2f);
        pPoseStack.translate(-1.2f, -1.5f, -1f);

        ItemStackHandler items = pBlockEntity.getRenderItemStackHandler();

        // Render rows of 4
        renderAngledRowSlots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource,
                0, 4, 0.8f, -40.0f, 15.0f);
        pPoseStack.translate(0.0f, 0f, 1f);
        renderAngledRowSlots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource,
                4, 4, 0.8f, -40.0f, 15.0f);
        pPoseStack.translate(0.0f, 0f, 1f);
        renderAngledRowSlots(itemRenderer, pPoseStack, items, pBlockEntity, pBufferSource,
                8, 4, 0.8f, -40.0f, 15.0f);
    }

    private void renderAngledRowSlots(ItemRenderer itemRenderer, PoseStack pPoseStack, ItemStackHandler items,
                                  LunchboxBlockEntity pBlockEntity, MultiBufferSource pBufferSource, int slotStart,
                                  int rowCount, float itemSpacing, float xDeg, float yDeg) {
        for (int i = 0; i < rowCount; i++) {
            pPoseStack.mulPose(Axis.XP.rotationDegrees(xDeg));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(yDeg));
            itemRenderer.renderStatic(items.getStackInSlot(i + slotStart), ItemTransforms.TransformType.GUI,
                    getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY,
                    pPoseStack, pBufferSource, 1);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(yDeg * -1));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(xDeg * -1));
            pPoseStack.translate(itemSpacing, 0f, 0f);
        }
        pPoseStack.translate(rowCount * -1 * itemSpacing, 0f, 0f);
    }
}
