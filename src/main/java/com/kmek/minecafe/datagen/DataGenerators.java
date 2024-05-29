package com.kmek.minecafe.datagen;

import com.kmek.minecafe.MineCafeMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MineCafeMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(true, new ModBlockModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));

        // Advancements
        generator.addProvider(
                // Tell generator to run only when server data are generating
                event.includeServer(),
                (DataProvider.Factory<DataProvider>) output -> new ForgeAdvancementProvider(
                        output,
                        event.getLookupProvider(),
                        event.getExistingFileHelper(),
                        // Sub providers which generate the advancements
                        List.of(/*new AnyAdvancementGenerator(),*/ new EveryAdvancementGenerator())
                )
        );
    }
}
