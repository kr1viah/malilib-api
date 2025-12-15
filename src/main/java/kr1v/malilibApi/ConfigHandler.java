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

    public void loadAdditionalData(JsonObject root) {}

    @Override
    public void load() {
        File configFile = new File(MinecraftClient.getInstance().runDirectory, "config/" + CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject()) {
                JsonObject root = element.getAsJsonObject();

                if (root.has("configs") && root.get("configs").isJsonObject()) {
                    JsonObject configs = root.get("configs").getAsJsonObject();
                    for (Class<?> configClass : AnnotationUtils.classesFor(MOD_ID)) {
                        ConfigUtils.readConfigBase(configs, configClass.getSimpleName(), AnnotationUtils.configListFor(MOD_ID, configClass));
                    }
                }

                if (root.has("custom_data") && root.get("custom_data").isJsonObject()) {
                    JsonObject customData = root.get("custom_data").getAsJsonObject();
                    loadAdditionalData(customData);
                }

            }
        }
    }

    public void saveAdditionalData(JsonObject root) {}

    @Override
    public void save() {
        File dir = new File(MinecraftClient.getInstance().runDirectory, "config");

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
            JsonObject root = new JsonObject();
            JsonObject configs = new JsonObject();
            JsonObject customData = new JsonObject();
            saveAdditionalData(customData);
            for (Class<?> configClass : AnnotationUtils.classesFor(MOD_ID)) {
                ConfigUtils.writeConfigBase(configs, configClass.getSimpleName(), AnnotationUtils.configListFor(MOD_ID, configClass));
            }
            root.add("configs", configs);
            root.add("custom_data", customData);
            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }
}
