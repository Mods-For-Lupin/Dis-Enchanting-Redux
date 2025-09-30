package com.cursee.disenchanting_table.core.world.block.entity.function;

import com.cursee.disenchanting_table.core.world.block.entity.DisEnchantingTableBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class AutomaticDisEnchantingProcess {
  Holder<Enchantment> keptEnchantment;
  Integer keptEnchantmentLevel;
  ItemEnchantments stolenEnchantments;

  public AutomaticDisEnchantingProcess(DisEnchantingTableBlockEntity table, Player player) {
    ItemStack input = table.getItem(0);
    if (!input.is(Items.ENCHANTED_BOOK)) {
      this.stolenEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(input);
      ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
      EnchantmentHelper.setEnchantments(output, this.stolenEnchantments);
      table.setItem(2, output);
      if (DisEnchantingStrategy.resetsRepairCost() && input.has(DataComponents.REPAIR_COST)) {
        input.set(DataComponents.REPAIR_COST, 0);
      }

      if (input.has(DataComponents.ENCHANTMENTS) || input.has(DataComponents.STORED_ENCHANTMENTS)) {
        EnchantmentHelper.updateEnchantments(input, (mutablex) -> mutablex.removeIf((holder) -> mutablex.keySet().contains(holder)));
      }

      table.setItem(0, input);
    } else {
      this.stolenEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(input);
      this.keptEnchantment = (Holder)this.stolenEnchantments.keySet().iterator().next();
      this.keptEnchantmentLevel = this.stolenEnchantments.getLevel(this.keptEnchantment);
      ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(this.stolenEnchantments);
      mutable.removeIf((holder) -> holder.value() == this.keptEnchantment.value());
      this.stolenEnchantments = mutable.toImmutable();
      table.setItem(2, EnchantmentHelper.createBook(new EnchantmentInstance(this.keptEnchantment, this.keptEnchantmentLevel)));
      ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
      EnchantmentHelper.setEnchantments(output, this.stolenEnchantments);
      table.setItem(0, output);
    }

    ItemStack bookStack = table.getItem(1);
    bookStack.shrink(1);
    table.setItem(1, bookStack);
    if (DisEnchantingStrategy.requiresExperience() && DisEnchantingStrategy.hasEnoughExperience(player)) {
      if (DisEnchantingStrategy.usesPoints()) {
        player.giveExperiencePoints(-DisEnchantingStrategy.experienceCost());
      } else {
        player.giveExperienceLevels(-DisEnchantingStrategy.experienceCost());
      }
    }

  }
}
