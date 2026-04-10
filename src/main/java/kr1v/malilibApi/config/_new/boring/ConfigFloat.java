package kr1v.malilibApi.config._new.boring;

import kr1v.malilibApi.config.plus.ConfigDoublePlus;

/// wrapper for ConfigDoublePlus providing default float min/max and helpers for getting/setting float values
/// Note: ConfigFloat does exist in vanilla malilib, but it's not available on all versions. This one is!
public class ConfigFloat extends ConfigDoublePlus {
	public ConfigFloat(String name) {
		super(name, 0.0, Float.MIN_VALUE, Float.MAX_VALUE);
	}

	public ConfigFloat(String name, float defaultValue) {
		super(name, defaultValue, Float.MIN_VALUE, Float.MAX_VALUE);
	}

	public ConfigFloat(String name, float defaultValue, String comment) {
		super(name, defaultValue, Float.MIN_VALUE, Float.MAX_VALUE, comment);
	}

	public ConfigFloat(String name, float defaultValue, String comment, String prettyName) {
		super(name, defaultValue, Float.MIN_VALUE, Float.MAX_VALUE, comment, prettyName);
	}

	public ConfigFloat(String name, float defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, Float.MIN_VALUE, Float.MAX_VALUE, comment, prettyName, translatedName);
	}

	public ConfigFloat(String name, float defaultValue, float minValue, float maxValue) {
		super(name, defaultValue, minValue, maxValue);
	}

	public ConfigFloat(String name, float defaultValue, float minValue, float maxValue, String comment) {
		super(name, defaultValue, minValue, maxValue, comment);
	}

	public ConfigFloat(String name, float defaultValue, float minValue, float maxValue, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName);
	}

	public ConfigFloat(String name, float defaultValue, float minValue, float maxValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, comment, prettyName, translatedName);
	}

	public ConfigFloat(String name, float defaultValue, float minValue, float maxValue, boolean useSlider) {
		super(name, defaultValue, minValue, maxValue, useSlider);
	}

	public ConfigFloat(String name, float defaultValue, float minValue, float maxValue, boolean useSlider, String comment) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment);
	}

	public ConfigFloat(String name, float defaultValue, float minValue, float maxValue, boolean useSlider, String comment, String prettyName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName);
	}

	public ConfigFloat(String name, float defaultValue, float minValue, float maxValue, boolean useSlider, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, minValue, maxValue, useSlider, comment, prettyName, translatedName);
	}

	public float getFloatValue() {
		return (float) this.getDoubleValue();
	}

	public float getDefaultFloatValue() {
		return (float) this.getDefaultDoubleValue();
	}

	public void setFloatValue(float value) {
		this.setDoubleValue(value);
	}

	public float getMinFloatValue() {
		return (float) this.getMinDoubleValue();
	}

	public float getMaxFloatValue() {
		return (float) this.getMaxDoubleValue();
	}
}