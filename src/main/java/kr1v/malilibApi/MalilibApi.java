package kr1v.malilibApi;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.registry.Registry;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.PopupConfig;
import kr1v.malilibApi.annotation.processor.ConfigProcessor;
import kr1v.malilibApi.screen.ConfigScreen;
import kr1v.malilibApi.util.AnnotationUtils;
import kr1v.malilibApi.util.ClassUtils;
import kr1v.malilibApi.util.ConfigUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

import java.util.*;

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
        AnnotationUtils.registerMod(modId);

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

        try {
            for (String s : ConfigProcessor.getElementsForMod(mainClass).keySet()) {
                Class<?> cfgClass = Class.forName(s);
                if (cfgClass.isAnnotationPresent(PopupConfig.class)) continue;
                Config ann = cfgClass.getDeclaredAnnotation(Config.class);

                AnnotationUtils.setDefaultEnabled(ann.defaultEnabled());
                List<IConfigBase> list = ConfigUtils.generateOptions(cfgClass, modId);
                AnnotationUtils.setDefaultEnabled(true);

                AnnotationUtils.cacheFor(modId).put(cfgClass, list);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}