package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.core.registry.ModRegistryFabric;
import com.cursee.disenchanting_table.platform.Services;
import net.fabricmc.api.ModInitializer;

public class DisEnchantingTableFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        DisEnchantingTable.init();

        ModRegistryFabric.declare();

        if (Services.PLATFORM.isDevelopmentEnvironment()) {

            Constants.LOG.info("Hello from Fabric initializer!");
        }
    }
}
