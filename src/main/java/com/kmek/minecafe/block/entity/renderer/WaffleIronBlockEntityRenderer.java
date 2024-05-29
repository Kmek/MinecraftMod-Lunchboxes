package com.kmek.minecafe.block.entity.renderer;

import com.kmek.minecafe.block.entity.WaffleIronBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class WaffleIronBlockEntityRenderer extends CustomBaseBlockEntityRenderer<WaffleIronBlockEntity> {
    public WaffleIronBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(WaffleIronBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        // Only render items if open
        if (pBlockEntity.getBlockState().getValue(BlockStateProperties.OPEN)) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            ItemStack itemStack = pBlockEntity.getRenderStack();
            pPoseStack.pushPose();

            centerAndRotateToFacing(pBlockEntity, pPoseStack);
            pPoseStack.translate(0f, -0.375f, 0f);
            pPoseStack.scale(0.65f, 0.65f, 0.65f);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            pPoseStack.translate(0.0f, -0.1f, 0.0f);

            // Bottom Item
            itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI,
                    getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);

            pPoseStack.mulPose(Axis.XP.rotationDegrees(270.0F));
            pPoseStack.translate(0.0d, -0.67d, 0.59d);

            // Back Item
            itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI,
                    getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);

            pPoseStack.popPose();
        }
    }
}
