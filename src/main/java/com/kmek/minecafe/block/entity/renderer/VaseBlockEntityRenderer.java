package com.kmek.minecafe.block.entity.renderer;

import com.kmek.minecafe.block.entity.VaseBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.items.ItemStackHandler;

public class VaseBlockEntityRenderer extends CustomBaseBlockEntityRenderer<VaseBlockEntity> {
    public VaseBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    private static final float tiltDegrees = 35f;
    private static final float moveDist = 0.4f;

    @Override
    protected void renderInner(ItemRenderer itemRenderer, VaseBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        centerAndRotateToFacing(pBlockEntity, pPoseStack);
        pPoseStack.translate(0f, 0.25f, 0f);
        pPoseStack.scale(0.5f, 0.5f, 0.5f);

        ItemStackHandler items = pBlockEntity.getRenderItemStackHandler();

        // Center item
        itemRenderer.renderStatic(items.getStackInSlot(0), ItemTransforms.TransformType.GUI,
                getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);

        // Surrounding items
        pPoseStack.translate(0f, -0.35f, 0f);
        for (int i = 1; i < 9; i++) {
            // Pose
            pPoseStack.translate(-1 * moveDist, 0f, 0f);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(tiltDegrees));

            // Render item
            itemRenderer.renderStatic(items.getStackInSlot(i), ItemTransforms.TransformType.GUI,
                    getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);

            // Undo pose
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(-1 * tiltDegrees));
            pPoseStack.translate(moveDist, 0f, 0f);

            // Increment rotation
            pPoseStack.mulPose(Axis.YP.rotationDegrees(45f));
        }
    }
}
