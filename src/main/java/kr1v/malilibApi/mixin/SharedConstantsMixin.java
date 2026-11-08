package kr1v.malilibApi.mixin;

import fi.dy.masa.malilib.MaLiLibReference;
import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SharedConstants.class)
public class SharedConstantsMixin {
	//? >=1.21.11 {
	@Shadow
	public static boolean IS_RUNNING_IN_IDE;

	@Inject(method = "<clinit>", at = @At("TAIL"), order = 10000)
	private static void preventRunningInIde(CallbackInfo ci) {
		if (IS_RUNNING_IN_IDE == MaLiLibReference.RUNNING_IN_IDE || MaLiLibReference.EXPERIMENTAL_MODE) { // might have some overlap but wtv
			IS_RUNNING_IN_IDE = false;
		}
	}
	//? }
}
