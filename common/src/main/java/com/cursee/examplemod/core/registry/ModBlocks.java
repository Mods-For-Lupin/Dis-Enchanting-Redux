package com.cursee.examplemod.core.registry;

import com.cursee.examplemod.Constants;
import com.cursee.matyrobbrt.registrationutils.RegistrationProvider;
import com.cursee.matyrobbrt.registrationutils.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

import java.util.LinkedList;
import java.util.function.Supplier;

public class ModBlocks {

    public static final LinkedList<RegistryObject<Block, Block>> ORDERED = new LinkedList<>();
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

    public static void declare() {}

    public static RegistryObject<Block, Block> register(String name, Supplier<Block> supplier) {
        var obj = BLOCKS.register(name, supplier);
        ORDERED.add(obj);
        return obj;
    }
}
