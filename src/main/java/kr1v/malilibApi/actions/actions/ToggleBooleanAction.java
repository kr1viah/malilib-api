package kr1v.malilibApi.actions.actions;

import fi.dy.masa.malilib.config.options.ConfigBoolean;

public class ToggleBooleanAction extends Action {
	private ConfigBoolean option;

	public ToggleBooleanAction(String name, ConfigBoolean option) {
		super(name);
		this.option = option;
	}

	@Override
	public void trigger() {
		option.toggleBooleanValue();
	}

	public ConfigBoolean getOption() {
		return option;
	}

	public void setOption(ConfigBoolean option) {
		this.option = option;
	}
}
