package kr1v.malilibApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.processor.ConfigProcessor;
import kr1v.malilibApi.interfaces.IButtonBasedResettableWidgetSupplier;
import kr1v.malilibApi.interfaces.IConfigScreenSupplier;
import kr1v.malilibApi.interfaces.IWidgetResettableSupplier;
import kr1v.malilibApi.interfaces.IWidgetSupplier;
import kr1v.malilibApi.screen.ConfigScreen;
import kr1v.malilibApi.util.AnnotationUtils;
import kr1v.malilibApi.util.ConfigUtils;
import kr1v.malilibApi.util.Util;
import net.minecraft.client.gui.screen.Screen;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;

/// this exists in order to not bloat MalilibApi with methods that need to be public but aren't ""supposed"" to be used by others
public class InternalMalilibApi {
	static final Map<String, ModRepresentation> registeredMods = new HashMap<>();
	public static final Map<Class<?>, List<ConfigProcessor.ElementRepresentation>> classToRepresentation = new HashMap<>();
	public static final Gson GSON = ConfigProcessor.GSON;

	public static void registerMod(String modId, String modName, ConfigHandler configHandler, InputHandler inputHandler, IConfigScreenSupplier configScreenSupplier) {
		if (isModRegistered(modId)) throw new IllegalStateException("Mod id is already registered!");

		Supplier<GuiBase> guiBaseSupplier = configScreenSupplier::get;

		ModInfo modInfo = new ModInfo(modId, modName, guiBaseSupplier);
		ModRepresentation modRepresentation = new ModRepresentation(modId, modInfo, configHandler, inputHandler, configScreenSupplier);

		registerMod(modId, modRepresentation);

		InitializationHandler.getInstance().registerInitializationHandler(() -> {
			ConfigManager.getInstance().registerConfigHandler(modId, configHandler);

			//? if >=1.21
			fi.dy.masa.malilib.registry.Registry.CONFIG_SCREEN.registerConfigScreenFactory(modInfo);

			InputEventHandler.getKeybindManager().registerKeybindProvider(inputHandler);
			InputEventHandler.getInputManager().registerKeyboardInputHandler(inputHandler);
			InputEventHandler.getInputManager().registerMouseInputHandler(inputHandler);
		});
	}

	public static void init() {
		Type type = new TypeToken<Map<String, List<ConfigProcessor.ElementRepresentation>>>() {
		}.getType();
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			Enumeration<URL> mods = cl.getResources("META-INF/kr1v/index.json");
			while (mods.hasMoreElements()) {
				URL mod = mods.nextElement();
				try (Reader reader = new InputStreamReader(mod.openStream(), StandardCharsets.UTF_8)) {
					Map<String, List<ConfigProcessor.ElementRepresentation>> map = GSON.fromJson(reader, type);

					for (String clazz : map.keySet()) {
						classToRepresentation.put(Class.forName(clazz, false, Thread.currentThread().getContextClassLoader()), map.get(clazz));
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			Util.rethrow(e);
		}

		for (Class<?> cfgClass : classToRepresentation.keySet()) {
			handleClass(cfgClass);
		}
	}

	private static void handleClass(Class<?> cfgClass) {
		if (!cfgClass.isAnnotationPresent(Config.class)) return;
		Config annotation = cfgClass.getAnnotation(Config.class);
		String modId = annotation.value();
		boolean defaultEnabled = annotation.defaultEnabled();
		int order = annotation.order();

		if (!isModRegistered(modId)) {
			registerMod(modId, modId, new ConfigHandler(modId), new InputHandler(modId), new IConfigScreenSupplier() {
				@Override
				public ConfigScreen get() {
					return new ConfigScreen(modId, modId);
				}

				@Override
				public ConfigScreen get(Screen parent) {
					return new ConfigScreen(modId, modId, parent);
				}
			});
		}

		setDefaultEnabled(defaultEnabled);
		List<IConfigBase> list = ConfigUtils.generateOptions(cfgClass, modId);
		setDefaultEnabled(true);
		registerTab(modId, AnnotationUtils.nameForConfig(cfgClass), list, false, order);
		cacheFor(modId).put(cfgClass, list);
	}

	public static ModRepresentation getMod(String modId) {
		return registeredMods.get(modId);
	}

	public static void openScreenFor(String modId, Screen parent) {
		getMod(modId).openScreen(parent);
	}

	public static void openScreenFor(String modId) {
		getMod(modId).openScreen();
	}

	public static Collection<ModRepresentation> getModConfigs() {
		return registeredMods.values();
	}

	public static Map<Class<?>, List<IConfigBase>> cacheFor(String modId) {
		return getMod(modId).configs;
	}

	public static List<IConfigBase> configListFor(String modId, Class<?> configClass) {
		return cacheFor(modId).get(configClass);
	}

	public static Set<Class<?>> classesFor(String modId) {
		return cacheFor(modId).keySet();
	}

	public static void registerMod(String modId, ModRepresentation modRepresentation) {
		registeredMods.put(modId, modRepresentation);
	}

	public static boolean isModRegistered(String modId) {
		return registeredMods.containsKey(modId);
	}

	private static boolean defaultEnabled = true;

	public static boolean getDefaultEnabled() {
		return defaultEnabled;
	}

	public static void setDefaultEnabled(boolean defaultEnabled1) {
		defaultEnabled = defaultEnabled1;
	}

	public static void registerTab(String modId, String tabName, List<IConfigBase> options, boolean isPopup, int order) {
		getMod(modId).registerTab(tabName, options, isPopup, order);
	}

	public static void unregisterTab(String modId, String tabName) {
		getMod(modId).unregisterTab(tabName);
	}

	public static List<ModRepresentation.Tab> getTabsFor(String modId) {
		return rawTabs(modId)
				.stream()
				.sorted(Comparator
						.comparing(ModRepresentation.Tab::isPopup)
						.thenComparingInt(ModRepresentation.Tab::order)
						.thenComparing(ModRepresentation.Tab::translationKey)
				)
				.toList();
	}

	public static ModRepresentation.Tab getActiveTabFor(String modId) {
		ModRepresentation.Tab tab = getMod(modId).activeTab;
		if (tab == null) tab = getTabsFor(modId).get(0);
		return tab;
	}

	public static ModRepresentation.Tab getTabForTranslationKey(String modId, String translationKey) {
		return getMod(modId).tabByTranslationKey(translationKey);
	}

	public static int getScrollValueFor(String modId) {
		return getMod(modId).scrollValue();
	}

	public static int getScrollValueFor(String modId, ModRepresentation.Tab tab) {
		return getMod(modId).scrollValue(tab);
	}

	public static void setActiveTabFor(String modId, ModRepresentation.Tab tab) {
		getMod(modId).setActiveTab(tab);
	}

	public static void setScrollValueFor(String modId, int value) {
		getMod(modId).setScrollValue(value);
	}

	public static void setScrollValueFor(String modId, ModRepresentation.Tab tab, int value) {
		getMod(modId).setScrollValue(tab, value);
	}

	private static List<ModRepresentation.Tab> rawTabs(String modId) {
		return getMod(modId).tabs;
	}

	public static Map<Class<?>, IWidgetSupplier<?>> customConfigMap = new HashMap<>();

	public static <T extends IConfigBase & IConfigResettable> void registerWidgetBasedConfigType(Class<?> configClass, IWidgetResettableSupplier<T> widgetSupplier) {
		if (customConfigMap.containsKey(configClass)) throw new IllegalStateException("Config class already registered! " + configClass);
		customConfigMap.put(configClass, widgetSupplier);
	}

	public static <T extends IConfigBase & IConfigResettable> void registerButtonBasedConfigType(Class<T> configClass, IButtonBasedResettableWidgetSupplier<T> buttonSupplier) {
		if (customConfigMap.containsKey(configClass)) throw new IllegalStateException("Config class already registered! " + configClass);
		customConfigMap.put(configClass, buttonSupplier);
	}

	public static <T extends IConfigBase & IConfigResettable> IWidgetSupplier<?> unregisterCustomWidget(Class<T> configClass) {
		if (!customConfigMap.containsKey(configClass)) throw new IllegalStateException("Config class is not registered! " + configClass);
		return customConfigMap.remove(configClass);
	}

	private static final Set<Object> toHide = new HashSet<>();

	public static void removeHide(Object o) {
		toHide.remove(o);
	}

	public static void addHide(Object o) {
		toHide.add(o);
	}

	public static boolean shouldHide(Object o) {
		return toHide.contains(o);
	}
}