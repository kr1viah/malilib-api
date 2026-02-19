/*package kr1v.malilibApi.widget;

import fi.dy.masa.malilib.gui.widgets.WidgetDropDownList;
import fi.dy.masa.malilib.interfaces.IStringRetriever;
import kr1v.malilibApi.config._new.ConfigDropdown;

public class ConfigDropdownWidget<T> extends WidgetDropDownList<T> {
	@org.jetbrains.annotations.NotNull
	private final ConfigDropdown<T> config;

	public ConfigDropdownWidget(int x, int y, int width, int height, int maxHeight, ConfigDropdown<T> config, IStringRetriever<T> stringRetriever) {
		super(x, y, width, height, maxHeight, 5, config.getValues(), stringRetriever);
		this.config = config;
		this.setSelectedEntry(config.getActiveValue());
	}

	@Override
	public WidgetDropDownList<T> setSelectedEntry(T entry) {
		this.config.setActiveValue(entry);
		return super.setSelectedEntry(entry);
	}

	@Override
	protected void setSelectedEntry(int index) {
		this.config.setActiveValue(this.config.getValues().get(index));
		super.setSelectedEntry(index);
	}

	@Override
	public boolean onMouseScrolledImpl(int mouseX, int mouseY, double horizontalAmount, double verticalAmount) {
		super.onMouseScrolledImpl(mouseX, mouseY, horizontalAmount, verticalAmount);
		return isOpen;
	}
}
*/