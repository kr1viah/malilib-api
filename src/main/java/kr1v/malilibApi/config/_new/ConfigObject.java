package kr1v.malilibApi.config._new;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.widget.ConfigObjectButton;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ConfigObject<T> extends CustomConfigBase<ConfigObject<T>> {
	public final ImmutableList<IConfigBase> configs;
	public final ImmutableList<IConfigResettable> resettables;
	private final T instance;

	public ConfigObject(String name, T instance, String comment) {
		this(name, instance, comment, name, name);
	}
	public ConfigObject(String name, T instance, String comment, String translatedName, String prettyName) {
		super(name, comment, translatedName, prettyName);

		this.instance = instance;

		ImmutableList.Builder<IConfigBase> configsBuilder = new ImmutableList.Builder<>();
		ImmutableList.Builder<IConfigResettable> resettablesBuilder = new ImmutableList.Builder<>();
		try {
			for (Field f : instance.getClass().getDeclaredFields()) {
				int mods = f.getModifiers();
				if (!Modifier.isStatic(mods)) {
					f.setAccessible(true);
					Object o = f.get(instance);
					if (o instanceof IConfigResettable resettable && o instanceof IConfigBase base) {
						configsBuilder.add(base);
						resettablesBuilder.add(resettable);
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		configs = configsBuilder.build();
		resettables = resettablesBuilder.build();
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

	static {
		InternalMalilibApi.registerButtonBasedConfigType(ConfigObject.class, (widgetConfigOption, config, x, y, configWidth, configHeight) -> new ConfigObjectButton(x, y, configWidth, configHeight, config));
	}
}
