package com.kmek.klunchbox.event;

import com.kmek.klunchbox.KLunchboxMod;
import com.kmek.klunchbox.block.entity.ModBlockEntities;
import com.kmek.klunchbox.block.entity.renderer.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = KLunchboxMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.LUNCHBOX.get(), LunchboxBlockEntityRenderer::new);
        }
    }
}
