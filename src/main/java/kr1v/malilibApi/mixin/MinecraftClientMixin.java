package kr1v.malilibApi.mixin;

import kr1v.malilibApi.MalilibApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    // handle before malilib
    @Inject(method = "<init>", at = @At("RETURN"), order = 500)
    private void init(RunArgs args, CallbackInfo ci) {
        MalilibApi.init();
    }
}
