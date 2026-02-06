package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigDouble;

public class ConfigDoublePlus extends ConfigDouble implements Plus {
	public ConfigDoublePlus(String name) {
		this(name, 0);
	}

	public ConfigDoublePlus(String name, double defaultValue) {
		this(name, defaultValue, "");
	}

	public ConfigDoublePlus(String name, double defaultValue, String comment) {
		this(name, defaultValue, comment, name);
	}

	public ConfigDoublePlus(String name, double defaultValue, String comment, String prettyName) {
		this(name, defaultValue, comment, prettyName, name);
	}

	public ConfigDoublePlus(String name, double defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, comment/*? if >=1.21 {*/, prettyName, translatedName/*? }*/);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue) {
		this(name, defaultValue, minValue, maxValue, "");
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, String comment) {
		this(name, defaultValue, minValue, maxValue, comment, name);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, String comment, String prettyName) {
		this(name, defaultValue, minValue, maxValue, comment, prettyName, name);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, comment/*? if >=1.21 {*/, prettyName, translatedName/*? }*/);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, boolean useSlider) {
		this(name, defaultValue, minValue, maxValue, useSlider, "");
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, boolean useSlider, String comment) {
		this(name, defaultValue, minValue, maxValue, useSlider, comment, name);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, boolean useSlider, String comment, String prettyName) {
		this(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName, name);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, boolean useSlider, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment/*? if >=1.21 {*/, prettyName, translatedName/*? }*/);
	}
}
