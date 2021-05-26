package ch.bbcag.handsfree.control.shortcuts;

import java.awt.*;

public class Click {

    private int button;
    private int time;

    private Point position;

    public Click(int key, int time, Point position) {
        this.button = key;
        this.time = time;
        this.position = position;
    }

    public int getButton() {
        return button;
    }

    public Point getPosition() {
        return position;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
