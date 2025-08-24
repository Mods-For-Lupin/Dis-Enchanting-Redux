package com.cursee.disenchanting_table.core.world.inventory;

import com.cursee.disenchanting_table.core.registry.ModMenus;
import com.cursee.disenchanting_table.core.world.level.block.entity.TileEntityConstants;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DisEnchantingTableMenu extends AbstractContainerMenu {

    Container container;
    ContainerData dataAccess;

    public DisEnchantingTableMenu(int containerID, Inventory inventory) {
        this(containerID, inventory, new SimpleContainer(TileEntityConstants.SLOT_COUNT), new SimpleContainerData(TileEntityConstants.DATA_COUNT));
    }

    public DisEnchantingTableMenu(int containerID, Inventory inventory, Container container, ContainerData dataAccess) {
        super(ModMenus.DISENCHANTING_TABLE, containerID);

        AbstractContainerMenu.checkContainerSize(container, TileEntityConstants.SLOT_COUNT);
        AbstractContainerMenu.checkContainerDataCount(dataAccess, TileEntityConstants.DATA_COUNT);

        this.container = container;
        this.dataAccess = dataAccess;

        // player internal inventory slots
        for(int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        // player hotbar inventory slots
        for(int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(inventory, column, 8 + column * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isDeadOrDying();
    }
}
