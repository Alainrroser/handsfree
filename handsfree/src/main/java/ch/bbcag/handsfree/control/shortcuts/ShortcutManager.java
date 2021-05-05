package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.error.Error;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShortcutManager {
    
    private Shortcut shortcut;
    private HandsFreeRobot robot;
    
    private boolean running;
    private boolean startedBefore = false;
    
    private List<Click> clicks = new ArrayList<>();
    private List<Shortcut> shortcuts = new ArrayList<>();
    
    public ShortcutManager(HandsFreeRobot robot) {
        this.robot = robot;
    }
    
    public void start() {
        running = true;
        startedBefore = true;
        shortcut = new Shortcut();
        shortcuts.add(shortcut);
        
        startRecording();
    }
    
    public void stop() {
        this.running = false;
        
        if(startedBefore) {
            try {
                ShortcutWriter writer = new ShortcutWriter();
                writer.write(shortcut, new File("shortcuts/"));
            } catch(IOException e) {
                Error.reportAndExit("Shortcuts", "The file or a directory couldn't have been created", e);
            }
        }
    }
    
    public void addClick(Click click) {
        if(running) {
            clicks.add(click);
        }
    }
    
    public List<Shortcut> getShortcuts() {
        return shortcuts;
    }
    
    public Shortcut getShortcut() {
        return shortcut;
    }
    
    public boolean hasBeenStartedBefore() {
        return startedBefore;
    }
    
    public void runShortcut(String name) {
        for(Shortcut shortcut : shortcuts) {
            if(shortcut.getName().equals(name)) {
                shortcut.run(robot);
            }
        }
    }
    
    private void startRecording() {
        shortcut.setClicks(clicks);
    }
}
