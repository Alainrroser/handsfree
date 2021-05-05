package ch.bbcag.handsfree;

import java.awt.*;

public class HandsFreeRobot {

    private Robot robot;

    public HandsFreeRobot() throws AWTException {
        robot = new Robot();
    }

    public void keyPress(int keyCode) {
        robot.keyPress(keyCode);
    }

    public void keyRelease(int keyCode) {
        robot.keyRelease(keyCode);
    }

    public void keyType(int keyCode) {
        keyPress(keyCode);
        keyRelease(keyCode);
    }

    public void mouseMove(int x, int y) {
        robot.mouseMove(x, y);
    }

    public void mousePress(int buttons) {
        robot.mousePress(buttons);
    }

    public void mouseRelease(int buttons) {
        robot.mouseRelease(buttons);
    }

    public void mouseClick(int buttons) {
        mousePress(buttons);
        mouseRelease(buttons);
    }

    public void mouseWheel(int wheelAmount) {
        robot.mouseWheel(wheelAmount);
    }

}
