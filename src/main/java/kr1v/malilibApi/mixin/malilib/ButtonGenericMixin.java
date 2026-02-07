package kr1v.malilibApi.mixin.malilib;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ButtonGeneric.class)
public class ButtonGenericMixin {
	//? if  <=1.20.1 {
	/*// the original rendering was _hyperjank_. I don't even know what to say
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/render/RenderUtils;drawTexturedRect(IIIIII)V", ordinal = 0))
	private void wrap(int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(argsOnly = true) DrawContext drawContext) {
		drawContext.drawNineSlicedTexture(
				ClickableWidget.WIDGETS_TEXTURE,
				x, y, width*2, height,
				20, 4, 200,
				20, u, v);
	}
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/render/RenderUtils;drawTexturedRect(IIIIII)V", ordinal = 1))
	private void prevent(int x, int y, int u, int v, int width, int height, Operation<Void> original) {

	}
	*///? }
}
