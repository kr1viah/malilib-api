package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public class ConfigHotkeyPlus extends ConfigHotkey implements Plus<ConfigHotkey> {
	public ConfigHotkeyPlus(String name) {
		this(name, "");
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), "");
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, String comment) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), comment, name);
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, String comment, String prettyName) {
		this(name, defaultStorageString.replaceAll("\\s+", ""), comment, prettyName, name);
	}

	public ConfigHotkeyPlus(String name, String defaultStorageString, String comment, String prettyName, String translatedName) {
		super(name, defaultStorageString.replaceAll("\\s+", ""), comment, prettyName/*? if >=1.21 {*/, translatedName/*? }*/);
	}

	public ConfigHotkeyPlus setSettings(KeybindSettings settings) {
		this.getKeybind().setSettings(settings);
		return this;
	}

	public ConfigHotkeyPlus setPressCallback(IHotkeyCallback callback) {
		this.getKeybind().setCallback(callback);
		return this;
	}

	// lowk this sucks but whatever, it works

	public ConfigHotkeyPlus setContext(KeybindSettings.Context context) {
		KeybindSettings originalSettings = this.getKeybind().getSettings();
		return this.setSettings(
				KeybindSettings.create(
						context,
						originalSettings.getActivateOn(),
						originalSettings.getAllowExtraKeys(),
						originalSettings.isOrderSensitive(),
						originalSettings.isExclusive(),
						originalSettings.shouldCancel(),
						originalSettings.getAllowEmpty()
				)
		);
	}

	public ConfigHotkeyPlus setActivateOn(KeyAction activateOn) {
		KeybindSettings originalSettings = this.getKeybind().getSettings();
		return this.setSettings(
				KeybindSettings.create(
						originalSettings.getContext(),
						activateOn,
						originalSettings.getAllowExtraKeys(),
						originalSettings.isOrderSensitive(),
						originalSettings.isExclusive(),
						originalSettings.shouldCancel(),
						originalSettings.getAllowEmpty()
				)
		);
	}

	public ConfigHotkeyPlus setAllowExtraKeys(boolean allowExtraKeys) {
		KeybindSettings originalSettings = this.getKeybind().getSettings();
		return this.setSettings(
				KeybindSettings.create(
						originalSettings.getContext(),
						originalSettings.getActivateOn(),
						allowExtraKeys,
						originalSettings.isOrderSensitive(),
						originalSettings.isExclusive(),
						originalSettings.shouldCancel(),
						originalSettings.getAllowEmpty()
				)
		);
	}

	public ConfigHotkeyPlus setOrderSensitive(boolean orderSensitive) {
		KeybindSettings originalSettings = this.getKeybind().getSettings();
		return this.setSettings(
				KeybindSettings.create(
						originalSettings.getContext(),
						originalSettings.getActivateOn(),
						originalSettings.getAllowExtraKeys(),
						orderSensitive,
						originalSettings.isExclusive(),
						originalSettings.shouldCancel(),
						originalSettings.getAllowEmpty()
				)
		);
	}

	public ConfigHotkeyPlus setExclusive(boolean exclusive) {
		KeybindSettings originalSettings = this.getKeybind().getSettings();
		return this.setSettings(
				KeybindSettings.create(
						originalSettings.getContext(),
						originalSettings.getActivateOn(),
						originalSettings.getAllowExtraKeys(),
						originalSettings.isOrderSensitive(),
						exclusive,
						originalSettings.shouldCancel(),
						originalSettings.getAllowEmpty()
				)
		);
	}

	public ConfigHotkeyPlus setShouldCancel(boolean shouldCancel) {
		KeybindSettings originalSettings = this.getKeybind().getSettings();
		return this.setSettings(
				KeybindSettings.create(
						originalSettings.getContext(),
						originalSettings.getActivateOn(),
						originalSettings.getAllowExtraKeys(),
						originalSettings.isOrderSensitive(),
						originalSettings.isExclusive(),
						shouldCancel,
						originalSettings.getAllowEmpty()
				)
		);
	}

	public ConfigHotkeyPlus setAllowEmpty(boolean allowEmpty) {
		KeybindSettings originalSettings = this.getKeybind().getSettings();
		return this.setSettings(
				KeybindSettings.create(
						originalSettings.getContext(),
						originalSettings.getActivateOn(),
						originalSettings.getAllowExtraKeys(),
						originalSettings.isOrderSensitive(),
						originalSettings.isExclusive(),
						originalSettings.shouldCancel(),
						allowEmpty
				)
		);
	}
}
