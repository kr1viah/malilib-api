package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigInteger;

// TODO: check if its actually sensible to support translatedName in all versions
public class ConfigIntegerPlus extends ConfigInteger implements Plus {
	public ConfigIntegerPlus(String name) {
		this(name, 0);
	}

	public ConfigIntegerPlus(String name, int defaultValue) {
		super(name, defaultValue, "");
	}

	public ConfigIntegerPlus(String name, int defaultValue, String comment) {
		this(name, defaultValue, comment, name);
	}

	public ConfigIntegerPlus(String name, int defaultValue, String comment, String prettyName) {
		this(name, defaultValue, comment, prettyName, name);
	}

	public ConfigIntegerPlus(String name, int defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, comment/*? if >=1.21 {*/, prettyName, translatedName/*? }*/);
	}

	public ConfigIntegerPlus(String name, int defaultValue, int minValue, int maxValue) {
		this(name, defaultValue, minValue, maxValue, "");
	}

	public ConfigIntegerPlus(String name, int defaultValue, int minValue, int maxValue, String comment) {
		this(name, defaultValue, minValue, maxValue, comment, name);
	}

	public ConfigIntegerPlus(String name, int defaultValue, int minValue, int maxValue, String comment, String prettyName) {
		this(name, defaultValue, minValue, maxValue, comment, prettyName, name);
	}

	public ConfigIntegerPlus(String name, int defaultValue, int minValue, int maxValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, comment/*? if >=1.21 {*/, prettyName, translatedName/*? }*/);
	}

	public ConfigIntegerPlus(String name, int defaultValue, int minValue, int maxValue, boolean useSlider) {
		this(name, defaultValue, minValue, maxValue, useSlider, "");
	}

	public ConfigIntegerPlus(String name, int defaultValue, int minValue, int maxValue, boolean useSlider, String comment) {
		this(name, defaultValue, minValue, maxValue, useSlider, comment, name);
	}

	public ConfigIntegerPlus(String name, int defaultValue, int minValue, int maxValue, boolean useSlider, String comment, String prettyName) {
		this(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName, name);
	}

	public ConfigIntegerPlus(String name, int defaultValue, int minValue, int maxValue, boolean useSlider, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment/*? if >=1.21 {*/, prettyName, translatedName/*? }*/);
	}
}
