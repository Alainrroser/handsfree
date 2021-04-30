package ch.bbcag.handsfree.eyetracking;

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
    
    private Robot robot;
    
    private MainMenu mainMenu;
    
    public void start(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        
        try {
            startTracking();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void startTracking() throws Exception {
        robot = new Robot();
        
        new Thread(() -> {
            startTime = System.currentTimeMillis();
            doTracking();
        }).start();
    }
    
    private void doTracking() {
        while(mainMenu.isEyeTrackingEnabled()) {
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
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        }
    }
    
    private void doLeftClick() {
        if(!isLeftButtonPressed) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
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
