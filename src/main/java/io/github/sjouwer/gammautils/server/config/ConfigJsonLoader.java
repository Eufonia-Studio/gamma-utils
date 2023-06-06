package io.github.sjouwer.gammautils.server.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ConfigJsonLoader {

    public static Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    private static final String CONFIG_NAME = "gammautils-config.json";
    private static ConfigData data;

    public static void loadFromConfig() {
        ConfigJsonLoader.data = ConfigJsonLoader.read();
    }

    public static void saveConfig() {
        ConfigJsonLoader.data = ConfigJsonLoader.write(ConfigJsonLoader.data, true);
    }

    private static ConfigData write(ConfigData config, boolean update) {
        if (config == null) {
            config = new ConfigData();
        }

        Path path = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_NAME);
        if (update || !path.toFile().exists()) {
            try {
                final OutputStreamWriter WRITER = new OutputStreamWriter(new FileOutputStream(path.toFile()), StandardCharsets.UTF_8);
                GSON.toJson(config.toJson(), WRITER);
                WRITER.close();
            } catch (IOException e) {
                e.printStackTrace();
                return new ConfigData();
            }
        }

        return ConfigData.read(path);
    }

    private static ConfigData read() {
        final Path PATH = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_NAME);
        if (!PATH.toFile().exists()) {
            ConfigJsonLoader.write(null, true);
        }
        return ConfigData.fromJson(PATH);
    }

    public static ConfigData data() {
        return ConfigJsonLoader.data;
    }
}
