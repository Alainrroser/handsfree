package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.control.HandsFreeRobot;

import java.util.ArrayList;
import java.util.List;

public class Shortcut {
    
    private List<Click> clicks = new ArrayList<>();
    
    private String name;
    
    private HandsFreeRobot robot;
    
    public Shortcut(HandsFreeRobot robot) {
        this.robot = robot;
    }
    
    public List<Click> getClicks() {
        return clicks;
    }
    
    public void setClicks(List<Click> clicks) {
        this.clicks = clicks;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void run() {
        for(Click click : clicks) {
            robot.mouseMove((int) click.getPosition().getX(), (int) click.getPosition().getY());
            robot.mouseClick(click.getButton());
        }
    }
}
