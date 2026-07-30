package kr1v.malilibApi.actions.actions.modify;

import fi.dy.masa.malilib.config.options.ConfigInteger;
import kr1v.malilibApi.actions.actions.Action;

public class ModifyIntegerAction extends Action {
	private ConfigInteger option;
	private Mode mode;
	private double amount;

	public ModifyIntegerAction(String name, ConfigInteger option, Mode mode, double amount) {
		super(name);
		this.option = option;
		this.mode = mode;
		this.amount = amount;
	}

	@Override
	public void trigger() {
		double original = this.option.getIntegerValue();

		switch (mode) {
			case ADD: original += amount; break;
			case MULTIPLY: original *= amount; break;
			case SUBTRACT: original -= amount; break;
		}

		this.option.setIntegerValue((int) Math.round(original));
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public ConfigInteger getOption() {
		return option;
	}

	public void setOption(ConfigInteger option) {
		this.option = option;
	}
}
