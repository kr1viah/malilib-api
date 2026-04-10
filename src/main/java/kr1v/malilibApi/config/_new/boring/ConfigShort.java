package kr1v.malilibApi.config._new.boring;

import kr1v.malilibApi.config.plus.ConfigIntegerPlus;

/// wrapper for ConfigIntegerPlus providing default short min/max and helpers for getting/setting short values
public class ConfigShort extends ConfigIntegerPlus {
	public ConfigShort(String name) {
		super(name, 0, Short.MIN_VALUE, Short.MAX_VALUE);
	}

	public ConfigShort(String name, short defaultValue) {
		super(name, defaultValue, Short.MIN_VALUE, Short.MAX_VALUE);
	}

	public ConfigShort(String name, short defaultValue, String comment) {
		super(name, defaultValue, Short.MIN_VALUE, Short.MAX_VALUE, comment);
	}

	public ConfigShort(String name, short defaultValue, String comment, String prettyName) {
		super(name, defaultValue, Short.MIN_VALUE, Short.MAX_VALUE, comment, prettyName);
	}

	public ConfigShort(String name, short defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, Short.MIN_VALUE, Short.MAX_VALUE, comment, prettyName, translatedName);
	}

	public ConfigShort(String name, short defaultValue, short minValue, short maxValue) {
		super(name, defaultValue, minValue, maxValue);
	}

	public ConfigShort(String name, short defaultValue, short minValue, short maxValue, String comment) {
		super(name, defaultValue, minValue, maxValue, comment);
	}

	public ConfigShort(String name, short defaultValue, short minValue, short maxValue, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName);
	}

	public ConfigShort(String name, short defaultValue, short minValue, short maxValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName, translatedName);
	}

	public ConfigShort(String name, short defaultValue, short minValue, short maxValue, boolean useSlider) {
		super(name, defaultValue, minValue, maxValue, useSlider);
	}

	public ConfigShort(String name, short defaultValue, short minValue, short maxValue, boolean useSlider, String comment) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment);
	}

	public ConfigShort(String name, short defaultValue, short minValue, short maxValue, boolean useSlider, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName);
	}

	public ConfigShort(String name, short defaultValue, short minValue, short maxValue, boolean useSlider, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName, translatedName);
	}

	public short getShortValue() {
		return (short) this.getIntegerValue();
	}

	public short getDefaultShortValue() {
		return (short) this.getDefaultIntegerValue();
	}

	public void setShortValue(short value) {
		this.setIntegerValue(value);
	}

	public short getMinShortValue() {
		return (short) this.getMinIntegerValue();
	}

	public short getMaxShortValue() {
		return (short) this.getMaxIntegerValue();
	}
}