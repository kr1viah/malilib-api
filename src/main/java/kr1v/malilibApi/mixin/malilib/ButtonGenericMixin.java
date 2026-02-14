package kr1v.malilibApi.mixin.malilib;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ButtonGeneric.class)
public abstract class ButtonGenericMixin extends ButtonBase {
	public ButtonGenericMixin(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	//? if <1.20.1 {
	/*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/render/RenderUtils;drawTexturedRect(IIIIII)V", ordinal = 0))
	private void renderMore(int x, int y, int u, int v, int width, int height, Operation<Void> original) {
		width = this.width;

		final int centerSrcU = u + 2;
		final int rightCapSrcU = u + 2 + 196;

		int remaining = width;
		int drawX = x;

		int leftDraw = Math.min(remaining, 2);
		if (leftDraw > 0) {
			original.call(drawX, y, u, v, leftDraw, height);
			drawX += leftDraw;
			remaining -= leftDraw;
		}

		while (remaining > 2) {
			int availableForCenter = remaining - 2;
			int segWidth = Math.min(availableForCenter, 196);

			original.call(drawX, y, centerSrcU, v, segWidth, height);

			drawX += segWidth;
			remaining -= segWidth;
		}

		if (remaining > 0) {
			int srcUForRightPartial = rightCapSrcU + (2 - remaining);
			original.call(drawX, y, srcUForRightPartial, v, remaining, height);
		}
	}
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/render/RenderUtils;drawTexturedRect(IIIIII)V", ordinal = 1))
	private void prevent(int x, int y, int u, int v, int width, int height, Operation<Void> original) {

	}
	*///? } else if =1.20.1 {
	/*// the original rendering was _hyperjank_. I don't even know what to say
	// EDIT: turns out on <1.19.4 vanilla also used "malilib"s way of rendering. what the fuck.
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/render/RenderUtils;drawTexturedRect(IIIIII)V", ordinal = 0))
	private void wrap(int x, int y, int u, int v, int width, int height, Operation<Void> original, @com.llamalad7.mixinextras.sugar.Local(argsOnly = true) net.minecraft.client.gui.DrawContext drawContext) {
		drawContext.drawNineSlicedTexture(
				net.minecraft.client.gui.widget.ClickableWidget.WIDGETS_TEXTURE,
				x, y, width*2, height,
				20, 4, 200,
				20, u, v);
	}
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/render/RenderUtils;drawTexturedRect(IIIIII)V", ordinal = 1))
	private void prevent(int x, int y, int u, int v, int width, int height, Operation<Void> original) {

	}
	*///? }
}
