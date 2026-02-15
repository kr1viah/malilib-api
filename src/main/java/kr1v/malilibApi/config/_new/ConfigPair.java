package kr1v.malilibApi.config._new;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.util.StringUtils;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.interfaces.IHotkeyContainer;
import kr1v.malilibApi.interfaces.IWidgetResettableSupplier;
import kr1v.malilibApi.mixin.accessor.WidgetConfigOptionAccessor;
import kr1v.malilibApi.mixin.accessor.WidgetConfigOptionBaseAccessor;
import kr1v.malilibApi.widget.WidgetPair;

import java.util.ArrayList;
import java.util.List;

public class ConfigPair<L extends IConfigBase & IConfigResettable, R extends IConfigBase & IConfigResettable> extends CustomConfigBase<ConfigPair<L, R>> implements IHotkeyContainer {
	private final L left;
	private final R right;

	public ConfigPair(String name, L left, R right) {
		this(name, left, right, "", name, name);
	}

	public ConfigPair(String name, L left, R right, String comment) {
		this(name, left, right, comment, name, name);
	}

	public ConfigPair(String name, L left, R right, String comment, String translatedName, String prettyName) {
		super(name, comment, translatedName, prettyName);
		this.left = left;
		this.right = right;
		if (!right.getName().isEmpty()) {
			throw new IllegalStateException("Please make right have an empty name! " + this);
		}
		if (!left.getName().isEmpty()) {
			throw new IllegalStateException("Please make left have an empty name! " + this);
		}
	}

	@Override
	public void setValueFromJsonElement(JsonElement element) {
		if (element.isJsonObject()) {
			JsonObject object = element.getAsJsonObject();
			left.setValueFromJsonElement(object.get("l"));
			right.setValueFromJsonElement(object.get("r"));
		}
	}

	@Override
	public JsonElement getAsJsonElement() {
		JsonObject object = new JsonObject();
		object.add("l", left.getAsJsonElement());
		object.add("r", right.getAsJsonElement());
		return object;
	}

	@Override
	public boolean isModified() {
		return left.isModified() || right.isModified();
	}

	@Override
	public void resetToDefault() {
		left.resetToDefault();
		right.resetToDefault();
//		left.setValueFromJsonElement(l);
//		right.setValueFromJsonElement(r);
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}

	static {
		InternalMalilibApi.registerWidgetBasedConfigType(ConfigPair.class, (IWidgetResettableSupplier<ConfigPair<?, ?>>) (widgetConfigOption, pair, x, y, configWidth, configHeight) -> {
			WidgetConfigOptionAccessor accessor1 = (WidgetConfigOptionAccessor) widgetConfigOption;
			WidgetConfigOptionBaseAccessor accessor2 = (WidgetConfigOptionBaseAccessor) widgetConfigOption;

			if (widgetConfigOption instanceof WidgetPair.WidgetConfigOptionPair) {
				WidgetPair.WidgetConfigOptionPair pair1 = (WidgetPair.WidgetConfigOptionPair) widgetConfigOption;
				WidgetPair pair2 = pair1.getEnclosing();
				ButtonGeneric resetButton = pair2.resetButton;
				WidgetPair.MultipleReset listenerReset = pair2.multipleListenerReset;
				return new WidgetBase[]{new WidgetPair(x, y, configWidth + 21, configHeight, pair, widgetConfigOption.getListIndex(), configWidth, accessor1.getHost(), accessor2.getParent(), resetButton, listenerReset)};
			} else {
				WidgetBase[] widgets = new WidgetBase[2];

				String labelReset = StringUtils.translate("malilib.gui.button.reset.caps");
				//? if <=1.17.1 {
				/*int resetButtonXOffset = 4;
				*///? } else
				int resetButtonXOffset = 2;
				ButtonGeneric resetButton = new ButtonGeneric(x + configWidth + resetButtonXOffset, y, -1, 20, labelReset);
				resetButton.setEnabled(pair.isModified());

				WidgetPair.MultipleReset listenerReset = new WidgetPair.MultipleReset();
				resetButton.setActionListener(listenerReset);
				widgets[0] = new WidgetPair(x, y, configWidth + 21, configHeight, pair, widgetConfigOption.getListIndex(), configWidth, accessor1.getHost(), accessor2.getParent(), resetButton, listenerReset);
				widgets[1] = resetButton;
				return widgets;
			}
		});
	}

	@Override
	public String toString() {
		return left.toString() + " | " + right.toString();
	}

	@Override
	public List<IHotkey> getHotkeys() {
		List<IHotkey> hotkeys = new ArrayList<>();
		if (left instanceof IHotkey) {
			IHotkey hotkey = (IHotkey) left;
			hotkeys.add(hotkey);
		} else if (left instanceof IHotkeyContainer) {
			IHotkeyContainer container = (IHotkeyContainer) left;
			hotkeys.addAll(container.getHotkeys());
		}
		if (right instanceof IHotkey) {
			IHotkey hotkey = (IHotkey) right;
			hotkeys.add(hotkey);
		} else if (right instanceof IHotkeyContainer) {
			IHotkeyContainer container = (IHotkeyContainer) right;
			hotkeys.addAll(container.getHotkeys());
		}
		return hotkeys;
	}
}
