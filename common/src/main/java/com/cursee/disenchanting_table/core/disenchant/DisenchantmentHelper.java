package com.cursee.disenchanting_table.core.disenchant;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class DisenchantmentHelper {

    /// Permits Items.ENCHANTED_BOOK with more than 1 enchantment, or any other enchanted ItemStack with 1 or more enchantments.
    public static boolean canDisenchant(ItemStack stack) {

        if (stack.is(Items.ENCHANTED_BOOK)) return EnchantmentHelper.getEnchantmentsForCrafting(stack).size() >= 2;
        else return !EnchantmentHelper.getEnchantmentsForCrafting(stack).isEmpty();
    }
}
