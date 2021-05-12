package ch.bbcag.handsfree.control.eyetracker;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.layout.Region;
import tobii.Tobii;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EyeTracker {

    private List<GazeListener> gazeListeners = new ArrayList<>();
    private List<RegionGazeListener> regionGazeListeners = new ArrayList<>();

    private RegionGazeListener currentRegionGazeListener = null;
    private double currentGazeRegionActivationTime = 0;
    private boolean regionGazeHandlerActivated = false;

    private boolean running;

    private static final int STARTUP_DELAY = 1000;

    public void start() {
        this.running = true;

        new Thread(this::doTracking).start();
    }

    public void stop() {
        this.running = false;
    }

    public void addGazeListener(GazeListener handler) {
        gazeListeners.add(handler);
    }

    public void removeGazeListener(GazeListener handler) {
        gazeListeners.remove(handler);
    }

    public void addRegionGazeListener(RegionGazeListener handler) {
        regionGazeListeners.add(handler);
    }

    public void removeRegionGazeListener(RegionGazeListener handler) {
        regionGazeListeners.remove(handler);
    }

    private void doTracking() {
        try {
            Thread.sleep(STARTUP_DELAY);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        while(running) {
            float[] position = Tobii.getGazePosition();
            float xRelative = position[0];
            float yRelative = position[1];

            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
            int x = (int) (xRelative * screenWidth);
            int y = (int) (yRelative * screenHeight);

            runGazeHandlers(x, y);
            runActivatedRegionGazeHandlers(x, y);
            sleepThread();
        }
    }

    private void runGazeHandlers(int x, int y) {
        for(GazeListener gazeListener : gazeListeners) {
            gazeListener.gaze(x, y);
        }
    }

    private void runActivatedRegionGazeHandlers(int x, int y) {
        for(RegionGazeListener regionGazeListener : regionGazeListeners) {
            try {
                Region region = regionGazeListener.getRegion();
                Bounds boundsLocal = region.getBoundsInLocal();
                Bounds boundsOnScreen = region.localToScreen(boundsLocal);

                if(boundsOnScreen.contains(new Point2D(x, y))) {
                    updateGazeRegion(regionGazeListener, x, y);
                    return;
                }
            } catch(IndexOutOfBoundsException e) {
                // Some JavaFX exception that doesn't crash the application
                // but prints annoying error messages
            }
        }

        currentRegionGazeListener = null;
    }

    private void updateGazeRegion(RegionGazeListener handler, int x, int y) {
        if(handler != currentRegionGazeListener) {
            startRegionGazeTimer(handler);
        } else {
            if(!regionGazeHandlerActivated && System.currentTimeMillis() >= currentGazeRegionActivationTime) {
                currentRegionGazeListener.getGazeListener().gaze(x, y);
                regionGazeHandlerActivated = true;
            }
        }
    }

    private void startRegionGazeTimer(RegionGazeListener handler) {
        currentRegionGazeListener = handler;
        currentGazeRegionActivationTime = System.currentTimeMillis() + handler.getMinTime();
        regionGazeHandlerActivated = false;
    }

    private void sleepThread() {
        try {
            Thread.sleep(20);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}
