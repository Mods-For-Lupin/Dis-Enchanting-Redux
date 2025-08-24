package com.cursee.examplemod.core.registry;

import com.cursee.examplemod.Constants;
import com.cursee.matyrobbrt.registrationutils.RegistrationProvider;
import com.cursee.matyrobbrt.registrationutils.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

import java.util.LinkedList;
import java.util.function.Supplier;

public class ModMenus {

    public static final LinkedList<RegistryObject<MenuType<?>, MenuType<?>>> ORDERED = new LinkedList<>();
    public static final RegistrationProvider<MenuType<?>> MENUS = RegistrationProvider.get(Registries.MENU, Constants.MOD_ID);

    public static void declare() {}

    public static RegistryObject<MenuType<?>, MenuType<?>> register(String name, Supplier<MenuType<?>> supplier) {
        var obj = MENUS.register(name, supplier);
        ORDERED.add(obj);
        return obj;
    }
}
