package com.cursee.disenchanting_table.core.registry;

import com.cursee.disenchanting_table.DisEnchantingTableNeoForge;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModRegistryNeoForge {

    public static void declare() {
        bind(Registries.BLOCK_ENTITY_TYPE, ModBlockEntities::register);
        bind(Registries.BLOCK, ModBlocks::register);
        bind(Registries.ENTITY_TYPE, ModEntities::register);
        bind(Registries.ITEM, ModItems::register);
        bind(Registries.MENU, ModMenus::register);
        bind(Registries.CREATIVE_MODE_TAB, ModTabs::register);
    }

    public static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        DisEnchantingTableNeoForge.eventBus.addListener((Consumer<RegisterEvent>) event -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }
}
