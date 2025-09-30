package com.cursee.disenchanting_table.core.util;

import net.minecraft.world.entity.player.Player;

public class ExperienceHelper {
  public static boolean hasEnoughExperiencePoints(Player player, int amount) {
    if (player.isCreative()) {
      return true;
    } else {
      return amount <= 0 || getTotalPlayerExperiencePoints(player) >= amount;
    }
  }

  public static boolean hasEnoughExperienceLevels(Player player, int amount) {
    if (player.isCreative()) {
      return true;
    } else {
      return amount <= 0 || getTotalPlayerExperienceLevels(player) >= amount;
    }
  }

  private static int getTotalPlayerExperienceLevels(Player player) {
    return player.experienceLevel;
  }

  public static void deductExperiencePoints(Player player, int amount) {
    if (amount > 0) {
      int playerXP = getTotalPlayerExperiencePoints(player);
      if (playerXP >= amount) {
        addExperiencePoints(player, -amount);
      }

    }
  }

  public static void deductExperienceLevels(Player player, int amount) {
    if (amount > 0) {
      player.experienceLevel -= amount;
    }
  }

  public static int getTotalPlayerExperiencePoints(Player player) {
    return (int)((float)getExperiencePointsFromLevel(player.experienceLevel) + player.experienceProgress * (float)player.getXpNeededForNextLevel());
  }

  public static void addExperiencePoints(Player player, int amount) {
    int experience = getTotalPlayerExperiencePoints(player) + amount;
    player.totalExperience = experience;
    player.experienceLevel = getLevelFromExperiencePoints(experience);
    int expForLevel = getExperiencePointsFromLevel(player.experienceLevel);
    player.experienceProgress = (float)(experience - expForLevel) / (float)player.getXpNeededForNextLevel();
  }

  public static void addExperienceLevels(Player player, int amount) {
    player.experienceLevel += amount;
  }

  public static int getHighestExperienceAtLevel(int level) {
    if (level >= 30) {
      return 112 + (level - 30) * 9;
    } else {
      return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
    }
  }

  public static int getExperiencePointsFromLevel(int level) {
    if (level == 0) {
      return 0;
    } else if (level <= 15) {
      return arithmeticSeriesSum(level, 7, 2);
    } else {
      return level <= 30 ? 315 + arithmeticSeriesSum(level - 15, 37, 5) : 1395 + arithmeticSeriesSum(level - 30, 112, 9);
    }
  }

  public static int getLevelFromExperiencePoints(int amount) {
    int level = 0;

    while(true) {
      int xpToNextLevel = getHighestExperienceAtLevel(level);
      if (amount < xpToNextLevel) {
        return level;
      }

      ++level;
      amount -= xpToNextLevel;
    }
  }

  private static int arithmeticSeriesSum(int n, int a, int d) {
    return n / 2 * (2 * a + (n - 1) * d);
  }
}
