package kr1v.malilibApi.interfaces;

import fi.dy.masa.malilib.config.IConfigBase;
import kr1v.malilibApi.annotation.processor.ConfigProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface AnnotationHandler {
	void handle(Annotation annotation,
				ConfigProcessor.Element element,
				List<IConfigBase> list,
				Class<?> declaringClass,
				String modId,
				boolean static_,
				Object instance
	) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException;
}
