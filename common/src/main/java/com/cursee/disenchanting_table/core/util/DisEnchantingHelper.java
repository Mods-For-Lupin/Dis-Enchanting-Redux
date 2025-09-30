package com.cursee.disenchanting_table.core.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class DisEnchantingHelper {
  public static boolean canDisenchant(ItemStack stack) {
    ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
    if (enchantments.isEmpty()) {
      return false;
    } else {
      return !stack.is(Items.ENCHANTED_BOOK) || enchantments.size() >= 2;
    }
  }
}
