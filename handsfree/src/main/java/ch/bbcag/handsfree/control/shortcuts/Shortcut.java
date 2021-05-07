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
        new Thread(() -> {
            int lastClickTime = 0;

            for(Click click : clicks) {
                robot.delay(click.getTime() - lastClickTime);
                robot.mouseMoveSmooth((int) click.getPosition().getX(), (int) click.getPosition().getY());
                robot.mouseClick(click.getButton());

                lastClickTime = click.getTime();
            }
        }).start();
    }
}
