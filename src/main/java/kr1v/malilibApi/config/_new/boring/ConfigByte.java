package kr1v.malilibApi.config._new.boring;

import kr1v.malilibApi.config.plus.ConfigIntegerPlus;

/// wrapper for ConfigIntegerPlus providing default byte min/max and helpers for getting/setting byte values
public class ConfigByte extends ConfigIntegerPlus {
	public ConfigByte(String name) {
		super(name, 0, Byte.MIN_VALUE, Byte.MAX_VALUE);
	}

	public ConfigByte(String name, byte defaultValue) {
		super(name, defaultValue, Byte.MIN_VALUE, Byte.MAX_VALUE);
	}

	public ConfigByte(String name, byte defaultValue, String comment) {
		super(name, defaultValue, Byte.MIN_VALUE, Byte.MAX_VALUE, comment);
	}

	public ConfigByte(String name, byte defaultValue, String comment, String prettyName) {
		super(name, defaultValue, Byte.MIN_VALUE, Byte.MAX_VALUE, comment, prettyName);
	}

	public ConfigByte(String name, byte defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, Byte.MIN_VALUE, Byte.MAX_VALUE, comment, prettyName, translatedName);
	}

	public ConfigByte(String name, byte defaultValue, byte minValue, byte maxValue) {
		super(name, defaultValue, minValue, maxValue);
	}

	public ConfigByte(String name, byte defaultValue, byte minValue, byte maxValue, String comment) {
		super(name, defaultValue, minValue, maxValue, comment);
	}

	public ConfigByte(String name, byte defaultValue, byte minValue, byte maxValue, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName);
	}

	public ConfigByte(String name, byte defaultValue, byte minValue, byte maxValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName, translatedName);
	}

	public ConfigByte(String name, byte defaultValue, byte minValue, byte maxValue, boolean useSlider) {
		super(name, defaultValue, minValue, maxValue, useSlider);
	}

	public ConfigByte(String name, byte defaultValue, byte minValue, byte maxValue, boolean useSlider, String comment) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment);
	}

	public ConfigByte(String name, byte defaultValue, byte minValue, byte maxValue, boolean useSlider, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName);
	}

	public ConfigByte(String name, byte defaultValue, byte minValue, byte maxValue, boolean useSlider, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName, translatedName);
	}

	public byte getByteValue() {
		return (byte) this.getIntegerValue();
	}

	public byte getDefaultByteValue() {
		return (byte) this.getDefaultIntegerValue();
	}

	public void setByteValue(byte value) {
		this.setIntegerValue(value);
	}

	public byte getMinByteValue() {
		return (byte) this.getMinIntegerValue();
	}

	public byte getMaxByteValue() {
		return (byte) this.getMaxIntegerValue();
	}
}
