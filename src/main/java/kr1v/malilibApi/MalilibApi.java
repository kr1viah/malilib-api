package kr1v.malilibApi;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.registry.Registry;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.screen.ConfigScreen;
import kr1v.malilibApi.util.AnnotationUtils;
import kr1v.malilibApi.util.ConfigUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import org.reflections.Reflections;

import java.util.*;
import java.util.function.Supplier;

public class MalilibApi {
    private static final Map<String, ModInfo> modIdToModInfoMap = new HashMap<>();

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
        configHandler.onConfigsChanged();
    }

    public static void init() {
        Reflections reflections = new Reflections();
        Set<Class<?>> configClasses = reflections.getTypesAnnotatedWith(Config.class);
        for (Class<?> cfgClass : configClasses) {
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

    public static void openScreenFor(String modId) {
        ModInfo modInfo = modIdToModInfoMap.get(modId);
        GuiBase.openGui(new ConfigScreen(modInfo.getModId(), modInfo.getModName(), modInfo));
    }
}