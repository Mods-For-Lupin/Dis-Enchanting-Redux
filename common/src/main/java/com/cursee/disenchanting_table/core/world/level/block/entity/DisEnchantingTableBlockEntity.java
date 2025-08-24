package com.cursee.disenchanting_table.core.world.level.block.entity;

import com.cursee.disenchanting_table.core.registry.ModBlockEntities;
import com.cursee.disenchanting_table.core.world.inventory.DisEnchantingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DisEnchantingTableBlockEntity extends BlockEntity implements MenuProvider {

    public DisEnchantingTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.DISENCHANTING_TABLE, pos, blockState);
    }

    public static void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState, DisEnchantingTableBlockEntity tileEntity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("itemGroup.disenchantingTable");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return this.level instanceof ServerLevel serverLevel ? new DisEnchantingTableMenu(i, inventory) : null;
    }
}
