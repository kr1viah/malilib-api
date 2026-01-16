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

				if (root.has("last_tab") && root.get("last_tab").isJsonPrimitive() && root.getAsJsonPrimitive("last_tab").isString()) {
					String lastTab = root.get("last_tab").getAsString();
					ModRepresentation.Tab tab = InternalMalilibApi.getTabForTranslationKey(modId, lastTab);
					InternalMalilibApi.setActiveTabFor(modId, tab);

					// is in here, because don't set scroll if last tab is unknown
					if (root.has("last_scroll") && root.get("last_scroll").isJsonPrimitive() && root.getAsJsonPrimitive("last_scroll").isNumber()) {
						int lastScroll = root.get("last_scroll").getAsInt();
						InternalMalilibApi.setScrollValueFor(modId, lastScroll);
					}
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
			root.addProperty("last_tab", InternalMalilibApi.getActiveTabFor(modId).translationKey());
			root.addProperty("last_scroll", InternalMalilibApi.getScrollValueFor(modId));

			JsonUtils.writeJsonToFile(root, new File(dir, configFileName));
		}
	}
}
