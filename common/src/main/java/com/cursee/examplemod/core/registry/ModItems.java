package com.cursee.examplemod.core.registry;

import com.cursee.examplemod.Constants;
import com.cursee.matyrobbrt.registrationutils.RegistrationProvider;
import com.cursee.matyrobbrt.registrationutils.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import java.util.LinkedList;
import java.util.function.Supplier;

public class ModItems {

    public static final LinkedList<RegistryObject<Item, Item>> ORDERED = new LinkedList<>();
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static void declare() {}

    public static RegistryObject<Item, Item> register(String name, Supplier<Item> supplier) {
        var obj = ITEMS.register(name, supplier);
        ORDERED.add(obj);
        return obj;
    }
}
