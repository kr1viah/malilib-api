package kr1v.malilibApi.actions.actions;

import java.util.ArrayList;
import java.util.List;

public class MultiAction extends Action {
	private final List<Action> actions = new ArrayList<>();

	public MultiAction(String name) {
		super(name);
	}

	@Override
	public void trigger() {
		for (Action action : actions) {
			action.trigger();
		}
	}

	public void add(Action action) {
		this.actions.add(action);
	}

	public void remove(Action action) {
		this.actions.remove(action);
	}

	public void clear() {
		this.actions.clear();
	}
}
