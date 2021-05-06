package ch.bbcag.handsfree.control.eyetracker;

import tobii.Tobii;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EyeTracker {

    private List<GazeHandler> gazeHandlers = new ArrayList<>();
    private List<RegionGazeHandler> regionGazeHandlers = new ArrayList<>();

    private boolean running;

    public void start() {
        this.running = true;

        startTracking();
    }

    public void stop() {
        this.running = false;
    }

    public void addGazeHandler(GazeHandler handler) {
        gazeHandlers.add(handler);
    }

    public void removeGazeHandler(GazeHandler handler) {
        gazeHandlers.remove(handler);
    }

    public void addRegionGazeHandler(RegionGazeHandler handler) {
        regionGazeHandlers.add(handler);
    }

    public void removeRegionGazeHandler(RegionGazeHandler handler) {
        regionGazeHandlers.remove(handler);
    }

    private void startTracking() {
        new Thread(this::doTracking).start();
    }

    private void doTracking() {
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
        for(GazeHandler gazeHandler : gazeHandlers) {
            gazeHandler.gaze(x, y);
        }
    }

    private void runActivatedRegionGazeHandlers(int x, int y) {
        for(RegionGazeHandler regionGazeHandler : regionGazeHandlers) {
            if(x >= regionGazeHandler.getTopLeft().x && y >= regionGazeHandler.getTopLeft().y &&
                    x < regionGazeHandler.getBottomRight().x && y < regionGazeHandler.getBottomRight().y) {
                regionGazeHandler.gaze();
            }
        }
    }

    private void sleepThread() {
        try {
            Thread.sleep(20);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

}
