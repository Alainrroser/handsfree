package ch.bbcag.handsfree.control.shortcuts;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseAdapter;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.logging.LogManager;

public class ShortcutRecorder {

    private ShortcutManager shortcutManager;

    private Shortcut shortcut;
    private boolean running = false;
    private long startTime;

    private NativeMouseListener listener;

    public ShortcutRecorder(ShortcutManager shortcutManager) {
        this.shortcutManager = shortcutManager;
    }

    public void start() {
        if(!running) {
            running = true;
            startTime = System.currentTimeMillis();
            shortcut = new Shortcut();
            addNativeMouseListener();
        }
    }

    public void addClick(Click click) {
        if(running) {
            shortcut.getClicks().add(click);
        }
    }

    public void addNativeMouseListener() {
        LogManager.getLogManager().reset();
        try {
            registerNativeHook();
            addListener();
        } catch(NativeHookException e) {
            e.printStackTrace();
        }
    }

    private void registerNativeHook() throws NativeHookException {
        if(!GlobalScreen.isNativeHookRegistered()) {
            GlobalScreen.registerNativeHook();
        }
    }

    private void addListener() {
        listener = new NativeMouseAdapter() {
            @Override
            public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
                int button = setButton(nativeMouseEvent);
                int timeRelativeToStart = (int) (System.currentTimeMillis() - startTime);
                Point point = new Point(nativeMouseEvent.getX(), nativeMouseEvent.getY());
                addClick(new Click(button, timeRelativeToStart, point));
            }
        };

        GlobalScreen.addNativeMouseListener(listener);
    }

    private int setButton(NativeMouseEvent nativeMouseEvent) {
        int button;
        switch(nativeMouseEvent.getButton()) {
            case NativeMouseEvent.BUTTON1:
                button = InputEvent.BUTTON1_DOWN_MASK;
                break;
            case NativeMouseEvent.BUTTON2:
                button = InputEvent.BUTTON2_DOWN_MASK;
                break;
            case NativeMouseEvent.BUTTON3:
                button = InputEvent.BUTTON3_DOWN_MASK;
                break;
            default:
                button = 0;
                break;
        }
        return button;
    }

    public void stopAndDiscard() {
        if(running) {
            stop();
        }
    }

    public void stopAndSave(String name) {
        if(running) {
            shortcut.setName(name);
            shortcutManager.addShortcut(shortcut);
            stop();
        }
    }

    private void stop() {
        setRunning(false);
        shortcut.getClicks().remove(shortcut.getClicks().size() - 1);
        shortcut.getClicks().remove(shortcut.getClicks().size() - 1);

        GlobalScreen.removeNativeMouseListener(listener);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
