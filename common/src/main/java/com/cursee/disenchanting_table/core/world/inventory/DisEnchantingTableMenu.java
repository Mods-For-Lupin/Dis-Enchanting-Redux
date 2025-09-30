package com.cursee.disenchanting_table.core.world.inventory;

import com.cursee.disenchanting_table.core.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import com.cursee.disenchanting_table.core.registry.ModMenus;
import com.cursee.disenchanting_table.core.util.DisEnchantingHelper;
import java.util.List;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Items;

public class DisEnchantingTableMenu extends AbstractContainerMenu implements IDisplayInventory {
  public static final int SLOT_Y_POS = 47;
  Container container;
  ContainerData containerData;
  List<Integer> inputSlotIndexes;

  public DisEnchantingTableMenu(int containerID, Inventory inventory) {
    this(containerID, inventory, new SimpleContainer(3), new SimpleContainerData(1));
  }

  public DisEnchantingTableMenu(int containerId, Inventory inventory, Container container, ContainerData containerData) {
    super((MenuType)ModMenus.DISENCHANTING_TABLE, containerId);
    this.inputSlotIndexes = List.of(0, 1);
    checkContainerSize(container, 3);
    checkContainerDataCount(containerData, 1);
    this.container = container;
    this.containerData = containerData;
    this.addSlot(new Slot(this.container, 0, 27, 47) {
      public boolean mayPlace(ItemStack stack) {
        return DisEnchantingHelper.canDisenchant(stack);
      }
    });
    this.addSlot(new Slot(this.container, 1, 76, 47) {
      public boolean mayPlace(ItemStack stack) {
        return stack.is(Items.BOOK);
      }
    });
    this.addSlot(new Slot(this.container, 2, 134, 47) {
      public boolean mayPlace(ItemStack stack) {
        return false;
      }
    });

    for(int row = 0; row < 3; ++row) {
      for(int column = 0; column < 9; ++column) {
        this.addSlot(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
      }
    }

    for(int column = 0; column < 9; ++column) {
      this.addSlot(new Slot(inventory, column, 8 + column * 18, 142));
    }

  }

  public int progress() {
    return this.containerData.get(0);
  }

  public int getResultSlot() {
    return 2;
  }

  protected boolean canMoveIntoInputSlots(ItemStack stack) {
    return DisEnchantingHelper.canDisenchant(stack) || stack.is(Items.BOOK);
  }

  public int getSlotToQuickMoveTo(ItemStack stack) {
    return stack.is(Items.BOOK) ? 1 : 0;
  }

  public ItemStack quickMoveStack(Player player, int index) {
    ItemStack stack = ItemStack.EMPTY;
    Slot slot = (Slot)this.slots.get(index);
    if (!slot.hasItem()) {
      return stack;
    } else {
      ItemStack stackInSlot = slot.getItem();
      stack = stackInSlot.copy();
      int inventorySlotStart = this.getInventorySlotStart();
      int useRowEnd = this.getUseRowEnd();
      if (index == this.getResultSlot()) {
        if (!this.moveItemStackTo(stackInSlot, inventorySlotStart, useRowEnd, true)) {
          return ItemStack.EMPTY;
        }

        slot.onQuickCraft(stackInSlot, stack);
      } else if (this.inputSlotIndexes.contains(index)) {
        if (!this.moveItemStackTo(stackInSlot, inventorySlotStart, useRowEnd, false)) {
          return ItemStack.EMPTY;
        }
      } else if (this.canMoveIntoInputSlots(stackInSlot) && index >= this.getInventorySlotStart() && index < this.getUseRowEnd()) {
        int moveTo = this.getSlotToQuickMoveTo(stack);
        if (!this.moveItemStackTo(stackInSlot, moveTo, this.getResultSlot(), false)) {
          return ItemStack.EMPTY;
        }
      } else if (index >= this.getInventorySlotStart() && index < this.getInventorySlotEnd()) {
        if (!this.moveItemStackTo(stackInSlot, this.getUseRowStart(), this.getUseRowEnd(), false)) {
          return ItemStack.EMPTY;
        }
      } else if (index >= this.getUseRowStart() && index < this.getUseRowEnd() && !this.moveItemStackTo(stackInSlot, this.getInventorySlotStart(), this.getInventorySlotEnd(), false)) {
        return ItemStack.EMPTY;
      }

      if (stackInSlot.isEmpty()) {
        slot.setByPlayer(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }

      if (stackInSlot.getCount() == stack.getCount()) {
        return ItemStack.EMPTY;
      } else {
        slot.onTake(player, stackInSlot);
        return stack;
      }
    }
  }

  public boolean stillValid(Player player) {
    return this.container.stillValid(player);
  }
}
