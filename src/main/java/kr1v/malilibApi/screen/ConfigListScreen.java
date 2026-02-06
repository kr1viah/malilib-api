package kr1v.malilibApi.screen;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.MaLiLibIcons;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.IConfigGui;
import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.StringUtils;
import kr1v.malilibApi.config._new.ConfigList;
import kr1v.malilibApi.util.ConfigUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// I hate *all* of this.
public class ConfigListScreen extends GuiConfigsBase {
	protected final ConfigList<?> config;
	protected final IConfigGui configGui;
	protected int dialogWidth;
	protected int dialogHeight;
	protected int dialogLeft;
	protected int dialogTop;

	private final Screen customParent;

	public ConfigListScreen(ConfigList<?> config, IConfigGui configGui, Screen parent) {
		super(0, 0, "", parent, "");

		this.config = config;
		this.configGui = configGui;
		this.title = config.getName();
		this.customParent = parent;
	}

	protected void setWidthAndHeight() {
		this.dialogWidth = 400;
		this.dialogHeight = GuiUtils.getScaledWindowHeight() - 90;
	}

	protected void centerOnScreen() {
		if (this.customParent != null) {
			this.dialogLeft = GuiUtils.getScaledWindowWidth() / 2 - this.dialogWidth / 2;
			this.dialogTop = GuiUtils.getScaledWindowHeight() / 2 - this.dialogHeight / 2;
		}
	}

	@Override
	public void initGui() {
		int scrollPos = getListWidget().getScrollbar().getValue();
		if (customParent != null && customParent instanceof GuiBase guiBase) {
			guiBase.initGui();
		}

		this.setWidthAndHeight();
		this.centerOnScreen();
		this.setListPosition(this.dialogLeft + 5, this.dialogTop + 20);
		this.reCreateListWidget();
		getListWidget().getScrollbar().setValue(scrollPos);

		super.initGui();
		if (config.getList().isEmpty()) {
			ButtonGeneric button = new ButtonGeneric(getListX() + 16, getListY() + 23, ButtonType.ADD.getIcon(), ButtonType.ADD.getDisplayName());
			ListenerListActions listener = new ListenerListActions(ButtonType.ADD, null);
			this.addButton(button, listener);
		}
	}

	public ConfigList<?> getConfig() {
		return this.config;
	}

	@Override
	protected int getBrowserWidth() {
		return this.dialogWidth - 14;
	}

	@Override
	protected int getBrowserHeight() {
		return this.dialogHeight - 30;
	}

	//? if >=1.21 {
	@Override
	protected void buildConfigSwitcher() {
	}
	//? }

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
		if (this.customParent != null) {
			this.customParent.render(drawContext, mouseX, mouseY, partialTicks);
		}

		super.render(drawContext, mouseX, mouseY, partialTicks);
	}

	//? if <1.21 {
	/*@Override
	protected void drawScreenBackground(int mouseX, int mouseY) {
		RenderUtils.drawOutlinedBox(this.dialogLeft, this.dialogTop, this.dialogWidth, this.dialogHeight, 0xFF000000, COLOR_HORIZONTAL_BAR);
		super.drawScreenBackground(mouseX, mouseY);
	}
	*///? }

	//? if >=1.21 {
	@Override
	protected void drawScreenBackground(/*? if >=1.21.11 {*//*fi.dy.masa.malilib.render.GuiContext*//*? } else {*/DrawContext/*? }*/ drawContext, int mouseX, int mouseY) {
		RenderUtils.drawOutlinedBox(/*? if >=1.21.8 {*//*drawContext, *//*? }*/this.dialogLeft, this.dialogTop, this.dialogWidth, this.dialogHeight, 0xFF000000, COLOR_HORIZONTAL_BAR);
	}
	//? }

	@Override
	protected void drawTitle(/*? if >=1.21.11 {*//*fi.dy.masa.malilib.render.GuiContext*//*? } else {*/DrawContext/*? }*/ drawContext, int mouseX, int mouseY, float partialTicks) {
		this.drawStringWithShadow(drawContext, this.title, this.dialogLeft + 10, this.dialogTop + 6, COLOR_WHITE);
	}

	@Override
	protected void closeGui(boolean showParent) {
		mc.setScreen(customParent);
	}

	@Override
	public List<ConfigOptionWrapper> getConfigs() {
		return new ArrayList<>(ConfigUtils.getConfigOptions(config.getList()));
	}

	@Override
	@NotNull
	protected WidgetListConfigOptions getListWidget() {
		return Objects.requireNonNull(super.getListWidget());
	}

	@Override
	public void close() {
		super.close();
	}

	// h

	@Override
	protected WidgetListConfigOptions createListWidget(int listX, int listY) {
		return new WidgetListConfigOptions(listX, listY, this.getBrowserWidth(), this.getBrowserHeight(), 204, 0.f, false, this) {
			@Override
			protected WidgetConfigOption createListEntryWidget(int x, int y, int listIndex, boolean isOdd, ConfigOptionWrapper wrapper) {
				return new WidgetConfigOption(x, y, this.browserEntryWidth, this.browserEntryHeight,
						this.maxLabelWidth, this.configWidth, wrapper, listIndex, this.parent, this) {
					{
						int by = y + 4;
						int bx = x + 258;
						int bOff = 18;
						this.addListActionButton(bx, by, ButtonType.ADD);
						bx += bOff;
						this.addListActionButton(bx, by, ButtonType.REMOVE);
						bx += bOff;
						if (this.canBeMoved(true)) {
							this.addListActionButton(bx, by, ButtonType.MOVE_DOWN);
						}

						bx += bOff;

						if (this.canBeMoved(false)) {
							this.addListActionButton(bx, by, ButtonType.MOVE_UP);
						}
					}

					private boolean canBeMoved(boolean down) {
						final int size = config.getList().size();
						return (this.listIndex >= 0 && this.listIndex < size) &&
								((down && this.listIndex < (size - 1)) || (!down && this.listIndex > 0));
					}

					private void addListActionButton(int x, int y, ButtonType type) {
						ButtonGeneric button = new ButtonGeneric(x, y, type.getIcon(), type.getDisplayName());
						ListenerListActions listener = new ListenerListActions(type, this);
						this.addButton(button, listener);
					}
				};
			}
		};
	}

	private class ListenerListActions implements IButtonActionListener {
		private final ButtonType type;
		private final WidgetConfigOption parent;

		public ListenerListActions(ButtonType type, WidgetConfigOption parent) {
			this.type = type;
			this.parent = parent;
		}

		@Override
		public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
			List<? extends IConfigBase> list = config.getList();
			final int size = list.size();
			if (this.type == ButtonType.ADD) {
				if (this.parent != null) {
					int index = parent.getListIndex() < 0 ? size : (Math.min(this.parent.getListIndex(), size));
					config.addNewAfter(index);
				} else {
					config.addNewAfter(0);
				}
			}
			else if (this.type == ButtonType.REMOVE) {
				int index = parent.getListIndex() < 0 ? size : (Math.min(this.parent.getListIndex(), size));
				config.getList().remove(index);
			} else {
				boolean down = this.type == ButtonType.MOVE_DOWN;

				if (parent.getListIndex() >= 0 && parent.getListIndex() < size) {
					int index1 = parent.getListIndex();
					int index2 = -1;

					if (down && parent.getListIndex() < (size - 1)) {
						index2 = index1 + 1;
					}
					else if (!down && parent.getListIndex() > 0) {
						index2 = index1 - 1;
					}

					if (index2 >= 0) {
						Collections.swap(list, index1, index2);
					}
				}
			}
			initGui();
		}
	}

	private enum ButtonType {
		ADD         (MaLiLibIcons.PLUS,         "malilib.gui.button.hovertext.add"),
		REMOVE      (MaLiLibIcons.MINUS,        "malilib.gui.button.hovertext.remove"),
		MOVE_UP     (MaLiLibIcons.ARROW_UP,     "malilib.gui.button.hovertext.move_up"),
		MOVE_DOWN   (MaLiLibIcons.ARROW_DOWN,   "malilib.gui.button.hovertext.move_down");

		private final MaLiLibIcons icon;
		private final String hoverTextkey;

		ButtonType(MaLiLibIcons icon, String hoverTextkey) {
			this.icon = icon;
			this.hoverTextkey = hoverTextkey;
		}

		public IGuiIcon getIcon() {
			return this.icon;
		}

		public String getDisplayName() {
			return StringUtils.translate(this.hoverTextkey);
		}
	}
}
