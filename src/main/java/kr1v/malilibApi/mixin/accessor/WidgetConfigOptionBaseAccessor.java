package kr1v.malilibApi.mixin.accessor;

import fi.dy.masa.malilib.gui.widgets.WidgetConfigOptionBase;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptionsBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WidgetConfigOptionBase.class)
public interface WidgetConfigOptionBaseAccessor {
	@Accessor
	WidgetListConfigOptionsBase<?, ?> getParent();
}
