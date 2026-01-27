package kr1v.malilibApi.config._new;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.widget.ConfigObjectButton;

import java.util.List;

public class ConfigObject<T> extends CustomConfigBase<ConfigObject<T>> {
	public final ImmutableList<IConfigBase> configs;
	public final ImmutableList<IConfigResettable> resettables;
	public final int distanceFromTops;
	public final int distanceFromSides;
	public final int width;
	public final int height;
	private final T instance;
	private final String buttonDisplayName;

	static {
		InternalMalilibApi.registerButtonBasedConfigType(ConfigObject.class, (widgetConfigOption, config, x, y, configWidth, configHeight) -> new ConfigObjectButton(x, y, configWidth, configHeight, config));
	}

	public ConfigObject(String name, T instance, String modId, String comment) {
		this(name, instance, modId, comment, name, name, name, 110, -1, -1, 300);
	}

	public ConfigObject(String name, T instance, String modId, String comment, String buttonDisplayName) {
		this(name, instance, modId, comment, buttonDisplayName, name, name, 110, -1, -1, 300);
	}

	public ConfigObject(String name, T instance, String modId, String comment, String buttonDisplayName, int distanceFromTops, int distanceFromSides, int width, int height) {
		this(name, instance, modId, comment, buttonDisplayName, name, name, distanceFromTops, distanceFromSides, width, height);
	}

	@SuppressWarnings("unchecked")
	public ConfigObject(String name, T instance, String modId, String comment, String buttonDisplayName, String translatedName, String prettyName, int distanceFromTops, int distanceFromSides, int width, int height) {
		super(name, comment, translatedName, prettyName);

		this.instance = instance;
		this.buttonDisplayName = buttonDisplayName;

		this.distanceFromTops = distanceFromTops;
		this.distanceFromSides = distanceFromSides;
		this.width = width;
		this.height = height;

		ImmutableList.Builder<IConfigBase> configsBuilder = new ImmutableList.Builder<>();
		configsBuilder.addAll((kr1v.malilibApi.util.ConfigUtils.generateOptions(instance.getClass(), modId, false, instance)));
		configs = configsBuilder.build();

		@SuppressWarnings("rawtypes")
		ImmutableList.Builder resettablesBuilder = new ImmutableList.Builder<>();
		configs.forEach(resettablesBuilder::add);
		resettables = (ImmutableList<IConfigResettable>) resettablesBuilder.build();
	}

	@SuppressWarnings("unchecked")
	public ConfigObject(String name, List<IConfigBase> configs, String comment, String buttonDisplayName, String translatedName, String prettyName, int distanceFromTops, int distanceFromSides, int width, int height) {
		super(name, comment, translatedName, prettyName);

		this.instance = null;
		this.buttonDisplayName = buttonDisplayName;

		this.distanceFromTops = distanceFromTops;
		this.distanceFromSides = distanceFromSides;
		this.width = width;
		this.height = height;

		ImmutableList.Builder<IConfigBase> configsBuilder = new ImmutableList.Builder<>();
		configsBuilder.addAll(configs);
		this.configs = configsBuilder.build();

		@SuppressWarnings("rawtypes")
		ImmutableList.Builder resettablesBuilder = new ImmutableList.Builder<>();
		configs.forEach(resettablesBuilder::add);
		resettables = (ImmutableList<IConfigResettable>) resettablesBuilder.build();
	}

	@Override
	public void setValueFromJsonElement(JsonElement element) {
		if (element.isJsonObject()) {
			JsonObject object = element.getAsJsonObject();
			ConfigUtils.readConfigBase(object, this.getName(), this.configs);
		}
	}

	@Override
	public JsonElement getAsJsonElement() {
		JsonObject object = new JsonObject();
		ConfigUtils.writeConfigBase(object, this.getName(), this.configs);
		return object;
	}

	@Override
	public boolean isModified() {
		for (IConfigResettable config : resettables) {
			if (config.isModified()) return true;
		}
		return false;
	}

	@Override
	public void resetToDefault() {
		for (IConfigResettable config : resettables) {
			config.resetToDefault();
		}
	}

	public T get() {
		return instance;
	}

	@Override
	public String toString() {
		return instance.toString();
	}

	public String getButtonDisplayName() {
		return buttonDisplayName;
	}
}
