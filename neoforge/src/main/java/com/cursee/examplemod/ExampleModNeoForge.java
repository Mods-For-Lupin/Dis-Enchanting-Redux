package com.cursee.examplemod;


import com.cursee.examplemod.core.registry.ModRegistryNeoForge;
import com.cursee.examplemod.platform.Services;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;

@Mod(Constants.MOD_ID)
public class ExampleModNeoForge {

    public static IEventBus eventBus;

    public ExampleModNeoForge(IEventBus eventBus, ModContainer container, FMLModContainer fmlContainer, Dist dist) {

        ExampleModNeoForge.eventBus = eventBus;

        ExampleMod.init();

        ModRegistryNeoForge.declare();

        if (Services.PLATFORM.isDevelopmentEnvironment()) {

            Constants.LOG.info("Hello from NeoForge initializer!");
        }
    }
}