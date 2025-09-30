package com.cursee.disenchanting_table.core.world.block.entity.util;

import com.google.common.base.Suppliers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public abstract class ExposedSimpleInventoryBlockEntity extends SimpleInventoryBlockEntity implements WorldlyContainer {
    
    private final Supplier<int[]> slots;
    
    public ExposedSimpleInventoryBlockEntity(BlockEntityType<? extends BlockEntity> type, BlockPos pos, BlockState state) {
        
        super(type, pos, state);

        this.slots = Suppliers.memoize(() -> IntStream.range(0, this.inventorySize()).toArray());
    }

    @Override
    public int getContainerSize() {
        return this.inventorySize();
    }

    @Override
    public boolean isEmpty() {
        return this.getItemHandler().isEmpty();
    }
    
    // SEPARATOR

    @Override
    public int countItem(Item item) {
        return this.getItemHandler().countItem(item);
    }

    @Override
    public boolean hasAnyOf(Set<Item> set) {
        return this.getItemHandler().hasAnyOf(set);
    }

    @Override
    public boolean hasAnyMatching(Predicate<ItemStack> predicate) {
        return this.getItemHandler().hasAnyMatching(predicate);
    }

    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) {
        return this.getItemHandler().canTakeItem(target, slot, stack);
    }

    @Override
    public ItemStack getItem(int i) {
        return this.getItemHandler().getItem(i);
    }

    @Override
    public ItemStack removeItem(int i, int count) {
        return this.getItemHandler().removeItem(i, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return this.getItemHandler().removeItemNoUpdate(i);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return this.getItemHandler().canPlaceItem(slot, stack);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        this.getItemHandler().setItem(i, itemStack);
    }

    // SEPARATOR

//    @Override
//    public void startOpen(Player player) {
//        this.getItemHandler().startOpen(player);
//    }
//
//    @Override
//    public void stopOpen(Player player) {
//        this.getItemHandler().stopOpen(player);
//    }

    @Override
    public void startOpen(ContainerUser user) {
        this.getItemHandler().startOpen(user);
    }

    @Override
    public void stopOpen(ContainerUser user) {
        this.getItemHandler().stopOpen(user);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.getItemHandler().stillValid(player);
    }

    // SEPARATOR

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return this.slots.get();
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return canPlaceItem(i, itemStack) && getItem(i).getCount() < getMaxStackSize();
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return true;
    }
}
