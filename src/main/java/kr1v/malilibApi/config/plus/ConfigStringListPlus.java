package kr1v.malilibApi.config.plus;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigStringList;

public class ConfigStringListPlus extends ConfigStringList implements Plus {
    public ConfigStringListPlus(String name) {
        super(name, ImmutableList.of());
    }

    public ConfigStringListPlus(String name, String comment) {
        super(name, ImmutableList.of(), comment);
    }

    public ConfigStringListPlus(String name, ImmutableList<String> defaultValue) {
        super(name, defaultValue);
    }

    public ConfigStringListPlus(String name, ImmutableList<String> defaultValue, String comment) {
        super(name, defaultValue, comment);
    }

    public ConfigStringListPlus(String name, ImmutableList<String> defaultValue, String comment, String prettyName) {
        super(name, defaultValue, comment, prettyName);
    }

    public ConfigStringListPlus(String name, ImmutableList<String> defaultValue, String comment, String prettyName, String translatedName) {
        super(name, defaultValue, comment, prettyName, translatedName);
    }
}
