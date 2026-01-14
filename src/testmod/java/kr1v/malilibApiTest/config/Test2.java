package kr1v.malilibApiTest.config;

import fi.dy.masa.malilib.config.IConfigBase;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.Extras;
import kr1v.malilibApi.config.ConfigLabel;

import java.util.List;

import static kr1v.malilibApiTest.Init.MOD_ID;

@Config(value = MOD_ID, name = "B Test 2 !", order = 0)
public class Test2 {
	@Extras
	static void name(List<IConfigBase> existing) {
		for (int i = 1; i <= 25; i++) {
			String str = i + ": ";
			if (i%3 == 0) str += "Fizz";
			if (i%5 == 0) str += "Buzz";

			ConfigLabel label = new ConfigLabel(str);
			existing.add(label);

			InternalMalilibApi.addHide(label);
			if (!str.equals(i + ": "))
				InternalMalilibApi.removeHide(label);
		}
	}
}
