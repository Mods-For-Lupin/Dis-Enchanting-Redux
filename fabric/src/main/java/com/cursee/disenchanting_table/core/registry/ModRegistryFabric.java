package com.cursee.disenchanting_table.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModRegistryFabric {

    public static void declare() {
        bind(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModBlockEntities::register);
        bind(BuiltInRegistries.BLOCK, ModBlocks::register);
        bind(BuiltInRegistries.ENTITY_TYPE, ModEntities::register);
        bind(BuiltInRegistries.ITEM, ModItems::register);
        bind(BuiltInRegistries.MENU, ModMenus::register);
        bind(BuiltInRegistries.CREATIVE_MODE_TAB, ModTabs::register);
    }

    public static <T> void bind(Registry<T> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        source.accept((t, rl) -> Registry.register(registry, rl, t));
    }
}
