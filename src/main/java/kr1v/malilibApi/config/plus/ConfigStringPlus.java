package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigString;

public class ConfigStringPlus extends ConfigString implements Plus {
    public ConfigStringPlus(String name) {
        super(name, "");
    }

    public ConfigStringPlus(String name, String defaultValue) {
        super(name, defaultValue);
    }

    public ConfigStringPlus(String name, String defaultValue, String comment) {
        super(name, defaultValue, comment);
    }

    public ConfigStringPlus(String name, String defaultValue, String comment, String prettyName) {
        super(name, defaultValue, comment, prettyName);
    }

    public ConfigStringPlus(String name, String defaultValue, String comment, String prettyName, String translatedName) {
        super(name, defaultValue, comment, prettyName, translatedName);
    }
}
