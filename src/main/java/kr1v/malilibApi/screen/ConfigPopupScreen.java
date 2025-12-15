package kr1v.malilibApi.screen;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import kr1v.malilibApi.MalilibApi;
import kr1v.malilibApi.annotation.PopupConfig;
import kr1v.malilibApi.config.ConfigLabel;
import kr1v.malilibApi.mixin.accessor.WidgetListConfigOptionsBaseAccessor;
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

    private final Class<?> configClass;
    private final String modId;

    private final Screen customParent;

    public ConfigPopupScreen(Class<?> configClass, Screen parent, String modId) {
        super(0, 0, "", parent, configClass.getAnnotation(PopupConfig.class).name().isEmpty() ? configClass.getSimpleName() : configClass.getAnnotation(PopupConfig.class).name());

        this.setParent(parent);
        this.customParent = parent;

        this.modId = modId;
        PopupConfig popupConfig = configClass.getAnnotation(PopupConfig.class);
        configDistanceFromTops = popupConfig.distanceFromTops();
        configDistanceFromSides = popupConfig.distanceFromSides();
        configWidth = popupConfig.width();
        configHeight = popupConfig.height();
        this.configClass = configClass;
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
                System.out.println(dialogWidth);
            }
            else {
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
    protected void buildConfigSwitcher() {}

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        ImmutableList.Builder<ConfigOptionWrapper> builder = ImmutableList.builder();
        for (IConfigBase config : MalilibApi.configListFor(modId, this.configClass)) {
            if (config instanceof ConfigLabel)
                builder.add(new ConfigOptionWrapper(config.getComment()));
            else
                builder.add(new ConfigOptionWrapper(config));
        }
        return builder.build();
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        if (this.customParent != null) {
            this.customParent.render(drawContext, mouseX, mouseY, partialTicks);
        }

        drawContext.getMatrices().translate(0f, 0f, 1000f);

        super.render(drawContext, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawScreenBackground(DrawContext drawContext, int mouseX, int mouseY) {
        RenderUtils.drawOutlinedBox(this.dialogLeft, this.dialogTop, this.dialogWidth, this.dialogHeight, 0xFF000000, COLOR_HORIZONTAL_BAR);
    }

    @Override
    protected void drawTitle(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        this.drawStringWithShadow(drawContext, this.title, this.dialogLeft + 10, this.dialogTop + 6, COLOR_WHITE);
    }

    @Override
    protected void closeGui(boolean showParent) {
        mc.setScreen(customParent);
    }
}
