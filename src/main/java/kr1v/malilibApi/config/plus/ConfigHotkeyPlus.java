package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public class ConfigHotkeyPlus extends ConfigHotkey implements Plus<ConfigHotkey> {
	public ConfigHotkeyPlus(String name) {
		this(name, "");
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), "");
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, String comment) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), comment, name);
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, String comment, String prettyName) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), comment, prettyName, name);
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, String comment, String prettyName, String translatedName) {
		super(name, defaultStorageString.replaceAll("\\s+", ""), comment, prettyName/*? if >=1.21 {*/, translatedName/*? }*/);
	}

	public ConfigHotkeyPlus setSettings(KeybindSettings settings) {
		this.getKeybind().setSettings(settings);
		return this;
	}

	public ConfigHotkeyPlus setPressCallback(IHotkeyCallback callback) {
		this.getKeybind().setCallback(callback);
		return this;
	}
}
