package kr1v.malilibApi.config._new;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;

public interface IDummyConfig extends IConfigBase, IConfigResettable {
	default void setValueFromJsonElement(JsonElement element) {
	}
	default JsonElement getAsJsonElement() {
		return JsonNull.INSTANCE;
	}
	default boolean isModified() {
		return false;
	}
	default void resetToDefault() {

	}
}
