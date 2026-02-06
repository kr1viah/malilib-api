package kr1v.malilibApi.config.plus;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigStringList;

public class ConfigStringListPlus extends ConfigStringList implements Plus {
	public ConfigStringListPlus(String name) {
		this(name, ImmutableList.of());
	}

	public ConfigStringListPlus(String name, String comment) {
		this(name, ImmutableList.of(), comment);
	}

	public ConfigStringListPlus(String name, ImmutableList<String> defaultValue) {
		this(name, defaultValue, "");
	}

	public ConfigStringListPlus(String name, ImmutableList<String> defaultValue, String comment) {
		this(name, defaultValue, comment, name);
	}

	public ConfigStringListPlus(String name, ImmutableList<String> defaultValue, String comment, String prettyName) {
		this(name, defaultValue, comment, prettyName, name);
	}

	public ConfigStringListPlus(String name, ImmutableList<String> defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, comment/*? if >=1.21 {*/, prettyName, translatedName/*? }*/);
	}
}
