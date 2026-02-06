package kr1v.malilibApiTest;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import kr1v.malilibApi.ConfigHandler;
import kr1v.malilibApi.InputHandler;
import kr1v.malilibApi.MalilibApi;
import kr1v.malilibApi.interfaces.IConfigScreenSupplier;
import kr1v.malilibApi.screen.ConfigScreen;
import kr1v.malilibApiTest.custo.ConfigClass;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.Screen;
import org.slf4j.Logger;

public class Init implements ClientModInitializer {
	public static final String MOD_ID = "malilib-api-test";
	public static final String MOD_NAME = "Test mod";
	public static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void onInitializeClient() {
		MalilibApi.registerButtonBasedConfigType(ConfigClass.class, (widgetConfigOption, config, x, y, configWidth, configHeight) -> new ButtonGeneric(x, y, configWidth, configHeight, config.getName()) {
			//? if <=1.21.8 {
            @Override
            protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
                System.out.println("Custom button!!");
                return super.onMouseClickedImpl(mouseX, mouseY, mouseButton);
            }
			//? }
        });

		MalilibApi.registerMod(
				MOD_ID,
				MOD_NAME,
				new ConfigHandler(MOD_ID) {
					int timesSaved = 0;

					@Override
					public void loadAdditionalData(JsonObject root) {
						if (root.has("timesSaved")) {
							timesSaved = root.get("timesSaved").getAsInt();
						}
					}

					@Override
					public void saveAdditionalData(JsonObject root) {
						root.addProperty("timesSaved", ++timesSaved);
						LOGGER.info("Times saved: {}", timesSaved);
					}
				},
				new InputHandler(MOD_ID),
				new IConfigScreenSupplier() {
					@Override
					public ConfigScreen get() {
						return new ConfigScreenn(MOD_ID, MOD_NAME);
					}

					@Override
					public ConfigScreen get(Screen parent) {
						return new ConfigScreenn(MOD_ID, MOD_NAME, parent);
					}
				}
		);
	}
}
