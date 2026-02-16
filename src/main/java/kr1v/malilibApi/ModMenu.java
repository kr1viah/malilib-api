package kr1v.malilibApi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import kr1v.malilibApi.interfaces.IConfigScreenSupplier;

import java.util.Map;
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
