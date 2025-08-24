package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.core.registry.ModRegistryNeoForge;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class DisEnchantingTableNeoForge {

    public static IEventBus eventBus;

    public DisEnchantingTableNeoForge(IEventBus eventBus, ModContainer container, FMLModContainer fmlContainer, Dist dist) {

        DisEnchantingTableNeoForge.eventBus = eventBus;

        DisEnchantingTable.init();

        ModRegistryNeoForge.declare();

        if (FMLEnvironment.dist == Dist.CLIENT) new DisEnchantingTableClientNeoForge();
    }
}