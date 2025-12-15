package kr1v.malilibApi.util;

import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.PopupConfig;

import java.util.*;

public final class AnnotationUtils {
    private AnnotationUtils() {}

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
}