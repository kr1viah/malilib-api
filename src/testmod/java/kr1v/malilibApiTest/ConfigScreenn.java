package kr1v.malilibApiTest;

import kr1v.malilibApi.screen.ConfigScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

public class ConfigScreenn extends ConfigScreen {
	public ConfigScreenn(String modId, String titleKey) {
		super(modId, titleKey);
	}

	public ConfigScreenn(String modId, String titleKey, Screen parent) {
		super(modId, titleKey, parent);
	}

	@Override
	public void initGui() {
		super.initGui();
		System.out.println("hii!");
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
		super.render(drawContext, mouseX, mouseY, partialTicks);
	}
}
