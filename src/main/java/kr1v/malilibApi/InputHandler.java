package kr1v.malilibApi;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.hotkeys.*;
import kr1v.malilibApi.interfaces.IHotkeyContainer;
import kr1v.malilibApi.interfaces.IVariableHotkeyContainer;

import java.util.ArrayList;
import java.util.List;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler {
	private final String MOD_ID;

	public InputHandler(String modId) {
		this.MOD_ID = modId;
	}

	@Override
	public void addKeysToMap(IKeybindManager manager) {
		for (ModRepresentation.Tab tab : InternalMalilibApi.getTabsFor(MOD_ID)) {
			for (IConfigBase option : tab.options()) {
				if (option instanceof IHotkey hotkey) {
					manager.addKeybindToMap(hotkey.getKeybind());
				} else if (option instanceof IHotkeyContainer container) {
					List<IHotkey> containedHotkeys = container.getHotkeys();
					for (IHotkey hotkey : containedHotkeys) {
						manager.addKeybindToMap(hotkey.getKeybind());
					}
				}
			}
		}
	}

	@Override
	public void addHotkeys(IKeybindManager manager) {
		for (ModRepresentation.Tab tab : InternalMalilibApi.getTabsFor(MOD_ID)) {
			List<IHotkey> hotkeys = new ArrayList<>();
			for (IConfigBase option : tab.options()) {
				if (option instanceof IHotkey hotkey) {
					hotkeys.add(hotkey);
				} else if (option instanceof IVariableHotkeyContainer variableContainer) {
					variableContainer.registerListener(() -> {
						List<IHotkey> containedHotkeys = variableContainer.getHotkeys();
						if (!containedHotkeys.isEmpty()) {
							manager.addHotkeysForCategory(MOD_ID, tab.translationKey() + " -> " + option.getName(), containedHotkeys);
						}
					});
				} else if (option instanceof IHotkeyContainer container) {
					List<IHotkey> containedHotkeys = container.getHotkeys();
					if (!containedHotkeys.isEmpty()) {
						manager.addHotkeysForCategory(MOD_ID, tab.translationKey() + " -> " + option.getName(), containedHotkeys);
					}
				}
			}
			manager.addHotkeysForCategory(MOD_ID, tab.translationKey(), hotkeys);
		}
	}
}