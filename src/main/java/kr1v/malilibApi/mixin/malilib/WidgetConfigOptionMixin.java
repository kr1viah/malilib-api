package kr1v.malilibApi.mixin.malilib;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.gui.button.ConfigButtonBoolean;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import kr1v.malilibApi.config.ConfigButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WidgetConfigOption.class)
public class WidgetConfigOptionMixin {
    @Definition(id = "BOOLEAN", field = "Lfi/dy/masa/malilib/config/ConfigType;BOOLEAN:Lfi/dy/masa/malilib/config/ConfigType;")
    @Definition(id = "type", local = @Local(type = ConfigType.class, name = "type"))
    @Expression("type == BOOLEAN")
    @ModifyExpressionValue(method = "addConfigOption", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean injected(boolean original, @Local(name = "config") IConfigBase config) {
        return original || config instanceof ConfigButton;
    }

    @Definition(id = "ConfigButtonBoolean", type = ConfigButtonBoolean.class)
    @Expression("new ConfigButtonBoolean(?, ?, ?, ?, ?)")
    @WrapOperation(method = "addConfigOption", at = @At("MIXINEXTRAS:EXPRESSION"))
    private ConfigButtonBoolean injected2(int x, int y, int width, int height, IConfigBoolean config, Operation<ConfigButtonBoolean> original) {
        if (config instanceof ConfigButton<?> configButton) {
            return new ConfigButtonBoolean(x, y, width, height, config) {
                @Override
                protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
                    configButton.execute();
                    return true;
                }

                @Override
                public void updateDisplayString() {
                    this.displayString = configButton.displayName;
                }
            };
        }
        return original.call(x, y, width, height, config);
    }
}
