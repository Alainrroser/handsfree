package ch.bbcag.handsfree;

import tobii.Tobii;

import java.awt.*;
import java.awt.event.InputEvent;

public class
TobiiTest {
    public void start() throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        Robot robot = new Robot();
        
        boolean isLeftButtonPressed = false;
        boolean isRightClickTimerRunning = false;
        long startTime = System.currentTimeMillis();
        
        while(true) {
            float[] position = Tobii.gazePosition();
            float xRelative = position[0];
            float yRelative = position[1];
            
            int x = (int) (xRelative * screenWidth);
            int y = (int) (yRelative * screenHeight);
            
            if(Tobii.isLeftEyePresent() && Tobii.isRightEyePresent()) { // Both eyes are open
                robot.mouseMove(x, y);
                if(isLeftButtonPressed) {
                    isLeftButtonPressed = false;
                }
            } else if(!Tobii.isLeftEyePresent() && !Tobii.isRightEyePresent()) { // Both eyes are closed
                isRightClickTimerRunning = false;
                if(!isLeftButtonPressed) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    System.out.println("Left Click");
                    isLeftButtonPressed = true;
                }
            } else if(!Tobii.isRightEyePresent()) { // The right eye is closed
                startTime = System.currentTimeMillis();
                isRightClickTimerRunning = true;
            }
            
            if(isRightClickTimerRunning && System.currentTimeMillis() - startTime >= 300) {
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                System.out.println("Right Click");
                isRightClickTimerRunning = false;
            }
        }
    }
}
