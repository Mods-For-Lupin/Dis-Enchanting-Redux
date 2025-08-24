package com.cursee.examplemod;

import com.cursee.examplemod.core.registry.ModRegistryFabric;
import com.cursee.examplemod.platform.Services;
import net.fabricmc.api.ModInitializer;

public class ExampleModFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        ExampleMod.init();

        ModRegistryFabric.declare();

        if (Services.PLATFORM.isDevelopmentEnvironment()) {

            Constants.LOG.info("Hello from Fabric initializer!");
        }
    }
}
