package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public class ConfigHotkeyPlus extends ConfigHotkey {
    public ConfigHotkeyPlus(String name, IHotkeyCallback callback) {
        super(name, "");
        getKeybind().setCallback(callback);
    }

    public ConfigHotkeyPlus(String name, String defaultStorageString, IHotkeyCallback callback) {
        super(name, defaultStorageString.replaceAll("\\s+", ""));
        getKeybind().setCallback(callback);
    }

    public ConfigHotkeyPlus(String name, String defaultStorageString, String comment) {
        super(name, defaultStorageString.replaceAll("\\s+", ""), comment);
    }

    public ConfigHotkeyPlus(String name, String defaultStorageString, String comment, String prettyName) {
        super(name, defaultStorageString.replaceAll("\\s+", ""), comment, prettyName);
    }

    public ConfigHotkeyPlus(String name, String defaultStorageString, String comment, String prettyName, String translatedName) {
        super(name, defaultStorageString.replaceAll("\\s+", ""), comment, prettyName, translatedName);
    }

    public ConfigHotkeyPlus(String name, String defaultStorageString, KeybindSettings settings) {
        super(name, defaultStorageString.replaceAll("\\s+", ""), settings);
    }

    public ConfigHotkeyPlus(String name, String defaultStorageString, KeybindSettings settings, String comment) {
        super(name, defaultStorageString.replaceAll("\\s+", ""), settings, comment);
    }

    public ConfigHotkeyPlus(String name, String defaultStorageString, KeybindSettings settings, String comment, String prettyName) {
        super(name, defaultStorageString.replaceAll("\\s+", ""), settings, comment, prettyName);
    }

    public ConfigHotkeyPlus(String name, String defaultStorageString, KeybindSettings settings, String comment, String prettyName, String translatedName) {
        super(name, defaultStorageString.replaceAll("\\s+", ""), settings, comment, prettyName, translatedName);
    }
}
