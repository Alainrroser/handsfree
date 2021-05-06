package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.control.HandsFreeRobot;

import java.util.ArrayList;
import java.util.List;

public class Shortcut {
    
    private List<Click> clicks = new ArrayList<>();
    
    private String name;

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
    
    public void run(HandsFreeRobot robot) {
        for(Click click : clicks) {
            robot.mouseMove((int) click.getPosition().getX(), (int) click.getPosition().getY());
            robot.delay(click.getTime());
            robot.mouseClick(click.getButton());
        }
    }
}
