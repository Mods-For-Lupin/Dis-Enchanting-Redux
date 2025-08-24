package com.cursee.examplemod.core.registry;

import com.cursee.examplemod.Constants;
import com.cursee.matyrobbrt.registrationutils.RegistrationProvider;
import com.cursee.matyrobbrt.registrationutils.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;

import java.util.LinkedList;
import java.util.function.Supplier;

public class ModTabs {

    public static final LinkedList<RegistryObject<CreativeModeTab, CreativeModeTab>> ORDERED = new LinkedList<>();
    public static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static void declare() {}

    public static RegistryObject<CreativeModeTab, CreativeModeTab> register(String name, Supplier<CreativeModeTab> supplier) {
        var obj = TABS.register(name, supplier);
        ORDERED.add(obj);
        return obj;
    }
}
