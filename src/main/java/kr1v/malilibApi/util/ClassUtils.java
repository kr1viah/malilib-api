package kr1v.malilibApi.util;

import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.annotation.processor.ConfigProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public final class ClassUtils {
	private ClassUtils() {
	}

	public static List<ConfigProcessor.Element> getDeclaredElements(Class<?> clazz) {
		List<ConfigProcessor.Element> elementsOfClass = new ArrayList<>();
		List<ConfigProcessor.ElementRepresentation> elementRepresentations = InternalMalilibApi.classToRepresentation.get(clazz);

		try {
			assert elementRepresentations != null;
			for (ConfigProcessor.ElementRepresentation el : elementRepresentations) {
				Field f = null;
				Method m = null;
				Class<?> klass = null;
				if (el.type.equals("field")) {
					f = clazz.getDeclaredField(el.name);
				} else if (el.type.equals("innerClass")) {
					klass = Class.forName(el.name);
				} else if (el.type.equals("method")) {
					if (el.name.contains("<")) continue;
					try {
						List<Class<?>> typeList = new ArrayList<>();
						for (String type : el.types) {
							typeList.add(Class.forName(type));
						}
						Class<?>[] types = typeList.toArray(new Class[]{});
						m = clazz.getDeclaredMethod(el.name, types);
					} catch (ClassNotFoundException ignored) {
						// cant really do anything.... mapping issues, compare and method name and parameter names/length
						outer:
						for (Method method : clazz.getDeclaredMethods()) {
							if (method.getParameterCount() != el.types.size()) continue;
							if (!method.getName().equals(el.name)) continue;

							Parameter[] params = method.getParameters();
							for (int i = 0; i < params.length; i++) {
								if (!params[i].getName().equals(el.parameterNames.get(i))) {
									continue outer;
								}
							}

							m = method;
							break;
						}
					}

					ConfigProcessor.Element element = new ConfigProcessor.Element(f, m, klass);
					for (ConfigProcessor.AnnotationDTO ann : el.annotations) {
						element.annotations.add(ConfigProcessor.toAnnotation(ann, Class.forName(ann.annotationType)));
					}
					elementsOfClass.add(element);
				}
			}
		} catch (NoSuchFieldException | ClassNotFoundException | NoSuchMethodException e) {
			Util.rethrow(e);
		}

		return elementsOfClass;
	}
}
