package com.cursee.disenchanting_table.client.gui.screens.inventory;

import com.cursee.disenchanting_table.Constants;
import com.cursee.disenchanting_table.DisEnchantingTable;
import com.cursee.disenchanting_table.core.world.inventory.DisEnchantingTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DisEnchantingTableScreen extends AbstractContainerScreen<DisEnchantingTableMenu> {

    private static final Component MANUAL_MODE_TEXT = Component.translatable("text.disenchanting_table.manual_mode");
    private static final Component AUTOMATIC_MODE_TEXT = Component.translatable("text.disenchanting_table.automatic_mode");

    public static final ResourceLocation BACKGROUND = DisEnchantingTable.identifier("textures/gui/container/disenchanting_table.png");

    boolean automatic = false;
    PlainTextButton toggleButton;

    public DisEnchantingTableScreen(DisEnchantingTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.font = Minecraft.getInstance().font;

        this.createToggleButton();
    }

    public void createToggleButton() {

        // todo validate against actual server mode
        Component currentMode = !automatic ? MANUAL_MODE_TEXT : AUTOMATIC_MODE_TEXT;

        // x, y, height, width, component, Consumer<Button>, font
        this.toggleButton = this.addRenderableWidget(new PlainTextButton(15, 15, this.font.width(currentMode), 10, currentMode, this::onPressToggleButton, this.font));
    }

    public void onPressToggleButton(Button button) {

        // todo: send packet to server to toggle tile entity mode
        Constants.LOG.info("Clicked!");
        this.automatic = !this.automatic;
        this.rebuildWidgets();
    }

    @Override
    protected void rebuildWidgets() {
        super.rebuildWidgets();
        this.createToggleButton();
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY += 9999;
        this.inventoryLabelY += 9999;
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        this.init(); // reposition labels
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;


        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
