package kr1v.malilibApi;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.util.AnnotationUtils;

import java.util.*;

public class ModConfig {
    public final Map<Class<?>, List<IConfigBase>> configs = new TreeMap<>(Comparator.comparing((Class<?> x) -> AnnotationUtils.nameForConfig(x) + x.getName()));
    public final List<Tab> tabs = new ArrayList<>();
    public final ModInfo modInfo;
    public final InputHandler inputHandler;
    public final ConfigHandler configHandler;

    public ModConfig(ModInfo modInfo, ConfigHandler configHandler, InputHandler inputHandler) {
        this.modInfo = modInfo;
        this.configHandler = configHandler;
        this.inputHandler = inputHandler;
    }

    public record Tab(String translationKey, List<IConfigBase> options, boolean isPopup, int order) {}
}
