package kr1v.malilibApi;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.util.data.ModInfo;
import kr1v.malilibApi.interfaces.IConfigScreenSupplier;
import kr1v.malilibApi.util.AnnotationUtils;

import java.util.*;

public class ModRepresentation {
    public final Map<Class<?>, List<IConfigBase>> configs = new TreeMap<>(Comparator.comparing((Class<?> x) -> AnnotationUtils.nameForConfig(x) + x.getName()));
    public final List<Tab> tabs = new ArrayList<>();
    public final ModInfo modInfo;
    public final InputHandler inputHandler;
    public final ConfigHandler configHandler;
	public final IConfigScreenSupplier configScreenSupplier;
	public final String modId;

    public ModRepresentation(String modId, ModInfo modInfo, ConfigHandler configHandler, InputHandler inputHandler, IConfigScreenSupplier configScreenSupplier) {
        this.modInfo = modInfo;
        this.configHandler = configHandler;
        this.inputHandler = inputHandler;
		this.configScreenSupplier = configScreenSupplier;
		this.modId = modId;
	}

    public record Tab(String translationKey, List<IConfigBase> options, boolean isPopup, int order) {}
}
