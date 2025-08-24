package com.cursee.examplemod.core.registry;

public class ModRegistry {

    public static void declare() {

        ModBlocks.declare();
        ModBlockEntities.declare();

        ModItems.declare();
        ModTabs.declare();

        ModMenus.declare();

        ModEntities.declare();
    }
}
