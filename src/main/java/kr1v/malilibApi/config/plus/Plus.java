package kr1v.malilibApi.config.plus;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigNotifiable;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;

public interface Plus<T extends IConfigBase> extends IConfigNotifiable<T> {
	@SuppressWarnings("unchecked")
	default T setChangeCallback(IValueChangeCallback<T> callback) {
		setValueChangeCallback(callback);
		return (T) this;
	}
}
