package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import kr1v.malilibApi.MalilibApi;

public class ConfigBooleanPlus extends ConfigBooleanHotkeyed implements Plus {
    {
        getKeybind().setCallback((keyAction, keybind) -> {
            setBooleanValue(!getBooleanValue());
            return true;
        });
    }

    public ConfigBooleanPlus(String name) {
        this(name, MalilibApi.getDefaultEnabled(), "", "");
    }

    public ConfigBooleanPlus(String name, boolean defaultValue) {
        this(name, defaultValue, "", "");
    }

    public ConfigBooleanPlus(String name, boolean defaultValue, String defaultHotkey) {
        this(name, defaultValue, defaultHotkey, "");
    }

    public ConfigBooleanPlus(String name, boolean defaultValue, String defaultHotkey, String comment) {
        super(name, defaultValue, defaultHotkey, comment.isEmpty() ? " " : comment);
    }

    public ConfigBooleanPlus(String name, IHotkeyCallback callback) {
        this(name, MalilibApi.getDefaultEnabled(), "", "");
        getKeybind().setCallback(callback);
    }

    public ConfigBooleanPlus(String name, boolean defaultValue, IHotkeyCallback callback) {
        this(name, defaultValue, "", "");
        getKeybind().setCallback(callback);
    }

    public ConfigBooleanPlus(String name, boolean defaultValue, String defaultHotkey, IHotkeyCallback callback) {
        this(name, defaultValue, defaultHotkey, "");
        getKeybind().setCallback(callback);
    }

    public ConfigBooleanPlus(String name, boolean defaultValue, String defaultHotkey, String comment, IHotkeyCallback callback) {
        super(name, defaultValue, defaultHotkey, comment.isEmpty() ? " " : comment);
        getKeybind().setCallback(callback);
    }

    public ConfigBooleanPlus(String name, boolean defaultValue, String defaultHotkey, KeybindSettings settings, String comment, String prettyName, String translatedName) {
        super(name, defaultValue, defaultHotkey, settings, comment.isEmpty() ? " " : comment, prettyName, translatedName);
    }

    public ConfigBooleanPlus(String name, IValueChangeCallback<ConfigBoolean> callback) {
        this(name, MalilibApi.getDefaultEnabled(), "", "");
        setValueChangeCallback(callback);
    }

    public ConfigBooleanPlus(String name, boolean defaultValue, IValueChangeCallback<ConfigBoolean> callback) {
        this(name, defaultValue, "", "");
        setValueChangeCallback(callback);
    }

    public ConfigBooleanPlus(String name, boolean defaultValue, String defaultHotkey, IValueChangeCallback<ConfigBoolean> callback) {
        this(name, defaultValue, defaultHotkey, "");
        setValueChangeCallback(callback);
    }

    public ConfigBooleanPlus(String name, boolean defaultValue, String defaultHotkey, String comment, IValueChangeCallback<ConfigBoolean> callback) {
        super(name, defaultValue, defaultHotkey, comment.isEmpty() ? " " : comment);
        setValueChangeCallback(callback);
    }
}
