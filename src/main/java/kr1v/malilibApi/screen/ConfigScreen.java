package kr1v.malilibApi.screen;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.MaLiLibConfigs;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetDropDownList;
import fi.dy.masa.malilib.registry.Registry;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.annotation.PopupConfig;
import kr1v.malilibApi.config.ConfigLabel;
import kr1v.malilibApi.mixin.accessor.WidgetListConfigOptionsBaseAccessor;
import kr1v.malilibApi.util.AnnotationUtils;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ConfigScreen  extends GuiConfigsBase {
    public ConfigGuiTab tab = ConfigScreen.ConfigGuiTab.values(this.modId)[0];
    private final ModInfo modInfo;

    public ConfigScreen(String modId, String titleKey, ModInfo modInfo) {
        super(10, 50, modId, null, titleKey);
        this.modInfo = modInfo;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();
        this.configWidth = ((WidgetListConfigOptionsBaseAccessor) Objects.requireNonNull(getListWidget())).getMaxLabelWidth();
        this.configWidth = this.width - this.configWidth - 94;
        ((WidgetListConfigOptionsBaseAccessor) getListWidget()).setConfigWidth(this.configWidth);

        int x = 10;
        int y = 26;

        for (ConfigGuiTab tab : ConfigGuiTab.values(this.modId)) {
            if (!tab.isPopup)
                x += this.createButton(x, y, -1, tab);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private int createButton(int x, int y, int width, ConfigGuiTab tab) {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(this.tab != tab);
        final ConfigGuiTab tab2 = tab;

        this.addButton(button, (button1, mouseButton) -> {
            this.tab = tab2;
            reCreateListWidget(); // apply the new config width
            initGui();
        });

        return button.getWidth() + 2;
    }

    @Override
    protected void reCreateListWidget() {
        super.reCreateListWidget();
    }

    @Override
    protected void closeGui(boolean showParent) {
        super.closeGui(showParent);
    }

    @Override
    protected int getConfigWidth() {
        return this.width / 2;
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        ImmutableList.Builder<ConfigOptionWrapper> builder = ImmutableList.builder();
        for (IConfigBase config : this.tab.getOptions()) {
            if (config instanceof ConfigLabel)
                builder.add(new ConfigOptionWrapper(config.getComment()));
            else
                builder.add(new ConfigOptionWrapper(config));
        }
        return builder.build();
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        if (this.client != null && this.client.world == null) this.renderPanoramaBackground(drawContext, partialTicks);
        this.applyBlur();
        super.render(drawContext, mouseX, mouseY, partialTicks);
    }

    // why was it using the class :sob: that's so brittle
    @Override
    protected void buildConfigSwitcher() {
        if (MaLiLibConfigs.Generic.ENABLE_CONFIG_SWITCHER.getBooleanValue()) {
            this.modSwitchWidget = new WidgetDropDownList<>(GuiUtils.getScaledWindowWidth() - 155, 6, 130, 18, 200, 10, Registry.CONFIG_SCREEN.getAllModsWithConfigScreens()) {
                {
                    selectedEntry = modInfo;
                }

                @Override
                protected void setSelectedEntry(int index) {
                    super.setSelectedEntry(index);

                    if (selectedEntry != null && selectedEntry.getConfigScreenSupplier() != null) {
                        GuiBase.openGui(selectedEntry.getConfigScreenSupplier().get());
                    }
                }

                @Override
                protected String getDisplayString(ModInfo entry) {
                    return entry.getModName();
                }
            };

            addWidget(this.modSwitchWidget);
        }
    }

    public static class ConfigGuiTab {
        private final String translationKey;
        private final List<? extends IConfigBase> options;
        private static final Map<String, ConfigGuiTab[]> valueMap = new HashMap<>();
        public final boolean isPopup;

        ConfigGuiTab(String translationKey, List<? extends IConfigBase> options, boolean isPopup) {
            this.options = options;
            this.translationKey = translationKey;
            this.isPopup = isPopup;
        }

        public static ConfigGuiTab[] values(@NotNull String modId) {
            if (valueMap.get(modId) == null) {
                List<ConfigGuiTab> valuesList = new ArrayList<>();
                for (Class<?> clazz : AnnotationUtils.cacheFor(modId).keySet()) {
                    if (clazz.isAnnotationPresent(PopupConfig.class)) {
                        valuesList.add(new ConfigGuiTab(AnnotationUtils.nameForConfig(clazz), AnnotationUtils.cacheFor(modId).get(clazz), true));
                    } else {
                        valuesList.add(new ConfigGuiTab(AnnotationUtils.nameForConfig(clazz), AnnotationUtils.cacheFor(modId).get(clazz), false));
                    }
                }
                valueMap.put(modId, valuesList.toArray(new ConfigGuiTab[0]));
            }
            return valueMap.get(modId);
        }

        public List<? extends IConfigBase> getOptions() {
            return this.options;
        }

        public String getDisplayName() {
            return StringUtils.translate(this.translationKey);
        }
    }
}

