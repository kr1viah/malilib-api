package kr1v.malilibApi.mixin;

import kr1v.malilibApi.InternalMalilibApi;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    // handle before malilib, but after mod init
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;"))
    private static void init(CallbackInfo ci) {
        InternalMalilibApi.init();
    }
}
