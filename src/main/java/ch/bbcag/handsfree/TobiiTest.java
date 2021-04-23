package ch.bbcag.handsfree;

import tobii.Tobii;

import java.awt.*;
import java.awt.event.InputEvent;

public class TobiiTest {
    public void start() throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        Robot robot = new Robot();
        
        boolean isLeftButtonPressed = false;
        boolean isRightButtonPressed = false;
        boolean isRightClickTimerRunning = false;
        long startTime = System.currentTimeMillis();
        
        while(true) {
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
            
            if(isRightClickTimerRunning && System.currentTimeMillis() - startTime >= 150) {
                if(!Tobii.isRightEyePresent() && Tobii.isLeftEyePresent()) {
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                }
                
                isRightClickTimerRunning = false;
            }
            
            Thread.sleep(50);
        }
    }
}
