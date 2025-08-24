package com.cursee.disenchanting_table.core.world.level.block.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class SimpleInventoryBlockEntity extends ModBlockEntity implements Clearable {

    private final SimpleContainer itemHandler;

    public SimpleInventoryBlockEntity(BlockEntityType<? extends BlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler = createItemHandlerInstance();
        this.itemHandler.addListener(container -> setChanged());
    }

    public abstract int inventorySize();

    protected abstract SimpleContainer createItemHandlerInstance();

    public final SimpleContainer getItemHandler() {

        return this.itemHandler;
    }

    @Override
    public void clearContent() {

        this.getItemHandler().clearContent();
    }

    /// SEPARATOR

    @Override
    public void writePacketData(ValueOutput output) {

        ContainerHelper.saveAllItems(output, SimpleInventoryHelper.copyFromInv(this.itemHandler));
    }

    @Override
    public void readPacketData(ValueInput input) {
        NonNullList<ItemStack> temporary = NonNullList.withSize(this.inventorySize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, temporary);
        SimpleInventoryHelper.copyToInv(temporary, this.itemHandler);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.itemHandler.getItems());
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        NonNullList<ItemStack> temporary = NonNullList.withSize(inventorySize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, temporary);
        SimpleInventoryHelper.copyToInv(temporary, itemHandler);
    }

    /// SEPARATOR

    public void setInventory(NonNullList<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            this.getItemHandler().setItem(i, items.get(i));
        }
    }

    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this instanceof Container container) {
            if (this.level != null) {
                Containers.dropContents(this.level, pos, container);
            }
        }
    }

    /// SEPARATOR

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {

        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::saveCustomOnly);
    }
}
