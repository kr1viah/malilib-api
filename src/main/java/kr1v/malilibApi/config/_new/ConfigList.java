package kr1v.malilibApi.config._new;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.interfaces.IStringValue;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.interfaces.IHotkeyContainer;
import kr1v.malilibApi.interfaces.IVariableHotkeyContainer;
import kr1v.malilibApi.widget.ConfigListButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ConfigList<T extends IConfigBase & IConfigResettable> extends CustomConfigBase<ConfigList<T>> implements IVariableHotkeyContainer {
	private final Supplier<T> supplier;
	private final String buttonDisplayName;
	private final List<T> list;
	private final int originalSize;

	static {
		InternalMalilibApi.registerButtonBasedConfigType(ConfigList.class, (widgetConfigOption, list, x, y, configWidth, configHeight) -> new ConfigListButton(x, y, configWidth, configHeight, list));
	}

	private final List<Runnable> runnables = new ArrayList<>();

	public ConfigList(String name, Supplier<T> supplier) {
		this(name, List.of(), supplier, "");
	}

	public ConfigList(String name, Supplier<T> supplier, String buttonDisplayName) {
		this(name, List.of(), supplier, buttonDisplayName);
	}

	public ConfigList(String name, List<T> defaultValue, Supplier<T> supplier, String buttonDisplayName) {
		this(name, defaultValue, supplier, "", buttonDisplayName, name, name);
	}

	public ConfigList(String name, Supplier<T> supplier, String comment, String buttonDisplayName) {
		this(name, List.of(), supplier, comment, buttonDisplayName);
	}

	public ConfigList(String name, List<T> defaultValue, Supplier<T> supplier, String comment, String buttonDisplayName) {
		this(name, defaultValue, supplier, comment, buttonDisplayName, name, name);
	}

	public ConfigList(String name, List<T> defaultValue, Supplier<T> supplier, String comment, String buttonDisplayName, String translatedName, String prettyName) {
		super(name, comment, translatedName, prettyName);
		this.supplier = supplier;
		this.buttonDisplayName = buttonDisplayName;
		if (!supplier.get().getName().isEmpty()) {
			throw new IllegalStateException("Please make the supplier supply with empty names!");
		}
		this.list = new ArrayList<>(defaultValue);
		this.originalSize = list.size();
	}

	@Override
	public void setValueFromJsonElement(JsonElement element) {
		list.clear();
		if (element.isJsonArray()) {
			JsonArray array = element.getAsJsonArray();
			for (JsonElement element1 : array) {
				T newInstance = supplier.get();
				newInstance.setValueFromJsonElement(element1);
				list.add(newInstance);
			}
		}
		this.run();
	}

	private void run() {
		for (Runnable r : runnables) {
			r.run();
		}
	}

	@Override
	public JsonElement getAsJsonElement() {
		JsonArray array = new JsonArray(list.size());
		for (T t : list) {
			array.add(t.getAsJsonElement());
		}
		return array;
	}

	public List<T> getList() {
		return list;
	}

	public void addNewAfter(int index) {
		T newInstance = supplier.get();
		list.add(index, newInstance);
		this.run();
	}

	public void remove(int index) {
		this.list.remove(index);
		this.run();
	}

	// TODO: resetToDefault and isModified etc
	@Override
	public boolean isModified() {
		if (originalSize != list.size()) {
			return true;
		}
		for (IConfigResettable config : list) {
			if (config.isModified()) return true;
		}
		return false;
	}

	@Override
	public void resetToDefault() {
		for (IConfigResettable config : list) {
			config.resetToDefault();
		}
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");

		boolean isFirst = true;
		for (T item : list) {
			if (!isFirst) {
				builder.append(", ");
			}
			switch (item) {
				case ConfigHotkey configHotkey -> {
					String stringValue = configHotkey.getStringValue();
					builder.append(stringValue.isEmpty() ? "NONE" : configHotkey.getStringValue().replace(",", " + "));
				}
				case ConfigBooleanHotkeyed configBooleanHotkeyed -> {
					String stringValue = configBooleanHotkeyed.getKeybind().getStringValue();
					builder.append("(")
							.append(configBooleanHotkeyed.getStringValue())
							.append(", ")
							.append(stringValue.isEmpty() ? "NONE" : stringValue.replace(",", " + "))
							.append(")");
				}
				case IStringValue representable -> builder.append(representable.getStringValue());
				case ConfigList<?> configList -> builder.append(configList);
				default -> {
					if (item.toString().equals(Objects.toIdentityString(item))) {
						builder.append(item.getAsJsonElement().toString());
					} else {
						builder.append(item);
					}
				}
			}
			isFirst = false;
		}

		builder.append("]");

		return builder.toString();
	}

	public String getButtonDisplayName() {
		return buttonDisplayName;
	}

	@Override
	public List<IHotkey> getHotkeys() {
		List<IHotkey> hotkeys = new ArrayList<>();
		for (IConfigBase config : list) {
			if (config instanceof IHotkey hotkey) {
				hotkeys.add(hotkey);
			} else if (config instanceof IHotkeyContainer container) {
				hotkeys.addAll(container.getHotkeys());
			}
		}
		return hotkeys;
	}

	@Override
	public void registerListener(Runnable toRun) {
		this.runnables.add(toRun);
	}
}
