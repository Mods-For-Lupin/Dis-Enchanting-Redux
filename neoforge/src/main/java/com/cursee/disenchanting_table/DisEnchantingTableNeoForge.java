package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.core.registry.ModRegistryNeoForge;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class DisEnchantingTableNeoForge {

    public static IEventBus eventBus;

    public DisEnchantingTableNeoForge(IEventBus eventBus, ModContainer container, FMLModContainer fmlContainer, Dist dist) {

        DisEnchantingTableNeoForge.eventBus = eventBus;

        DisEnchantingTable.init();

        ModRegistryNeoForge.declare();

        if (FMLEnvironment.dist == Dist.CLIENT) new DisEnchantingTableClientNeoForge();

        NeoForge.EVENT_BUS.addListener((Consumer<ServerStartingEvent>) event -> DisEnchantingTableServer.createOrLoadConfiguration());
    }
}