package kr1v.malilibApiTest;

import kr1v.malilibApi.MalilibApi;
import kr1v.malilibApi.interfaces.IConfigScreenSupplier;
import kr1v.malilibApi.screen.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.Screen;

public class Init implements ClientModInitializer {
	public static final String MOD_ID = "test-mod";
	public static final String MOD_NAME = "Test mod";

	@Override
	public void onInitializeClient() {
		MalilibApi.registerMod(MOD_ID, MOD_NAME, new IConfigScreenSupplier() {
			@Override
			public ConfigScreen get() {
				return new ConfigScreenn(MOD_ID, MOD_NAME);
			}

			@Override
			public ConfigScreen get(Screen parent) {
				return new ConfigScreenn(MOD_ID, MOD_NAME, parent);
			}
		});
	}
}
