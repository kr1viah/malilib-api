package kr1v.malilibApi.config;

import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBase;

public class ConfigButton extends ConfigBase<ConfigButton> implements IConfigBoolean {
	private final Runnable onPressed;
	public final String displayName;

	public ConfigButton(String name, String buttonDisplayName, Runnable onPressed) {
		this(name, "", name, name, buttonDisplayName, onPressed);
	}

	public ConfigButton(String name, String comment, String prettyName, String translatedName, String buttonDisplayName, Runnable onPressed) {
		super(null, name, comment, prettyName, translatedName);
		this.displayName = buttonDisplayName;
		this.onPressed = onPressed;
	}

	@Override
	public void setValueFromJsonElement(JsonElement element) {
	}

	@Override
	public JsonElement getAsJsonElement() {
		return null;
	}

	@Override
	public boolean isModified() {
		return false;
	}

	@Override
	public void resetToDefault() {

	}

	public void execute() {
		this.onPressed.run();
	}

	@Override
	public boolean getBooleanValue() {
		return false;
	}

	@Override
	public boolean getDefaultBooleanValue() {
		return false;
	}

	@Override
	public void setBooleanValue(boolean value) {

	}

	@Override
	public String getDefaultStringValue() {
		return "";
	}

	@Override
	public void setValueFromString(String value) {

	}

	@Override
	public boolean isModified(String newValue) {
		return false;
	}

	@Override
	public String getStringValue() {
		return "";
	}
}
