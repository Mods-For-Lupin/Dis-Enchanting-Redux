package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.platform.Services;
import com.cursee.moandjiezana.toml.Toml;

import java.io.*;

public class DisEnchantingTableServer {

    public static boolean automatic_disenchanting = false;

    public static boolean resets_repair_cost = true;

    public static boolean requires_experience = true;
    public static boolean uses_points = true;
    public static int experience_cost = 25;

    public static void createOrLoadConfiguration() {

        String configDirectoryPathString = Services.PLATFORM.getGameDirectory() + File.separator + "config";
        File configDirectory = new File(configDirectoryPathString);
        if (!configDirectory.isDirectory() && !configDirectory.mkdirs()) {
            DisEnchantingTable.mkdirsFailed(configDirectoryPathString);
            return;
        }

        String configFileNameString = Constants.MOD_ID + "-server.toml";
        String configFilePathString = configDirectoryPathString + File.separator + configFileNameString;
        File configFile = new File(configFilePathString);

        if (!configFile.exists()) {
            try (InputStream inputStream = DisEnchantingTable.class.getClassLoader().getResourceAsStream("assets/" + configFileNameString)) {

                if (inputStream == null) {
                    Constants.LOG.info("Failed to initialize InputStream instance for assets/{}", configFileNameString);
                    return;
                }

                try (BufferedInputStream bis = new BufferedInputStream(inputStream); BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(configFile))) {
                    byte[] buffer = new byte[8192];

                    int bytesRead;
                    while((bytesRead = bis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                }
            }
            catch (IOException e) {
                Constants.LOG.info("Failed to read internal resource: assets/{}", configFileNameString);
                Constants.LOG.info(e.getMessage());
            }
        }
        else {
            Toml toml = new Toml().read(configFile);

            automatic_disenchanting = toml.getBoolean("automatic_disenchanting");
            resets_repair_cost = toml.getBoolean("resets_repair_cost");
            requires_experience = toml.getBoolean("requires_experience");
            uses_points = toml.getBoolean("uses_points");
            experience_cost = Math.toIntExact(toml.getLong("experience_cost"));
        }
    }
}
