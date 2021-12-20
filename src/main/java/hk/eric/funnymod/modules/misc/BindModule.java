package hk.eric.funnymod.modules.misc;

import com.lukflug.panelstudio.base.IToggleable;
import hk.eric.funnymod.event.EventHandler;
import hk.eric.funnymod.event.EventManager;
import hk.eric.funnymod.event.events.KeyEvent;
import hk.eric.funnymod.event.events.UseItemEvent;
import hk.eric.funnymod.gui.Gui;
import hk.eric.funnymod.gui.setting.KeybindSetting;
import hk.eric.funnymod.modules.Category;
import hk.eric.funnymod.modules.Module;
import org.lwjgl.glfw.GLFW;

public class BindModule extends Module{

    private static BindModule instance;
    private static final EventHandler<KeyEvent> keybindHandler = new EventHandler<>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getAction() != GLFW.GLFW_PRESS) return;
            if (mc.screen != null) return;
            if (getPlayer() == null) return;
            if (keyEvent.getKey() == GLFW.GLFW_KEY_Z) {
                new UseItemEvent(getPlayer().getMainHandItem()).call();
            }
            if (!Gui.getGUI().getGUI().getGUIVisibility().isOn()) {
                for (Category value : Category.values()) {
                    value.modules.forEach(module -> module.settings.forEach(setting -> {
                        if (setting instanceof KeybindSetting keybindSetting) {
                            if (keybindSetting.getKey() == keyEvent.getKey()) {
                                keybindSetting.getAction().run();
                            }
                        }
                    }));
                }
            }
        }
    };

    public BindModule() {
        super("Bind", "Triggers keybinds.");
        instance = this;
        EventManager.getInstance().register(keybindHandler);
    }

    public static IToggleable getToggle() {
        return instance.isEnabled();
    }
}