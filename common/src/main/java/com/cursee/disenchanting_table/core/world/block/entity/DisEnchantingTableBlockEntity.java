package com.cursee.disenchanting_table.core.world.block.entity;

import com.cursee.disenchanting_table.core.disenchant.SlotPredicates;
import com.cursee.disenchanting_table.core.registry.ModBlockEntities;
import com.cursee.disenchanting_table.core.util.DisEnchantingHelper;
import com.cursee.disenchanting_table.core.util.S2CBlockEntityUpdatePacket;
import com.cursee.disenchanting_table.core.world.block.entity.function.AutomaticDisEnchantingProcess;
import com.cursee.disenchanting_table.core.world.block.entity.function.DisEnchantingStrategy;
import com.cursee.disenchanting_table.core.world.inventory.DisEnchantingMenu;
import com.cursee.disenchanting_table.core.world.inventory.DisEnchantingTableMenu;
import com.cursee.disenchanting_table.core.world.block.entity.util.ExposedSimpleInventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class DisEnchantingTableBlockEntity extends ExposedSimpleInventoryBlockEntity implements MenuProvider {
    int progress;
    boolean dirty;
    final ContainerData data = new ContainerData() {
        public int get(int i) {
            return i == 0 ? DisEnchantingTableBlockEntity.this.progress : 0;
        }

        public void set(int i, int value) {
            if (i == 0) {
                DisEnchantingTableBlockEntity.this.progress = value;
            }

        }

        public int getCount() {
            return 1;
        }
    };

    public DisEnchantingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DISENCHANTING_TABLE, pos, state);
    }

    public int inventorySize() {
        return 3;
    }

    public ItemStack getRenderedItemStack() {
        if (!this.getItem(2).isEmpty()) {
            return this.getItem(2);
        } else {
            return !this.getItem(0).isEmpty() ? this.getItem(0) : ItemStack.EMPTY;
        }
    }

    protected SimpleContainer createItemHandlerInstance() {
        return new SimpleContainer(this.inventorySize()) {
            public boolean canPlaceItem(int slot, ItemStack stack) {
                if (!DisEnchantingStrategy.automaticModeEnabled()) {
                    return false;
                } else {
                    return DisEnchantingStrategy.canAcceptInput(slot, stack) || DisEnchantingStrategy.canAcceptExtra(slot, stack);
                }
            }
        };
    }

    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
        return !DisEnchantingStrategy.automaticModeEnabled() ? false : DisEnchantingStrategy.canOutputToHopper(slot, itemStack);
    }

    public Component getDisplayName() {
        return Component.translatable("itemGroup.disenchantingTable");
    }

    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return (AbstractContainerMenu)(DisEnchantingStrategy.automaticModeEnabled() ? new DisEnchantingTableMenu(i, inventory, this, this.data) : new DisEnchantingMenu(i, inventory, ContainerLevelAccess.create(this.getLevel(), this.getBlockPos())));
    }

    protected void saveAdditional(ValueOutput output) {
        output.putInt("Progress", this.progress);
        super.saveAdditional(output);
    }

    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.progress = input.getIntOr("Progress", 0);
    }

    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    public void setChanged() {
        super.setChanged();
        if (this.level != null) {
            if (!this.level.isClientSide()) {
                this.dirty = true;
            }
        }
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, DisEnchantingTableBlockEntity table) {
        if (DisEnchantingStrategy.automaticModeEnabled()) {
            Player player = level.getNearestPlayer((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), (double)16.0F, false);
            if (player != null && DisEnchantingStrategy.hasEnoughExperience(player)) {
                boolean validInput = DisEnchantingHelper.canDisenchant(table.getItem(0));
                boolean validExtra = table.getItem(1).is(Items.BOOK);
                boolean validOutput = table.getItem(2).isEmpty();
                if (validInput && validExtra && validOutput) {
                    if (table.progress >= DisEnchantingStrategy.automaticModeTime()) {
                        new AutomaticDisEnchantingProcess(table, player);
                        level.levelEvent(1503, blockPos, 0);
                        table.progress = 0;
                    }

                    ++table.progress;
                    table.dirty = true;
                } else {
                    table.progress = 0;
                }

                if (table.dirty) {
                    table.dirty = false;
                    S2CBlockEntityUpdatePacket.sendToClients(table);
                }

            } else {
                table.progress = 0;
            }
        }
    }
}
