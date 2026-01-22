package kr1v.malilibApi.widget;

import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.StringUtils;
import kr1v.malilibApi.config.ConfigList;
import kr1v.malilibApi.screen.ConfigListScreen;
import net.minecraft.client.MinecraftClient;

public class ConfigListButton extends ButtonGeneric {
	private final ConfigList<?> list;

	public ConfigListButton(int x, int y, int width, int height, ConfigList<?> list, String text) {
		super(x, y, width, height, text);
		this.list = list;
	}


	@Override
	protected boolean onMouseClickedImpl(/*? if >=1.21.10 {*//*net.minecraft.client.gui.Click click, boolean doubleClick*//*? } else {*/int mouseX, int mouseY, int mouseButton/*? }*/) {
		super.onMouseClickedImpl(/*? if >=1.21.10 {*//*click, doubleClick*//*? } else {*/mouseX, mouseY, mouseButton/*? }*/);
		MinecraftClient.getInstance().setScreen(new ConfigListScreen(list, null, MinecraftClient.getInstance().currentScreen));
		return true;
	}
	//? if <= 1.21.5 {
	@Override
	public void render(int mouseX, int mouseY, boolean selected, net.minecraft.client.gui.DrawContext drawContext) {
		setDisplay();
		super.render(mouseX, mouseY, selected, drawContext);
	}
	//? } else if <=1.21.10 {
	/*@Override
	public void render(net.minecraft.client.gui.DrawContext drawContext, int mouseX, int mouseY, boolean selected) {
		setDisplay();
		super.render(drawContext, mouseX, mouseY, selected);
	}
	*///? } else {
	/*@Override
	public void render(fi.dy.masa.malilib.render.GuiContext ctx, int mouseX, int mouseY, boolean selected) {
		setDisplay();
		super.render(ctx, mouseX, mouseY, selected);
	}
	*///? }

	private void setDisplay() {
		String str = list.toString();
		int len = 0;
		if (str.length() > 10000) {
			str = str.substring(0, 10000);
		}
		StringBuilder builder = new StringBuilder();
		for (String c : str.codePoints()
				.mapToObj(cp -> new String(Character.toChars(cp)))
				.toArray(String[]::new)) {
			len += StringUtils.getStringWidth(c);
			if (len > this.width - 10 - StringUtils.getStringWidth(" ...")) {
				builder.append(" ...");
				break;
			} else {
				builder.append(c);
			}
		}
		this.setDisplayString(builder.toString());
	}
}
