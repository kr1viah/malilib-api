package kr1v.malilibApi.widget;

import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.GuiUtils;
import kr1v.malilibApi.config._new.ConfigObject;
import kr1v.malilibApi.screen.ConfigObjectScreen;
import net.minecraft.client.MinecraftClient;

public class ConfigObjectButton extends ButtonGeneric {
	private final ConfigObject<?> configObject;

	public ConfigObjectButton(int x, int y, int width, int height, ConfigObject<?> configObject) {
		super(x, y, width, height, configObject.getButtonDisplayName());
		this.configObject = configObject;
		if (this.displayString.isEmpty()) {
			this.displayString = configObject.toString();
		}
	}

	@Override
	protected boolean onMouseClickedImpl(/*? if >=1.21.10 {*//*net.minecraft.client.gui.Click click, boolean doubleClick*//*? } else {*/int mouseX, int mouseY, int mouseButton/*? }*/) {
		super.onMouseClickedImpl(/*? if >=1.21.10 {*//*click, doubleClick*//*? } else {*/mouseX, mouseY, mouseButton/*? }*/);
		MinecraftClient.getInstance().setScreen(
				new ConfigObjectScreen(
						configObject.configs,
						GuiUtils.getCurrentScreen(),
						configObject.getButtonDisplayName(),
						configObject.distanceFromTops,
						configObject.distanceFromSides,
						configObject.width,
						configObject.height
				)
		);
		return true;
	}

	@Override
	public void updateDisplayString() {
		this.displayString = this.configObject.getButtonDisplayName();
		if (this.displayString.isEmpty()) {
			this.displayString = this.configObject.toString();
		}
	}
}
