package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.core.registry.ModRegistryFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class DisEnchantingTableFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        DisEnchantingTable.init();

        ModRegistryFabric.declare();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> DisEnchantingTableServer.createOrLoadConfiguration());
    }
}
