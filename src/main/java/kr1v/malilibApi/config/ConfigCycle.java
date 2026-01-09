package kr1v.malilibApi.config;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.config.options.ConfigOptionList;

public abstract class ConfigCycle<T> extends ConfigOptionList {
    public ConfigCycle(String name, IConfigOptionListEntry defaultValue, String comment, String prettyName, String translatedName) {
        super(name, defaultValue, comment, prettyName, translatedName);
    }

    public abstract T getValue();
    public abstract void setValue(T value);
}
