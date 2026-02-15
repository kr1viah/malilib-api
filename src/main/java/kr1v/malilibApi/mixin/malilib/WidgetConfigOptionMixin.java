package kr1v.malilibApi.mixin.malilib;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigResettable;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOptionBase;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptionsBase;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.interfaces.IButtonBasedResettableWidgetSupplier;
import kr1v.malilibApi.interfaces.IWidgetResettableSupplier;
import kr1v.malilibApi.interfaces.IWidgetSupplier;
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
		for (java.util.Map.Entry<Class<?>, IWidgetSupplier<?>> entry : InternalMalilibApi.customConfigMap.entrySet()) {
			Class<?> configClass = entry.getKey();
			IWidgetSupplier<?> widgetSupplier = entry.getValue();

			if (configClass.isInstance(config)){
				if (widgetSupplier instanceof IButtonBasedResettableWidgetSupplier) {
					//noinspection rawtypes
					IButtonBasedResettableWidgetSupplier buttonGiver = (IButtonBasedResettableWidgetSupplier) widgetSupplier;
					//noinspection unchecked
					ButtonBase button = buttonGiver.getButton((WidgetConfigOption) (Object) this, config, x, y, configWidth, configHeight);
					this.addConfigButtonEntry(x + configWidth + 2, y, (IConfigResettable) config, button);
				} else if (widgetSupplier instanceof IWidgetResettableSupplier) {
					//noinspection rawtypes
					IWidgetResettableSupplier widgetGiver = (IWidgetResettableSupplier) widgetSupplier;
					//noinspection unchecked
					WidgetBase[] widgetsToAdd = widgetGiver.getWidget((WidgetConfigOption) (Object) this, config, x, y, configWidth, configHeight);
					for (WidgetBase widget : widgetsToAdd) {
						this.addWidget(widget);
					}
				}
				return original;
			}
		}
		return original;
	}

	//? if =1.21.5 {
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/gui/widgets/WidgetConfigOption;drawSubWidgets(IILnet/minecraft/client/gui/DrawContext;)V"))
	private void preventRedraw(WidgetConfigOption instance, int mouseX, int mouseY, net.minecraft.client.gui.DrawContext drawContext, Operation<Void> original) {
	}
	//? } else if >=1.21.8 <= 1.21.10 {
	/*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/gui/widgets/WidgetConfigOption;drawSubWidgets(Lnet/minecraft/client/gui/DrawContext;II)V"))
	private void preventRedraw(WidgetConfigOption instance, net.minecraft.client.gui.DrawContext drawContext, int mouseX, int mouseY, Operation<Void> original) {
	}
	*///? } else if >=1.21.11 {
	/*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/gui/widgets/WidgetConfigOption;drawSubWidgets(Lfi/dy/masa/malilib/render/GuiContext;II)V"))
	private void preventRedraw(WidgetConfigOption instance, fi.dy.masa.malilib.render.GuiContext guiContext, int mouseX, int mouseY, Operation<Void> original) {

	}
	*///? }
}
