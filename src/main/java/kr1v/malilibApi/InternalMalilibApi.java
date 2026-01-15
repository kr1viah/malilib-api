package kr1v.malilibApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.registry.Registry;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.processor.ConfigProcessor;
import kr1v.malilibApi.interfaces.IConfigScreenSupplier;
import kr1v.malilibApi.screen.ConfigScreen;
import kr1v.malilibApi.util.AnnotationUtils;
import kr1v.malilibApi.util.ConfigUtils;
import net.minecraft.client.gui.screen.Screen;
import org.apache.commons.lang3.NotImplementedException;

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
	private static final Map<String, ModRepresentation> registeredMods = new HashMap<>();
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

			Registry.CONFIG_SCREEN.registerConfigScreenFactory(modInfo);

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
						classToRepresentation.put(Class.forName(clazz), map.get(clazz));
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
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

	public static ModInfo modInfoFor(String modId) {
		return registeredMods.get(modId).modInfo;
	}

	public static void openScreenFor(String modId, Screen parent) {
		ModRepresentation modRepresentation = registeredMods.get(modId);
		GuiBase.openGui(modRepresentation.configScreenSupplier.get(parent));
	}

	public static void openScreenFor(String modId) {
		ModRepresentation modRepresentation = registeredMods.get(modId);
		GuiBase.openGui(modRepresentation.configScreenSupplier.get());
	}

	public static ModRepresentation getModConfig(String modId) {
		return registeredMods.get(modId);
	}

	public static Collection<ModRepresentation> getModConfigs() {
		return registeredMods.values();
	}

	public static Map<Class<?>, List<IConfigBase>> cacheFor(String modId) {
		return registeredMods.get(modId).configs;
	}

	public static List<IConfigBase> configListFor(String modId, Class<?> configClass) {
		return registeredMods.get(modId).configs.get(configClass);
	}

	public static Set<Class<?>> classesFor(String modId) {
		return registeredMods.get(modId).configs.keySet();
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

	public static void registerTab(String modId, String tab, List<IConfigBase> options, boolean isPopup, int order) {
		getModConfig(modId).tabs.add(new ModRepresentation.Tab(tab, options, isPopup, order));
	}

	public static void unregisterTab(String modId, String tabName) {
		getModConfig(modId).tabs.removeIf(tab -> tab.translationKey().equals(tabName));
	}

	public static List<ModRepresentation.Tab> tabsFor(String modId) {
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
		throw new NotImplementedException("TODO");
	}

	public static int getScrollValueFor(String modId) {
		return getScrollValueFor(modId, getActiveTabFor(modId));
	}

	public static int getScrollValueFor(String modId, ModRepresentation.Tab tab) {
		throw new NotImplementedException("TODO");
	}

	public static void setScrollValueFor(String modId, int value) {
		setScrollValueFor(modId, getActiveTabFor(modId), value);
	}

	public static void setActiveTabFor(String modId, ModRepresentation.Tab tab) {
		throw new NotImplementedException("TODO");
	}

	public static void setScrollValueFor(String modId, ModRepresentation.Tab tab, int value) {
		throw new NotImplementedException("TODO");
	}

	private static List<ModRepresentation.Tab> rawTabs(String modId) {
		return getModConfig(modId).tabs;
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