package ch.bbcag.handsfree.control.eyetracker;

import javafx.geometry.Bounds;
import javafx.scene.layout.Region;

public class RegionGazeHandler {

    private Region region;
    private double minTime;
    private GazeHandler gazeHandler;

    public RegionGazeHandler(Region region, double minTime, GazeHandler gazeHandler) {
        this.region = region;
        this.minTime = minTime;
        this.gazeHandler = gazeHandler;
    }

    public Region getRegion() {
        return region;
    }

    public double getMinTime() {
        return minTime;
    }

    public GazeHandler getGazeHandler() {
        return gazeHandler;
    }

}
