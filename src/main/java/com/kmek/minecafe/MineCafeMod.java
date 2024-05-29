package com.kmek.minecafe;

import com.kmek.minecafe.block.ModBlocksInit;
import com.kmek.minecafe.block.entity.ModBlockEntities;
import com.kmek.minecafe.fluid.ModFluidTypes;
import com.kmek.minecafe.fluid.ModFluids;
import com.kmek.minecafe.item.ModCreativeTabInit;
import com.kmek.minecafe.item.ModItemsInit;
import com.kmek.minecafe.loot.ModLootModifiers;
import com.kmek.minecafe.networking.ModMessages;
import com.kmek.minecafe.painting.ModPaintingsInit;
import com.kmek.minecafe.recipe.ModRecipes;
import com.kmek.minecafe.screen.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MineCafeMod.MODID)
public class MineCafeMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "minecafe";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public MineCafeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItemsInit.register(modEventBus);
        ModBlocksInit.register(modEventBus);

        ModPaintingsInit.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register creative mode tabs
        ModCreativeTabInit creativeTabsInit = new ModCreativeTabInit();
        creativeTabsInit.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        ModMessages.register();
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            /**
             * Add Compostable Items
             */
            ModItemsInit.compostableItems.forEach((reg, rate) -> {
                ComposterBlock.COMPOSTABLES.put(reg.get(), rate);
            });
            ModBlocksInit.compostableItems.forEach((reg, rate) -> {
                ComposterBlock.COMPOSTABLES.put(reg.get().asItem(), rate);
            });

            /**
             * Set Block Render Layer Types
             */
            // Cutout Blocks
            ModBlocksInit.renderAsCutout.stream().forEach(block -> {
                ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.cutout());
            });
            // Translucent Blocks
            ModBlocksInit.renderAsTranslucent.stream().forEach(block -> {
                ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.translucent());
            });
            // Fluid
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_COFFEE_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_COFFEE_FLUID.get(), RenderType.translucent());

            /**
             * Register Menu Screens
             */
            MenuScreens.register(ModMenuTypes.WAFFLE_IRON_MENU.get(), WaffleIronScreen::new);
            MenuScreens.register(ModMenuTypes.COFFEE_MACHINE_MENU.get(), CoffeeMachineScreen::new);
            MenuScreens.register(ModMenuTypes.ESPRESSO_MACHINE_MENU.get(), EspressoMachineScreen::new);
            MenuScreens.register(ModMenuTypes.JUICER_MENU.get(), JuicerScreen::new);
            MenuScreens.register(ModMenuTypes.DISPLAY_CASE_MENU.get(), DisplayCaseScreen::new);
            MenuScreens.register(ModMenuTypes.CAKE_STAND_MENU.get(), CakeStandScreen::new);
            MenuScreens.register(ModMenuTypes.VASE_MENU.get(), VaseScreen::new);
            MenuScreens.register(ModMenuTypes.CASH_REGISTER_MENU.get(), CashRegisterScreen::new);
            MenuScreens.register(ModMenuTypes.WALL_SHELF_MENU.get(), WallShelfScreen::new);
            MenuScreens.register(ModMenuTypes.LUNCHBOX_MENU.get(), LunchboxScreen::new);
        }
    }

    /**
     * Helper Methods
     */
    // For rotating voxel shapes for blocks
    public static VoxelShape calculateShapes(Direction to, VoxelShape shape) {
        final VoxelShape[] buffer = { shape, Shapes.empty() };

        final int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1],
                    Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }
}
