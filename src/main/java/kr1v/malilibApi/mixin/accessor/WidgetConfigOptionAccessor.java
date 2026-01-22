package kr1v.malilibApi.mixin.accessor;

import fi.dy.masa.malilib.gui.interfaces.IKeybindConfigGui;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WidgetConfigOption.class)
public interface WidgetConfigOptionAccessor {
	@Accessor
	IKeybindConfigGui getHost();
}
