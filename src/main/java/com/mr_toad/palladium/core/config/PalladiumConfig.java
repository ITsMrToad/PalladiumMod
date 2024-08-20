package com.mr_toad.palladium.core.config;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mr_toad.palladium.core.Palladium;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class PalladiumConfig {

    public boolean enableShaderUniformCaching = true;
    public boolean enableModernStateHolder = true;
    public boolean enableResourceKeyDedup = true;
    public boolean enableQuadsDedup = true;
    public boolean enableComposterFix = true;

    public int maxNbtSize = 0x1000000;
   
    public ResourceLocationDeduplication resourceLocationDeduplication = ResourceLocationDeduplication.ALL;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ResourceLocationDeduplication.class, new ResourceLocationDeduplication.Adapter()).excludeFieldsWithModifiers(Modifier.PRIVATE).create();

    private PalladiumConfig() {}

    public static PalladiumConfig loadOrCreate() {
        if (Files.exists(getConfigPath())) {
            try (BufferedReader bufferedreader = Files.newBufferedReader(getConfigPath(), Charsets.UTF_8)) {
                return GSON.fromJson(bufferedreader, PalladiumConfig.class);
            } catch (IOException e) {
                Palladium.LOGGER.error("Could not load config", e);
            }
        }
        return new PalladiumConfig();
    }

    public void save() {
        try (final PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getConfigPath().toFile()), StandardCharsets.UTF_8))) {
            printwriter.println(GSON.toJson(this));
        } catch (FileNotFoundException e) {
            Palladium.LOGGER.error("Config file not found", e);
        }
    }

    private static Path getConfigPath() {
        return Path.of(Minecraft.getInstance().gameDirectory.getPath(), "config").resolve("palladium-config.json");
    }
}
