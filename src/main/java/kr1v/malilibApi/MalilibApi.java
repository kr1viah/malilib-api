package kr1v.malilibApi;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.registry.Registry;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.screen.ConfigScreen;
import kr1v.malilibApi.util.AnnotationUtils;
import kr1v.malilibApi.util.ClassUtils;
import kr1v.malilibApi.util.ConfigUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

public class MalilibApi implements ClientModInitializer {
    private static final List<String> registeredods = new ArrayList<>();

    @Override
    public void onInitializeClient() {}

    public static void registerMod(String modName) {
        registerMod(modName, modName, "0.0.0", new ConfigHandler(modName), new InputHandler(modName), ClassUtils.getCaller());
    }

    public static void registerMod(String modName, String version) {
        registerMod(modName, modName, version, new ConfigHandler(modName), new InputHandler(modName), ClassUtils.getCaller());
    }

    public static void registerMod(String modName, String version, ConfigHandler configHandler) {
        registerMod(modName, modName, version, configHandler, new InputHandler(modName), ClassUtils.getCaller());
    }

    public static void registerMod(String modName, String version, InputHandler inputHandler) {
        registerMod(modName, modName, version, new ConfigHandler(modName), inputHandler, ClassUtils.getCaller());
    }

    public static void registerMod(String modName, String version, ConfigHandler configHandler, InputHandler inputHandler) {
        registerMod(modName, modName, version, configHandler, inputHandler, ClassUtils.getCaller());
    }

    public static void registerMod(String modId, String modName, String version, ConfigHandler configHandler, InputHandler inputHandler, Class<?> mainClass) {
        if (registeredods.contains(modId)) throw new IllegalStateException("Mod id is already registered!");
        registeredods.add(modId);

        InitializationHandler.getInstance().registerInitializationHandler(() -> {
            ConfigManager.getInstance().registerConfigHandler(modId, configHandler);

            Registry.CONFIG_SCREEN.registerConfigScreenFactory(
                    new ModInfo(modId, modName, () -> new ConfigScreen(modId, modName, version))
            );
            InputEventHandler.getKeybindManager().registerKeybindProvider(inputHandler);
            InputEventHandler.getInputManager().registerKeyboardInputHandler(inputHandler);
            InputEventHandler.getInputManager().registerMouseInputHandler(inputHandler);
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> configHandler.save());

        Set<String> configTypeClasses;

        String path = "META-INF/kr1v/";
        ClassLoader cl = mainClass.getClassLoader();

        URL url = cl.getResource(path);

        assert url != null;
        String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
        try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
            configTypeClasses = jar.stream()
                    .map(ZipEntry::getName)
                    .filter(name -> name.startsWith(path) && name.endsWith(".json"))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Set<Class<?>> configTypes = new HashSet<>();
        try {
            for (String s : configTypeClasses) {
                configTypes.add(Class.forName(s.substring(s.lastIndexOf('/') + 1, s.length() - 5)));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Class<?> cfgClass : configTypes) {
            AnnotationUtils.setDefaultEnabled(cfgClass.getAnnotation(Config.class).defaultEnabled());
            List<IConfigBase> list = ConfigUtils.generateOptions(cfgClass);
            AnnotationUtils.setDefaultEnabled(true);
            AnnotationUtils.cacheFor(modId).put(cfgClass, list);
        }
    }
}
