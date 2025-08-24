package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.platform.Services;
import com.cursee.monolib.core.sailing.Sailing;
import net.minecraft.resources.ResourceLocation;

public class DisEnchantingTable {

    public static void init() {

        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);

        if (Services.PLATFORM.isDevelopmentEnvironment()) {

            Constants.LOG.info("Hello from common initializer!");
        }
    }

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}