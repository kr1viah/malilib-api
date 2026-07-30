package kr1v.malilibApi.actions.actions;

public abstract class Action {
	private final String name;

	public Action(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void trigger();
}
