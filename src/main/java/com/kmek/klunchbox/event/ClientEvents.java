package com.kmek.klunchbox.event;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.entity.ModBlockEntities;
import com.kmek.klunchbox.block.entity.renderer.*;
import com.kmek.klunchbox.client.CropInspectorOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = KLunchboxMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.WAFFLE_IRON.get(), WaffleIronBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.CAKE_STAND.get(), CakeStandBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.DISPLAY_CASE.get(), DisplayCaseBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.VASE.get(), VaseBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.WALL_SHELF.get(), WallShelfBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.COFFEE_MACHINE.get(), CoffeeMachineBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.JUICER.get(), JuicerBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.LUNCHBOX.get(), LunchboxBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerBelowAll("crop_inspector", CropInspectorOverlay.HUD_CROP_INSPECTOR);
        }
    }
}
