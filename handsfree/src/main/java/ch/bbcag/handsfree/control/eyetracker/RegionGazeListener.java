package ch.bbcag.handsfree.control.eyetracker;

import javafx.scene.layout.Region;

public class RegionGazeListener {

    private Region region;
    private double minTime;
    private GazeListener gazeListener;

    public RegionGazeListener(Region region, double minTime, GazeListener gazeListener) {
        this.region = region;
        this.minTime = minTime;
        this.gazeListener = gazeListener;
    }

    public Region getRegion() {
        return region;
    }

    public double getMinTime() {
        return minTime;
    }

    public GazeListener getGazeListener() {
        return gazeListener;
    }

}
