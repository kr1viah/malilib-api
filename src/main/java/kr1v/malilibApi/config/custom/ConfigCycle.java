package kr1v.malilibApi.config.custom;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import kr1v.malilibApi.config.plus.Plus;

public abstract class ConfigCycle<T> extends ConfigOptionList implements Plus<ConfigOptionList> {
	T defaultValue;

	public ConfigCycle(String name, CycleConfigEntry<T> defaultValue, String comment, String prettyName, String translatedName) {
		super(name, defaultValue, comment, prettyName/*? if >=1.21 {*/, translatedName/*? }*/);
		this.defaultValue = defaultValue.value;
	}

	public T getValue() {
		return this.getOptionListValue().value;
	}

	public void setValue(T value) {
		this.getOptionListValue().value = value;
	}

	@Override
	public boolean isModified() {
		return !this.defaultValue.equals(this.getOptionListValue().value);
	}

	@Override
	public boolean isModified(String newValue) {
		try {
			return !this.defaultValue.equals(this.getOptionListValue().fromString(newValue));
		} catch (Exception ignored) {
		}

		return true;
	}

	@Override
	public void resetToDefault() {
		this.getOptionListValue().value = defaultValue;
	}

	@Override
	public IConfigOptionListEntry getDefaultOptionListValue() {
		CycleConfigEntry<T> clone = this.getOptionListValue().clone();
		clone.value = defaultValue;
		return clone;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CycleConfigEntry<T> getOptionListValue() {
		return (CycleConfigEntry<T>) super.getOptionListValue();
	}

	@Override
	public String toString() {
		return getValue().toString();
	}

	public static abstract class CycleConfigEntry<T> implements IConfigOptionListEntry {
		private T value;

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public abstract CycleConfigEntry<T> clone();
	}
}
