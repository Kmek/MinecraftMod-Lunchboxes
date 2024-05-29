package com.kmek.minecafe.client;

import com.kmek.minecafe.item.ModItemsInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.IPlantable;

import java.util.Map;

public class CropInspectorOverlay {
    public static final IGuiOverlay HUD_CROP_INSPECTOR = ((gui, poseStack, partialTick, width, height) -> {
        int x = 10;
        int y = 10;

        Minecraft minecraft = gui.getMinecraft();
        Player player = minecraft.player;
        Entity camera = minecraft.getCameraEntity();
        if (player == null || camera == null) return;

        ItemStack itemInHand = player.getMainHandItem();
        if (itemInHand.is(ModItemsInit.CROP_INSPECTOR_ITEM.get())) {
            minecraft.font.drawShadow(poseStack, "Crop Inspector", x, y, 0x00ff00);

            HitResult targetBlock = camera.pick(20.0D, 0.0F, false);
            if (targetBlock.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult) targetBlock).getBlockPos();
                BlockState bState = minecraft.level.getBlockState(blockpos);
                Block block = bState.getBlock();
                if (block instanceof IPlantable) {
                    minecraft.font.drawShadow(poseStack, block.getName(), x, y + 20, 0x00ff00);

                    // Trying to draw image of crop? Maybe later
//                    ResourceLocation resourceImg = new ResourceLocation(block.asItem().getDescriptionId());
//                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
//                    RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, 0.8F);
//                    RenderSystem.setShaderTexture(1, resourceImg);
//                    gui.blit(poseStack, 10, 20, 0, 0, 16, 16);

                    for (Map.Entry<Property<?>, Comparable<?>> entry : bState.getValues().entrySet()) {
                        if (entry.getKey().getName().equals("age")) {
                            IntegerProperty ageProperty = (IntegerProperty) entry.getKey();
                            int maxAge = (ageProperty.getPossibleValues().size() - 1);
                            int age = ageProperty.getValue(entry.getValue().toString()).get();
//                            minecraft.font.drawShadow(poseStack, "Age: " + age + " / " + maxAge, x, y + 30, 0x00ff00);
                            int growth = Math.round(((float) age / (float) maxAge) * 100);
                            minecraft.font.drawShadow(poseStack, "Growth: " + growth + "%", x, y + 30, 0x00ff00);
                            break;
                        }
                    }
                }
            }
        }
    });
}