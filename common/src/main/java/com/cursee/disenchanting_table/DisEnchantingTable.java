package com.cursee.disenchanting_table;

import com.cursee.monolib.api.common.sailing.SailingApi;
import net.minecraft.resources.ResourceLocation;

public class DisEnchantingTable {

    public static void init() {

        SailingApi.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);

        DisEnchantingTableServer.createOrLoadConfiguration(); // pre-load configuration, so all config files exist
    }

    public static ResourceLocation identifier(String path) {

        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
    }

    public static void mkdirsFailed(String path) {
        Constants.LOG.info("Insufficient privileges to create directory: {}", path);
        Constants.LOG.info("Config will retain default values.");
    }
}