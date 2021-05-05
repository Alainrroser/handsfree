package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.error.Error;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShortcutManager {
    
    private Shortcut shortcut;
    
    private boolean running;
    private boolean startedBefore = false;
    
    private List<Click> clicks = new ArrayList<>();
    private List<Shortcut> shortcuts = new ArrayList<>();
    
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
            String fileName = shortcut.getName() + ".txt";
            String path = "shortcuts/" + fileName;
            try {
                File file = new File(path);
                file.getParentFile().mkdirs();
                if(!file.exists()) {
                    file.createNewFile();
                }
                FileWriter writer = new FileWriter(file);
                for(Click click : clicks) {
                    writer.write(click.getPosition().getX() + ";" + click.getPosition().getY() + ";" + click.getKey() + "\n");
                }
                writer.close();
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
    
    private void startRecording() {
        shortcut.setClicks(clicks);
    }
}
