package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import kr1v.malilibApi.InternalMalilibApi;

public class ConfigBooleanHotkeyedPlus extends ConfigBooleanHotkeyed implements Plus<ConfigBoolean> {
	public ConfigBooleanHotkeyedPlus(String name) {
		this(name, InternalMalilibApi.getDefaultEnabled(), "", "");
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue) {
		this(name, defaultValue, "", "");
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, String defaultHotkey) {
		this(name, defaultValue, defaultHotkey.replaceAll("\\s+", ""), "");
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, String defaultHotkey, String comment) {
		super(name, defaultValue, defaultHotkey.replaceAll("\\s+", ""), comment);
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, String defaultHotkey, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, defaultHotkey.replaceAll("\\s+", ""), comment, prettyName/*? if >=1.21 {*/, translatedName/*? }*/);
	}

	public ConfigBooleanHotkeyedPlus setSettings(KeybindSettings settings) {
		this.getKeybind().setSettings(settings);
		return this;
	}

	public ConfigBooleanHotkeyedPlus setPressCallback(IHotkeyCallback callback) {
		this.getKeybind().setCallback(callback);
		return this;
	}
}
