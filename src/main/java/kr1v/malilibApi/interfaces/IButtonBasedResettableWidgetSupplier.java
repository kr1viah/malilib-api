package kr1v.malilibApi.interfaces;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;

public interface IButtonBasedResettableWidgetSupplier<T extends IConfigBase & IConfigResettable> extends IWidgetSupplier<T> {
	ButtonBase getButton(WidgetConfigOption widgetConfigOption, T config, int x, int y, int configWidth, int configHeight);
}
