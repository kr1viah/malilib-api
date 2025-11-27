package kr1v.malilibApi.util;

import fi.dy.masa.malilib.config.IConfigBase;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.PopupConfig;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class AnnotationUtils {
    private AnnotationUtils() {}

    private static final Map<String, Map<Class<?>, List<IConfigBase>>> MOD_CACHE = new HashMap<>();

    @NotNull
    public static Map<Class<?>, List<IConfigBase>> cacheFor(String modId) {
        return MOD_CACHE.get(modId);
    }

    public static void registerMod(String modId) {
        MOD_CACHE.put(modId, new TreeMap<>(Comparator.comparing((Class<?> x) -> AnnotationUtils.nameForConfig(x) + x.getName())));
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