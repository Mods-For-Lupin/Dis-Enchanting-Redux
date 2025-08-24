package com.cursee.disenchanting_table.core.world.level.block.entity;

import com.cursee.disenchanting_table.core.disenchant.SlotPredicates;
import com.cursee.disenchanting_table.core.registry.ModBlockEntities;
import com.cursee.disenchanting_table.core.world.inventory.DisEnchantingTableMenu;
import com.cursee.disenchanting_table.core.world.level.block.entity.base.ExposedSimpleInventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DisEnchantingTableBlockEntity extends ExposedSimpleInventoryBlockEntity implements MenuProvider {

    int progress = 0;
    int automatic = 0;
    final ContainerData data;

    public DisEnchantingTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.DISENCHANTING_TABLE, pos, blockState);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {

                return switch (index) {
                    case TileEntityConstants.PROGRESS_DATA_SLOT -> DisEnchantingTableBlockEntity.this.progress;
                    case TileEntityConstants.AUTOMATIC_DATA_SLOT -> DisEnchantingTableBlockEntity.this.automatic;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case TileEntityConstants.PROGRESS_DATA_SLOT -> DisEnchantingTableBlockEntity.this.progress = value;
                    case TileEntityConstants.AUTOMATIC_DATA_SLOT -> DisEnchantingTableBlockEntity.this.automatic = value;
                }
            }

            @Override
            public int getCount() {
                return TileEntityConstants.DATA_COUNT;
            }
        };
    }

    @Override
    public int inventorySize() {
        return TileEntityConstants.SLOT_COUNT;
    }

    @Override
    protected SimpleContainer createItemHandlerInstance() {
        return new SimpleContainer(TileEntityConstants.SLOT_COUNT) {
            @Override
            public boolean canPlaceItem(int slot, ItemStack stack) {
                return SlotPredicates.VALID_INPUT.test(slot, stack) || SlotPredicates.VALID_EXTRA.test(slot, stack);
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("itemGroup.disenchantingTable");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return this.level instanceof ServerLevel ? new DisEnchantingTableMenu(i, inventory, this, this.data) : null;
    }

    public static void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState, DisEnchantingTableBlockEntity tile) {}
}
