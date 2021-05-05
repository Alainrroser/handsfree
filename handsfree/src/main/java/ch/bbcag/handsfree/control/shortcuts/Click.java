package ch.bbcag.handsfree.control.shortcuts;

import java.awt.*;

public class Click {
    
    private int key;
    
    private Point position;
    
    public Click(int key, Point position) {
        this.key = key;
        this.position = position;
    }
    
    public int getKey() {
        return key;
    }
    
    public Point getPosition() {
        return position;
    }
}
