package kr1v.malilibApi.config.custom;

import java.util.function.Function;

public class EnumBackedCycleConfig<T extends Enum<T>> extends ArrayBackedCycleConfig<T> {
	public EnumBackedCycleConfig(String name, T initialValue, Class<T> enumClass, String comment, String prettyName, String translatedName, Function<T, String> displayNameProvider) {
		super(name, initialValue, comment, prettyName, displayNameProvider, translatedName, enumClass.getEnumConstants());
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
			if (prettyName == null) prettyName = name;
			if (comment == null) comment = "";

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
}
