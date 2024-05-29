package com.kmek.minecafe.block.entity.renderer;

import com.kmek.minecafe.block.entity.CoffeeMachineBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

public class CoffeeMachineBlockEntityRenderer extends CustomBaseBlockEntityRenderer<CoffeeMachineBlockEntity>{
    public CoffeeMachineBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderInner(ItemRenderer itemRenderer, CoffeeMachineBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        centerAndRotateToFacing(pBlockEntity, pPoseStack);
        pPoseStack.translate(0.025f, -0.15f, 0.24f);
        pPoseStack.scale(0.65f, 0.65f, 0.65f);

        ItemStack itemStack = pBlockEntity.getRenderStack();

        // Bottom Item
        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI,
                getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
    }
}
