package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.config.Autorun;
import ch.bbcag.handsfree.config.Configuration;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
import ch.bbcag.handsfree.error.SpeechRecognizerException;
import ch.bbcag.handsfree.gui.HandsFreeScrollPane;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import ch.bbcag.handsfree.gui.button.HandsFreeToggleButton;
import ch.bbcag.handsfree.gui.dialog.HandsFreeMessageDialog;
import ch.bbcag.handsfree.gui.onscreenkeyboard.HandsFreeOnScreenKeyboard;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends ApplicationScene {

    private HandsFreeOnScreenKeyboard keyboard;

    private HandsFreeToggleButton toggleEyeTracking;
    private HandsFreeToggleButton toggleHeadTracking;
    private HandsFreeToggleButton toggleSpeechControl;
    private HandsFreeToggleButton toggleOnScreenKeyboard;
    private HandsFreeToggleButton toggleAutorun;

    private boolean isFirstTime = true;
    private boolean hasBeenRead = false;

    public MainMenu(HandsFreeApplication application, HandsFreeContext context) {
        super(application.getPrimaryStage(), new HandsFreeScrollPane(), application.getConfiguration(), "HandsFree");

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);

        keyboard = new HandsFreeOnScreenKeyboard(context);
        initGUI(application, context);
    }

    private void initGUI(HandsFreeApplication application, HandsFreeContext context) {
        HandsFreeScrollPane scrollPane = (HandsFreeScrollPane) getContentRoot();

        VBox vBox = initVBox();
        HBox hBoxTitle = initTitle();

        initToggleButtons(context);
        HandsFreeTextButton commandsList = initCommandsListButton(application);
        HandsFreeTextButton openShortCutMenu = initOpenShortCutMenu(application);

        vBox.getChildren().addAll(hBoxTitle, toggleEyeTracking, toggleHeadTracking, toggleSpeechControl, commandsList, toggleOnScreenKeyboard,
                                  toggleAutorun, openShortCutMenu);
        scrollPane.setContent(vBox);
    }

    private HandsFreeTextButton initOpenShortCutMenu(HandsFreeApplication application) {
        HandsFreeTextButton openShortCutMenu = new HandsFreeTextButton("Shortcuts");
        openShortCutMenu.setOnAction(event -> application.getNavigator().navigateTo(SceneType.SHORTCUT_MENU));
        return openShortCutMenu;
    }

    private HandsFreeTextButton initCommandsListButton(HandsFreeApplication application) {
        HandsFreeTextButton commandsList = new HandsFreeTextButton("List of commands");
        commandsList.setOnAction(event -> application.getNavigator().navigateTo(SceneType.COMMANDS_LIST));
        return commandsList;
    }

    private void initToggleButtons(HandsFreeContext context) {
        initToggleEyeTracking(context);
        initToggleHeadTracking(context);
        initToggleSpeechControl(context);
        initToggleOnScreenKeyboard();
        initToggleAutorun();
        toggleEyeTracking.setEnabled(Configuration.readConfiguration(Const.EYE_TRACKING_STATE));
        toggleHeadTracking.setEnabled(Configuration.readConfiguration(Const.HEAD_TRACKING_STATE));
        toggleSpeechControl.setEnabled(Configuration.readConfiguration(Const.SPEECH_CONTROL_STATE));
        toggleAutorun.setEnabled(Configuration.readConfiguration(Const.AUTORUN_STATE));
        hasBeenRead = true;
    }

    private void initToggleEyeTracking(HandsFreeContext context) {
        toggleEyeTracking = new HandsFreeToggleButton("Eye Tracking");
        toggleEyeTracking.setOnEnabled(() -> {
            context.getEyeTracker().start();
            if(hasBeenRead) {
                saveConfiguration();
            }
        });
        toggleEyeTracking.setOnDisabled(() -> {
            context.getEyeTracker().stop();
            if(hasBeenRead) {
                saveConfiguration();
            }
        });
    }

    private void initToggleHeadTracking(HandsFreeContext context) {
        toggleHeadTracking = new HandsFreeToggleButton("Head Tracking");
        toggleHeadTracking.setOnEnabled(() -> {
            context.getHeadTracker().start();
            if(hasBeenRead) {
                saveConfiguration();
            }
        });
        toggleEyeTracking.setOnDisabled(() -> {
            context.getHeadTracker().stop();
            if(hasBeenRead) {
                saveConfiguration();
            }
        });
    }

    private void initToggleSpeechControl(HandsFreeContext context) {
        toggleSpeechControl = new HandsFreeToggleButton("Speech Control");
        toggleSpeechControl.setOnEnabled(() -> {
            startSpeechRecognizerIfSupported(context);
            if(hasBeenRead) {
                saveConfiguration();
            }
        });
        toggleSpeechControl.setOnDisabled(() -> {
            context.getSpeechRecognizer().stop();
            if(hasBeenRead) {
                saveConfiguration();
            }
        });
    }

    private void startSpeechRecognizerIfSupported(HandsFreeContext context) {
        if(context.getSpeechRecognizer().isSupported()) {
            startSpeechRecognizer(context);
        } else {
            Error.reportMinor(ErrorMessages.NO_MICROPHONE);
            toggleSpeechControl.setEnabledWithoutTriggering(false);
        }
    }

    private void startSpeechRecognizer(HandsFreeContext context) {
        try {
            context.getSpeechRecognizer().start();
        } catch(SpeechRecognizerException e) {
            Error.reportMinor(e.getMessage());
            toggleSpeechControl.setEnabledWithoutTriggering(false);
        }
    }

    private void initToggleOnScreenKeyboard() {
        toggleOnScreenKeyboard = new HandsFreeToggleButton("On-Screen Keyboard");
        toggleOnScreenKeyboard.setOnEnabled(() -> {
            keyboard.display();
            stage.setIconified(true);
        });
        toggleOnScreenKeyboard.setOnDisabled(() -> keyboard.hide());
        toggleOnScreenKeyboard.setEnabled(false);
    }

    private void initToggleAutorun() {
        toggleAutorun = new HandsFreeToggleButton("Autorun");
        toggleAutorun.setOnEnabled(() -> {
            Autorun.saveToAutorunFolder();
            checkIfFirstTimeTogglingAutorun();
            if(hasBeenRead) {
                saveConfiguration();
            }
        });
        toggleAutorun.setOnDisabled(() -> {
            Autorun.deleteFromAutorunFolder();
            if(hasBeenRead) {
                saveConfiguration();
            }
        });
    }

    private void checkIfFirstTimeTogglingAutorun() {
        if(isFirstTime && Configuration.readConfiguration(Const.AUTORUN_STATE)) {
            isFirstTime = false;
        } else {
            HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Autorun", "Notice that autorun won't work anymore if you move or rename the" +
                                                                                  " application file.");
            dialog.show();
        }
    }

    private void saveConfiguration() {
        Configuration.writeConfiguration(toggleEyeTracking.isEnabled(), toggleHeadTracking.isEnabled(), toggleSpeechControl.isEnabled(),
                                         toggleAutorun.isEnabled());
    }
}

