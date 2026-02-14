package kr1v.malilibApiTest.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.*;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.Label;
import kr1v.malilibApi.config.custom.ArrayBackedCycleConfig;

import static kr1v.malilibApiTest.Init.MOD_ID;

@SuppressWarnings("unused")
@Config(MOD_ID)
public class MalilibConfigTest {
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
