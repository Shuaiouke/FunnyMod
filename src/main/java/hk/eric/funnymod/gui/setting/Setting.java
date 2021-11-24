package hk.eric.funnymod.gui.setting;

import com.lukflug.panelstudio.base.IBoolean;
import com.lukflug.panelstudio.setting.ILabeled;
import com.lukflug.panelstudio.setting.ISetting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class Setting<T> implements ILabeled {
	public final String displayName,configName,description;
	public IBoolean visible;
	public final List<Setting<?>> subSettings= new ArrayList<>();
	private T value;
	private final Consumer<T> onChange;

	public Setting(String displayName, String configName, String description, IBoolean visible, T value) {
		this(displayName, configName, description, visible, value, null);
	}

	public Setting (String displayName, String configName, String description, IBoolean visible, T value, Consumer<T> onChange) {
		this.displayName=displayName;
		this.configName=configName;
		this.description=description;
		this.visible=visible;
		this.value=value;
		this.onChange=onChange;
	}

	public T getValue() {
		return value;
	}
	
	public void setValue (T value) {
		this.value=value;
		if(onChange!=null) onChange.accept(value);
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public IBoolean isVisible() {
		return visible;
	}
	
	public Stream<ISetting<?>> getSubSettings() {
		if (subSettings.size()==0) return null;
		return subSettings.stream().filter(setting->setting instanceof ISetting).sorted(Comparator.comparing(a -> a.displayName)).map(setting->(ISetting<?>)setting);
	}
}
