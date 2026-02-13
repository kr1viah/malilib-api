package kr1v.malilibApi.mixin.malilib;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ButtonGeneric.class)
public abstract class ButtonGenericMixin extends ButtonBase {
	public ButtonGenericMixin(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

//	 TODO this
//	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/render/RenderUtils;drawTexturedRect(IIIIII)V", ordinal = 0))
//	private void wrap(int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local MatrixStack matrixStack) {
//		drawContext.drawNineSlicedTexture(
//				ClickableWidget.WIDGETS_TEXTURE,
//				x, y, width*2, height,
//				20, 4, 200,
//				20, u, v);
//	}
	//? if <1.20.1 {
	//? } else if  <=1.20.1 {
	/*// the original rendering was _hyperjank_. I don't even know what to say
	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/render/RenderUtils;drawTexturedRect(IIIIII)V", ordinal = 0))
	private void wrap(int x, int y, int u, int v, int width, int height, Operation<Void> original, @Local(argsOnly = true) net.minecraft.client.gui.DrawContext drawContext) {
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
