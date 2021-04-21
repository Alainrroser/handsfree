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

        while(true) {
            float[] position = Tobii.gazePosition();
            float xRelative = position[0];
            float yRelative = position[1];

            int x = (int) (xRelative * screenWidth);
            int y = (int) (yRelative * screenHeight);

            if(Tobii.isLeftEyePresent() && Tobii.isRightEyePresent()) {
                System.out.println("your eyes are both open");
                robot.mouseMove(x, y);
                if(pressed) {
                    pressed = false;
                }
            } else if(!Tobii.isLeftEyePresent() && !Tobii.isRightEyePresent()) {
                System.out.println("your eyes are both closed");
                if(!pressed) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    pressed = true;
                }
            } else if(!Tobii.isLeftEyePresent()) {
                System.out.println("your left eye is closed");
            } else if(!Tobii.isRightEyePresent()) {
                System.out.println("your right eye is closed");
            }

            Thread.sleep(10);
        }
    }
}
