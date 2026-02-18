package kr1v.malilibApi.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import kr1v.malilibApi.InternalMalilibApi;
import kr1v.malilibApi.ModRepresentation;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference", "InvalidInjectorMethodSignature"})
@Debug(export = true)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	// handle before malilib, but after mod init
//	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/fabricmc/loader/impl/game/minecraft/Hooks;startClient(Ljava/io/File;Ljava/lang/Object;)V"))
//	private static void init(CallbackInfo ci) {
//		InternalMalilibApi.init();
//	}

//	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/fabricmc/loader/impl/game/minecraft/Hooks;startClient(Ljava/io/File;Ljava/lang/Object;)V"))
//	private static void startClient(File runDir, Object gameInstance, Operation<Void> original) {
//		original.call(runDir, gameInstance);
//		InternalMalilibApi.init();
//	}

	@Group(name = "init inject", min = 1, max = 1)
	@WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/fabricmc/loader/impl/game/minecraft/Hooks;startClient(Ljava/io/File;Ljava/lang/Object;)V"))
	private static void startClient1_14_4_dev(File runDir, Object gameInstance, Operation<Void> original) {
		original.call(runDir, gameInstance);
		InternalMalilibApi.init();
	}

	@Group(name = "init inject", min = 1, max = 1)
	@WrapOperation(method = "method_1503", at = @At(value = "INVOKE", target = "Lnet/fabricmc/loader/impl/game/minecraft/Hooks;startClient(Ljava/io/File;Ljava/lang/Object;)V"))
	private static void startClient_1_14_4_prod(File runDir, Object gameInstance, Operation<Void> original) {
		original.call(runDir, gameInstance);
		InternalMalilibApi.init();
	}

	@Group(name = "init inject", min = 1, max = 1)
	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/fabricmc/loader/impl/game/minecraft/Hooks;startClient(Ljava/io/File;Ljava/lang/Object;)V"))
	private static void startClient_rest(File runDir, Object gameInstance, Operation<Void> original) {
		original.call(runDir, gameInstance);
		InternalMalilibApi.init();
	}

	// save
	@Inject(method = "stop", at = @At(value = "HEAD"))
	private void onStopping(CallbackInfo ci) {
		for (ModRepresentation modRepresentation : InternalMalilibApi.getModConfigs()) {
			modRepresentation.configHandler.save();
		}
	}
}
