package kr1v.malilibApi.widget;

import fi.dy.masa.malilib.config.*;
import fi.dy.masa.malilib.config.gui.*;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.button.*;
import fi.dy.masa.malilib.gui.interfaces.IKeybindConfigGui;
import fi.dy.masa.malilib.gui.interfaces.ISliderCallback;
import fi.dy.masa.malilib.gui.widgets.*;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import kr1v.malilibApi.config._new.ConfigPair;

import java.util.ArrayList;
import java.util.List;

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
		x -= 10;
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

			switch (config)
			{
				case IConfigDouble iConfigDouble -> callback = new SliderCallbackDouble(iConfigDouble, resetButton);
				case IConfigFloat iConfigFloat -> callback = new SliderCallbackFloat(iConfigFloat, resetButton);
				case IConfigInteger iConfigInteger -> callback = new SliderCallbackInteger(iConfigInteger, resetButton);
				default ->
				{
					return;
				}
			}

			WidgetSlider slider = new WidgetSlider(x, y, configWidth, configHeight, callback);
			ConfigOptionListenerResetConfig listenerReset = new ConfigOptionListenerResetConfig(config, null, resetButton, null);
			multipleListenerReset.add(listenerReset);

			this.addWidget(slider);
		}

		@Override
		protected void addKeybindResetButton(int x, int y, IKeybind keybind, ConfigButtonKeybind buttonHotkey) {
//			ButtonGeneric button = this.createResetButton(x, y, keybind);

			ConfigOptionChangeListenerKeybind listener = new ConfigOptionChangeListenerKeybind(keybind, buttonHotkey, resetButton, this.host);
			this.host.addKeybindChangeListener(listener::updateButtons);
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
