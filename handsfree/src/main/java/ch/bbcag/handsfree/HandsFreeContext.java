package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.eyetracker.EyeTracker;
import ch.bbcag.handsfree.control.shortcuts.ShortcutManager;
import ch.bbcag.handsfree.control.speechcontrol.SpeechControl;
import ch.bbcag.handsfree.control.speechcontrol.SpeechRecognizer;
import ch.bbcag.handsfree.error.HandsFreeRobotException;
import ch.bbcag.handsfree.error.NativeException;

public class HandsFreeContext {

    private EyeTracker eyeTracker;
    private SpeechRecognizer speechRecognizer;
    private HandsFreeRobot robot;

    private ShortcutManager shortcutManager;
    private SpeechControl speechControl;

    public HandsFreeContext() throws HandsFreeRobotException, NativeException {
        eyeTracker = new EyeTracker();
        speechRecognizer = new SpeechRecognizer();
        robot = new HandsFreeRobot();

        shortcutManager = new ShortcutManager(this);
        speechControl = new SpeechControl(this);
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

    public SpeechControl getSpeechControl() {
        return speechControl;
    }

    public ShortcutManager getShortcutManager() {
        return shortcutManager;
    }

}
