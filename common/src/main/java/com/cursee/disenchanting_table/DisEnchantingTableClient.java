package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.platform.Services;
import com.cursee.moandjiezana.toml.Toml;

import java.io.*;

public class DisEnchantingTableClient {

    public static boolean render_block_particles = true;
    public static boolean render_experience_cost = true;
    public static boolean render_table_item = true;

    public static void createOrLoadConfiguration() {

        String configDirectoryPathString = Services.PLATFORM.getGameDirectory() + File.separator + "config";
        File configDirectory = new File(configDirectoryPathString);
        if (!configDirectory.isDirectory() && !configDirectory.mkdirs()) {
            DisEnchantingTable.mkdirsFailed(configDirectoryPathString);
            return;
        }

        String configFileNameString = Constants.MOD_ID + "-client.toml";
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

            render_block_particles = toml.getBoolean("render_block_particles");
            render_experience_cost = toml.getBoolean("render_experience_cost");
            render_table_item = toml.getBoolean("render_table_item");
        }
    }
}
