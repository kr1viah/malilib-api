package kr1v.malilibApiTest.config;

import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.config._new.ConfigList;
import kr1v.malilibApi.config._new.ConfigObject;
import kr1v.malilibApi.config.plus.ConfigHotkeyPlus;
import kr1v.malilibApi.config.plus.ConfigIntegerPlus;
import kr1v.malilibApiTest.Init;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
@Config(Init.MOD_ID)
public class ConfigObjectTest {
	static class BlockPosConfig {
		public final ConfigIntegerPlus x = new ConfigIntegerPlus("x");
		public final ConfigIntegerPlus y = new ConfigIntegerPlus("y");
		public final ConfigIntegerPlus z = new ConfigIntegerPlus("z");

		public BlockPos get() {
			return new BlockPos(x.getIntegerValue(), y.getIntegerValue(), z.getIntegerValue());
		}

		@Override
		public String toString() {
			return get().toString();
		}
	}

	static final ConfigList<ConfigObject<BlockPosConfig>> BLOCKS = new ConfigList<>("Block positions", () -> new ConfigObject<>("", new BlockPosConfig(), "", "Edit block pos"));

	static final ConfigHotkeyPlus PRINT_KEY = new ConfigHotkeyPlus("Print blocks", (action, key) -> {
		StringBuilder s = new StringBuilder();

		for (ConfigObject<BlockPosConfig> config : BLOCKS.getList()) {
			BlockPos pos = config.get().get();
			s.append(pos.toString()).append(", \n");
		}

		Init.LOGGER.info(s.substring(0, s.length() - 3));

		return true;
	});
}















