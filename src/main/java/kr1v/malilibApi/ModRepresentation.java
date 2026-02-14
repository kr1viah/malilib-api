package kr1v.malilibApi;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.data.ModInfo;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import kr1v.malilibApi.interfaces.IConfigScreenSupplier;
import kr1v.malilibApi.util.AnnotationUtils;
import net.minecraft.client.gui.screen.Screen;

import java.util.*;
import java.util.stream.Collectors;

public class ModRepresentation {
	public final Map<Class<?>, List<IConfigBase>> configs = new TreeMap<>(Comparator.comparing((Class<?> x) -> AnnotationUtils.nameForConfig(x) + x.getName()));
	public final List<Tab> tabs = new ArrayList<>();
	public final ModInfo modInfo;
	public final InputHandler inputHandler;
	public final ConfigHandler configHandler;
	public final IConfigScreenSupplier configScreenSupplier;
	public final String modId;
	// hehe silly :3 way to default getting 0
	public final Object2IntMap<Tab> tabToScrollValue = new Object2IntOpenHashMap<>();
	public Tab activeTab;

	public ModRepresentation(String modId, ModInfo modInfo, ConfigHandler configHandler, InputHandler inputHandler, IConfigScreenSupplier configScreenSupplier) {
		this.modInfo = modInfo;
		this.configHandler = configHandler;
		this.inputHandler = inputHandler;
		this.configScreenSupplier = configScreenSupplier;
		this.modId = modId;
	}

	public static final class Tab {
		private final String translationKey;
		private final List<IConfigBase> options;
		private final boolean isPopup;
		private final int order;

		public Tab(String translationKey, List<IConfigBase> options, boolean isPopup, int order) {
			this.translationKey = translationKey;
			this.options = options == null ? null : Collections.unmodifiableList(new ArrayList<>(options));
			this.isPopup = isPopup;
			this.order = order;
		}

		public String translationKey() {
			return translationKey;
		}

		public List<IConfigBase> options() {
			return options;
		}

		public boolean isPopup() {
			return isPopup;
		}

		public int order() {
			return order;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Tab)) return false;
			Tab tab = (Tab) o;
			return isPopup == tab.isPopup &&
					order == tab.order &&
					Objects.equals(translationKey, tab.translationKey) &&
					Objects.equals(options, tab.options);
		}

		@Override
		public int hashCode() {
			return Objects.hash(translationKey, options, isPopup, order);
		}

		@Override
		public String toString() {
			return "Tab[" +
					"translationKey=" + translationKey +
					", options=" + options +
					", isPopup=" + isPopup +
					", order=" + order +
					']';
		}
	}

	public void openScreen() {
		GuiBase.openGui(configScreenSupplier.get());
	}

	public void openScreen(Screen parent) {
		GuiBase.openGui(configScreenSupplier.get(parent));
	}

	public void registerTab(String tabName, List<IConfigBase> options, boolean isPopup, int order) {
		this.tabs.add(new ModRepresentation.Tab(tabName, options, isPopup, order));
	}

	public void unregisterTab(String tabName) {
		this.tabs.removeIf(tab -> tab.translationKey().equals(tabName));
	}

	public List<Tab> tabs() {
		return tabs
				.stream()
				.sorted(Comparator
						.comparing(ModRepresentation.Tab::isPopup)
						.thenComparingInt(ModRepresentation.Tab::order)
						.thenComparing(ModRepresentation.Tab::translationKey)
				)
				.collect(Collectors.toList());
	}

	public Tab activeTab() {
		if (this.activeTab == null) return tabs().get(0);
		return this.activeTab;
	}

	public Tab tabByTranslationKey(String translationKey) {
		for (ModRepresentation.Tab tab : tabs()) {
			if (tab.translationKey().equals(translationKey)) {
				return tab;
			}
		}
		return null;
	}

	public int scrollValue() {
		return this.tabToScrollValue.getInt(activeTab());
	}

	public int scrollValue(Tab tab) {
		return this.tabToScrollValue.getInt(tab);
	}

	public void setActiveTab(Tab tab) {
		this.activeTab = tab;
	}

	public void setScrollValue(int value) {
		this.tabToScrollValue.put(activeTab(), value);
	}

	public void setScrollValue(Tab tab, int value) {
		this.tabToScrollValue.put(tab, value);
	}
}
