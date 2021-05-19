package ch.bbcag.handsfree.control.eyetracker;

import ch.bbcag.handsfree.HandsFreeContext;
import tobii.Tobii;

import java.awt.event.InputEvent;

public class EyeMouseController {

    private long startTime;

    private boolean isRightClickTimerRunning = false;
    private boolean isLeftButtonPressed = false;
    private boolean isRightButtonPressed = false;

    private HandsFreeContext context;

    public EyeMouseController(HandsFreeContext context) {
        this.context = context;
        context.getEyeTracker().addGazeListener(this::controlCursor);
    }

    private void controlCursor(int x, int y) {
        if(Tobii.isLeftEyePresent() && Tobii.isRightEyePresent()) { // Both eyes are open
            context.getRobot().mouseMove(x, y);

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
            context.getRobot().mouseClick(InputEvent.BUTTON3_DOWN_MASK);
        }
    }

    private void doLeftClick(int x, int y) {
        if(!isLeftButtonPressed) {
            context.getRobot().mouseClick(InputEvent.BUTTON1_DOWN_MASK);

            isLeftButtonPressed = true;
            isRightClickTimerRunning = false;
        }
    }

}
