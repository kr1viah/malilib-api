package kr1v.malilibApi;

import fi.dy.masa.malilib.config.IConfigBase;
import kr1v.malilibApi.util.AnnotationUtils;

import java.util.*;

public class ModConfig {
    public final Map<Class<?>, List<IConfigBase>> configs = new TreeMap<>(Comparator.comparing((Class<?> x) -> AnnotationUtils.nameForConfig(x) + x.getName()));
    public final List<Tab> tabs = new ArrayList<>();

    public record Tab(String translationKey, List<IConfigBase> options, boolean isPopup) {}
}
