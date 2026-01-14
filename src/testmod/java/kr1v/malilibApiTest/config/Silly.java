package kr1v.malilibApiTest.config;

import fi.dy.masa.malilib.config.IConfigBase;
import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApi.annotation.Extras;
import kr1v.malilibApi.annotation.Label;
import kr1v.malilibApi.annotation.Marker;
import kr1v.malilibApi.config.ConfigLabel;

import java.util.List;

import static kr1v.malilibApiTest.Init.MOD_ID;

@Config(MOD_ID)
public class Silly {
	@Label("Label 1")
	@Marker("One")
	@Label("Label 2")
	@Marker("Two")
	@Label("Label :3")

	@Extras
	static void wawa1(List<IConfigBase> existing) {
		existing.add(new ConfigLabel("meow :3"));
	}

	@Extras(runAt = "One")
	static void wawa2(List<IConfigBase> existing) {
		existing.add(new ConfigLabel("meow 1"));
	}
	@Extras(runAt = {"One", "Two"})
	static void wawa3(List<IConfigBase> existing) {
		existing.add(new ConfigLabel("meow 2"));
	}
}
