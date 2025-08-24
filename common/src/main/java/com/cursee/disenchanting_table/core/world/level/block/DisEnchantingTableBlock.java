package com.cursee.disenchanting_table.core.world.level.block;

import com.cursee.disenchanting_table.core.world.level.block.entity.DisEnchantingTableBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DisEnchantingTableBlock extends BaseEntityBlock {

    public static final MapCodec<DisEnchantingTableBlock> CODEC = simpleCodec(DisEnchantingTableBlock::new);

    protected DisEnchantingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {

        return new DisEnchantingTableBlockEntity(blockPos, blockState);
    }

    protected static <T extends BlockEntity> BlockEntityTicker<T> createFurnaceTicker(Level level, BlockEntityType<T> serverType, BlockEntityType<? extends DisEnchantingTableBlockEntity> clientType) {

        return level instanceof ServerLevel serverLevel ? createTickerHelper(serverType, clientType, (levelX, blockPos, blockState, tileEntity) -> DisEnchantingTableBlockEntity.serverTick(serverLevel, blockPos, blockState, tileEntity)) : null;
    }
}
