package kr1v.malilibApi.interfaces;

import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;

import java.util.List;

public interface IHotkeyContainer extends IKeybindContainer {
	List<IHotkey> getHotkeys();

	@Override
	default List<IKeybind> getKeybinds() {
		return getHotkeys().stream().map(IHotkey::getKeybind).toList();
	}
}
