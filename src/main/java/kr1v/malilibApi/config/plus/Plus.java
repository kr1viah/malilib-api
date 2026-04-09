package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigNotifiable;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;

public interface Plus<T extends IConfigBase, P extends T, V> extends IConfigNotifiable<T> {
	@SuppressWarnings("unchecked")
	default P setChangeCallback(IValueChangeCallback<T> callback) {
		setValueChangeCallback(callback);
		return (P) this;
	}

	V get();
	V getDefault();
	void set(V value);
}
