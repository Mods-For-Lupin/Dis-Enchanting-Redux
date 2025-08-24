package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.client.gui.screens.inventory.DisEnchantingTableScreen;
import com.cursee.disenchanting_table.client.renderer.blockentity.DisEnchantingTableRenderer;
import com.cursee.disenchanting_table.core.registry.ModBlockEntities;
import com.cursee.disenchanting_table.core.registry.ModMenus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class DisEnchantingTableClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockEntityRenderers.register(ModBlockEntities.DISENCHANTING_TABLE, DisEnchantingTableRenderer::new);

        MenuScreens.register(ModMenus.DISENCHANTING_TABLE, DisEnchantingTableScreen::new);

        DisEnchantingTableClient.createOrLoadConfiguration();
    }
}
