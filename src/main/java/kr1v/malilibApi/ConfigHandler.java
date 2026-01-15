package kr1v.malilibApi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.util.JsonUtils;
import net.minecraft.client.MinecraftClient;

import java.io.File;

public class ConfigHandler implements IConfigHandler {
	private final String modId;
	private final String configFileName;

	public ConfigHandler(String modId) {
		this(modId, modId + ".json");
	}

	public ConfigHandler(String modId, String configFileName) {
		this.modId = modId;
		this.configFileName = configFileName;
	}

	public void loadAdditionalData(JsonObject root) {
	}

	@Override
	public void load() {
		File configFile = new File(MinecraftClient.getInstance().runDirectory, "config/" + configFileName);

		if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
			JsonElement element = JsonUtils.parseJsonFile(configFile);

			if (element != null && element.isJsonObject()) {
				JsonObject root = element.getAsJsonObject();

				if (root.has("configs") && root.get("configs").isJsonObject()) {
					JsonObject configs = root.get("configs").getAsJsonObject();
					for (Class<?> configClass : InternalMalilibApi.classesFor(modId)) {
						ConfigUtils.readConfigBase(configs, configClass.getSimpleName(), InternalMalilibApi.configListFor(modId, configClass));
					}
				}

				if (root.has("custom_data") && root.get("custom_data").isJsonObject()) {
					JsonObject customData = root.get("custom_data").getAsJsonObject();
					loadAdditionalData(customData);
				}

			}
		}
	}

	public void saveAdditionalData(JsonObject root) {
	}

	@Override
	public void save() {
		File dir = new File(MinecraftClient.getInstance().runDirectory, "config");

		if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
			JsonObject root = new JsonObject();
			JsonObject configs = new JsonObject();
			JsonObject customData = new JsonObject();
			saveAdditionalData(customData);
			for (Class<?> configClass : InternalMalilibApi.classesFor(modId)) {
				ConfigUtils.writeConfigBase(configs, configClass.getSimpleName(), InternalMalilibApi.configListFor(modId, configClass));
			}
			root.add("configs", configs);
			root.add("custom_data", customData);
			JsonUtils.writeJsonToFile(root, new File(dir, configFileName));
		}
	}
}
