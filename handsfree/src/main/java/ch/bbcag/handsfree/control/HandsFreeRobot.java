package ch.bbcag.handsfree.control;

import ch.bbcag.handsfree.error.HandsFreeRobotException;
import ch.bbcag.handsfree.error.NativeException;
import ch.bbcag.handsfree.utils.NativeLibraryLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HandsFreeRobot {

    private Robot robot;

    private List<Integer> pressedKeys = new CopyOnWriteArrayList<>();
    private List<Integer> pressedMouseButtons = new CopyOnWriteArrayList<>();

    public HandsFreeRobot() throws NativeException {
        try {
            robot = new Robot();
        } catch(AWTException e) {
            throw new HandsFreeRobotException(e);
        }

        NativeLibraryLoader.loadLibraryFromResource("/lib/robot/robot_jni.dll");
    }

    public void keyPressSpecial(int keyCode) {
        // The gods have decided that alt graph cannot be simulated
        // using VK_ALT_GRAPH, you have to use VK_CONTROL + VK_ALT instead
        if(keyCode == KeyEvent.VK_ALT_GRAPH) {
            keyPressInternal(KeyEvent.VK_CONTROL);
            keyPressInternal(KeyEvent.VK_ALT);
        } else {
            keyPressInternal(keyCode);
        }
    }

    public void keyReleaseSpecial(int keyCode) {
        if(keyCode == KeyEvent.VK_ALT_GRAPH) {
            keyReleaseInternal(KeyEvent.VK_CONTROL);
            keyReleaseInternal(KeyEvent.VK_ALT);
        } else {
            keyReleaseInternal(keyCode);
        }
    }

    public void keyTypeSpecial(int keyCode) {
        keyPressSpecial(keyCode);
        keyReleaseSpecial(keyCode);
    }

    public void pressHotkey(int... keys) {
        for(int key : keys) {
            keyPressSpecial(key);
        }
        for(int key : keys) {
            keyReleaseSpecial(key);
        }
    }

    private void keyPressInternal(int keyCode) {
        jniKeyPress((short) keyCode);
        pressedKeys.add(keyCode);
    }

    private void keyReleaseInternal(int keyCode) {
        jniKeyRelease((short) keyCode);
        pressedKeys.remove((Integer) keyCode);
    }

    public void mouseMove(int x, int y) {
        robot.mouseMove(x, y);
    }

    public void mouseMoveSmooth(int x, int y) {
        int sx = MouseInfo.getPointerInfo().getLocation().x;
        int sy = MouseInfo.getPointerInfo().getLocation().y;

        int steps = 100;

        for(int i = 0; i < steps; i++) {
            double progress = (double) i / (double) steps;
            double cx = sx + (x - sx) * progress;
            double cy = sy + (y - sy) * progress;
            mouseMove((int) cx, (int) cy);
            robot.delay(5);
        }
    }

    public void mousePress(int button) {
        robot.mousePress(button);
        pressedMouseButtons.add(button);
    }

    public void mouseRelease(int button) {
        robot.mouseRelease(button);
        pressedMouseButtons.remove((Integer) button);
    }

    public void mouseClick(int buttons) {
        mousePress(buttons);
        mouseRelease(buttons);
    }

    public void mouseWheel(int wheelAmount) {
        robot.mouseWheel(wheelAmount);
    }

    public void scrollContinuously(int wheelAmount) {
        for(int i = 0; i < Math.abs(wheelAmount); i++) {
            mouseWheel((int) Math.signum(wheelAmount));
            delay(40);
        }
    }

    public void delay(int millis) {
        robot.delay(millis);
    }

    public void exit() {
        for(int pressedKey : pressedKeys) {
            keyReleaseInternal(pressedKey);
        }

        for(int pressedButton : pressedMouseButtons) {
            mouseRelease(pressedButton);
        }
    }

    private native void jniKeyPress(short keyCode);
    private native void jniKeyRelease(short keyCode);

}
