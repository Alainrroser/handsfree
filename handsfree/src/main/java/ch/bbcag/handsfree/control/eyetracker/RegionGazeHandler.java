package ch.bbcag.handsfree.control.eyetracker;

import java.awt.*;

public abstract class RegionGazeHandler {

    private Point topLeft;
    private Point bottomRight;
    private double minTime;

    public RegionGazeHandler(Point topLeft, Point bottomRight, double minTime) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.minTime = minTime;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public double getMinTime() {
        return minTime;
    }

    public abstract void gaze();

}
