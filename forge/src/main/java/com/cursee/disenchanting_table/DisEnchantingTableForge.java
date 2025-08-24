package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.core.registry.ModRegistryForge;
import com.cursee.disenchanting_table.platform.Services;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class DisEnchantingTableForge {

    public static BusGroup busGroup;

    public DisEnchantingTableForge(FMLJavaModLoadingContext context) {

        DisEnchantingTableForge.busGroup = context.getModBusGroup();

        DisEnchantingTable.init();

        ModRegistryForge.declare();

        if (Services.PLATFORM.isDevelopmentEnvironment()) {

            Constants.LOG.info("Hello from Forge initializer!");
        }
    }
}