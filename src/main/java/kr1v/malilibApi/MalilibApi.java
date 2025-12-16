package kr1v.malilibApi;

import fi.dy.masa.malilib.config.IConfigBase;
import net.minecraft.client.gui.screen.Screen;

import java.util.List;

@SuppressWarnings("unused")
public class MalilibApi {
    /// Use if you want a different internal ID than mod name.
    public static void registerMod(String modId, String modName) {
        registerMod(modId, modName, new ConfigHandler(modId), new InputHandler(modId));
    }

    /// Use if you want a custom ConfigHandler/InputHandler
    public static void registerMod(String modId, String modName, ConfigHandler configHandler, InputHandler inputHandler) {
        InternalMalilibApi.registerMod(modId, modName, configHandler, inputHandler);
    }

    public static void openScreenFor(String modId) {
        openScreenFor(modId, null);
    }

    public static void openScreenFor(String modId, Screen parent) {
        InternalMalilibApi.openScreenFor(modId, parent);
    }

    public static void registerTab(String modId, String tab, List<IConfigBase> options) {
        registerTab(modId, tab, options, 1000);
    }

    public static void registerTab(String modId, String tab, List<IConfigBase> options, int order) {
        InternalMalilibApi.registerTab(modId, tab, options, false, order);
    }

    public static void unregisterTab(String modId, String tabName) {
        InternalMalilibApi.unregisterTab(modId, tabName);
    }
}