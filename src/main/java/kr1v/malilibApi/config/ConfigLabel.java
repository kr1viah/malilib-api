package kr1v.malilibApi.config;

import com.google.gson.JsonElement;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBase;
import org.jetbrains.annotations.Nullable;

public class ConfigLabel implements IConfigBase {
    private String label;

    public ConfigLabel(String label) {
        this.label = label;
    }

    @Override
    public ConfigType getType() {
        return null;
    }

    @Override
    public String getName() {
        return label;
    }

    @Override
    public @Nullable String getComment() {
        return label;
    }

    @Override
    public String getTranslatedName() {
        return label;
    }

    @Override
    public void setPrettyName(String s) {
    }

    @Override
    public void setTranslatedName(String s) {
    }

    @Override
    public void setComment(String s) {
        label = s;
    }

    @Override
    public void setValueFromJsonElement(JsonElement jsonElement) {
    }

    @Override
    public JsonElement getAsJsonElement() {
        return null;
    }

    //? if >=1.21.10 {
    /*@Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public void markClean() {

    }

    @Override
    public void checkIfClean() {

    }
    *///? }
}
