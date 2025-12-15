package kr1v.malilibApi.util;

import fi.dy.masa.malilib.config.IConfigBase;
import kr1v.malilibApi.ModConfig;

import java.util.List;

public final class TabUtils {
    private TabUtils() {}

    public static void registerTab(String modId, String tab, List<IConfigBase> options, boolean isPopup) {
        AnnotationUtils.getModConfig(modId).tabs.add(new ModConfig.Tab(tab, options, isPopup));
    }

    public static void unregisterTab(String modId, String tabName) {
        AnnotationUtils.getModConfig(modId).tabs.removeIf(tab -> tab.translationKey().equals(tabName));
    }

    public static List<ModConfig.Tab> tabsFor(String modId) {
        return AnnotationUtils.getModConfig(modId).tabs;
    }
}
