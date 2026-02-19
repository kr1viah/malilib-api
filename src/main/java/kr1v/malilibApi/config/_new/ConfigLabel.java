package kr1v.malilibApi.config._new;

public class ConfigLabel extends CustomConfigBase<ConfigLabel> implements IDummyConfig {
	private final String label;

	public ConfigLabel(String label) {
		super(label, label, label, label);
		this.label = label;
	}

	@Override
	public String getComment() {
		return label;
	}
}
