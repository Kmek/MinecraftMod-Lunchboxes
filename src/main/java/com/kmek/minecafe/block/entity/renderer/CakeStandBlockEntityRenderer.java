package com.kmek.minecafe.block.entity.renderer;

import com.kmek.minecafe.MineCafeMod;
import com.kmek.minecafe.block.entity.CakeStandBlockEntity;
import com.kmek.minecafe.tags.ModTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public class CakeStandBlockEntityRenderer extends CustomBaseBlockEntityRenderer<CakeStandBlockEntity> {
    public CakeStandBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderInner(ItemRenderer itemRenderer, CakeStandBlockEntity pBlockEntity, float pPartialTick,
                       PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStack itemStack = pBlockEntity.getRenderStack();

        if (itemStack.is(ModTags.Items.CAKE_BLOCKS)) {
            BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
            ModelManager modelmanager = blockRenderer.getBlockModelShaper().getModelManager();

            // Small cake block
            pPoseStack.translate(0.2f, 0.375f, 0.2f);
            pPoseStack.scale(0.6f, 0.6f, 0.6f);

            if ((itemStack.is(Items.CAKE))) {
                ModelResourceLocation modelresourcelocation = ModelResourceLocation.vanilla("cake", "bites=0");
                blockRenderer.getModelRenderer().renderModel(pPoseStack.last(), pBufferSource.getBuffer(Sheets.solidBlockSheet()), (BlockState) null, modelmanager.getModel(modelresourcelocation), 1.0F, 1.0F, 1.0F, pPackedLight, OverlayTexture.NO_OVERLAY);
            } else {
                String id = itemStack.getItem().getDescriptionId().substring("block.minecafe.".length());
                ModelResourceLocation modelresourcelocation = new ModelResourceLocation(MineCafeMod.MODID, id, "bites=0");
                blockRenderer.getModelRenderer().renderModel(pPoseStack.last(), pBufferSource.getBuffer(Sheets.solidBlockSheet()), (BlockState) null, modelmanager.getModel(modelresourcelocation), 1.0F, 1.0F, 1.0F, pPackedLight, OverlayTexture.NO_OVERLAY);
            }
        } else {
            // Upright item
            centerAndRotateToFacing(pBlockEntity, pPoseStack);
            pPoseStack.translate(0f, 0.15f, 0f);
            pPoseStack.scale(0.7f, 0.7f, 0.7f);

            itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI,
                    getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
        }
    }
}
