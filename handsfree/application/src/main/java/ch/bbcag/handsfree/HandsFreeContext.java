package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.eyetracker.EyeMouseController;
import ch.bbcag.handsfree.control.eyetracker.EyeTracker;
import ch.bbcag.handsfree.control.headtracker.HeadGestureController;
import ch.bbcag.handsfree.control.headtracker.HeadTracker;
import ch.bbcag.handsfree.control.shortcuts.ShortcutManager;
import ch.bbcag.handsfree.control.speechcontrol.SpeechControl;
import ch.bbcag.handsfree.control.speechcontrol.SpeechRecognizer;
import ch.bbcag.handsfree.error.HandsFreeRobotException;
import ch.bbcag.handsfree.error.NativeException;
import ch.bbcag.handsfree.gui.onscreenkeyboard.HandsFreeOnScreenKeyboard;
import javafx.stage.Stage;

public class HandsFreeContext {

    private HandsFreeRobot robot;

    private EyeTracker eyeTracker;
    private EyeMouseController eyeMouseController;

    private HeadTracker headTracker;
    private HeadGestureController headGestureController;

    private SpeechRecognizer speechRecognizer;
    private SpeechControl speechControl;

    private ShortcutManager shortcutManager;

    private HandsFreeOnScreenKeyboard keyboard;

    private boolean shortcutRunning = false;

    public HandsFreeContext(Stage primaryStage) throws HandsFreeRobotException, NativeException {
        robot = new HandsFreeRobot();

        eyeTracker = new EyeTracker();
        eyeMouseController = new EyeMouseController(this);

        headTracker = new HeadTracker();
        headGestureController = new HeadGestureController(this);

        speechRecognizer = new SpeechRecognizer();
        speechControl = new SpeechControl(this);

        shortcutManager = new ShortcutManager(this, primaryStage);

        keyboard = new HandsFreeOnScreenKeyboard(this);
    }

    public HandsFreeRobot getRobot() {
        return robot;
    }

    public EyeTracker getEyeTracker() {
        return eyeTracker;
    }

    public EyeMouseController getEyeMouseController() {
        return eyeMouseController;
    }

    public HeadTracker getHeadTracker() {
        return headTracker;
    }

    public HeadGestureController getHeadGestureController() {
        return headGestureController;
    }

    public SpeechRecognizer getSpeechRecognizer() {
        return speechRecognizer;
    }

    public SpeechControl getSpeechControl() {
        return speechControl;
    }

    public ShortcutManager getShortcutManager() {
        return shortcutManager;
    }

    public HandsFreeOnScreenKeyboard getKeyboard() {
        return keyboard;
    }

    public boolean isShortcutRunning() {
        return shortcutRunning;
    }

    public void setShortcutRunning(boolean shortcutRunning) {
        this.shortcutRunning = shortcutRunning;
    }
}
