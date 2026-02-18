package kr1v.malilibApi.screen;

import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.ModRepresentation;
import kr1v.malilibApi.mixin.accessor.WidgetListConfigOptionsBaseAccessor;
import kr1v.malilibApi.util.ConfigUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ConfigScreen extends GuiConfigsBase {
	public ModRepresentation.Tab tab = InternalMalilibApi.getActiveTabFor(modId);

	//? if <1.16
	//protected MinecraftClient client = this.minecraft;

	public ConfigScreen(String modId, String titleKey) {
		this(modId, titleKey, null);
	}

	public ConfigScreen(String modId, String titleKey, Screen parent) {
		super(10, 50, modId, parent, titleKey);
		//? if <1.21.11 {
		if (this.client == null) {
			this.client = MinecraftClient.getInstance();
		}
		//? }
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

	//? if <1.16 {
	/*@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		if (this.client != null && this.client.world == null) this.renderBackground();
		InternalMalilibApi.setActiveTabFor(modId, this.tab);
		InternalMalilibApi.setScrollValueFor(modId, this.tab, getListWidget().getScrollbar().getValue());
		super.render(mouseX, mouseY, partialTicks);
	}
	*///? } else if <1.20.1 {
	/*@Override
	public void render(net.minecraft.client.util.math.MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (this.client != null && this.client.world == null) this.renderBackground(matrixStack);
		InternalMalilibApi.setActiveTabFor(modId, this.tab);
		InternalMalilibApi.setScrollValueFor(modId, this.tab, getListWidget().getScrollbar().getValue());
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	*///? } else {
	@Override
	public void render(net.minecraft.client.gui.DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
		//? if >=1.20.6 {
		if (this.client != null && this.client.world == null) this.renderPanoramaBackground(drawContext, partialTicks);
		//? } else if >=1.20.2 {
		/*if (this.client != null && this.client.world == null) this.renderBackground(drawContext, mouseX, mouseY, partialTicks);
		*///? } else {
		/*if (this.client != null && this.client.world == null) this.renderBackground(drawContext);
		*///? }
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
	//? }


	//? if <=1.20.4 {
	/*@Override
	protected void drawScreenBackground(int mouseX, int mouseY) {
		if (this.client != null && this.client.world == null) {
			return;
		}
		super.drawScreenBackground(mouseX, mouseY);
	}
	*///? }


	//? if >=1.21 {
	// why was it using the class :sob: that's so brittle
	@Override
	protected void buildConfigSwitcher() {
		if (fi.dy.masa.malilib.MaLiLibConfigs.Generic.ENABLE_CONFIG_SWITCHER.getBooleanValue()) {
			this.modSwitchWidget = new fi.dy.masa.malilib.gui.widgets.WidgetDropDownList<>(fi.dy.masa.malilib.util.GuiUtils.getScaledWindowWidth() - 155, 6, 130, 18, 200, 10, fi.dy.masa.malilib.registry.Registry.CONFIG_SCREEN.getAllModsWithConfigScreens()) {
				{
					selectedEntry = InternalMalilibApi.getMod(modId).modInfo;
				}

				@Override
				protected void setSelectedEntry(int index) {
					super.setSelectedEntry(index);

					//? if >=1.21.11 {
					/*if (selectedEntry != null && selectedEntry.configScreenSupplier() != null) {
						fi.dy.masa.malilib.gui.GuiBase.openGui(selectedEntry.configScreenSupplier().get());
					}
					*///? } else {
					if (selectedEntry != null && selectedEntry.getConfigScreenSupplier() != null) {
						fi.dy.masa.malilib.gui.GuiBase.openGui(selectedEntry.getConfigScreenSupplier().get());
					}
					//? }
				}

				@Override
				protected String getDisplayString(fi.dy.masa.malilib.util.data.ModInfo entry) {
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
	//? }

	@Override
	@NotNull
	protected WidgetListConfigOptions getListWidget() {
		return Objects.requireNonNull(super.getListWidget());
	}
}

