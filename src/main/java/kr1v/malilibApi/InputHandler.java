package kr1v.malilibApi;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.hotkeys.*;
import kr1v.malilibApi.screen.ConfigScreen;

import java.util.ArrayList;
import java.util.List;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler {
    private final String MOD_ID;

    public InputHandler(String modId) {
        this.MOD_ID = modId;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (ConfigScreen.ConfigGuiTab config : ConfigScreen.ConfigGuiTab.values(MOD_ID)) {
            for (IConfigBase option : config.getOptions()) {
                if (option instanceof IHotkey hotkey) {
                    manager.addKeybindToMap(hotkey.getKeybind());
                }
            }
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        for (ConfigScreen.ConfigGuiTab config : ConfigScreen.ConfigGuiTab.values(MOD_ID)) {
            List<IHotkey> hotkeys = new ArrayList<>();
            for (IConfigBase option : config.getOptions()) {
                if (option instanceof IHotkey hotkey) {
                    hotkeys.add(hotkey);
                }
            }
            manager.addHotkeysForCategory(MOD_ID, config.getDisplayName(), hotkeys);
        }
    }
}