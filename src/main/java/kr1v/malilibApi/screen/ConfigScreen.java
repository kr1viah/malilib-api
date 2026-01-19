package kr1v.malilibApi.screen;

import fi.dy.masa.malilib.MaLiLibConfigs;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetDropDownList;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import fi.dy.masa.malilib.registry.Registry;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.ModRepresentation;
import kr1v.malilibApi.mixin.accessor.WidgetListConfigOptionsBaseAccessor;
import kr1v.malilibApi.util.ConfigUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ConfigScreen extends GuiConfigsBase {
	public ModRepresentation.Tab tab = InternalMalilibApi.getActiveTabFor(modId);

	public ConfigScreen(String modId, String titleKey) {
		this(modId, titleKey, null);
	}

	public ConfigScreen(String modId, String titleKey, Screen parent) {
		super(10, 50, modId, parent, titleKey);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.clearOptions();
		this.configWidth = ((WidgetListConfigOptionsBaseAccessor) Objects.requireNonNull(getListWidget())).getMaxLabelWidth();
		this.configWidth = this.width - this.configWidth - 94;
		((WidgetListConfigOptionsBaseAccessor) getListWidget()).setConfigWidth(this.configWidth);

		getListWidget().getScrollbar().setValue(InternalMalilibApi.getScrollValueFor(this.modId));

		int x = 10;
		int y = 26;

		for (ModRepresentation.Tab tab : InternalMalilibApi.getTabsFor(modId)) {
			if (!tab.isPopup()) {
				x += this.createButton(x, y, -1, tab);
			}
		}
	}

	@SuppressWarnings("SameParameterValue")
	private int createButton(int x, int y, int width, ModRepresentation.Tab tab) {
		// I need to be sent to jail for this method
		ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.translationKey());
		button.setEnabled(!this.tab.equals(tab));
		final ModRepresentation.Tab tab2 = tab;

		this.addButton(button, (button1, mouseButton) -> {
			InternalMalilibApi.setScrollValueFor(modId, this.tab, getListWidget().getScrollbar().getValue());
			this.tab = tab2;
			InternalMalilibApi.setActiveTabFor(modId, this.tab);
			reCreateListWidget(); // apply the new config width
			initGui();
		});

		return button.getWidth() + 2;
	}

	@Override
	protected void closeGui(boolean showParent) {
		InternalMalilibApi.setActiveTabFor(modId, this.tab);
		InternalMalilibApi.setScrollValueFor(modId, this.tab, getListWidget().getScrollbar().getValue());
		super.closeGui(true);
	}

	@Override
	protected int getConfigWidth() {
		return this.width / 2;
	}

	@Override
	public List<ConfigOptionWrapper> getConfigs() {
		return ConfigUtils.getConfigOptions(this.tab.options());
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
		if (this.client != null && this.client.world == null) this.renderPanoramaBackground(drawContext, partialTicks);
		//? if =1.21 {
		/*this.applyBlur(partialTicks); // this arg was removed in 24w33a
		 *///? } else if =1.21.5 {
		this.applyBlur();
		//? } else if >=1.21.8 {
		/*this.applyBlur(drawContext); // this arg was added in 25w17a
		 *///? }
		InternalMalilibApi.setActiveTabFor(modId, this.tab);
		InternalMalilibApi.setScrollValueFor(modId, this.tab, getListWidget().getScrollbar().getValue());
		super.render(drawContext, mouseX, mouseY, partialTicks);
	}

	// why was it using the class :sob: that's so brittle
	@Override
	protected void buildConfigSwitcher() {
		if (MaLiLibConfigs.Generic.ENABLE_CONFIG_SWITCHER.getBooleanValue()) {
			this.modSwitchWidget = new WidgetDropDownList<>(GuiUtils.getScaledWindowWidth() - 155, 6, 130, 18, 200, 10, Registry.CONFIG_SCREEN.getAllModsWithConfigScreens()) {
				{
					selectedEntry = InternalMalilibApi.modInfoFor(modId);
				}

				@Override
				protected void setSelectedEntry(int index) {
					super.setSelectedEntry(index);

					//? if >=1.21.11 {
					/*if (selectedEntry != null && selectedEntry.configScreenSupplier() != null) {
						GuiBase.openGui(selectedEntry.configScreenSupplier().get());
					}
					*///? } else {
					if (selectedEntry != null && selectedEntry.getConfigScreenSupplier() != null) {
						GuiBase.openGui(selectedEntry.getConfigScreenSupplier().get());
					}
					//? }
				}

				@Override
				protected String getDisplayString(ModInfo entry) {
					//? if >=1.21.11 {
					/*return entry.modName();
					 *///? } else {
					return entry.getModName();
					//? }
				}
			};

			addWidget(this.modSwitchWidget);
		}
	}

	@Override
	@NotNull
	protected WidgetListConfigOptions getListWidget() {
		return Objects.requireNonNull(super.getListWidget());
	}
}

