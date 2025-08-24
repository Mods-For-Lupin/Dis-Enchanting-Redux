package com.cursee.examplemod;

import com.cursee.examplemod.core.registry.ModRegistryForge;
import com.cursee.examplemod.platform.Services;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class ExampleModForge {

    public static BusGroup busGroup;

    public ExampleModForge(FMLJavaModLoadingContext context) {

        ExampleModForge.busGroup = context.getModBusGroup();

        ExampleMod.init();

        ModRegistryForge.declare();

        if (Services.PLATFORM.isDevelopmentEnvironment()) {

            Constants.LOG.info("Hello from Forge initializer!");
        }
    }
}