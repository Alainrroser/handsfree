package ch.bbcag.handsfree.control.eyetracker;

import ch.bbcag.handsfree.HandsFreeContext;

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

    private void controlCursor(GazeEvent event) {
        if(context.isShortcutRunning()) {
            return;
        }

        if(event.isLeftEyeOpen() && event.isRightEyeOpen()) { // Both eyes are open
            context.getRobot().mouseMove(event.getX(), event.getY());

            isLeftButtonPressed = false;
            isRightButtonPressed = false;
        } else if(!event.isLeftEyeOpen() && !event.isRightEyeOpen()) { // Both eyes are closed
            doLeftClick();
        } else if(!event.isRightEyeOpen()) { // The right eye is closed
            startRightClick();
        }

        stopRightClick(event);
    }

    private void startRightClick() {
        if(!isRightClickTimerRunning && !isRightButtonPressed) {
            startTime = System.currentTimeMillis();
            isRightClickTimerRunning = true;
            isRightButtonPressed = true;
        }
    }

    private void stopRightClick(GazeEvent event) {
        if(isRightClickTimerRunning && ((System.currentTimeMillis() - startTime) >= 150)) {
            doRightClick(event);
            isRightClickTimerRunning = false;
        }
    }

    private void doRightClick(GazeEvent event) {
        if(!event.isRightEyeOpen() && event.isLeftEyeOpen()) {
            context.getRobot().mouseClick(InputEvent.BUTTON3_DOWN_MASK);
        }
    }

    private void doLeftClick() {
        if(!isLeftButtonPressed) {
            context.getRobot().mouseClick(InputEvent.BUTTON1_DOWN_MASK);

            isLeftButtonPressed = true;
            isRightClickTimerRunning = false;
        }
    }

}
