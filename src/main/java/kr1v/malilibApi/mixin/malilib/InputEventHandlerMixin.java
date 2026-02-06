package kr1v.malilibApi.mixin.malilib;

import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Collection;

@Mixin(InputEventHandler.class)
public class InputEventHandlerMixin {
	@WrapOperation(method = "checkKeyBindsForChanges", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;get(Ljava/lang/Object;)Ljava/util/Collection;"))
	private <V, K> Collection<V> wrap(Multimap<Integer, IKeybind> instance, K k, Operation<Collection<V>> original) {
		return new ArrayList<>(original.call(instance, k));
	}
}
