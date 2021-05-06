package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.eyetracker.EyeTracker;
import ch.bbcag.handsfree.control.shortcuts.ShortcutManager;
import ch.bbcag.handsfree.error.HandsFreeRobotException;

public class HandsFreeContext {

    private EyeTracker eyeTracker;

    private HandsFreeRobot robot;
    private ShortcutManager shortcutManager;

    public HandsFreeContext() throws HandsFreeRobotException {
        eyeTracker = new EyeTracker();

        robot = new HandsFreeRobot();
        shortcutManager = new ShortcutManager(robot);
    }

    public EyeTracker getEyeTracker() {
        return eyeTracker;
    }

    public HandsFreeRobot getRobot() {
        return robot;
    }

    public ShortcutManager getShortcutManager() {
        return shortcutManager;
    }

}
