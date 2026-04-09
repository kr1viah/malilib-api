package kr1v.malilibApi.config.plus;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigStringList;

import java.util.ArrayList;
import java.util.List;

public class ConfigStringListPlus extends ConfigStringList implements Plus<ConfigStringList, ConfigStringListPlus, List<String>> {
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

	@Override
	public List<String> get() {
		return new ArrayList<>(this.getStrings());
	}

	@Override
	public List<String> getDefault() {
		return this.getDefaultStrings();
	}

	@Override
	public void set(List<String> value) {
		this.setStrings(value);
	}
}
