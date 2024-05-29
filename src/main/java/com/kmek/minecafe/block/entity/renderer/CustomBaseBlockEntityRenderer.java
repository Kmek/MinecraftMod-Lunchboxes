package com.kmek.minecafe.block.entity.renderer;

import com.kmek.minecafe.block.custom.DisplayCaseBlock;
import com.kmek.minecafe.block.entity.CustomBaseBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.items.ItemStackHandler;

public class CustomBaseBlockEntityRenderer<T extends CustomBaseBlockEntity> implements BlockEntityRenderer<T> {
    public CustomBaseBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        pPoseStack.pushPose();

        renderInner(itemRenderer, pBlockEntity, pPartialTick, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);

        pPoseStack.popPose();
    }

    protected void renderInner(ItemRenderer itemRenderer, T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        // for child class to override
    }

    protected void centerAndRotateToFacing(T pBlockEntity, PoseStack pPoseStack) {
        pPoseStack.translate(0.5f, 0.5f, 0.5f);
        switch(pBlockEntity.getBlockState().getValue(DisplayCaseBlock.FACING)) {
            case NORTH -> pPoseStack.mulPose(Axis.YP.rotationDegrees(180.f));
            case EAST -> pPoseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            case SOUTH -> pPoseStack.mulPose(Axis.YP.rotationDegrees(0.0F));
            case WEST -> pPoseStack.mulPose(Axis.YP.rotationDegrees(270.0F));
        }
    }

    protected void renderRowOf4Slots(ItemRenderer itemRenderer, PoseStack pPoseStack, ItemStackHandler items,
                                   T pBlockEntity, MultiBufferSource pBufferSource, int slotStart) {
        pPoseStack.translate(-1.5f, 0f, 0f);
        for (int i = 0; i < 4; i++) {
            itemRenderer.renderStatic(items.getStackInSlot(i + slotStart), ItemTransforms.TransformType.GUI,
                    getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
            pPoseStack.translate(1.0f, 0f, 0f);
        }
        pPoseStack.translate(-2.5f, 0f, 0f);
    }

    protected int getLightLevel(Level level, BlockPos blockPos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, blockPos);
        int sLight = level.getBrightness(LightLayer.SKY, blockPos);
        return LightTexture.pack(bLight, sLight);
    }
}
