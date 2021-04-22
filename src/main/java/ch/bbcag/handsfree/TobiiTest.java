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
        
        boolean pressed = false;
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        
        while(true) {
            
            float[] position = Tobii.gazePosition();
            float xRelative = position[0];
            float yRelative = position[1];

            int x = (int) (xRelative * screenWidth);
            int y = (int) (yRelative * screenHeight);

            if(Tobii.isLeftEyePresent() && Tobii.isRightEyePresent()) { // Both eyes are open
                endTime = System.currentTimeMillis();
                robot.mouseMove(x, y);
                if(pressed) {
                    pressed = false;
                }
                if((endTime - startTime) >= 1000) {
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    pressed = true;
                }
            } else if(!Tobii.isLeftEyePresent() && !Tobii.isRightEyePresent()) { // Both eyes are closed
                startTime = System.currentTimeMillis();
                if(!pressed && ((endTime - startTime) <= 1000)) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    pressed = true;
                }
            }
            Thread.sleep(10);
        }
    }
    
}
