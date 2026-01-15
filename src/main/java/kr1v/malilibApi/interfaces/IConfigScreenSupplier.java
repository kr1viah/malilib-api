package kr1v.malilibApi.interfaces;

import kr1v.malilibApi.screen.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;

public interface IConfigScreenSupplier {
	ConfigScreen get();

	ConfigScreen get(Screen parent);
}
