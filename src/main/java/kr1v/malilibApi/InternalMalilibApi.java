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
		return getMod(modId).modInfo;
	}

	public static void openScreenFor(String modId, Screen parent) {
		GuiBase.openGui(getMod(modId).configScreenSupplier.get(parent));
	}

	public static void openScreenFor(String modId) {
		GuiBase.openGui(getMod(modId).configScreenSupplier.get());
	}

	public static ModRepresentation getMod(String modId) {
		return registeredMods.get(modId);
	}

	public static Collection<ModRepresentation> getModConfigs() {
		return registeredMods.values();
	}

	public static Map<Class<?>, List<IConfigBase>> cacheFor(String modId) {
		return getMod(modId).configs;
	}

	public static List<IConfigBase> configListFor(String modId, Class<?> configClass) {
		return getMod(modId).configs.get(configClass);
	}

	public static Set<Class<?>> classesFor(String modId) {
		return getMod(modId).configs.keySet();
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
		getMod(modId).tabs.add(new ModRepresentation.Tab(tab, options, isPopup, order));
	}

	public static void unregisterTab(String modId, String tabName) {
		getMod(modId).tabs.removeIf(tab -> tab.translationKey().equals(tabName));
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
		if (tab == null) tab = getTabsFor(modId).getFirst();
		return tab;
	}

	public static ModRepresentation.Tab getTabForTranslationKey(String modId, String translationKey) {
		for (ModRepresentation.Tab tab : getTabsFor(modId)) {
			if (tab.translationKey().equals(translationKey)) {
				return tab;
			}
		}
		return null;
	}

	public static int getScrollValueFor(String modId) {
		return getScrollValueFor(modId, getActiveTabFor(modId));
	}

	public static int getScrollValueFor(String modId, ModRepresentation.Tab tab) {
		return getMod(modId).tabToScrollValue.getInt(tab);
	}

	public static void setActiveTabFor(String modId, ModRepresentation.Tab tab) {
		getMod(modId).activeTab = tab;
	}

	public static void setScrollValueFor(String modId, int value) {
		setScrollValueFor(modId, getActiveTabFor(modId), value);
	}

	public static void setScrollValueFor(String modId, ModRepresentation.Tab tab, int value) {
		getMod(modId).tabToScrollValue.put(tab, value);
	}

	private static List<ModRepresentation.Tab> rawTabs(String modId) {
		return getMod(modId).tabs;
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