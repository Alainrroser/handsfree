package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.config.Autorun;
import ch.bbcag.handsfree.config.Configuration;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
import ch.bbcag.handsfree.error.SpeechRecognizerException;
import ch.bbcag.handsfree.gui.Colors;
import ch.bbcag.handsfree.gui.HandsFreeFont;
import ch.bbcag.handsfree.gui.HandsFreeScene;
import ch.bbcag.handsfree.gui.button.HandsFreeDefaultButton;
import ch.bbcag.handsfree.gui.button.HandsFreeToggleButton;
import ch.bbcag.handsfree.gui.dialog.HandsFreeMessageDialog;
import ch.bbcag.handsfree.gui.onscreenkeyboard.HandsFreeOnScreenKeyboard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends HandsFreeScene {

    private HandsFreeOnScreenKeyboard keyboard;
    
    private HandsFreeToggleButton toggleEyeTracking;
    private HandsFreeToggleButton toggleSpeechControl;
    private HandsFreeToggleButton toggleOnScreenKeyboard;
    private HandsFreeToggleButton toggleAutorun;
    
    private boolean isFirstTime = true;

    public MainMenu(HandsFreeApplication application, HandsFreeContext context) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);
    
        init(context);
        initGUI(application, context);
    }

    private void init(HandsFreeContext context) {
        keyboard = new HandsFreeOnScreenKeyboard(context);
    }
    
    private void initGUI(HandsFreeApplication application, HandsFreeContext context) {
        VBox vBox = (VBox) getContentRoot();
        vBox.setSpacing(Const.V_BOX_SPACING);
        vBox.setPadding(new Insets(Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT, Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT));
        HBox hBoxTitle = new HBox();
        
        Label title = new Label("HandsFree");
        title.setFont(HandsFreeFont.getFont(30));
        title.setTextFill(Colors.FONT);
        
        hBoxTitle.getChildren().add(title);
        hBoxTitle.setAlignment(Pos.CENTER);
        
        initToggleButtons(context);
        
        HandsFreeDefaultButton openShortCutMenu = new HandsFreeDefaultButton("Shortcuts");
        openShortCutMenu.setOnAction(event -> application.getNavigator().navigateTo(SceneType.SHORTCUT_MENU));
        
        vBox.getChildren().addAll(hBoxTitle, toggleEyeTracking, toggleSpeechControl, toggleOnScreenKeyboard, toggleAutorun, openShortCutMenu);
    }
    
    private void initToggleButtons(HandsFreeContext context) {
        initToggleEyeTracking(context);
        initToggleSpeechControl(context);
        initToggleOnScreenKeyboard();
        initToggleAutorun();
        toggleEyeTracking.setEnabled(Configuration.readConfiguration(Const.EYE_TRACKING_STATE));
        toggleSpeechControl.setEnabled(Configuration.readConfiguration(Const.SPEECH_CONTROL_STATE));
        toggleAutorun.setEnabled(Configuration.readConfiguration(Const.AUTORUN_STATE));
    }
    
    private void initToggleEyeTracking(HandsFreeContext context) {
        toggleEyeTracking = new HandsFreeToggleButton("Eye Tracking");
        toggleEyeTracking.setOnEnabled(() -> {
            context.getEyeTracker().start();
            saveConfiguration();
        });
        toggleEyeTracking.setOnDisabled(() -> {
            context.getEyeTracker().stop();
            saveConfiguration();
        });
    }
    
    private void initToggleSpeechControl(HandsFreeContext context) {
        toggleSpeechControl = new HandsFreeToggleButton("Speech Control");
        toggleSpeechControl.setOnEnabled(() -> {
            startSpeechRecognizerIfSupported(context);
            saveConfiguration();
        });
        toggleSpeechControl.setOnDisabled(() -> {
            context.getSpeechRecognizer().stop();
            saveConfiguration();
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
        toggleOnScreenKeyboard.setOnEnabled(() -> keyboard.display());
        toggleOnScreenKeyboard.setOnDisabled(() -> keyboard.hide());
        toggleOnScreenKeyboard.setEnabled(false);
    }
    
    private void initToggleAutorun() {
        toggleAutorun = new HandsFreeToggleButton("Autorun");
        toggleAutorun.setOnEnabled(() -> {
            checkIfFirstTimeTogglingAutorun();
            saveConfiguration();
        });
        toggleAutorun.setOnDisabled(() -> {
            Autorun.deleteFromAutorunFolder();
            saveConfiguration();
        });
    }
    
    private void checkIfFirstTimeTogglingAutorun() {
        if(!isFirstTime) {
            showAutorunDialog();
        }
        if(isFirstTime) {
            isFirstTime = false;
        }
    }
    
    private void showAutorunDialog() {
        HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Autorun", "Notice that autorun won't work anymore if you move or rename the application file.");
        dialog.show();
    }
    
    private void saveConfiguration() {
        Configuration.writeConfiguration(toggleEyeTracking.isEnabled(), toggleSpeechControl.isEnabled(), toggleAutorun.isEnabled());
    }
}

