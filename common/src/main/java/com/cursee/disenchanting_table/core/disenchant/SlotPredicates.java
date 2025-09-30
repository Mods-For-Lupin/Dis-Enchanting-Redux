package com.cursee.disenchanting_table.core.disenchant;

import com.cursee.disenchanting_table.core.world.block.entity.TileEntityConstants;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.BiPredicate;

public class SlotPredicates {

    public static final BiPredicate<Integer, ItemStack> VALID_INPUT = (i, stack) -> i == TileEntityConstants.INPUT_SLOT && DisenchantmentHelper.canDisenchant(stack);
    public static final BiPredicate<Integer, ItemStack> VALID_EXTRA = (i, stack) -> i == TileEntityConstants.EXTRA_SLOT && stack.is(Items.BOOK);
}
