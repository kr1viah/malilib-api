package kr1v.malilibApi;

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
import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;

import static kr1v.malilibApi.annotation.processor.ConfigProcessor.GSON;

public class MalilibApi {
    private static final Map<String, ModInfo> modIdToModInfoMap = new HashMap<>();
    public static final Map<Class<?>, List<ConfigProcessor.ElementRepresentation>> classToRepresentation = new HashMap<>();

    public static Reflections reflections;

    public static void registerMod(String modId, String modName, ConfigHandler configHandler, InputHandler inputHandler) {
        if (AnnotationUtils.isModRegistered(modId)) throw new IllegalStateException("Mod id is already registered!");
        AnnotationUtils.registerMod(modId);

        InitializationHandler.getInstance().registerInitializationHandler(() -> {
            ConfigManager.getInstance().registerConfigHandler(modId, configHandler);

            var ref = new Object() {ModInfo modInfo = null;};
            Supplier<GuiBase> configScreenSupplier = () -> new ConfigScreen(modId, modName, ref.modInfo);
            ref.modInfo = new ModInfo(modId, modName, configScreenSupplier);

            Registry.CONFIG_SCREEN.registerConfigScreenFactory(ref.modInfo);
            modIdToModInfoMap.put(modId, ref.modInfo);

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
            if (!cfgClass.isAnnotationPresent(Config.class)) continue;
            Config annotation = cfgClass.getAnnotation(Config.class);
            String modId = annotation.value();
            boolean defaultEnabled = annotation.defaultEnabled();

            if (!AnnotationUtils.isModRegistered(modId)) {
                registerMod(modId, modId, new ConfigHandler(modId), new InputHandler(modId));
            }

            AnnotationUtils.setDefaultEnabled(defaultEnabled);
            List<IConfigBase> list = ConfigUtils.generateOptions(cfgClass, modId);
            AnnotationUtils.setDefaultEnabled(true);

            AnnotationUtils.cacheFor(modId).put(cfgClass, list);
        }
    }

    @SuppressWarnings("unused")
    public static void openScreenFor(String modId) {
        ModInfo modInfo = modIdToModInfoMap.get(modId);
        GuiBase.openGui(new ConfigScreen(modInfo.getModId(), modInfo.getModName(), modInfo));
    }
}