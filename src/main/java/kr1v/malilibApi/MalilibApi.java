package kr1v.malilibApi;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import kr1v.malilibApi.interfaces.IButtonBasedResettableWidgetSupplier;
import kr1v.malilibApi.interfaces.IConfigScreenSupplier;
import kr1v.malilibApi.interfaces.IWidgetResettableSupplier;
import kr1v.malilibApi.screen.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;

import java.util.List;

@SuppressWarnings("unused")
public class MalilibApi {
	/// Use if you want a different internal ID than mod name.
	public static void registerMod(String modId, String modName) {
		registerMod(modId, modName, new ConfigHandler(modId), new InputHandler(modId));
	}

	/// Use if you want a custom ConfigHandler/InputHandler
	public static void registerMod(String modId, String modName, ConfigHandler configHandler, InputHandler inputHandler) {
		InternalMalilibApi.registerMod(modId, modName, configHandler, inputHandler, new IConfigScreenSupplier() {
			@Override
			public ConfigScreen get() {
				return new ConfigScreen(modId, modName);
			}

			@Override
			public ConfigScreen get(Screen parent) {
				return new ConfigScreen(modId, modName, parent);
			}
		});
	}

	/// Use if you want a custom config screen
	public static void registerMod(String modId, String modName, IConfigScreenSupplier configScreenSupplier) {
		InternalMalilibApi.registerMod(modId, modName, new ConfigHandler(modId), new InputHandler(modId), configScreenSupplier);
	}

	/// Use if you want a custom config screen and a custom ConfigHandler/InputHandler
	public static void registerMod(String modId, String modName, ConfigHandler configHandler, InputHandler inputHandler, IConfigScreenSupplier configScreenSupplier) {
		InternalMalilibApi.registerMod(modId, modName, configHandler, inputHandler, configScreenSupplier);
	}

	public static void openScreenFor(String modId) {
		InternalMalilibApi.openScreenFor(modId);
	}

	public static void openScreenFor(String modId, Screen parent) {
		InternalMalilibApi.openScreenFor(modId, parent);
	}

	public static void registerTab(String modId, String tab, List<IConfigBase> options) {
		registerTab(modId, tab, options, 1000);
	}

	public static void registerTab(String modId, String tab, List<IConfigBase> options, int order) {
		InternalMalilibApi.registerTab(modId, tab, options, false, order);
	}

	public static void unregisterTab(String modId, String tabName) {
		InternalMalilibApi.unregisterTab(modId, tabName);
	}

	public static <T extends IConfigBase & IConfigResettable> void registerWidgetBasedConfigType(Class<?> configClass, IWidgetResettableSupplier<T> widgetSupplier) {
		InternalMalilibApi.registerWidgetBasedConfigType(configClass, widgetSupplier);
	}

	public static <T extends IConfigBase & IConfigResettable> void registerButtonBasedConfigType(Class<T> configClass, IButtonBasedResettableWidgetSupplier<T> buttonSupplier) {
		InternalMalilibApi.registerButtonBasedConfigType(configClass, buttonSupplier);
	}
}