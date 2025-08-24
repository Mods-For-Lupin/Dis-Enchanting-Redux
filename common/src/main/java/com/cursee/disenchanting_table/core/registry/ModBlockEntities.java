package com.cursee.disenchanting_table.core.registry;

import com.cursee.disenchanting_table.core.world.level.block.entity.DisEnchantingTableBlockEntity;
import com.cursee.disenchanting_table.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;

public class ModBlockEntities {

    public static final BlockEntityType<DisEnchantingTableBlockEntity> DISENCHANTING_TABLE = Services.PLATFORM.blockEntityType(DisEnchantingTableBlockEntity::new, ModBlocks.DISENCHANTING_TABLE);

    public static void register(BiConsumer<BlockEntityType<?>, ResourceLocation> consumer) {

    }
}
