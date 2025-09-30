package com.cursee.disenchanting_table.core.world.inventory;

import com.cursee.disenchanting_table.DisEnchantingTableClient;
import com.cursee.disenchanting_table.core.registry.ModBlocks;
import com.cursee.disenchanting_table.core.registry.ModMenus;
import com.cursee.disenchanting_table.core.util.DisEnchantingHelper;
import com.cursee.disenchanting_table.core.world.block.entity.function.DisEnchantingStrategy;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DisEnchantingMenu extends ItemCombinerMenu {
  boolean mayPickup;
  Holder<Enchantment> keptEnchantment;
  Integer keptEnchantmentLevel;
  ItemEnchantments stolenEnchantments;

  public DisEnchantingMenu(int containerIndex, Inventory inventory) {
    this(containerIndex, inventory, ContainerLevelAccess.NULL);
  }

  public DisEnchantingMenu(int containerIndex, Inventory inventory, ContainerLevelAccess access) {
    super((MenuType)ModMenus.DISENCHANTING_TABLE, containerIndex, inventory, access, slotDefinition());
    this.mayPickup = false;
    this.keptEnchantment = null;
    this.keptEnchantmentLevel = 0;
    this.stolenEnchantments = null;
  }

  protected boolean isValidBlock(BlockState blockState) {
    return blockState.is((Block)ModBlocks.DISENCHANTING_TABLE);
  }

  private void reset() {
    this.mayPickup = false;
    this.keptEnchantment = null;
    this.keptEnchantmentLevel = 0;
    this.stolenEnchantments = null;
    this.resultSlots.setItem(0, ItemStack.EMPTY);
  }

  public boolean hasResult() {
    return !this.resultSlots.isEmpty();
  }

  public void createResult() {
    ItemStack input = this.inputSlots.getItem(0);
    ItemStack extra = this.inputSlots.getItem(1);
    if (DisEnchantingHelper.canDisenchant(input) && extra.is(Items.BOOK)) {
      if (!input.is(Items.ENCHANTED_BOOK)) {
        this.stolenEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(input);
        ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(output, this.stolenEnchantments);
        this.resultSlots.setItem(0, output);
      } else {
        this.stolenEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(input);
        this.keptEnchantment = (Holder)this.stolenEnchantments.keySet().iterator().next();
        this.keptEnchantmentLevel = this.stolenEnchantments.getLevel(this.keptEnchantment);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(this.stolenEnchantments);
        mutable.removeIf((holder) -> holder.value() == this.keptEnchantment.value());
        this.stolenEnchantments = mutable.toImmutable();
        this.resultSlots.setItem(0, EnchantmentHelper.createBook(new EnchantmentInstance(this.keptEnchantment, this.keptEnchantmentLevel)));
      }

    } else {
      this.reset();
    }
  }

  protected boolean mayPickup(Player player, boolean hasStack) {
    return DisEnchantingStrategy.hasEnoughExperience(player);
  }

  protected void onTake(Player player, ItemStack itemStack) {
    if (DisEnchantingTableClient.render_block_particles) {
      this.access.execute((level, blockPos) -> level.levelEvent(1503, blockPos, 0));
    }

    if (player instanceof ServerPlayer) {
      ItemStack input = this.inputSlots.getItem(0);
      if (!input.is(Items.ENCHANTED_BOOK)) {
        if (DisEnchantingStrategy.resetsRepairCost() && input.has(DataComponents.REPAIR_COST)) {
          input.set(DataComponents.REPAIR_COST, 0);
        }

        if (input.has(DataComponents.ENCHANTMENTS)) {
          EnchantmentHelper.updateEnchantments(input, (mutable) -> mutable.removeIf((holder) -> mutable.keySet().contains(holder)));
        }

        this.inputSlots.setItem(0, input);
      } else {
        if (this.stolenEnchantments == null) {
          return;
        }

        ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(output, this.stolenEnchantments);
        this.inputSlots.setItem(0, output);
      }

      ItemStack extra = this.inputSlots.getItem(1);
      extra.shrink(1);
      this.inputSlots.setItem(1, extra);
      if (DisEnchantingStrategy.requiresExperience() && DisEnchantingStrategy.hasEnoughExperience(player)) {
        if (DisEnchantingStrategy.usesPoints()) {
          player.giveExperiencePoints(-DisEnchantingStrategy.experienceCost());
        } else {
          player.giveExperienceLevels(-DisEnchantingStrategy.experienceCost());
        }
      }

      this.reset();
      if (!this.inputSlots.getItem(0).isEmpty()) {
        this.createResult();
      }

    }
  }

  protected static ItemCombinerMenuSlotDefinition slotDefinition() {
    return ItemCombinerMenuSlotDefinition.create().withSlot(0, 27, 47, DisEnchantingHelper::canDisenchant).withSlot(1, 76, 47, (stack) -> stack.is(Items.BOOK)).withResultSlot(2, 134, 47).build();
  }
}
