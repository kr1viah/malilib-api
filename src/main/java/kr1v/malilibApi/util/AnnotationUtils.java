package kr1v.malilibApi.util;

import fi.dy.masa.malilib.config.IConfigBase;
import kr1v.malilibApi.ModConfig;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.PopupConfig;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class AnnotationUtils {
    private AnnotationUtils() {}

    private static final Map<String, ModConfig> MODS = new HashMap<>();

    public static ModConfig getModConfig(String modId) {
        return MODS.get(modId);
    }

    @NotNull
    public static Map<Class<?>, List<IConfigBase>> cacheFor(String modId) {
        return MODS.get(modId).configs;
    }

    public static List<IConfigBase> configListFor(String modId, Class<?> configClass) {
        return MODS.get(modId).configs.get(configClass);
    }

    public static Set<Class<?>> classesFor(String modId) {
        return MODS.get(modId).configs.keySet();
    }

    public static void registerMod(String modId) {
        MODS.put(modId, new ModConfig());
    }

    public static boolean isModRegistered(String modId) {
        return MODS.containsKey(modId);
    }

    private static boolean defaultEnabled = true;

    public static String nameForConfig(Class<?> configClass) {
        String name;
        if (configClass.isAnnotationPresent(PopupConfig.class)) {
            name = configClass.getAnnotation(PopupConfig.class).name();
        } else {
            name = configClass.getAnnotation(Config.class).name();
        }
        if (name.isEmpty()) name = configClass.getSimpleName();
        return name;
    }

    public static boolean getDefaultEnabled() {
        return defaultEnabled;
    }

    public static void setDefaultEnabled(boolean defaultEnabled1) {
        defaultEnabled = defaultEnabled1;
    }
}