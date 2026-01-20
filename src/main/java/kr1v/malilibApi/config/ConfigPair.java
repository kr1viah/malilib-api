package kr1v.malilibApi.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;

public class ConfigPair<L extends IConfigBase & IConfigResettable, R extends IConfigBase & IConfigResettable> extends CustomConfigBase<ConfigPair<L, R>> {
	private final L left;
	private final R right;

	public ConfigPair(String name, L left, R right) {
		this(name, left, right, "", name, name);
	}

	public ConfigPair(String name, L left, R right, String comment, String translatedName, String prettyName) {
		super(name, comment, translatedName, prettyName);
		this.left = left;
		this.right = right;
	}

	@Override
	public void setValueFromJsonElement(JsonElement element) {
		if (element.isJsonObject()) {
			JsonObject object = element.getAsJsonObject();
			left.setValueFromJsonElement(object.get("l"));
			right.setValueFromJsonElement(object.get("r"));
		}
	}

	@Override
	public JsonElement getAsJsonElement() {
		JsonObject object = new JsonObject();
		object.add("l", left.getAsJsonElement());
		object.add("r", right.getAsJsonElement());
		return object;
	}

	@Override
	public boolean isModified() {
		return left.isModified() || right.isModified();
	}

	@Override
	public void resetToDefault() {
		left.resetToDefault();
		right.resetToDefault();
//		left.setValueFromJsonElement(l);
//		right.setValueFromJsonElement(r);
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}
}
