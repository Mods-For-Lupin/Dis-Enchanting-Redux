package com.cursee.disenchanting_table.core.registry;

import com.cursee.disenchanting_table.Constants;
import com.cursee.disenchanting_table.DisEnchantingTable;
import com.cursee.disenchanting_table.core.world.block.DisEnchantingTableBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.BiConsumer;

public class ModBlocks {

    public static final Block DISENCHANTING_TABLE = new DisEnchantingTableBlock();

    public static void register(BiConsumer<Block, ResourceLocation> consumer) {
        consumer.accept(DISENCHANTING_TABLE, DisEnchantingTable.identifier(Constants.MOD_ID));
    }
}
