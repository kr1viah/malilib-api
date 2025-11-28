package kr1v.malilibApi.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import kr1v.malilibApi.MalilibApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    @Inject(method = "initRenderThread", at = @At("HEAD"))
    private static void initMods(CallbackInfo ci) {
        MalilibApi.init();
    }
}
