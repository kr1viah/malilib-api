package kr1v.malilibApi.mixin;
// TODO: make the config screen have a dirt background on versions where that is applicable when not in a world
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.ModRepresentation;
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

	// save
	@Inject(method = "stop", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;)V"))
	private void onStopping(CallbackInfo ci) {
		for (ModRepresentation modRepresentation : InternalMalilibApi.getModConfigs()) {
			modRepresentation.configHandler.save();
		}
	}
}
