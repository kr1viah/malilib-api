package kr1v.malilibApiTest;

import kr1v.malilibApi.screen.ConfigScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

import static kr1v.malilibApiTest.Init.LOGGER;

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

	private static int renderCount = 0;

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
		long start = System.nanoTime();
		super.render(drawContext, mouseX, mouseY, partialTicks);
		long dt = System.nanoTime() - start;

		if (renderCount++ % 10 == 0) {
			LOGGER.info("Render time: {}ms", dt / 1_000_000);
		}
	}
}
