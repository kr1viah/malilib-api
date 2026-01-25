package kr1v.malilibApiTest.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.*;
import kr1v.malilibApi.annotation.*;
import kr1v.malilibApi.config._new.ConfigList;
import kr1v.malilibApi.config._new.ConfigPair;
import kr1v.malilibApi.config.custom.ArrayBackedCycleConfig;
import kr1v.malilibApi.config.custom.ConfigCycle;
import kr1v.malilibApi.config.custom.EnumBackedCycleConfig;
import kr1v.malilibApiTest.Init;
import net.minecraft.world.GameMode;

@SuppressWarnings("unused")
@Config(Init.MOD_ID)
public class Test5 {
	public static final ConfigBoolean DUMMY = new ConfigBoolean("1", false);
	public static final ConfigPair<ConfigBoolean, ConfigBoolean> TEST_2 = new ConfigPair<>("2", new ConfigBoolean("", false), new ConfigBoolean("", false));
	public static final ConfigPair<ConfigPair<ConfigBoolean, ConfigBoolean>, ConfigPair<ConfigBoolean, ConfigBoolean>> TEST = new ConfigPair<>(
			"4",
			new ConfigPair<>("",
					new ConfigBoolean("", false),
					new ConfigBoolean("", false)
			),
			new ConfigPair<>("",
					new ConfigBoolean("", false),
					new ConfigBoolean("", false)
			)
	);
	public static final ConfigPair<ConfigPair<ConfigPair<ConfigBoolean, ConfigBoolean>, ConfigPair<ConfigBoolean, ConfigBoolean>>, ConfigPair<ConfigPair<ConfigBoolean, ConfigBoolean>, ConfigPair<ConfigBoolean, ConfigBoolean>>> TEST_8 = new ConfigPair<>(
			"8",
			new ConfigPair<>(
					"",
					new ConfigPair<>("",
							new ConfigBoolean("", false),
							new ConfigBoolean("", false)
					),
					new ConfigPair<>("",
							new ConfigBoolean("", false),
							new ConfigBoolean("", false)
					)
			),
			new ConfigPair<>(
					"",
					new ConfigPair<>("",
							new ConfigBoolean("", false),
							new ConfigBoolean("", false)
					),
					new ConfigPair<>("",
							new ConfigBoolean("", false),
							new ConfigBoolean("", false)
					)
			)
	);

	public static final ConfigPair<ConfigPair<ConfigPair<ConfigPair<ConfigBoolean, ConfigBoolean>, ConfigPair<ConfigBoolean, ConfigBoolean>>, ConfigPair<ConfigPair<ConfigBoolean, ConfigBoolean>, ConfigPair<ConfigBoolean, ConfigBoolean>>>, ConfigPair<ConfigPair<ConfigPair<ConfigBoolean, ConfigBoolean>, ConfigPair<ConfigBoolean, ConfigBoolean>>, ConfigPair<ConfigPair<ConfigBoolean, ConfigBoolean>, ConfigPair<ConfigBoolean, ConfigBoolean>>>> TEST_16 = new ConfigPair<>(
			"16",
			new ConfigPair<>(
					"",
					new ConfigPair<>(
							"",
							new ConfigPair<>("",
									new ConfigBoolean("", false),
									new ConfigBoolean("", false)
							),
							new ConfigPair<>("",
									new ConfigBoolean("", false),
									new ConfigBoolean("", false)
							)
					),
					new ConfigPair<>(
							"",
							new ConfigPair<>("",
									new ConfigBoolean("", false),
									new ConfigBoolean("", false)
							),
							new ConfigPair<>("",
									new ConfigBoolean("", false),
									new ConfigBoolean("", false)
							)
					)
			),
			new ConfigPair<>(
					"",
					new ConfigPair<>(
							"",
							new ConfigPair<>("",
									new ConfigBoolean("", false),
									new ConfigBoolean("", false)
							),
							new ConfigPair<>("",
									new ConfigBoolean("", false),
									new ConfigBoolean("", false)
							)
					),
					new ConfigPair<>(
							"",
							new ConfigPair<>("",
									new ConfigBoolean("", false),
									new ConfigBoolean("", false)
							),
							new ConfigPair<>("",
									new ConfigBoolean("", false),
									new ConfigBoolean("", false)
							)
					)
			)
	);

	@Label
	@Label("MaLiLib")
	public static final ConfigPair<ConfigBoolean, ConfigBoolean> TEST_BOOLEAN_PAIR = new ConfigPair<>("boolean", new ConfigBoolean("", true), new ConfigBoolean("", true));
	public static final ConfigPair<ConfigBooleanHotkeyed, ConfigBooleanHotkeyed> TEST_BOOLEAN_HOTKEYED_PAIR = new ConfigPair<>("boolean_hotkeyed", new ConfigBooleanHotkeyed("", true, ""), new ConfigBooleanHotkeyed("", true, ""));
	public static final ConfigPair<ConfigColor, ConfigColor> TEST_COLOR_PAIR = new ConfigPair<>("color", new ConfigColor("", ""), new ConfigColor("", ""));
	public static final ConfigPair<ConfigColorList, ConfigColorList> TEST_COLOR_LIST_PAIR = new ConfigPair<>("color_list", new ConfigColorList("", ImmutableList.of()), new ConfigColorList("", ImmutableList.of()));
	public static final ConfigPair<ConfigDouble, ConfigDouble> TEST_DOUBLE_PAIR = new ConfigPair<>("double", new ConfigDouble("", 0), new ConfigDouble("", 0));
	public static final ConfigPair<ConfigFloat, ConfigFloat> TEST_FLOAT_PAIR = new ConfigPair<>("float", new ConfigFloat("", 0), new ConfigFloat("", 0));
	public static final ConfigPair<ConfigHotkey, ConfigHotkey> TEST_HOTKEY_PAIR = new ConfigPair<>("hotkey", new ConfigHotkey("", ""), new ConfigHotkey("", ""));
	public static final ConfigPair<ConfigInteger, ConfigInteger> TEST_INTEGER_PAIR = new ConfigPair<>("integer", new ConfigInteger("", 0), new ConfigInteger("", 0));
	public static final ConfigPair<ArrayBackedCycleConfig<String>, ArrayBackedCycleConfig<String>> TEST_OPTION_LIST_PAIR = new ConfigPair<>(
			"option_list",
			new ArrayBackedCycleConfig<>("", "", "", "", str -> str, "", new String[] { "", "awaw", "wawaw" }),
			new ArrayBackedCycleConfig<>("", "", "", "", str -> str, "", new String[] { "", "awaw", "wawaw" })
	);
	public static final ConfigPair<ConfigString, ConfigString> TEST_STRING_PAIR = new ConfigPair<>("string", new ConfigString("", ""), new ConfigString("", ""));
	public static final ConfigPair<ConfigStringList, ConfigStringList> TEST_STRING_LIST_PAIR = new ConfigPair<>("string_list", new ConfigStringList("", ImmutableList.of()), new ConfigStringList("", ImmutableList.of()));

	@Label("MaLiLib API")
	public static final ConfigPair<ConfigCycle<GameMode>, ConfigCycle<GameMode>> TEST_CYCLE_PAIR = new ConfigPair<>(
			"cycle",
			new EnumBackedCycleConfig.Builder<>("", GameMode.class).build(),
			new EnumBackedCycleConfig.Builder<>("", GameMode.class).build()
	);
	public static final ConfigPair<ConfigList<ConfigBoolean>, ConfigList<ConfigBoolean>> TEST_LIST_PAIR = new ConfigPair<>(
			"list",
			new ConfigList<>("", () -> new ConfigBoolean("", false)),
			new ConfigList<>("", () -> new ConfigBoolean("", false))
	);

}
