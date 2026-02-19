package kr1v.malilibApi.config._new;

import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigNotifiable;
import fi.dy.masa.malilib.config.IConfigResettable;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.StringUtils;
import kr1v.malilibApi.config.plus.Plus;
import org.jetbrains.annotations.Nullable;

public abstract class CustomConfigBase<T extends IConfigBase> implements IConfigBase, IConfigResettable, IConfigNotifiable<T>, Plus<T> {
	private final String name;
	private String comment;
	private String translatedName;
	private String prettyName;
	@Nullable
	private IValueChangeCallback<T> callback;

	public CustomConfigBase(String name, String comment, String translatedName, String prettyName) {
		this.name = name;
		this.comment = comment;
		this.translatedName = translatedName;
		this.prettyName = prettyName;
	}

	@Override
	public ConfigType getType() {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getComment() {
		return comment;
	}

	//? if >=1.21
	@Override
	public String getTranslatedName() {
		return translatedName;
	}

	@Override
	public String getPrettyName() {
		if (this.prettyName.isEmpty()) {
			return StringUtils.splitCamelCase(this.getName());
		} else {
			return StringUtils.translate(this.prettyName);
		}
	}

	//? if >=1.21
	@Override
	public void setPrettyName(String prettyName) {
		this.prettyName = prettyName;
	}

	//? if >=1.21
	@Override
	public void setTranslatedName(String translatedName) {
		this.translatedName = translatedName;
	}

	//? if >=1.21
	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onValueChanged() {
		if (this.callback != null) {
			this.callback.onValueChanged((T) this);
		}
	}

	@Override
	public void setValueChangeCallback(IValueChangeCallback<T> callback) {
		this.callback = callback;
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
