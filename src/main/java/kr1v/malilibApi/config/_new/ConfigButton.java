package kr1v.malilibApi.config._new;

import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.widget.ConfigButtonButton;

public class ConfigButton extends CustomConfigBase<ConfigButton, Void> implements IDummyConfig {
	private final Runnable onPressed;
	public final String displayName;

	public ConfigButton(String name, String buttonDisplayName, Runnable onPressed) {
		this(name, "", name, name, buttonDisplayName, onPressed);
	}

	public ConfigButton(String name, String comment, String prettyName, String translatedName, String buttonDisplayName, Runnable onPressed) {
		super(name, comment, prettyName, translatedName);
		this.displayName = buttonDisplayName;
		this.onPressed = onPressed;
	}

	public void execute() {
		this.onPressed.run();
	}

	static {
		InternalMalilibApi.registerButtonBasedConfigType(ConfigButton.class, (widgetConfigOption, button, x, y, configWidth, configHeight) -> new ConfigButtonButton(x, y, configWidth, configHeight, button.displayName, button));
	}

	@Override
	public Void get() {
		throw new IllegalStateException("This makes no sense.");
	}

	@Override
	public Void getDefault() {
		throw new IllegalStateException("This makes no sense.");
	}

	@Override
	public void set(Void value) {
		throw new IllegalStateException("This makes no sense.");
	}
}
