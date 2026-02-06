package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigString;

public class ConfigStringPlus extends ConfigString implements Plus {
	public ConfigStringPlus(String name) {
		this(name, "");
	}

	public ConfigStringPlus(String name, String defaultValue) {
		this(name, defaultValue, "");
	}

	public ConfigStringPlus(String name, String defaultValue, String comment) {
		this(name, defaultValue, comment, name);
	}

	public ConfigStringPlus(String name, String defaultValue, String comment, String prettyName) {
		this(name, defaultValue, comment, prettyName, name);
	}

	public ConfigStringPlus(String name, String defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, comment/*? if >=1.21 {*/, prettyName, translatedName/*? }*/);
	}
}
