package kr1v.malilibApi.util;

import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.annotation.processor.ConfigProcessor;
import net.fabricmc.loader.api.FabricLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ClassUtils {
    private ClassUtils() {}

    public static List<ConfigProcessor.Element> getDeclaredElements(Class<?> clazz) {
        List<ConfigProcessor.Element> elementsOfClass = new ArrayList<>();
        List<ConfigProcessor.ElementRepresentation> elementRepresentations = InternalMalilibApi.classToRepresentation.get(clazz);

        try {
            assert elementRepresentations != null;
            for (ConfigProcessor.ElementRepresentation el : elementRepresentations) {
                Field f = null;
                Method m = null;
                Class<?> klass = null;
                switch (el.type) {
                    case "field" -> f = clazz.getField(el.name);
                    case "innerClass" -> klass = Class.forName(el.name);
                    case "method" -> {
                        if (el.name.contains("<")) continue;
                        List<Class<?>> typeList = new ArrayList<>();
                        for (String type : el.types) {
                            if (!FabricLoader.getInstance().isDevelopmentEnvironment())
                                type = MappingUtils.yarnToIntermediary(type);
                            typeList.add(Class.forName(type));
                        }
                        Class<?>[] types = typeList.toArray(new Class[]{});

                        m = clazz.getDeclaredMethod(el.name, types);
                    }
                    default -> {}
                }

                ConfigProcessor.Element element = new ConfigProcessor.Element(f, m, klass);
                for (ConfigProcessor.AnnotationDTO ann : el.annotations) {
                    element.annotations.add(ConfigProcessor.toAnnotation(ann, Class.forName(ann.annotationType)));
                }
                elementsOfClass.add(element);
            }
        } catch (NoSuchFieldException | ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return elementsOfClass;
    }
}
