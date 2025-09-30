package com.cursee.disenchanting_table.core.world.block.entity.function;

import com.cursee.disenchanting_table.DisEnchantingTableServer;
import com.cursee.disenchanting_table.core.util.DisEnchantingHelper;
import com.cursee.disenchanting_table.core.util.ExperienceHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DisEnchantingStrategy {
  public static final int INPUT_SLOT = 0;
  public static final int EXTRA_SLOT = 1;
  public static final int OUTPUT_SLOT = 2;
  public static final int SLOT_COUNT = 3;
  public static final int PROGRESS_DATA_SLOT = 0;
  public static final int DATA_SLOT_COUNT = 1;

  public static boolean canAcceptInput(int slot, ItemStack stack) {
    return slot == 0 && DisEnchantingHelper.canDisenchant(stack);
  }

  public static boolean canAcceptExtra(int slot, ItemStack stack) {
    return slot == 1 && stack.is(Items.BOOK);
  }

  public static boolean canOutputToHopper(int slot, ItemStack stack) {
    return slot == 0 && !DisEnchantingHelper.canDisenchant(stack) || slot == 2;
  }

  public static boolean automaticModeEnabled() {
    return DisEnchantingTableServer.automatic_disenchanting;
  }

  public static int automaticModeTime() {
    return DisEnchantingTableServer.automatic_disenchanting_ticks;
  }

  public static boolean requiresExperience() {
    return DisEnchantingTableServer.requires_experience;
  }

  public static boolean usesPoints() {
    return DisEnchantingTableServer.uses_points;
  }

  public static int experienceCost() {
    return DisEnchantingTableServer.experience_cost;
  }

  public static boolean resetsRepairCost() {
    return DisEnchantingTableServer.resets_repair_cost;
  }

  public static boolean hasEnoughExperience(Player player) {
    if (!requiresExperience()) {
      return true;
    } else if (player.getAbilities().instabuild) {
      return true;
    } else if (player.isCreative()) {
      return true;
    } else {
      return usesPoints() ? ExperienceHelper.hasEnoughExperiencePoints(player, experienceCost()) : ExperienceHelper.hasEnoughExperienceLevels(player, experienceCost());
    }
  }
}
