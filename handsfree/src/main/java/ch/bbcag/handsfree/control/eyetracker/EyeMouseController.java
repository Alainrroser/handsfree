package ch.bbcag.handsfree.control.eyetracker;

import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.shortcuts.Click;
import ch.bbcag.handsfree.control.shortcuts.ShortcutManager;
import tobii.Tobii;

import java.awt.*;
import java.awt.event.InputEvent;

public class EyeMouseController {

    private long startTime;

    private boolean isRightClickTimerRunning = false;
    private boolean isLeftButtonPressed = false;
    private boolean isRightButtonPressed = false;

    private HandsFreeRobot robot;
    private ShortcutManager shortcutManager;

    private EyeTracker eyeTracker;

    public EyeMouseController(HandsFreeRobot robot, ShortcutManager shortcutManager) {
        this.robot = robot;
        this.shortcutManager = shortcutManager;

        eyeTracker = new EyeTracker();
        eyeTracker.addGazeHandler(this::controlCursor);
    }

    public void start() {
        eyeTracker.start();
    }

    public void stop() {
        eyeTracker.stop();
    }

    private void controlCursor(int x, int y) {
        if(Tobii.isLeftEyePresent() && Tobii.isRightEyePresent()) { // Both eyes are open
            robot.mouseMove(x, y);

            isLeftButtonPressed = false;
            isRightButtonPressed = false;
        } else if(!Tobii.isLeftEyePresent() && !Tobii.isRightEyePresent()) { // Both eyes are closed
            doLeftClick(x, y);
        } else if(!Tobii.isRightEyePresent()) { // The right eye is closed
            startRightClick();
        }

        stopRightClick(x, y);
    }

    private void startRightClick() {
        if(!isRightClickTimerRunning && !isRightButtonPressed) {
            startTime = System.currentTimeMillis();
            isRightClickTimerRunning = true;
            isRightButtonPressed = true;
        }
    }

    private void stopRightClick(int x, int y) {
        if(isRightClickTimerRunning && ((System.currentTimeMillis() - startTime) >= 150)) {
            doRightClick(x, y);
            isRightClickTimerRunning = false;
        }
    }

    private void doRightClick(int x, int y) {
        if(!Tobii.isRightEyePresent() && Tobii.isLeftEyePresent()) {
            robot.mouseClick(InputEvent.BUTTON3_DOWN_MASK);

            shortcutManager.addClick(new Click(InputEvent.BUTTON3_DOWN_MASK, new Point(x, y)));
        }
    }

    private void doLeftClick(int x, int y) {
        if(!isLeftButtonPressed) {
            robot.mouseClick(InputEvent.BUTTON1_DOWN_MASK);

            shortcutManager.addClick(new Click(InputEvent.BUTTON1_DOWN_MASK, new Point(x, y)));

            isLeftButtonPressed = true;
            isRightClickTimerRunning = false;
        }
    }

}
