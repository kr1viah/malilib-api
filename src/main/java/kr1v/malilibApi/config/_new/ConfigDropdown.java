// some stupid thing is preventing this from working and I cannot be asked to fix it.

//package kr1v.malilibApi.config._new;
//
//import com.google.common.collect.ImmutableList;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonPrimitive;
//import fi.dy.masa.malilib.gui.button.ButtonGeneric;
//import fi.dy.masa.malilib.gui.widgets.WidgetBase;
//import fi.dy.masa.malilib.util.StringUtils;
//import kr1v.malilibApi.InternalMalilibApi;
//import kr1v.malilibApi.interfaces.IWidgetResettableSupplier;
//import kr1v.malilibApi.widget.ConfigDropdownWidget;
//import kr1v.malilibApi.widget.WidgetPair;
//
//import java.util.List;
//
//public class ConfigDropdown<T> extends CustomConfigBase<ConfigDropdown<T>> {
//	private final ImmutableList<T> values;
//	private final int defaultIndex;
//	private int activeIndex;
//
//	public ConfigDropdown(String name, ImmutableList<T> values, T defaultValue, String comment, String translatedName, String prettyName) {
//		super(name, comment, translatedName, prettyName);
//		this.values = values;
//		this.defaultIndex = values.indexOf(defaultValue);
//		this.activeIndex = this.defaultIndex;
//		if (!values.contains(defaultValue)) throw new IllegalStateException("Default value is not in values!");
//	}
//
//	@Override
//	public void setValueFromJsonElement(JsonElement element) {
//		if (element.isJsonPrimitive()) {
//			if (element.getAsJsonPrimitive().isNumber()) {
//				this.activeIndex = element.getAsInt();
//			}
//		}
//	}
//
//	@Override
//	public JsonElement getAsJsonElement() {
//		return new JsonPrimitive(activeIndex);
//	}
//
//	@Override
//	public boolean isModified() {
//		return activeIndex != defaultIndex;
//	}
//
//	@Override
//	public void resetToDefault() {
//		this.activeIndex = defaultIndex;
//	}
//
//	public T getActiveValue() {
//		return values.get(activeIndex);
//	}
//
//	public void setActiveValue(T activeValue) {
//		this.activeIndex = values.indexOf(activeValue);
//	}
//
//	public T getDefaultValue() {
//		return values.get(defaultIndex);
//	}
//
//	public List<T> getValues() {
//		return values;
//	}
//
//	static {
//		InternalMalilibApi.registerWidgetBasedConfigType(
//				ConfigDropdown.class,
//				(IWidgetResettableSupplier<ConfigDropdown<?>>) (widgetConfigOption, dropdown, x, y, configWidth, configHeight) -> {
//					WidgetBase[] widgets = new WidgetBase[2];
//
//					String labelReset = StringUtils.translate("malilib.gui.button.reset.caps");
//					//? if <=1.17.1 {
//					/*int resetButtonXOffset = 4;
//					 *///? } else
//					int resetButtonXOffset = 2;
//					ButtonGeneric resetButton = new ButtonGeneric(x + configWidth + resetButtonXOffset, y, -1, 20, labelReset);
//					resetButton.setEnabled(dropdown.isModified());
//
//					WidgetPair.MultipleReset listenerReset = new WidgetPair.MultipleReset();
//					resetButton.setActionListener(listenerReset);
//					widgets[0] = new ConfigDropdownWidget<>(x + 1, y + 1, configWidth - 2, configHeight - 2, 200, dropdown, Object::toString);
//					widgets[1] = resetButton;
//					return widgets;
//				}
//		);
//	}
//}
