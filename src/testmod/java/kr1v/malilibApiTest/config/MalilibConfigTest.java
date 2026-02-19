package kr1v.malilibApiTest.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.*;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.Label;
import kr1v.malilibApi.config._new.ConfigButton;
//import kr1v.malilibApi.config._new.ConfigDropdown;
import kr1v.malilibApi.config._new.ConfigObject;
import kr1v.malilibApi.config.custom.ArrayBackedCycleConfig;
import kr1v.malilibApi.config.plus.ConfigStringPlus;

import static kr1v.malilibApiTest.Init.MOD_ID;

@SuppressWarnings("unused")
@Config(MOD_ID)
public class MalilibConfigTest {
	@Label("MaLiLib API")
	public static final ConfigButton TEST_BUTTON = new ConfigButton("button", "button", () -> System.out.println("Hi!"));
//	public static final ConfigDropdown<String> TEST_DROPDOWN = new ConfigDropdown<>("dropdown", ImmutableList.of("Wawa", "Wawa 2", "Wawa 3", "Wawa 4", "5", "6", "seven", "eight", "9 (nine)"), "Wawa", "", "dropdown", "dropdown");
	public static class ObjectThing { public final ConfigString STRING = new ConfigStringPlus("String"); }
	public static final ConfigObject<ObjectThing> TEST_OBJECT = new ConfigObject<>("object", new ObjectThing(), MOD_ID, "");

	@Label
	@Label("MaLiLib")
	public static final ConfigBoolean TEST_BOOLEAN = new ConfigBoolean("boolean", true, "");
	public static final ConfigBooleanHotkeyed TEST_BOOLEAN_HOTKEYED = new ConfigBooleanHotkeyed("boolean_hotkeyed", true, "", "");
	public static final ConfigColor TEST_COLOR = new ConfigColor("color", "", "");
	//? if >=1.20.1
	public static final ConfigColorList TEST_COLOR_LIST = new ConfigColorList("color_list", ImmutableList.of(), "");
	public static final ConfigDouble TEST_DOUBLE = new ConfigDouble("double", 0, "");
	//? if >=1.21
	public static final ConfigFloat TEST_FLOAT = new ConfigFloat("float", 0);
	public static final ConfigHotkey TEST_HOTKEY = new ConfigHotkey("hotkey", "", "");
	public static final ConfigInteger TEST_INTEGER = new ConfigInteger("integer", 0, "");
	public static final ConfigOptionList TEST_OPTION_LIST = new ArrayBackedCycleConfig<>("option_list", "", "", "", str -> str, "", new String[]{"", "awaw", "wawaw"});
	public static final ConfigString TEST_STRING = new ConfigString("string", "", "");
	public static final ConfigStringList TEST_STRING_LIST = new ConfigStringList("string_list", ImmutableList.of(), "");
}
