package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.eyetracker.EyeTracker;
import ch.bbcag.handsfree.control.shortcuts.ShortcutManager;
import ch.bbcag.handsfree.control.speechcontrol.SpeechRecognizer;
import ch.bbcag.handsfree.error.HandsFreeRobotException;
import ch.bbcag.handsfree.error.NativeException;

import java.io.IOException;

public class HandsFreeContext {

    private EyeTracker eyeTracker;
    private SpeechRecognizer speechRecognizer;

    private HandsFreeRobot robot;
    private ShortcutManager shortcutManager;

    public HandsFreeContext() throws HandsFreeRobotException, NativeException, IOException {
        eyeTracker = new EyeTracker();
        speechRecognizer = new SpeechRecognizer();

        robot = new HandsFreeRobot();
        shortcutManager = new ShortcutManager(robot);
    }

    public EyeTracker getEyeTracker() {
        return eyeTracker;
    }

    public SpeechRecognizer getSpeechRecognizer() {
        return speechRecognizer;
    }

    public HandsFreeRobot getRobot() {
        return robot;
    }

    public ShortcutManager getShortcutManager() {
        return shortcutManager;
    }

}
