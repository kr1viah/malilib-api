package kr1v.malilibApiTest.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.*;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.Label;
import kr1v.malilibApi.config._new.ConfigList;
import kr1v.malilibApi.config.custom.ArrayBackedCycleConfig;
import kr1v.malilibApi.config.custom.ConfigCycle;
import kr1v.malilibApi.config.custom.EnumBackedCycleConfig;
import kr1v.malilibApiTest.Init;
import net.minecraft.world.GameMode;

@SuppressWarnings("unused")
@Config(Init.MOD_ID)
public class Test3 {
	@Label("MaLiLib")
	public static final ConfigList<ConfigBoolean> TEST_BOOLEAN = new ConfigList<>("boolean", () -> new ConfigBoolean("", true, ""));
	public static final ConfigList<ConfigBooleanHotkeyed> TEST_BOOLEAN_HOTKEYED = new ConfigList<>("boolean_hotkeyed", () -> new ConfigBooleanHotkeyed("", true, "", ""));
	public static final ConfigList<ConfigColor> TEST_COLOR = new ConfigList<>("color", () -> new ConfigColor("", "", ""));
	public static final ConfigList<ConfigColorList> TEST_COLOR_LIST = new ConfigList<>("color_list", () -> new ConfigColorList("", ImmutableList.of(), ""));
	public static final ConfigList<ConfigDouble> TEST_DOUBLE = new ConfigList<>("double", () -> new ConfigDouble("", 0, ""));
	//? if >=1.21
	public static final ConfigList<ConfigFloat> TEST_FLOAT = new ConfigList<>("float", () -> new ConfigFloat("", 0));
	public static final ConfigList<ConfigHotkey> TEST_HOTKEY = new ConfigList<>("hotkey", () -> new ConfigHotkey("", "", ""));
	public static final ConfigList<ConfigInteger> TEST_INTEGER = new ConfigList<>("integer", () -> new ConfigInteger("", 0, ""));
	public static final ConfigList<ConfigOptionList> TEST_OPTION_LIST = new ConfigList<>("option_list", () -> new ArrayBackedCycleConfig<>("", "", "", "", str -> str, "", new String[]{"", "awaw", "wawaw"}));
	public static final ConfigList<ConfigString> TEST_STRING = new ConfigList<>("string", () -> new ConfigString("", "", ""));
	public static final ConfigList<ConfigStringList> TEST_STRING_LIST = new ConfigList<>("string_list", () -> new ConfigStringList("", ImmutableList.of(), ""));

	@Label("MaLiLib API")
	public static final ConfigList<ConfigCycle<GameMode>> TEST_CYCLE = new ConfigList<>("cycle", () -> new EnumBackedCycleConfig.Builder<>("", GameMode.class).build());
	public static final ConfigList<ConfigList<ConfigBoolean>> TEST_LIST = new ConfigList<>("list", () -> new ConfigList<>("", () -> new ConfigBoolean("", false, "")));
}
