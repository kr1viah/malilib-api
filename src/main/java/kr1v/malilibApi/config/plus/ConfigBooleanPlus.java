package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import kr1v.malilibApi.InternalMalilibApi;

public class ConfigBooleanPlus extends ConfigBoolean implements Plus<ConfigBoolean> {
	public ConfigBooleanPlus(String name) {
		this(name, InternalMalilibApi.getDefaultEnabled(), "");
	}

	public ConfigBooleanPlus(String name, boolean defaultValue) {
		this(name, defaultValue, "");
	}

	public ConfigBooleanPlus(String name, boolean defaultValue, String comment) {
		this(name, defaultValue, comment, name);
	}

	public ConfigBooleanPlus(String name, boolean defaultValue, String comment, String prettyName) {
		this(name, defaultValue, comment, prettyName, name);
	}

	public ConfigBooleanPlus(String name, boolean defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, comment, prettyName/*? if >=1.21 {*/, translatedName/*? }*/);
	}
}
