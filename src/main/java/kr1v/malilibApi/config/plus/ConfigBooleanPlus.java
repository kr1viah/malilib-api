package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import kr1v.malilibApi.InternalMalilibApi;

// you are encouraged to use ConfigBooleanPlus instead, because it has a hotkey for toggling it.
// use this if it doesn't make sense to have a hotkey to toggle it.
public class ConfigBooleanPlus extends ConfigBoolean implements Plus {
	public ConfigBooleanPlus(String name) {
		super(name, InternalMalilibApi.getDefaultEnabled(), " ");
	}
}
