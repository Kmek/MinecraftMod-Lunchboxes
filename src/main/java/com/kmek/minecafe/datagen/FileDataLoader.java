package com.kmek.minecafe.datagen;

import com.kmek.minecafe.MineCafeMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class FileDataLoader {
    final String fileName;

    public FileDataLoader(String fileName) {
        this.fileName = fileName;
    }

    public static FileDataLoader reg(String fileName) {
        return new FileDataLoader("registration_data/" + fileName);
    }

    public ArrayList<ArrayList<String>> read() {
        ArrayList<String> lines;

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(
                            getClass().getClassLoader()
                                    .getResourceAsStream("assets/" + MineCafeMod.MODID + "/" + fileName)),
                            "UTF-8"));
            lines = new ArrayList<>(br.lines()
                    .filter(s -> s.length() > 0 && !s.startsWith("//")) // Skip empty lines and comments
                    .toList());
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<ArrayList<String>> data = new ArrayList<>(lines.stream()
                .map(s -> new ArrayList<>(Arrays.stream(s.split(", ")).toList()))
                .toList());

        return data;
    }

    /**
     * Register a list
     */

    public static List<RegistryObject<Item>> regListItems(String fileName,
                                 DeferredRegister<Item> defReg, Function<ArrayList<String>, Supplier<Item>> funcSupp) {
        return regListItems(fileName, "", "", defReg, funcSupp);
    }
    public static List<RegistryObject<Item>> regListItems(String fileName, String prefix, String suffix,
                                 DeferredRegister<Item> defReg, Function<ArrayList<String>, Supplier<Item>> funcSupp) {
        return FileDataLoader.reg(fileName).toRegListItems(prefix, suffix, defReg, funcSupp);
    }
    public List<RegistryObject<Item>> toRegListItems(String prefix, String suffix,
                                 DeferredRegister<Item> defReg, Function<ArrayList<String>, Supplier<Item>> funcSupp) {
        return this.read().stream()
                .map(args -> defReg.register((prefix + args.get(0) + suffix), funcSupp.apply(args)))
                .toList();
    }

    /**
     * Supplier creators
     */

    public static final Function<ArrayList<String>, Supplier<Item>> food2Args = args ->
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(Integer.parseInt(args.get(1)))
                            .saturationMod(Float.parseFloat(args.get(2))).build()));

    public static final BiFunction<Integer, Float, Function<ArrayList<String>, Supplier<Item>>> foodFixedArgs = (nut, sat) ->
            args ->
                    () -> new Item(new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .nutrition(nut)
                                    .saturationMod(sat).build()));

}
