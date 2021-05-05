package ch.bbcag.handsfree;

import ch.bbcag.handsfree.jni.NativeUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class HandsFreeRobot {

    private Robot robot;

    public HandsFreeRobot() throws AWTException {
        robot = new Robot();

        try {
            NativeUtils.loadLibraryFromResource("/lib/robot/robot_jni.dll");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void keyPress(int keyCode) {
        robot.keyPress(keyCode);
    }

    public void keyRelease(int keyCode) {
        robot.keyRelease(keyCode);
    }

    public void keyPressSpecial(int keyCode) {
        // The gods have decided that alt graph cannot be simulated
        // using VK_ALT_GRAPH, you have to use VK_CONTROL + VK_ALT instead
        if(keyCode == KeyEvent.VK_ALT_GRAPH) {
            jniKeyPress((short) KeyEvent.VK_CONTROL);
            jniKeyPress((short) KeyEvent.VK_ALT);
        } else {
            jniKeyPress((short) keyCode);
        }
    }

    public void keyReleaseSpecial(int keyCode) {
        if(keyCode == KeyEvent.VK_ALT_GRAPH) {
            jniKeyRelease((short) KeyEvent.VK_CONTROL);
            jniKeyRelease((short) KeyEvent.VK_ALT);
        } else {
            jniKeyRelease((short) keyCode);
        }
    }

    public void keyType(int keyCode) {
        keyPress(keyCode);
        keyRelease(keyCode);
    }

    public void keyTypeSpecial(int keyCode) {
        keyPressSpecial(keyCode);
        keyReleaseSpecial(keyCode);
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

    private native void jniKeyPress(short keyCode);
    private native void jniKeyRelease(short keyCode);

}
