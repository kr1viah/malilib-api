package kr1v.malilibApi.config;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;

import java.util.function.Function;

public class ArrayBackedCycleConfig<T> extends ConfigCycle<T> {
	public ArrayBackedCycleConfig(String name, T initialValue, String comment, String prettyName, Function<T, String> displayNameProvider, String translatedName, T[] values) {
		super(name, new ArrayBackedCycleEntry<>(initialValue, displayNameProvider, values), comment, prettyName, translatedName);
	}

	public static class Builder<T> {
		@SafeVarargs
		public Builder(String name, T... values) {
			this.name = name;
			this.values = values;
		}

		public Builder<T> name(String name) {
			if (name == null) throw new IllegalArgumentException("name == null");
			this.name = name;
			return this;
		}

		public Builder<T> initialValue(T initialValue) {
			this.initialValue = initialValue;
			return this;
		}

		public Builder<T> comment(String comment) {
			this.comment = comment;
			return this;
		}

		public Builder<T> prettyName(String prettyName) {
			this.prettyName = prettyName;
			return this;
		}

		public Builder<T> displayNameProvider(Function<T, String> displayNameProvider) {
			this.displayNameProvider = displayNameProvider;
			return this;
		}

		public Builder<T> translatedName(String translatedName) {
			this.translatedName = translatedName;
			return this;
		}

		@SafeVarargs
		public final Builder<T> values(T... values) {
			if (values == null) throw new IllegalArgumentException("values == null");
			this.values = values;
			return this;
		}

		private String name;
		private T initialValue;
		private String comment;
		private String prettyName;
		private Function<T, String> displayNameProvider;
		private String translatedName;
		private T[] values;

		public ArrayBackedCycleConfig<T> build() {
			if (translatedName == null) translatedName = name;
			if (prettyName 	   == null) prettyName     = name;
			if (comment        == null) comment        = "";

			return new ArrayBackedCycleConfig<>(
					name,
					initialValue,
					comment,
					prettyName,
					displayNameProvider,
					translatedName,
					values
			);
		}
	}

	public static class ArrayBackedCycleEntry<T> extends ConfigCycle.CycleConfigEntry<T> {
		private final T[] options;
		final Function<T, String> displayNameProvider;

		public ArrayBackedCycleEntry(T initial, Function<T, String> displayNameProvider, T[] options) {
			if (options == null) throw new IllegalArgumentException("options must not be null");
			this.options = options;
			this.setValue(initial);
			this.displayNameProvider = displayNameProvider == null ? (t -> t == null ? "null" : t.toString()) : displayNameProvider;
			if (initial == null && this.options.length > 0) {
				this.setValue(this.options[0]);
			}
		}

		@Override
		public String getStringValue() {
			return getValue() == null ? "" : getValue().toString();
		}

		@Override
		public String getDisplayName() {
			return displayNameProvider.apply(getValue());
		}

		@Override
		public IConfigOptionListEntry cycle(boolean forward) {
			int idx = -1;
			for (int i = 0; i < options.length; i++) {
				if (options[i].equals(getValue())) idx = i;
			}
			if (idx == -1) return this;
			int next = forward ? (idx + 1) % options.length : (idx - 1 + options.length) % options.length;
			setValue(options[next]);
			return this;
		}

		@Override
		public IConfigOptionListEntry fromString(String valueStr) {
			if (valueStr == null) return null;
			for (T opt : options) {
				if (opt == null) {
					if (valueStr.isEmpty()) {
						this.setValue(null);
						return this;
					}
					continue;
				}
				String s = opt.toString();
				if (valueStr.equalsIgnoreCase(s)) {
					this.setValue(opt);
					return this;
				}
			}
			return null;
		}

		@Override
		public void setValue(T value) {
			super.setValue(value);
		}

		@Override
		public CycleConfigEntry<T> clone() {
			return new ArrayBackedCycleEntry<>(getValue(), displayNameProvider, options);
		}
	}
}