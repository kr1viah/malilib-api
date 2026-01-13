package kr1v.malilibApiTest;

import kr1v.malilibApi.MalilibApi;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.config.plus.*;

import static kr1v.malilibApiTest.Init.MOD_ID;

@SuppressWarnings("unused")
@Config(MOD_ID)
public class Test {
	public static final ConfigStringPlus TEST = new ConfigStringPlus("Hiii");

	static final ConfigHotkeyPlus HOTKEY = new ConfigHotkeyPlus("Open gui", "G,C", (keyAction, iKeybind) -> {
		MalilibApi.openScreenFor(MOD_ID); // opens the screen
		return true; // return true if pressing the hotkey did something, otherwise false.
	});
}
