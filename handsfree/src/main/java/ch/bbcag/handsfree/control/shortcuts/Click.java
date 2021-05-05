package ch.bbcag.handsfree.control.shortcuts;

import java.awt.*;

public class Click {
    
    private int button;
    
    private Point position;
    
    public Click(int key, Point position) {
        this.button = key;
        this.position = position;
    }
    
    public int getButton() {
        return button;
    }
    
    public Point getPosition() {
        return position;
    }
}
