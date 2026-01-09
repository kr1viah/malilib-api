package kr1v.malilibApi.config;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;

import java.util.function.Function;

public class EnumBackedCycleConfig<T extends Enum<T>> extends ConfigCycle<T> {
    public EnumBackedCycleConfig(String name, T initialValue, Class<T> enumClass, String comment, String prettyName, String translatedName, Function<T, String> displayNameProvider) {
        super(name, new EnumBackedCycleEntry<>(enumClass, initialValue, displayNameProvider), comment, prettyName, translatedName);
    }

    @Override
    public T getValue() {
		EnumBackedCycleEntry<T> entry = this.getOptionListValue();
        return entry.getValue();
    }

    @Override
    public void setValue(T value) {
		EnumBackedCycleEntry<T> entry = this.getOptionListValue();
        entry.setValue(value);
    }


	@SuppressWarnings("unchecked")
	@Override
	public EnumBackedCycleEntry<T> getOptionListValue() {
		return (EnumBackedCycleEntry<T>) super.getOptionListValue();
	}

	public static class Builder<T extends Enum<T>> {
		private String name;
		private Class<T> enumClass;
		private T initialValue;
		private String comment;
		private String prettyName;
		private Function<T, String> displayNameProvider;
		private String translatedName;

		public Builder(String name, Class<T> enumClass) {
			if (name == null) throw new IllegalArgumentException("name == null");
			if (enumClass == null) throw new IllegalArgumentException("enumClass == null");
			this.name = name;
			this.enumClass = enumClass;
		}

		public Builder<T> name(String name) {
			if (name == null) throw new IllegalArgumentException("name == null");
			this.name = name;
			return this;
		}

		public Builder<T> enumClass(Class<T> enumClass) {
			if (enumClass == null) throw new IllegalArgumentException("enumClass == null");
			this.enumClass = enumClass;
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

		public EnumBackedCycleConfig<T> build() {
			if (name == null) throw new IllegalArgumentException("name == null");
			if (enumClass == null) throw new IllegalArgumentException("enumClass == null");

			T[] constants = enumClass.getEnumConstants();
			if (constants == null || constants.length == 0) {
				throw new IllegalArgumentException("Not an enum class or has no constants: " + enumClass);
			}

			if (initialValue == null) {
				initialValue = constants[0];
			}

			if (translatedName == null) translatedName = name;
			if (prettyName    == null) prettyName     = name;
			if (comment       == null) comment        = "";

			return new EnumBackedCycleConfig<>(
					name,
					initialValue,
					enumClass,
					comment,
					prettyName,
					translatedName,
					displayNameProvider
			);
		}
	}


	public static class EnumBackedCycleEntry<T extends Enum<T>> implements IConfigOptionListEntry {
        private final T[] constants;
        private final Function<T, String> displayNameProvider;
        private T value;

        public EnumBackedCycleEntry(Class<T> enumClass, T initial, Function<T, String> displayNameProvider) {
            this.constants = enumClass.getEnumConstants();
            if (this.constants == null) throw new IllegalArgumentException("Not an enum class: " + enumClass);
            this.value = initial;
            this.displayNameProvider = displayNameProvider == null ? Enum::name : displayNameProvider;
        }

        @Override
        public String getStringValue() {
            return value.name();
        }

        @Override
        public String getDisplayName() {
            return displayNameProvider.apply(value);
        }

        @Override
        public IConfigOptionListEntry cycle(boolean forward) {
            int idx = -1;
            for (int i = 0; i < constants.length; i++) {
                if (constants[i].equals(value)) idx = i;
            }
            if (idx == -1) return this;
            int next = forward ? (idx + 1) % constants.length : (idx - 1 + constants.length) % constants.length;
            value = constants[next];
            return this;
        }

        @Override
        public IConfigOptionListEntry fromString(String valueStr) {
            for (T val : constants) {
                if (valueStr.compareToIgnoreCase(val.name()) == 0) {
                    value = val;
                    return this;
                }
            }
            return null;
        }

        public T getValue() {
            return value;
        }

        @SuppressWarnings("unchecked")
        public void setValue(Object value) {
            this.value = (T)value;
        }
    }

}
