package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.client.gui.screens.inventory.DisEnchantingTableScreen;
import com.cursee.disenchanting_table.client.renderer.blockentity.DisEnchantingTableRenderer;
import com.cursee.disenchanting_table.core.registry.ModBlockEntities;
import com.cursee.disenchanting_table.core.registry.ModMenus;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.function.Consumer;

public class DisEnchantingTableClientNeoForge {

    public DisEnchantingTableClientNeoForge() {

        DisEnchantingTableNeoForge.eventBus.addListener((Consumer<EntityRenderersEvent.RegisterRenderers>) event -> {
            event.registerBlockEntityRenderer(ModBlockEntities.DISENCHANTING_TABLE, DisEnchantingTableRenderer::new);
        });

        DisEnchantingTableNeoForge.eventBus.addListener((Consumer<RegisterMenuScreensEvent>) event -> {
            event.register(ModMenus.DISENCHANTING_TABLE, DisEnchantingTableScreen::new);
        });

        DisEnchantingTableClient.createOrLoadConfiguration();
    }
}
