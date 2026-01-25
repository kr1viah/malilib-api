package kr1v.malilibApi.screen;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import fi.dy.masa.malilib.util.GuiUtils;
import kr1v.malilibApi.annotation.PopupConfig;
import kr1v.malilibApi.config._new.ConfigObject;
import kr1v.malilibApi.mixin.accessor.WidgetListConfigOptionsBaseAccessor;
import kr1v.malilibApi.util.ConfigUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

import java.util.List;

public class ConfigPopupScreen extends GuiConfigsBase {
	protected int dialogWidth;
	protected int dialogHeight;
	protected int dialogLeft;
	protected int dialogTop;

	private final int configWidth;
	private final int configHeight;
	private final int configDistanceFromSides;
	private final int configDistanceFromTops;

	private final List<IConfigBase> configs;

	private final Screen customParent;

	public ConfigPopupScreen(ConfigObject<?> configObject, Screen parent) {
		super(0, 0, "", parent, "", configObject.getName());

		this.setParent(parent);
		this.customParent = parent;
		configDistanceFromTops = -1;
		configDistanceFromSides = -1;
		configWidth = -1;
		configHeight = 300;

		this.configs = configObject.configs;
	}

	public ConfigPopupScreen(Class<?> configClass, Screen parent, String modId) {
		super(0, 0, "", parent, configClass.getAnnotation(PopupConfig.class).name().isEmpty() ? configClass.getSimpleName() : configClass.getAnnotation(PopupConfig.class).name());

		this.setParent(parent);
		this.customParent = parent;

		PopupConfig popupConfig = configClass.getAnnotation(PopupConfig.class);
		configDistanceFromTops = popupConfig.distanceFromTops();
		configDistanceFromSides = popupConfig.distanceFromSides();
		configWidth = popupConfig.width();
		configHeight = popupConfig.height();

		this.configs = ConfigUtils.generateOptions(configClass, modId);
	}

	protected void centerOnScreen() {
		if (this.customParent != null) {
			this.dialogLeft = GuiUtils.getScaledWindowWidth() / 2 - this.dialogWidth / 2;
			this.dialogTop = GuiUtils.getScaledWindowHeight() / 2 - this.dialogHeight / 2;
		}
	}

	protected void setWidthAndHeight() {
		if (configDistanceFromSides != -1) {
			this.dialogWidth = GuiUtils.getScaledWindowWidth() - configDistanceFromSides;
		} else {
			if (configWidth == -1) {
				WidgetListConfigOptions listWidget = getListWidget();
				assert listWidget != null;
				this.dialogWidth = ((WidgetListConfigOptionsBaseAccessor) listWidget).getMaxLabelWidth();
				this.dialogWidth += 204 + 85;
				if (this.dialogWidth < 400) this.dialogWidth = 400;
			} else {
				this.dialogWidth = configWidth;
			}
		}
		if (configDistanceFromTops != -1) {
			this.dialogHeight = GuiUtils.getScaledWindowHeight() - configDistanceFromTops;
		} else {
			this.dialogHeight = configHeight;
		}
	}

	@Override
	public void initGui() {
		if (customParent != null && customParent instanceof GuiBase guiBase) {
			guiBase.initGui();
		}
		super.initGui();
		this.setWidthAndHeight();
		this.centerOnScreen();
		this.setListPosition(this.dialogLeft + 5, this.dialogTop + 20);
		this.reCreateListWidget();
		super.initGui();

	}

	@Override
	protected int getBrowserWidth() {
		return this.dialogWidth - 14;
	}

	@Override
	protected int getBrowserHeight() {
		return this.dialogHeight - 30;
	}

	@Override
	protected WidgetListConfigOptions createListWidget(int listX, int listY) {
		return new WidgetListConfigOptions(listX, listY,
				this.getBrowserWidth(), this.getBrowserHeight(), 204, 0.f, false, this);
	}

	@Override
	protected void buildConfigSwitcher() {
	}

	@Override
	public List<ConfigOptionWrapper> getConfigs() {
		return ConfigUtils.getConfigOptions(configs);
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
		if (this.customParent != null) {
			this.customParent.render(drawContext, mouseX, mouseY, partialTicks);
		}

		//? if <=1.21.5
		drawContext.getMatrices().translate(0f, 0f, 1000f); // this got changed in 25w15a

		super.render(drawContext, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawScreenBackground(/*? if >=1.21.11 {*//*fi.dy.masa.malilib.render.GuiContext*//*? } else {*/DrawContext/*? }*/ drawContext, int mouseX, int mouseY) {
		drawContext.fill(this.dialogLeft, this.dialogTop, this.dialogLeft + this.dialogWidth, this.dialogTop + this.dialogHeight, COLOR_HORIZONTAL_BAR);
		drawContext.fill(this.dialogLeft + 1, this.dialogTop + 1, this.dialogLeft + this.dialogWidth - 1, this.dialogTop + this.dialogHeight - 1, 0xFF000000);
	}

	@Override
	protected void drawTitle(/*? if >=1.21.11 {*//*fi.dy.masa.malilib.render.GuiContext*//*? } else {*/DrawContext/*? }*/ drawContext, int mouseX, int mouseY, float partialTicks) {
		this.drawStringWithShadow(drawContext, this.title, this.dialogLeft + 10, this.dialogTop + 6, COLOR_WHITE);
	}

	@Override
	protected void closeGui(boolean showParent) {
		mc.setScreen(customParent);
	}
}
