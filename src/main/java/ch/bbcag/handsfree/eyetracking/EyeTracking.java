package ch.bbcag.handsfree.eyetracking;

import ch.bbcag.handsfree.HandsFreeRobot;
import ch.bbcag.handsfree.scenes.MainMenu;
import tobii.Tobii;

import java.awt.*;
import java.awt.event.InputEvent;

public class EyeTracking {
    
    private final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    private long startTime;
    
    private boolean isRightClickTimerRunning = false;
    private boolean isLeftButtonPressed = false;
    private boolean isRightButtonPressed = false;
    
    private HandsFreeRobot robot;
    
    private boolean running;

    public EyeTracking(HandsFreeRobot robot) {
        this.robot = robot;
    }

    public void start() {
        System.out.println("Starting eye tracking...");
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
                doLeftClick();
            } else if(!Tobii.isRightEyePresent()) { // The right eye is closed
                startRightClick();
            }
            stopRightClick();
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
    
    private void stopRightClick() {
        if(isRightClickTimerRunning && ((System.currentTimeMillis() - startTime) >= 150)) {
            doRightClick();
            isRightClickTimerRunning = false;
        }
    }
    
    private void doRightClick() {
        if(!Tobii.isRightEyePresent() && Tobii.isLeftEyePresent()) {
            robot.mouseClick(InputEvent.BUTTON3_DOWN_MASK);
        }
    }
    
    private void doLeftClick() {
        if(!isLeftButtonPressed) {
            robot.mouseClick(InputEvent.BUTTON1_DOWN_MASK);

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
