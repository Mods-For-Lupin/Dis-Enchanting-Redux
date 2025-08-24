package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.core.registry.ModRegistryFabric;
import net.fabricmc.api.ModInitializer;

public class DisEnchantingTableFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        DisEnchantingTable.init();

        ModRegistryFabric.declare();
    }
}
