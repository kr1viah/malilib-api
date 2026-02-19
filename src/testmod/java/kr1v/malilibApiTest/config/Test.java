package kr1v.malilibApiTest.config;

import fi.dy.masa.malilib.config.*;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.MalilibApi;
import kr1v.malilibApi.annotation.*;
import kr1v.malilibApi.config._new.ConfigLabel;
import kr1v.malilibApi.config.plus.*;

import java.util.List;

import static kr1v.malilibApiTest.Init.MOD_ID;

@SuppressWarnings("unused")
@Config(value = MOD_ID, name = "A Test")
public class Test {
	public static final ConfigStringPlus TEST = new ConfigStringPlus("Hiii");

	static final ConfigHotkeyPlus HOTKEY = new ConfigHotkeyPlus("Open gui", "G,    C").setPressCallback((keyAction, iKeybind) -> {
		MalilibApi.openScreenFor(MOD_ID);
		return true;
	});

	@Hide
	public static final ConfigStringPlus INVISIBLE = new ConfigStringPlus("I'm invisible!");

	@Extras
	private static void moreCustom(List<IConfigBase> existing) {
		for (int i = 0; i < 4; i++) {
			ConfigStringListPlus list = new ConfigStringListPlus("I'm also invisible!  " + i);
			existing.add(list);
			InternalMalilibApi.addHide(list); // this makes it invisible
		}
	}

	@Label("Label above class")
	@PopupConfig
	public static class Popup {
		@Extras
		private static void moreCustom(List<IConfigBase> existing) {
			for (int i = 0; i < 4; i++) {
				existing.add(new ConfigStringListPlus("String list " + i));
			}
		}
	}

	@Label("Label above method")
	@Extras
	private static void addCustom(List<IConfigBase> existing) {
		existing.add(new ConfigBooleanPlus("Programmatically add new ones"));
		for (int i = 0; i < 6; i++) {
			existing.add(new ConfigLabel("" + i));
		}
	}

	public static final ConfigStringPlus STRING = new ConfigStringPlus("String");
	@Label("Labels and markers mixed in")
	@Marker("Marker name")
	@Label("Another label sandwiching the marker")
	public static final ConfigIntegerPlus INT = new ConfigIntegerPlus("Integer");

	@Extras(runAt = "Marker name")
	private static void addMoreCustom(List<IConfigBase> existing) {
		existing.add(new ConfigStringPlus("In between the labels"));
	}
}
