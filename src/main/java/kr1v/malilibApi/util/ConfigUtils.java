package kr1v.malilibApi.util;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.util.GuiUtils;
import kr1v.malilibApi.MalilibApi;
import kr1v.malilibApi.annotation.Extras;
import kr1v.malilibApi.annotation.Label;
import kr1v.malilibApi.annotation.Marker;
import kr1v.malilibApi.annotation.PopupConfig;
import kr1v.malilibApi.annotation.processor.ConfigProcessor;
import kr1v.malilibApi.config.ConfigButton;
import kr1v.malilibApi.config.ConfigLabel;
import kr1v.malilibApi.screen.ConfigPopupScreen;
import net.minecraft.client.MinecraftClient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ConfigUtils {
    private ConfigUtils() {}

    public static List<IConfigBase> generateOptions(Class<?> clazz, String modId) {
        List<IConfigBase> list = new ArrayList<>();

        List<ConfigProcessor.Element> elements = ClassUtils.getDeclaredElements(clazz);

        try {
            for (ConfigProcessor.Element element : elements) {
                handleAnnotations(element, list, clazz, modId);
                if (element.field != null) {
                    Field f = element.field;
                    if (IConfigBase.class.isAssignableFrom(f.getType())) {
                        f.setAccessible(true);
                        IConfigBase value = (IConfigBase) f.get(null);
                        if (value != null) {
                            list.add(value);
                        }
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private static void handleAnnotations(ConfigProcessor.Element element, List<IConfigBase> list, Class<?> declaringClass, String modId) throws InvocationTargetException, IllegalAccessException {
        for (Annotation annotation : element.annotations) {
            switch (annotation) {
                case PopupConfig popupConfig -> {
                    assert element.aClass != null;
                    Class<?> klass = element.aClass;
                    String name = popupConfig.name();
                    if (name.isEmpty()) {
                        name = klass.getSimpleName();
                    }
                    String buttonName = popupConfig.buttonName();
                    if (buttonName.isEmpty()) {
                        buttonName = "Edit " + name;
                    }

                    ConfigButton<Class<?>> configButton = new ConfigButton<>(name, buttonName, () ->
                            MinecraftClient.getInstance().setScreen(
                                    new ConfigPopupScreen(klass, GuiUtils.getCurrentScreen(), modId)
                            ),
                            klass);

                    boolean prev = MalilibApi.getDefaultEnabled();
                    MalilibApi.setDefaultEnabled(klass.getAnnotation(PopupConfig.class).defaultEnabled());
                    MalilibApi.cacheFor(modId).put(klass, generateOptions(klass, modId));
                    MalilibApi.setDefaultEnabled(prev);
                    MalilibApi.registerTab(modId, AnnotationUtils.nameForConfig(klass), list, true);
                    list.add(configButton);
                }
                case Label label ->
                        list.add(new ConfigLabel(label.value()));
                case Extras extras -> {
                    if (extras.runAt().isEmpty()) {
                        element.method.setAccessible(true);
                        element.method.invoke(null, list);
                    }
                }
                case Marker marker -> {
                    if (marker.value().isEmpty()) {
                        continue;
                    }
                    for (Method m : declaringClass.getDeclaredMethods()) {
                        if (m.isAnnotationPresent(Extras.class)) {
                            Extras extras = m.getAnnotation(Extras.class);
                            if (marker.value().equals(extras.runAt())) {
                                m.setAccessible(true);
                                m.invoke(null, list);
                            }
                        }
                    }
                }
                default -> {}
            }
        }
    }
}
