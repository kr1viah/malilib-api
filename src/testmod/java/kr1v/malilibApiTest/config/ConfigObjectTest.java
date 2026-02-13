package kr1v.malilibApiTest.config;

import fi.dy.masa.malilib.config.IConfigBase;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.Extras;
import kr1v.malilibApi.config._new.ConfigLabel;
import kr1v.malilibApi.config._new.ConfigList;
import kr1v.malilibApi.config._new.ConfigObject;
import kr1v.malilibApi.config.plus.ConfigHotkeyPlus;
import kr1v.malilibApi.config.plus.ConfigIntegerPlus;
import kr1v.malilibApi.config.plus.ConfigStringListPlus;
import kr1v.malilibApi.config.plus.ConfigStringPlus;
import kr1v.malilibApiTest.Init;
import net.minecraft.util.math.BlockPos;

import java.util.List;

@SuppressWarnings("unused")
@Config(Init.MOD_ID)
public class ConfigObjectTest {
	static class BlockPosConfig {
		public final ConfigIntegerPlus x = new ConfigIntegerPlus("x");
		public final ConfigIntegerPlus y = new ConfigIntegerPlus("y");
		public final ConfigIntegerPlus z = new ConfigIntegerPlus("z");

		@Extras
		void add(List<IConfigBase> existing) {
			existing.add(new ConfigLabel("Hi"));
		}

		public BlockPos get() {
			return new BlockPos(x.getIntegerValue(), y.getIntegerValue(), z.getIntegerValue());
		}

		@Override
		public String toString() {
			return get().toString();
		}
	}

	static final ConfigList<ConfigObject<BlockPosConfig>> BLOCKS = new ConfigList<>("Block positions", () -> new ConfigObject<>("", new BlockPosConfig(), Init.MOD_ID, "Edit block pos"));
	public static final ConfigStringListPlus STRING_LIST_PLUS = new ConfigStringListPlus("Test");
	public static final ConfigStringPlus STRING_PLUS = new ConfigStringPlus("S");

	static final ConfigHotkeyPlus PRINT_KEY = new ConfigHotkeyPlus("Print blocks", (action, key) -> {
		StringBuilder s = new StringBuilder();

		for (ConfigObject<BlockPosConfig> config : BLOCKS.getList()) {
			BlockPos pos = config.get().get();
			s.append(pos.toString()).append(", \n");
		}

		System.out.println(s.substring(0, s.length() - 3));

		return true;
	});
}















