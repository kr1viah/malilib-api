package kr1v.malilibApi;

// TODO fix

//? if >=1.16 {
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import kr1v.malilibApi.interfaces.IConfigScreenSupplier;
import net.minecraft.client.gui.screen.Screen;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModMenu implements ModMenuApi {
	@Override
	public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
		return InternalMalilibApi.registeredMods.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						e -> (screen -> {
							IConfigScreenSupplier supplier = e.getValue().configScreenSupplier;
							if (screen == null) return supplier.get();
							return supplier.get(screen);
						})
				));
	}

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		// TODO this
//		var supplier = InternalMalilibApi.registeredMods.get("malilib-api").configScreenSupplier;
//		return screen -> {
//			if (screen == null) return supplier.get();
//			return supplier.get(screen);
//		};
		return screen -> null;
	}
}
//? } else {
/*public class ModMenu implements io.github.prospector.modmenu.api.ModMenuApi {
	@Override
	public String getModId() {
		return "malilib-api";
	}
}
*///? }