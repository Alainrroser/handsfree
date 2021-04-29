package ch.bbcag.handsfree.eyetracking;

import ch.bbcag.handsfree.scenes.MainMenu;
import tobii.Tobii;

import java.awt.*;
import java.awt.event.InputEvent;

public class EyeTracking {
    
    public void start(MainMenu mainMenu) {
        try {
            startTracking(mainMenu);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void startTracking(MainMenu mainMenu) throws Exception {
        Robot robot = new Robot();
        
        new Thread(() -> {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = screenSize.width;
            int screenHeight = screenSize.height;
            
            boolean isLeftButtonPressed = false;
            boolean isRightButtonPressed = false;
            boolean isRightClickTimerRunning = false;
            long startTime = System.currentTimeMillis();
            
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
                    if(!isLeftButtonPressed) {
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                
                        isLeftButtonPressed = true;
                        isRightClickTimerRunning = false;
                    }
                } else if(!Tobii.isRightEyePresent()) { // The right eye is closed
                    if(!isRightClickTimerRunning && !isRightButtonPressed) {
                        startTime = System.currentTimeMillis();
                        isRightClickTimerRunning = true;
                        isRightButtonPressed = true;
                    }
                }
        
                if(isRightClickTimerRunning && ((System.currentTimeMillis() - startTime) >= 150)) {
                    if(!Tobii.isRightEyePresent() && Tobii.isLeftEyePresent()) {
                        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    }
            
                    isRightClickTimerRunning = false;
                }
    
                try {
                    Thread.sleep(20);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
