package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.platform.Services;
import net.minecraft.resources.ResourceLocation;

public class DisEnchantingTable {

    public static void init() {

        if (Services.PLATFORM.isDevelopmentEnvironment()) {

            Constants.LOG.info("Hello from common initializer!");
        }
    }

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}