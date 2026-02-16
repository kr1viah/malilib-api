package kr1v.malilibApi.widget;

import fi.dy.masa.malilib.config.*;
import fi.dy.masa.malilib.config.gui.*;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.MaLiLibIcons;
import fi.dy.masa.malilib.gui.button.*;
import fi.dy.masa.malilib.gui.interfaces.*;
import fi.dy.masa.malilib.gui.widgets.*;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.config._new.ConfigPair;
import kr1v.malilibApi.interfaces.IButtonBasedResettableWidgetSupplier;
import kr1v.malilibApi.interfaces.IWidgetResettableSupplier;
import kr1v.malilibApi.interfaces.IWidgetSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WidgetPair extends WidgetContainer {
	public final ButtonGeneric resetButton;
	public final MultipleReset multipleListenerReset;

	public WidgetPair(int x, int y, int width, int height,
					  ConfigPair<?, ?> config, int listIndex,
					  int configWidth, IKeybindConfigGui host,
					  WidgetListConfigOptionsBase<?, ?> parent,
					  ButtonGeneric resetButton,
					  MultipleReset listenerReset) {
		super(x - 10, y - 1, width, height);
		x -= 10 + 1 - 1;
		y -= 1;
		this.resetButton = resetButton;
		this.multipleListenerReset = listenerReset;
		this.addWidget(new WidgetConfigOptionPair(x, y, width / 2 - 2, height, 0, configWidth / 2 - 2, new GuiConfigsBase.ConfigOptionWrapper(config.getLeft()), listIndex, host, parent));
		this.addWidget(new WidgetConfigOptionPair(x + configWidth / 2 + 2, y, width / 2 - 2, height, 0, (configWidth /*no one should ever have to do this*/ - 3 /*but I guess I do.*/) / 2, new GuiConfigsBase.ConfigOptionWrapper(config.getRight()), listIndex, host, parent));
	}

	public class WidgetConfigOptionPair extends WidgetConfigOption {
		public WidgetConfigOptionPair(int x, int y, int width, int height, int labelWidth, int configWidth, GuiConfigsBase.ConfigOptionWrapper wrapper, int listIndex, IKeybindConfigGui host, WidgetListConfigOptionsBase<?, ?> parent) {
			super(x, y, width, height, labelWidth, configWidth, wrapper, listIndex, host, parent);
		}

		//? if <=1.17.1 {
		/*@Override
		protected void addConfigOption(int x, int y, float zLevel, int labelWidth, int configWidth, IConfigBase config) {
			ConfigType type = config.getType();

			y += 1;
			int configHeight = 20;

			this.addLabel(x, y + 7, labelWidth, 8, 0xFFFFFFFF, config.getConfigGuiDisplayName());

			String comment;
			IConfigInfoProvider infoProvider = this.host.getHoverInfoProvider();

			if (infoProvider != null) {
				comment = infoProvider.getHoverInfo(config);
			} else {
				comment = config.getComment();
			}

			if (comment != null) {
				this.addConfigComment(x, y + 5, labelWidth, 12, comment);
			}

			x += labelWidth + 10;

			for (Map.Entry<Class<?>, IWidgetSupplier<?>> entry : InternalMalilibApi.customConfigMap.entrySet()) {
				Class<?> configClass = entry.getKey();
				IWidgetSupplier<?> widgetSupplier = entry.getValue();

				if (configClass.isInstance(config)){
					if (widgetSupplier instanceof IButtonBasedResettableWidgetSupplier) {
						//noinspection rawtypes
						IButtonBasedResettableWidgetSupplier buttonGiver = (IButtonBasedResettableWidgetSupplier) widgetSupplier;
						//noinspection unchecked
						ButtonBase button = buttonGiver.getButton((WidgetConfigOption) (Object) this, config, x, y, configWidth, configHeight);
						this.addConfigButtonEntry(x + configWidth + 2, y, (IConfigResettable) config, button);
					} else if (widgetSupplier instanceof IWidgetResettableSupplier) {
						//noinspection rawtypes
						IWidgetResettableSupplier widgetGiver = (IWidgetResettableSupplier) widgetSupplier;
						//noinspection unchecked
						WidgetBase[] widgetsToAdd = widgetGiver.getWidget((WidgetConfigOption) (Object) this, config, x, y, configWidth, configHeight);
						for (WidgetBase widget : widgetsToAdd) {
							this.addWidget(widget);
						}
					}
				}
			}

			if (type == ConfigType.BOOLEAN) {
				ConfigButtonBoolean optionButton = new ConfigButtonBoolean(x, y, configWidth, configHeight, (IConfigBoolean) config);
				this.addConfigButtonEntry(x + configWidth + 4, y, (IConfigResettable) config, optionButton);
			} else if (type == ConfigType.OPTION_LIST) {
				ConfigButtonOptionList optionButton = new ConfigButtonOptionList(x, y, configWidth, configHeight, (IConfigOptionList) config);
				this.addConfigButtonEntry(x + configWidth + 4, y, (IConfigResettable) config, optionButton);
			} else if (type == ConfigType.STRING_LIST) {
				ConfigButtonStringList optionButton = new ConfigButtonStringList(x, y, configWidth, configHeight, (IConfigStringList) config, this.host, this.host.getDialogHandler());
				this.addConfigButtonEntry(x + configWidth + 4, y, (IConfigResettable) config, optionButton);
			} else if (type == ConfigType.HOTKEY) {
				configWidth -= 22;
				IKeybind keybind = ((IHotkey) config).getKeybind();
				ConfigButtonKeybind keybindButton = new ConfigButtonKeybind(x, y, configWidth, 20, keybind, this.host);
				x += configWidth + 2;

				this.addWidget(new WidgetKeybindSettings(x, y, 20, 20, keybind, config.getName(), this.parent, this.host.getDialogHandler()));
				x += 22;

				this.addButton(keybindButton, this.host.getButtonPressListener());
				this.addKeybindResetButton(x, y, keybind, keybindButton);
			} else if (type == ConfigType.STRING ||
					type == ConfigType.COLOR ||
					type == ConfigType.INTEGER ||
					type == ConfigType.DOUBLE) {
				int resetX = x + configWidth + 4;

				if (type == ConfigType.COLOR) {
					configWidth -= 24; // adjust the width to match other configs due to the color display
					this.colorDisplayPosX = x + configWidth + 4;
				} else if (type == ConfigType.INTEGER || type == ConfigType.DOUBLE) {
					configWidth -= 18;
					this.colorDisplayPosX = x + configWidth + 2;
				}

				if ((type == ConfigType.INTEGER || type == ConfigType.DOUBLE) &&
						config instanceof IConfigSlider && ((IConfigSlider) config).shouldUseSlider()) {
					this.addConfigSliderEntry(x, y, resetX, configWidth, configHeight, (IConfigSlider) config);
				} else {
					this.addConfigTextFieldEntry(x, y, resetX, configWidth, configHeight, (IConfigValue) config);
				}

				if (config instanceof IConfigSlider) {
					IGuiIcon icon = ((IConfigSlider) config).shouldUseSlider() ? MaLiLibIcons.BTN_TXTFIELD : MaLiLibIcons.BTN_SLIDER;
					ButtonGeneric toggleBtn = new ButtonGeneric(this.colorDisplayPosX, y + 2, icon);
					this.addButton(toggleBtn, new ListenerSliderToggle((IConfigSlider) config));
				}
			}
		}

		*///? } else {
		@Override
		protected void addHotkeyConfigElements(int x, int y, int configWidth, String configName, IHotkey hotkey) {
			configWidth -= 22;
			IKeybind keybind = hotkey.getKeybind();
			ConfigButtonKeybind keybindButton = new ConfigButtonKeybind(x, y, configWidth, 20, keybind, this.host);
			x += configWidth + 2;

			this.addWidget(new WidgetKeybindSettings(x, y, 20, 20, keybind, configName, this.parent, this.host.getDialogHandler()));
			x += 22;

			this.addButton(keybindButton, this.host.getButtonPressListener());
			this.addKeybindResetButton(x, y, keybind, keybindButton);
		}

		@Override
		protected void addBooleanAndHotkeyWidgets(int x, int y, int configWidth, IConfigResettable resettableConfig, IConfigBoolean booleanConfig, IKeybind keybind) {
			int booleanBtnWidth = 60;
			ConfigButtonBoolean booleanButton = new ConfigButtonBoolean(x, y, booleanBtnWidth, 20, booleanConfig);
			x += booleanBtnWidth + 2;
			configWidth -= booleanBtnWidth + 2 + 22;

			ConfigButtonKeybind keybindButton = new ConfigButtonKeybind(x, y, configWidth, 20, keybind, this.host);
			x += configWidth + 2;

			this.addWidget(new WidgetKeybindSettings(x, y, 20, 20, keybind, booleanConfig.getName(), this.parent, this.host.getDialogHandler()));

//			ButtonGeneric resetButton = this.createResetButton(x, y, resettableConfig);

			ConfigOptionChangeListenerButton booleanChangeListener = new ConfigOptionChangeListenerButton(resettableConfig, resetButton, null);
			HotkeyedBooleanResetListener resetListener = new HotkeyedBooleanResetListener(resettableConfig, booleanButton, keybindButton, resetButton, this.host);
			multipleListenerReset.add(resetListener);

			this.host.addKeybindChangeListener(resetListener::updateButtons);

			this.addButton(booleanButton, booleanChangeListener);
			this.addButton(keybindButton, this.host.getButtonPressListener());
//			this.addButton(resetButton, resetListener);
		}
		//? }

		@Override
		protected void addConfigTextFieldEntry(int x, int y, int resetX, int configWidth, int configHeight, IConfigValue config) {
			GuiTextFieldGeneric field = this.createTextField(x, y + 1, configWidth - 4, configHeight - 3);
			field.setMaxLength(this.maxTextfieldTextLength);
			field.setText(config.getStringValue());

			ConfigOptionChangeListenerTextField listenerChange = new ConfigOptionChangeListenerTextField(config, field, resetButton);
			ConfigOptionListenerResetConfig listenerReset = new ConfigOptionListenerResetConfig(config, new ConfigOptionListenerResetConfig.ConfigResetterTextField(config, field), resetButton, null);
			multipleListenerReset.add(listenerReset);

			this.addTextField(field, listenerChange);
		}

		@Override
		protected void addConfigButtonEntry(int xReset, int yReset, IConfigResettable config, ButtonBase optionButton) {
			ConfigOptionChangeListenerButton listenerChange = new ConfigOptionChangeListenerButton(config, resetButton, null);
			ConfigOptionListenerResetConfig listenerReset = new ConfigOptionListenerResetConfig(config, new ConfigOptionListenerResetConfig.ConfigResetterButton(optionButton), resetButton, null);
			multipleListenerReset.add(listenerReset);

			this.addButton(optionButton, listenerChange);
		}

		@Override
		protected void addConfigSliderEntry(int x, int y, int resetX, int configWidth, int configHeight, IConfigSlider config) {
			ISliderCallback callback;

			if (config instanceof IConfigDouble) callback = new SliderCallbackDouble((IConfigDouble) config, resetButton);
			//? if >=1.21
			else if (config instanceof IConfigFloat iConfigFloat) callback = new SliderCallbackFloat(iConfigFloat, resetButton);
			else if (config instanceof IConfigInteger) callback = new SliderCallbackInteger((IConfigInteger) config, resetButton);
			else return;

			WidgetSlider slider = new WidgetSlider(x, y, configWidth, configHeight, callback);
			ConfigOptionListenerResetConfig listenerReset = new ConfigOptionListenerResetConfig(config, null, resetButton, null);
			multipleListenerReset.add(listenerReset);

			this.addWidget(slider);
		}

		@Override
		protected void addKeybindResetButton(int x, int y, IKeybind keybind, ConfigButtonKeybind buttonHotkey) {
//			ButtonGeneric button = this.createResetButton(x, y, keybind);

			ConfigOptionChangeListenerKeybind listener = new ConfigOptionChangeListenerKeybind(keybind, buttonHotkey, resetButton, this.host);
//			this.host.addKeybindChangeListener(listener::updateButtons);
			multipleListenerReset.add(listener);
//			this.addButton(button, listener);
		}

		public WidgetPair getEnclosing() { return WidgetPair.this; }
	}

	public static class MultipleReset implements IButtonActionListener {
		private final List<IButtonActionListener> list = new ArrayList<>();

		public void add(IButtonActionListener configOptionListenerResetConfig) {
			list.add(configOptionListenerResetConfig);
		}

		@Override
		public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
			for (IButtonActionListener configOptionListenerResetConfig : list) {
				configOptionListenerResetConfig.actionPerformedWithButton(button, mouseButton);
			}
		}
	}
}
