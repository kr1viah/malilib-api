package kr1v.malilibApiTest.custo;

import com.google.gson.JsonElement;
import kr1v.malilibApi.config.CustomConfigBase;

public class ConfigClass extends CustomConfigBase<ConfigClass> {
    public ConfigClass(String name, String comment, String translatedName, String prettyName) {
        super(name, comment, translatedName, prettyName);
    }

    public ConfigClass(String name, String comment) {
        this(name, comment, name, name);
    }

    @Override
    public void setValueFromJsonElement(JsonElement jsonElement) {

    }

    @Override
    public JsonElement getAsJsonElement() {
        return null;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void resetToDefault() {

    }
}
