package com.cursee.examplemod.core.registry;

import com.cursee.examplemod.Constants;
import com.cursee.matyrobbrt.registrationutils.RegistrationProvider;
import com.cursee.matyrobbrt.registrationutils.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

import java.util.LinkedList;
import java.util.function.Supplier;

public class ModEntities {

    public static final LinkedList<RegistryObject<EntityType<?>, EntityType<?>>> ORDERED = new LinkedList<>();
    public static final RegistrationProvider<EntityType<?>> ENTITIES = RegistrationProvider.get(Registries.ENTITY_TYPE, Constants.MOD_ID);

    public static void declare() {}

    public static RegistryObject<EntityType<?>, EntityType<?>> register(String name, Supplier<EntityType<?>> supplier) {
        var obj = ENTITIES.register(name, supplier);
        ORDERED.add(obj);
        return obj;
    }
}
