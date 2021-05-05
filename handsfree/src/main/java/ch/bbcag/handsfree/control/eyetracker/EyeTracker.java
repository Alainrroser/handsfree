package ch.bbcag.handsfree.control.eyetracker;

import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.shortcuts.Click;
import ch.bbcag.handsfree.control.shortcuts.ShortcutManager;
import tobii.Tobii;

import java.awt.*;
import java.awt.event.InputEvent;

public class EyeTracker {
    
    private final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    private long startTime;
    
    private boolean isRightClickTimerRunning = false;
    private boolean isLeftButtonPressed = false;
    private boolean isRightButtonPressed = false;
    
    private HandsFreeRobot robot;
    private ShortcutManager shortcutManager;
    
    private boolean running;
    
    public EyeTracker(HandsFreeRobot robot, ShortcutManager shortcutManager) {
        this.robot = robot;
        this.shortcutManager = shortcutManager;
    }

    public void start() {
        this.running = true;

        startTracking();
    }

    public void stop() {
        this.running = false;
    }
    
    private void startTracking() {
        new Thread(() -> {
            startTime = System.currentTimeMillis();
            doTracking();
        }).start();
    }
    
    private void doTracking() {
        while(running) {
            float[] position = Tobii.getGazePosition();
            float xRelative = position[0];
            float yRelative = position[1];
        
            int x = (int) (xRelative * screenWidth);
            int y = (int) (yRelative * screenHeight);
        
            if(Tobii.isLeftEyePresent() && Tobii.isRightEyePresent()) { // Both eyes are open
                robot.mouseMove(x, y);
                
                isLeftButtonPressed = false;
                isRightButtonPressed = false;
            } else if(!Tobii.isLeftEyePresent() && !Tobii.isRightEyePresent()) { // Both eyes are closed
                doLeftClick(x, y);
            } else if(!Tobii.isRightEyePresent()) { // The right eye is closed
                startRightClick();
            }
            stopRightClick(x, y);
            sleepThread();
        }
    }
    
    private void startRightClick() {
        if(!isRightClickTimerRunning && !isRightButtonPressed) {
            startTime = System.currentTimeMillis();
            isRightClickTimerRunning = true;
            isRightButtonPressed = true;
        }
    }
    
    private void stopRightClick(int x, int y) {
        if(isRightClickTimerRunning && ((System.currentTimeMillis() - startTime) >= 150)) {
            doRightClick(x, y);
            isRightClickTimerRunning = false;
        }
    }
    
    private void doRightClick(int x, int y) {
        if(!Tobii.isRightEyePresent() && Tobii.isLeftEyePresent()) {
            robot.mouseClick(InputEvent.BUTTON3_DOWN_MASK);
            
            shortcutManager.addClick(new Click(3, new Point(x, y)));
        }
    }
    
    private void doLeftClick(int x, int y) {
        if(!isLeftButtonPressed) {
            robot.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
            
            shortcutManager.addClick(new Click(1, new Point(x, y)));
            
            isLeftButtonPressed = true;
            isRightClickTimerRunning = false;
        }
    }
    
    private void sleepThread() {
        try {
            Thread.sleep(20);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
