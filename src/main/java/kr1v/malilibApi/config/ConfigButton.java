package kr1v.malilibApi.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import fi.dy.masa.malilib.config.options.ConfigBase;

public class ConfigButton extends ConfigBase<ConfigButton> {
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
		return JsonNull.INSTANCE;
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
}
