package kr1v.malilibApi.util;

import fi.dy.masa.malilib.config.IConfigBase;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.PopupConfig;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class AnnotationUtils {
    private AnnotationUtils() {}

    public static Map<Class<?>, List<IConfigBase>> CACHE;
    private static final Map<String, Map<Class<?>, List<IConfigBase>>> MOD_CACHE = new HashMap<>();

    @NotNull
    public static Map<Class<?>, List<IConfigBase>> cacheFor(String modId) {
        MOD_CACHE.computeIfAbsent(modId, k -> new TreeMap<>(Comparator.comparing((Class<?> x) -> AnnotationUtils.nameForConfig(x) + x.getName())));
        return MOD_CACHE.get(modId);
    }

    private static boolean defaultEnabled = true;

    public static List<IConfigBase> configsFor(Class<?> configClass) {
        return CACHE.get(configClass);
    }

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