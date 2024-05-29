package com.kmek.minecafe.block.entity.renderer;

import com.kmek.minecafe.block.entity.JuicerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

public class JuicerBlockEntityRenderer extends CustomBaseBlockEntityRenderer<JuicerBlockEntity>{
    public JuicerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderInner(ItemRenderer itemRenderer, JuicerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        centerAndRotateToFacing(pBlockEntity, pPoseStack);
        pPoseStack.translate(0f, -0.3f, 0.28f);
        pPoseStack.scale(0.40f, 0.40f, 0.40f);

        // todo adjust placement and size

        ItemStack itemStack = pBlockEntity.getRenderStack();

        // Bottom Item
        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI,
                getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
    }
}
