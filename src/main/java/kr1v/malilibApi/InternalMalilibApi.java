package kr1v.malilibApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import kr1v.malilibApi.screen.ConfigScreen;
import kr1v.malilibApi.util.AnnotationUtils;
import kr1v.malilibApi.util.ConfigUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.gui.screen.Screen;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;

/// this exists in order to not bloat MalilibApi with methods that need to be public but aren't supposed to be used by others
public class InternalMalilibApi {
    private static final Map<String, ModConfig> modIdToModConfig = new HashMap<>();

    public static final Map<Class<?>, List<ConfigProcessor.ElementRepresentation>> classToRepresentation = new HashMap<>();

    /// this gets initialised in a mixin config plugin off thread
    public static Reflections reflections;
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(ConfigProcessor.ValueDTO.class, new ConfigProcessor.ValueDTODeserializer()).setPrettyPrinting().create();

    public static void registerMod(String modId, String modName, ConfigHandler configHandler, InputHandler inputHandler) {
        if (isModRegistered(modId)) throw new IllegalStateException("Mod id is already registered!");

        Supplier<GuiBase> configScreenSupplier = () -> new ConfigScreen(modId, modName);
        ModInfo modInfo = new ModInfo(modId, modName, configScreenSupplier);

        registerMod(modId, modInfo);

        InitializationHandler.getInstance().registerInitializationHandler(() -> {
            ConfigManager.getInstance().registerConfigHandler(modId, configHandler);

            Registry.CONFIG_SCREEN.registerConfigScreenFactory(modInfo);

            InputEventHandler.getKeybindManager().registerKeybindProvider(inputHandler);
            InputEventHandler.getInputManager().registerKeyboardInputHandler(inputHandler);
            InputEventHandler.getInputManager().registerMouseInputHandler(inputHandler);
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> configHandler.save());
    }

    public static void init() {
        Type type = new TypeToken<Map<String, List<ConfigProcessor.ElementRepresentation>>>() {}.getType();
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

        if (!isModRegistered(modId)) {
            registerMod(modId, modId, new ConfigHandler(modId), new InputHandler(modId));
        }

        setDefaultEnabled(defaultEnabled);
        List<IConfigBase> list = ConfigUtils.generateOptions(cfgClass, modId);
        setDefaultEnabled(true);
        registerTab(modId, AnnotationUtils.nameForConfig(cfgClass), list, false);
        cacheFor(modId).put(cfgClass, list);
    }

    public static ModInfo modInfoFor(String modId) {
        return modIdToModConfig.get(modId).modInfo;
    }

    public static void openScreenFor(String modId, Screen parent) {
        ModInfo modInfo = modIdToModConfig.get(modId).modInfo;
        GuiBase.openGui(new ConfigScreen(modInfo.getModId(), modInfo.getModName(), parent));
    }

    public static ModConfig getModConfig(String modId) {
        return modIdToModConfig.get(modId);
    }

    public static Map<Class<?>, List<IConfigBase>> cacheFor(String modId) {
        return modIdToModConfig.get(modId).configs;
    }

    public static List<IConfigBase> configListFor(String modId, Class<?> configClass) {
        return modIdToModConfig.get(modId).configs.get(configClass);
    }

    public static Set<Class<?>> classesFor(String modId) {
        return modIdToModConfig.get(modId).configs.keySet();
    }

    public static void registerMod(String modId, ModInfo modInfo) {
        modIdToModConfig.put(modId, new ModConfig(modInfo));
    }

    public static boolean isModRegistered(String modId) {
        return modIdToModConfig.containsKey(modId);
    }

    private static boolean defaultEnabled = true;

    public static boolean getDefaultEnabled() {
        return defaultEnabled;
    }

    public static void setDefaultEnabled(boolean defaultEnabled1) {
        defaultEnabled = defaultEnabled1;
    }

    public static void registerTab(String modId, String tab, List<IConfigBase> options, boolean isPopup) {
        getModConfig(modId).tabs.add(new ModConfig.Tab(tab, options, isPopup));
    }

    public static void unregisterTab(String modId, String tabName) {
        getModConfig(modId).tabs.removeIf(tab -> tab.translationKey().equals(tabName));
    }

    public static List<ModConfig.Tab> tabsFor(String modId) {
        return getModConfig(modId).tabs;
    }
}