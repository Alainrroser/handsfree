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
        int buttonPressed = 0;
        
        while(true) {
            float[] position = Tobii.gazePosition();
            float xRelative = position[0];
            float yRelative = position[1];

            int x = (int) (xRelative * screenWidth);
            int y = (int) (yRelative * screenHeight);

            if(Tobii.isLeftEyePresent() && Tobii.isRightEyePresent()) { // Both eyes are open
                robot.mouseMove(x, y);
                if(pressed) {
                    if(buttonPressed == 1) {
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                    if(buttonPressed == 3) {
                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    }
                    pressed = false;
                }
            } else if(!Tobii.isLeftEyePresent() && !Tobii.isRightEyePresent()) { // Both eyes are closed
                if(!pressed) {
//                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//                    pressed = true;
                }
            } else if(!Tobii.isLeftEyePresent()) { // The left eye is closed
                if(!pressed) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    pressed = true;
                    buttonPressed = 1;
                }
            } else if(!Tobii.isRightEyePresent()) { // The right eye is closed
                if(!pressed) {
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    pressed = true;
                    buttonPressed = 3;
                }
            }
        }
    }
}
