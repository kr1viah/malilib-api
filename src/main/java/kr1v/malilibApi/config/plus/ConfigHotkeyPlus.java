package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public class ConfigHotkeyPlus extends ConfigHotkey implements Plus<ConfigHotkey> {
	public ConfigHotkeyPlus(String name, IHotkeyCallback callback) {
		this(name, "", callback);
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, IHotkeyCallback callback) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), "");
		getKeybind().setCallback(callback);
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

	public ConfigHotkeyPlus(String name, String defaultStorageString, KeybindSettings settings) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), settings, "");
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, KeybindSettings settings, String comment) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), settings, comment, name);
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, KeybindSettings settings, String comment, String prettyName) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), settings, comment, prettyName, name);
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, KeybindSettings settings, String comment, String prettyName, String translatedName) {
		super(name, defaultStorageString.replaceAll("\\s+", ""), settings, comment, prettyName/*? if >=1.21 {*/, translatedName/*? }*/);
	}

	public ConfigHotkeyPlus setSettings(KeybindSettings settings) {
		this.getKeybind().setSettings(settings);
		return this;
	}
}
