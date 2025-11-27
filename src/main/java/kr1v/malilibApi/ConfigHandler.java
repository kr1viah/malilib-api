package kr1v.malilibApi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.util.JsonUtils;
import kr1v.malilibApi.util.AnnotationUtils;
import net.minecraft.client.MinecraftClient;

import java.io.File;

public class ConfigHandler implements IConfigHandler {
    private final String MOD_ID;
    private final String CONFIG_FILE_NAME;

    public ConfigHandler(String modId) {
        this.MOD_ID = modId;
        this.CONFIG_FILE_NAME = MOD_ID + ".json";
    }


    @Override
    public void load() {
        File configFile = new File(MinecraftClient.getInstance().runDirectory, "config/" + CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                for (Class<?> configClass : AnnotationUtils.cacheFor(MOD_ID).keySet()) {
                    ConfigUtils.readConfigBase(root, configClass.getSimpleName(), AnnotationUtils.cacheFor(MOD_ID).get(configClass));
                }
            }
        }
    }

    @Override
    public void save() {
        File dir = new File(MinecraftClient.getInstance().runDirectory, "config");

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();
            for (Class<?> configClass : AnnotationUtils.cacheFor(MOD_ID).keySet()) {
                ConfigUtils.writeConfigBase(root, configClass.getSimpleName(), AnnotationUtils.cacheFor(MOD_ID).get(configClass));
            }
            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }
}
