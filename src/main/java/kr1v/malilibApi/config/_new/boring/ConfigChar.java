package kr1v.malilibApi.config._new.boring;

import kr1v.malilibApi.config.plus.ConfigIntegerPlus;

/// wrapper for ConfigIntegerPlus providing default char min/max and helpers for getting/setting char values
// todo: maybe uhh do something with the "char" part (display it in the gui)
//  (maybe use a string instead?)
//  (maybe add a new ConfigString that has and ability to set max length, then extend that?)
public class ConfigChar extends ConfigIntegerPlus {
	public ConfigChar(String name) {
		super(name, 0, Character.MIN_VALUE, Character.MAX_VALUE);
	}

	public ConfigChar(String name, char defaultValue) {
		super(name, defaultValue, Character.MIN_VALUE, Character.MAX_VALUE);
	}

	public ConfigChar(String name, char defaultValue, String comment) {
		super(name, defaultValue, Character.MIN_VALUE, Character.MAX_VALUE, comment);
	}

	public ConfigChar(String name, char defaultValue, String comment, String prettyName) {
		super(name, defaultValue, Character.MIN_VALUE, Character.MAX_VALUE, comment, prettyName);
	}

	public ConfigChar(String name, char defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, Character.MIN_VALUE, Character.MAX_VALUE, comment, prettyName, translatedName);
	}

	public ConfigChar(String name, char defaultValue, char minValue, char maxValue) {
		super(name, defaultValue, minValue, maxValue);
	}

	public ConfigChar(String name, char defaultValue, char minValue, char maxValue, String comment) {
		super(name, defaultValue, minValue, maxValue, comment);
	}

	public ConfigChar(String name, char defaultValue, char minValue, char maxValue, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName);
	}

	public ConfigChar(String name, char defaultValue, char minValue, char maxValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName, translatedName);
	}

	public ConfigChar(String name, char defaultValue, char minValue, char maxValue, boolean useSlider) {
		super(name, defaultValue, minValue, maxValue, useSlider);
	}

	public ConfigChar(String name, char defaultValue, char minValue, char maxValue, boolean useSlider, String comment) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment);
	}

	public ConfigChar(String name, char defaultValue, char minValue, char maxValue, boolean useSlider, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName);
	}

	public ConfigChar(String name, char defaultValue, char minValue, char maxValue, boolean useSlider, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName, translatedName);
	}

	public char getCharValue() {
		return (char) this.getIntegerValue();
	}

	public char getDefaultCharValue() {
		return (char) this.getDefaultIntegerValue();
	}

	public void setCharValue(char value) {
		this.setIntegerValue(value);
	}

	public char getMinCharValue() {
		return (char) this.getMinIntegerValue();
	}

	public char getMaxCharValue() {
		return (char) this.getMaxIntegerValue();
	}
}