package com.cursee.examplemod;

import com.cursee.examplemod.platform.Services;
import net.minecraft.resources.ResourceLocation;

public class ExampleMod {

    public static void init() {

        if (Services.PLATFORM.isDevelopmentEnvironment()) {

            Constants.LOG.info("Hello from common initializer!");
        }
    }

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}