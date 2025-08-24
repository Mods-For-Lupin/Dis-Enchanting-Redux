package com.cursee.examplemod.core.registry;

import com.cursee.examplemod.Constants;
import com.cursee.matyrobbrt.registrationutils.RegistrationProvider;
import com.cursee.matyrobbrt.registrationutils.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.LinkedList;
import java.util.function.Supplier;

public class ModBlockEntities {

    public static final LinkedList<RegistryObject<BlockEntityType<?>, BlockEntityType<?>>> ORDERED = new LinkedList<>();
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static void declare() {}

    public static RegistryObject<BlockEntityType<?>, BlockEntityType<?>> register(String name, Supplier<BlockEntityType<?>> supplier) {
        var obj = BLOCK_ENTITIES.register(name, supplier);
        ORDERED.add(obj);
        return obj;
    }
}
