package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import kr1v.malilibApi.InternalMalilibApi;

public class ConfigBooleanHotkeyedPlus extends ConfigBooleanHotkeyed implements Plus {
	{
		getKeybind().setCallback((keyAction, keybind) -> {
			setBooleanValue(!getBooleanValue());
			return true;
		});
	}

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
		super(name, defaultValue, defaultHotkey.replaceAll("\\s+", ""), comment.isEmpty() ? " " : comment);
	}

	public ConfigBooleanHotkeyedPlus(String name, IHotkeyCallback callback) {
		this(name, InternalMalilibApi.getDefaultEnabled(), "", "");
		getKeybind().setCallback(callback);
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, IHotkeyCallback callback) {
		this(name, defaultValue, "", "");
		getKeybind().setCallback(callback);
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, String defaultHotkey, IHotkeyCallback callback) {
		this(name, defaultValue, defaultHotkey.replaceAll("\\s+", ""), "");
		getKeybind().setCallback(callback);
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, String defaultHotkey, String comment, IHotkeyCallback callback) {
		super(name, defaultValue, defaultHotkey.replaceAll("\\s+", ""), comment.isEmpty() ? " " : comment);
		getKeybind().setCallback(callback);
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, String defaultHotkey, KeybindSettings settings, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, defaultHotkey.replaceAll("\\s+", ""), settings, comment.isEmpty() ? " " : comment, prettyName, translatedName);
	}

	public ConfigBooleanHotkeyedPlus(String name, IValueChangeCallback<ConfigBoolean> callback) {
		this(name, InternalMalilibApi.getDefaultEnabled(), "", "");
		setValueChangeCallback(callback);
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, IValueChangeCallback<ConfigBoolean> callback) {
		this(name, defaultValue, "", "");
		setValueChangeCallback(callback);
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, String defaultHotkey, IValueChangeCallback<ConfigBoolean> callback) {
		this(name, defaultValue, defaultHotkey.replaceAll("\\s+", ""), "");
		setValueChangeCallback(callback);
	}

	public ConfigBooleanHotkeyedPlus(String name, boolean defaultValue, String defaultHotkey, String comment, IValueChangeCallback<ConfigBoolean> callback) {
		super(name, defaultValue, defaultHotkey.replaceAll("\\s+", ""), comment.isEmpty() ? " " : comment);
		setValueChangeCallback(callback);
	}
}
