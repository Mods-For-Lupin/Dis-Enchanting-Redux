package com.cursee.disenchanting_table.core.world.level.block.entity;

import com.cursee.disenchanting_table.core.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DisEnchantingTableBlockEntity extends BlockEntity {

    public DisEnchantingTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.DISENCHANTING_TABLE, pos, blockState);
    }

    public static void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState, DisEnchantingTableBlockEntity tileEntity) {}
}
