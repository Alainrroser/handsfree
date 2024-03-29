package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.ApplicationConstants;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.config.Autorun;
import ch.bbcag.handsfree.config.Configuration;
import ch.bbcag.handsfree.error.ApplicationErrorMessages;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.SpeechRecognizerException;
import ch.bbcag.handsfree.gui.HandsFreeScrollPane;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import ch.bbcag.handsfree.gui.button.HandsFreeToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends ApplicationScene {

    private HandsFreeToggleButton toggleEyeTracking;
    private HandsFreeToggleButton toggleHeadTracking;
    private HandsFreeToggleButton toggleSpeechControl;
    private HandsFreeToggleButton toggleOnScreenKeyboard;
    private HandsFreeToggleButton toggleAutorun;

    private boolean hasBeenRead = false;

    public MainMenu(HandsFreeApplication application, HandsFreeContext context) {
        super(application.getPrimaryStage(), new HandsFreeScrollPane(), application.getConfiguration(), "HandsFree");

        getContentRoot().setMinSize(ApplicationConstants.WIDTH, ApplicationConstants.HEIGHT);
        getContentRoot().setMaxSize(ApplicationConstants.WIDTH, ApplicationConstants.HEIGHT);

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
        initToggleOnScreenKeyboard(context);
        initToggleAutorun();
        toggleEyeTracking.setEnabled(Configuration.readConfiguration(ApplicationConstants.EYE_TRACKING_STATE));
        toggleHeadTracking.setEnabled(Configuration.readConfiguration(ApplicationConstants.HEAD_TRACKING_STATE));
        toggleSpeechControl.setEnabled(Configuration.readConfiguration(ApplicationConstants.SPEECH_CONTROL_STATE));
        toggleAutorun.setEnabled(Configuration.readConfiguration(ApplicationConstants.AUTORUN_STATE));
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
        toggleHeadTracking.setOnDisabled(() -> {
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
            Error.reportMinor(ApplicationErrorMessages.NO_MICROPHONE);
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

    private void initToggleOnScreenKeyboard(HandsFreeContext context) {
        toggleOnScreenKeyboard = new HandsFreeToggleButton("On-Screen Keyboard");
        toggleOnScreenKeyboard.setOnEnabled(() -> {
            context.getKeyboard().display();
            stage.setIconified(true);
        });
        toggleOnScreenKeyboard.setOnDisabled(() -> context.getKeyboard().close());
        toggleOnScreenKeyboard.setEnabled(false);
    }

    private void initToggleAutorun() {
        toggleAutorun = new HandsFreeToggleButton("Autorun");
        toggleAutorun.setOnEnabled(() -> {
            Autorun.saveToAutorunFolder();
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

    private void saveConfiguration() {
        Configuration.writeConfiguration(toggleEyeTracking.isEnabled(), toggleHeadTracking.isEnabled(), toggleSpeechControl.isEnabled(),
                                         toggleAutorun.isEnabled());
    }
}

