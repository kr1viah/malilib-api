package kr1v.malilibApi.util;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.util.GuiUtils;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.annotation.*;
import kr1v.malilibApi.annotation.processor.ConfigProcessor;
import kr1v.malilibApi.config._new.ConfigButton;
import kr1v.malilibApi.config._new.ConfigLabel;
import kr1v.malilibApi.screen.ConfigPopupScreen;
import net.minecraft.client.MinecraftClient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ConfigUtils {
	private ConfigUtils() {
	}

	public static List<IConfigBase> generateOptions(Class<?> clazz, String modId) {
		List<IConfigBase> list = new ArrayList<>();

		List<ConfigProcessor.Element> elements = ClassUtils.getDeclaredElements(clazz);

		try {
			for (ConfigProcessor.Element element : elements) {
				handleAnnotations(element, list, clazz, modId);
				if (element.field != null) {
					Field f = element.field;
					if (IConfigBase.class.isAssignableFrom(f.getType()) && Modifier.isStatic(f.getModifiers())) {
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

					ConfigButton configButton = new ConfigButton(name, buttonName, () ->
							MinecraftClient.getInstance().setScreen(
									new ConfigPopupScreen(klass, GuiUtils.getCurrentScreen(), modId)
							)
					);

					boolean prev = InternalMalilibApi.getDefaultEnabled();
					InternalMalilibApi.setDefaultEnabled(klass.getAnnotation(PopupConfig.class).defaultEnabled());
					InternalMalilibApi.cacheFor(modId).put(klass, generateOptions(klass, modId));
					InternalMalilibApi.setDefaultEnabled(prev);
					InternalMalilibApi.registerTab(modId, AnnotationUtils.nameForConfig(klass), list, true, 0); // order doesnt matter for popups
					list.add(configButton);
				}
				case Label label -> list.add(new ConfigLabel(label.value()));
				case Extras extras -> {
					// if is @Extras or any is "" (e.g. @Extras {"One", ""}) run here
					if (extras.runAt().length == 0 || Arrays.stream(extras.runAt()).anyMatch(String::isEmpty)) {
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
							for (String value : extras.runAt()) {
								if (marker.value().equals(value)) {
									m.setAccessible(true);
									m.invoke(null, list);
								}
							}
						}
					}
				}
				case Hide ignored -> InternalMalilibApi.addHide(element.field.get(null));
				default -> {
				}
			}
		}
	}

	public static ImmutableList<GuiConfigsBase.ConfigOptionWrapper> getConfigOptions(List<? extends IConfigBase> configs) {
		ImmutableList.Builder<GuiConfigsBase.ConfigOptionWrapper> builder = ImmutableList.builder();
		for (IConfigBase config : configs) {
			if (InternalMalilibApi.shouldHide(config)) continue;
			if (config instanceof ConfigLabel)
				builder.add(new GuiConfigsBase.ConfigOptionWrapper(config.getComment()));
			else
				builder.add(new GuiConfigsBase.ConfigOptionWrapper(config));
		}
		return builder.build();
	}
}
