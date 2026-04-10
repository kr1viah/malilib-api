package kr1v.malilibApi.config._new;

// TODO: move this to the new thing
// 	to the past me: what is "the new thing"
public class ConfigLabel extends CustomConfigBase<ConfigLabel, String> implements IDummyConfig {
	private String label;

	public ConfigLabel(String label) {
		super(label, label, label, label);
		this.label = label;
	}

	@Override
	public String getComment() {
		return label;
	}

	@Override
	public String get() {
		throw new IllegalStateException("This makes no sense.");
	}

	@Override
	public String getDefault() {
		throw new IllegalStateException("This makes no sense.");
	}

	@Override
	public void set(String value) {
		throw new IllegalStateException("This makes no sense.");
	}
}
