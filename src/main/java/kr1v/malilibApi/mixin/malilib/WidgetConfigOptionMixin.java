package kr1v.malilibApi.mixin.malilib;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import fi.dy.masa.malilib.util.StringUtils;
import kr1v.malilibApi.config.ConfigButton;
import kr1v.malilibApi.config.ConfigList;
import kr1v.malilibApi.screen.ConfigListScreen;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WidgetConfigOption.class)
public abstract class WidgetConfigOptionMixin {
	@Shadow
	protected abstract void addConfigButtonEntry(int xReset, int yReset, IConfigResettable config, ButtonBase optionButton);

	@Definition(id = "BOOLEAN", field = "Lfi/dy/masa/malilib/config/ConfigType;BOOLEAN:Lfi/dy/masa/malilib/config/ConfigType;")
	@Definition(id = "type", local = @Local(type = ConfigType.class, name = "type"))
	@Expression("type == BOOLEAN")
	@ModifyExpressionValue(method = "addConfigOption", at = @At("MIXINEXTRAS:EXPRESSION"))
	private boolean injected(boolean original,
							 @Local(name = "config") IConfigBase config,
							 @Local(name = "x") int x,
							 @Local(name = "y") int y,
							 @Local(name = "configWidth") int configWidth,
							 @Local(name = "configHeight") int configHeight) {
		if (config instanceof ConfigList<?> list) {
			var button = new ButtonGeneric(x, y, configWidth, configHeight, list.toString()) {
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
			};
			this.addConfigButtonEntry(x + configWidth + 2, y, (IConfigResettable) config, button);
		} else if (config instanceof ConfigButton configButton) {
			var button = new ButtonGeneric(x, y, configWidth, configHeight, configButton.displayName) {
				@Override
				protected boolean onMouseClickedImpl(/*? if >=1.21.10 {*//*net.minecraft.client.gui.Click click, boolean doubleClick*//*? } else {*/int mouseX, int mouseY, int mouseButton/*? }*/) {
					super.onMouseClickedImpl(/*? if >=1.21.10 {*//*click, doubleClick*//*? } else {*/mouseX, mouseY, mouseButton/*? }*/);
					configButton.execute();
					return true;
				}

				@Override
				public void updateDisplayString() {
					this.displayString = configButton.displayName;
				}
			};
			this.addConfigButtonEntry(x + configWidth + 2, y, (IConfigResettable) config, button);
		}
		return original;
	}
}
