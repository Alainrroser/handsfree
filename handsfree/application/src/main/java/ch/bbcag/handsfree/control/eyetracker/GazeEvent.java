package ch.bbcag.handsfree.control.eyetracker;

import com.sun.javafx.geom.Point2D;

public class GazeEvent {

    private Point2D position;
    private boolean rightEyeOpen;
    private boolean leftEyeOpen;

    public GazeEvent(Point2D position, boolean rightEyeOpen, boolean leftEyeOpen) {
        this.position = position;
        this.rightEyeOpen = rightEyeOpen;
        this.leftEyeOpen = leftEyeOpen;
    }

    public Point2D getPosition() {
        return position;
    }

    public boolean isRightEyeOpen() {
        return rightEyeOpen;
    }

    public boolean isLeftEyeOpen() {
        return leftEyeOpen;
    }

}
