package com.cursee.disenchanting_table.core.world.inventory;

public interface IDisplayInventory {
  int getResultSlot();

  default int getInventorySlotStart() {
    return this.getResultSlot() + 1;
  }

  default int getInventorySlotEnd() {
    return this.getInventorySlotStart() + 27;
  }

  default int getUseRowStart() {
    return this.getInventorySlotEnd();
  }

  default int getUseRowEnd() {
    return this.getUseRowStart() + 9;
  }
}
