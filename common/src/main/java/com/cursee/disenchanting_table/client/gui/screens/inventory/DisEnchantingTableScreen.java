package com.cursee.disenchanting_table.client.gui.screens.inventory;

import com.cursee.disenchanting_table.core.world.inventory.DisEnchantingTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DisEnchantingTableScreen extends AbstractContainerScreen<DisEnchantingTableMenu> {

    public DisEnchantingTableScreen(DisEnchantingTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }
}
