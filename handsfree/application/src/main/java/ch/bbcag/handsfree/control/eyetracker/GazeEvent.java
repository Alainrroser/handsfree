package ch.bbcag.handsfree.control.eyetracker;

import javafx.geometry.Point2D;

public class GazeEvent {

    private int x;
    private int y;
    private boolean leftEyeOpen;
    private boolean rightEyeOpen;

    public GazeEvent(int x, int y, boolean leftEyeOpen, boolean rightEyeOpen) {
        this.x = x;
        this.y = y;
        this.leftEyeOpen = leftEyeOpen;
        this.rightEyeOpen = rightEyeOpen;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLeftEyeOpen() {
        return leftEyeOpen;
    }

    public boolean isRightEyeOpen() {
        return rightEyeOpen;
    }

}
