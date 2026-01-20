package kr1v.malilibApi.mixin.malilib;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.interfaces.IKeybindConfigGui;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOptionBase;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptionsBase;
import kr1v.malilibApi.config.ConfigButton;
import kr1v.malilibApi.config.ConfigList;
import kr1v.malilibApi.config.ConfigPair;
import kr1v.malilibApi.widget.ConfigButtonButton;
import kr1v.malilibApi.widget.ConfigListButton;
import kr1v.malilibApi.widget.WidgetPair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WidgetConfigOption.class)
public abstract class WidgetConfigOptionMixin extends WidgetConfigOptionBase<GuiConfigsBase.ConfigOptionWrapper> {
	public WidgetConfigOptionMixin(int x, int y, int width, int height, WidgetListConfigOptionsBase<?, ?> parent, GuiConfigsBase.ConfigOptionWrapper entry, int listIndex) {
		super(x, y, width, height, parent, entry, listIndex);
	}

	@Shadow
	protected abstract void addConfigButtonEntry(int xReset, int yReset, IConfigResettable config, ButtonBase optionButton);

	@Shadow
	@Final
	protected IKeybindConfigGui host;

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
		IConfigResettable resettable = null;
		if (config instanceof IConfigResettable) resettable = (IConfigResettable) config;

		if (config instanceof ConfigList<?> list) {
			this.addConfigButtonEntry(x + configWidth + 2, y, resettable, new ConfigListButton(x, y, configWidth, configHeight, list, list.toString()));
		} else if (config instanceof ConfigButton button) {
			this.addConfigButtonEntry(x + configWidth + 2, y, resettable, new ConfigButtonButton(x, y, configWidth, configHeight, button.displayName, button));
		} else if (config instanceof ConfigPair<?,?> pair) {
			if ((Object) this instanceof WidgetPair.WidgetConfigOptionPair pair1) {
				WidgetPair pair2 = pair1.getEnclosing();
				ButtonGeneric resetButton = pair2.resetButton;
				WidgetPair.MultipleReset listenerReset = pair2.multipleListenerReset;
				this.addWidget(new WidgetPair(x, y, width, height, pair, listIndex, configWidth, this.host, this.parent, resetButton, listenerReset));
			} else {
				ButtonGeneric resetButton = this.createResetButton(x + configWidth + 2, y, resettable);
				WidgetPair.MultipleReset listenerReset = new WidgetPair.MultipleReset(resettable, null, resetButton, null);
				this.addWidget(new WidgetPair(x, y, width, height, pair, listIndex, configWidth, this.host, this.parent, resetButton, listenerReset));
				this.addButton(resetButton, listenerReset);
			}
		}
		return original;
	}
}
