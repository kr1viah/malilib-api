package kr1v.malilibApi.screen;

import fi.dy.masa.malilib.MaLiLibConfigs;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetDropDownList;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.ModRepresentation;
import kr1v.malilibApi.mixin.accessor.WidgetListConfigOptionsBaseAccessor;
import kr1v.malilibApi.util.ConfigUtils;
//? >=1.20
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
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
		//? if <1.21.11 {
		/*if (this.minecraft == null) {
			this.minecraft = Minecraft.getInstance();
		}
		*///? }
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
			x += this.createButton(x, y, -1, tab);
		}
	}

	@SuppressWarnings("SameParameterValue")
	private int createButton(int x, int y, int width, ModRepresentation.Tab tab) {
		// I need to be sent to jail for this method
		ButtonGeneric button = new ButtonGeneric(x, y, width, 20, StringUtils.translate(tab.translationKey()));
		button.setEnabled(!this.tab.equals(tab));

		this.addButton(button, (button1, mouseButton) -> {
			InternalMalilibApi.setScrollValueFor(modId, this.tab, getListWidget().getScrollbar().getValue());
			this.tab = tab;
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
		if (this.minecraft != null && this.minecraft.level == null) this.renderBackground();
		InternalMalilibApi.setActiveTabFor(modId, this.tab);
		InternalMalilibApi.setScrollValueFor(modId, this.tab, getListWidget().getScrollbar().getValue());
		super.render(mouseX, mouseY, partialTicks);
	}
	*///? } else if <1.20.1 {
	/*@Override
	public void render(com.mojang.blaze3d.vertex.PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		if (this.minecraft != null && this.minecraft.level == null) this.renderBackground(stack);
		InternalMalilibApi.setActiveTabFor(modId, this.tab);
		InternalMalilibApi.setScrollValueFor(modId, this.tab, getListWidget().getScrollbar().getValue());
		super.render(stack, mouseX, mouseY, partialTicks);
	}
	*///? } else {
	@Override
	//~ if >1.21.11 'render' -> 'extractRenderState'
	public void extractRenderState(GuiGraphicsExtractor gui, int mouseX, int mouseY, float partialTicks) {
		//? if >=1.20.5 {
		//~ if >1.21.11 'render' -> 'extract'
		if (this.minecraft != null && this.minecraft.level == null) this.extractPanorama(gui, partialTicks);
		//? } else {
		//if (this.minecraft != null && this.minecraft.level == null) this.renderDirtBackground(gui);
		//? }
		//? if =1.21 {
		//this.renderBlurredBackground(partialTicks);
		 //? } else if =1.21.5 {
		//this.renderBlurredBackground();
		//? } else if >=1.21.8 {
		//~ if >1.21.11 'render' -> 'extract'
		this.extractBlurredBackground(gui);
		 //? }
		InternalMalilibApi.setActiveTabFor(modId, this.tab);
		InternalMalilibApi.setScrollValueFor(modId, this.tab, getListWidget().getScrollbar().getValue());
		//~ if >1.21.11 'render' -> 'extractRenderState'
		super.extractRenderState(gui, mouseX, mouseY, partialTicks);
	}
	//? }


	//? if <=1.20.4 {
	/*@Override
	protected void drawScreenBackground(int mouseX, int mouseY) {
		if (this.minecraft != null && this.minecraft.level == null) {
			return;
		}
		super.drawScreenBackground(mouseX, mouseY);
	}
	*///? }


	//? if >=1.21 {
	// why was it using the class :sob: that's so brittle
	@Override
	protected void buildConfigSwitcher() {
		if (MaLiLibConfigs.Generic.ENABLE_CONFIG_SWITCHER.getBooleanValue()) {
			this.modSwitchWidget = new WidgetDropDownList<>(GuiUtils.getScaledWindowWidth() - 155, 6, 130, 18, 200, 10, fi.dy.masa.malilib.registry.Registry.CONFIG_SCREEN.getAllModsWithConfigScreens()) {
				{
					selectedEntry = InternalMalilibApi.getMod(modId).modInfo;
				}

				@Override
				protected void setSelectedEntry(int index) {
					super.setSelectedEntry(index);

					//? if >=1.21.11 {
					if (selectedEntry != null && selectedEntry.configScreenSupplier() != null) {
						GuiBase.openGui(selectedEntry.configScreenSupplier().get());
					}
					//? } else {
					/*if (selectedEntry != null && selectedEntry.getConfigScreenSupplier() != null) {
						fi.dy.masa.malilib.gui.GuiBase.openGui(selectedEntry.getConfigScreenSupplier().get());
					}
					*///? }
				}

				@Override
				protected String getDisplayString(ModInfo entry) {
					//? if >=1.21.11 {
					return entry.modName();
					 //? } else {
					//return entry.getModName();
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

