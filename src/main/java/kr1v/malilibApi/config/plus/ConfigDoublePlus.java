package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigDouble;

public class ConfigDoublePlus extends ConfigDouble implements Plus {
	public ConfigDoublePlus(String name) {
		super(name, 0);
	}

	public ConfigDoublePlus(String name, double defaultValue) {
		super(name, defaultValue);
	}

	public ConfigDoublePlus(String name, double defaultValue, String comment) {
		super(name, defaultValue, comment);
	}

	public ConfigDoublePlus(String name, double defaultValue, String comment, String prettyName) {
		super(name, defaultValue, comment, prettyName);
	}

	public ConfigDoublePlus(String name, double defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, comment, prettyName, translatedName);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue) {
		super(name, defaultValue, minValue, maxValue);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, String comment) {
		super(name, defaultValue, minValue, maxValue, comment);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName, translatedName);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, boolean useSlider) {
		super(name, defaultValue, minValue, maxValue, useSlider);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, boolean useSlider, String comment) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, boolean useSlider, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName);
	}

	public ConfigDoublePlus(String name, double defaultValue, double minValue, double maxValue, boolean useSlider, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName, translatedName);
	}
}
