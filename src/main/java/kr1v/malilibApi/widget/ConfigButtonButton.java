package kr1v.malilibApi.widget;

import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import kr1v.malilibApi.config._new.ConfigButton;

public class ConfigButtonButton extends ButtonGeneric {
	private final ConfigButton configButton;

	public ConfigButtonButton(int x, int y, int width, int height, String text, ConfigButton configButton) {
		super(x, y, width, height, text);
		this.configButton = configButton;
	}

	@Override
	protected boolean onMouseClickedImpl(/*? if >=1.21.10 {*//*net.minecraft.client.gui.Click click, boolean doubleClick*//*? } else {*/int mouseX, int mouseY, int mouseButton/*? }*/) {
		super.onMouseClickedImpl(/*? if >=1.21.10 {*//*click, doubleClick*//*? } else {*/mouseX, mouseY, mouseButton/*? }*/);
		configButton.execute();
		return true;
	}

	@Override
	public void updateDisplayString() {
		this.displayString = configButton.displayName;
	}
}
