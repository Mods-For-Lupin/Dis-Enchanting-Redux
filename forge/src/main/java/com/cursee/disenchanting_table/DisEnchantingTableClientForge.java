package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.client.gui.screens.inventory.DisEnchantingTableScreen;
import com.cursee.disenchanting_table.client.renderer.blockentity.DisEnchantingTableRenderer;
import com.cursee.disenchanting_table.core.registry.ModBlockEntities;
import com.cursee.disenchanting_table.core.registry.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class DisEnchantingTableClientForge {

    public DisEnchantingTableClientForge() {

        EntityRenderersEvent.RegisterRenderers.getBus(DisEnchantingTableForge.busGroup).addListener(event -> {
           event.registerBlockEntityRenderer(ModBlockEntities.DISENCHANTING_TABLE, DisEnchantingTableRenderer::new);
        });

        MenuScreens.register(ModMenus.DISENCHANTING_TABLE, DisEnchantingTableScreen::new);

        DisEnchantingTableClient.createOrLoadConfiguration();
    }
}
