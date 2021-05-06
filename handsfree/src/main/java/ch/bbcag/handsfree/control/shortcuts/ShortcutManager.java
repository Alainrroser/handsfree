package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;

public class ShortcutManager {
    
    private Shortcut shortcut;
    private HandsFreeRobot robot;
    
    private boolean running;
    
    private long startTime;
    
    private List<Shortcut> shortcuts = new ArrayList<>();
    
    public ShortcutManager(HandsFreeRobot robot) {
        this.robot = robot;
    }
    
    public void start() {
        if(!running) {
            running = true;
            startTime = System.currentTimeMillis();
            shortcut = new Shortcut();
            shortcuts.add(shortcut);
            addNativeMouseListener();
        }
    }
    
    public void stopAndDiscard() {
        if(running) {
            setRunning(false);
        }
    }
    
    public void stopAndSave() {
        if(running) {
            setRunning(false);
            
            try {
                shortcut.getClicks().remove(shortcut.getClicks().size() - 1);
                shortcut.getClicks().remove(shortcut.getClicks().size() - 1);
                ShortcutWriter writer = new ShortcutWriter();
                writer.write(shortcut, new File(Const.SHORTCUT_PATH));
            } catch(IOException e) {
                Error.reportCritical(ErrorMessages.WRITE_SHORTCUT, e);
            }
        }
    }
    
    public void addClick(Click click) {
        if(running) {
            shortcut.getClicks().add(click);
        }
    }
    
    public void addNativeMouseListener() {
        LogManager.getLogManager().reset();
        try{
            if(!GlobalScreen.isNativeHookRegistered()) {
                GlobalScreen.registerNativeHook();
            }
            GlobalScreen.addNativeMouseListener(new NativeMouseListener() {
                @Override
                public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
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
                    addClick(new Click(button, calcTime(System.currentTimeMillis()), new Point(nativeMouseEvent.getX(), nativeMouseEvent.getY())));
                }
                
                @Override
                public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
                
                }
                
                @Override
                public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
                
                }
            });
        } catch(NativeHookException e) {
            e.printStackTrace();
        }
    }
    
    public List<Shortcut> getShortcuts() {
        return shortcuts;
    }
    
    public Shortcut getShortcut() {
        return shortcut;
    }
    
    public void runShortcut(String name) {
        for(Shortcut shortcut : shortcuts) {
            if(shortcut.getName().equals(name)) {
                shortcut.run(robot);
            }
        }
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public int calcTime(long time) {
        return (int) (time - startTime);
    }
}
