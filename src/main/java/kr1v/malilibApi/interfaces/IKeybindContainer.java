package kr1v.malilibApi.interfaces;

import fi.dy.masa.malilib.hotkeys.IKeybind;

import java.util.List;

/// Use this over IHotkeyContainer if you are implementing a CustomConfigBase and need a keybind
public interface IKeybindContainer {
	List<IKeybind> getKeybinds();
}
