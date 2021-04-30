package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeFont;
import ch.bbcag.handsfree.control.HandsFreeScene;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;
import ch.bbcag.handsfree.control.dialog.HandsFreeMessageDialog;
import ch.bbcag.handsfree.control.onscreenkeyboard.HandsFreeOnScreenKeyboard;
import ch.bbcag.handsfree.eyetracking.EyeTracking;
import ch.bbcag.handsfree.speechcontrol.SpeechControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainMenu extends HandsFreeScene {
    
    private String eyeTrackingState = "ON";
    private String speechControlState = "OFF";
    private String onScreenKeyboardState = "OFF";
    private String autorunState = "ON";
    
    private boolean isEyeTrackingEnabled = true;
    private boolean isSpeechControlEnabled = false;
    private boolean isOnScreenKeyboardEnabled = false;
    private boolean isAutoRunEnabled = true;
    
    private EyeTracking eyeTracking = new EyeTracking();
    private SpeechControl speechControl = new SpeechControl();
    private HandsFreeOnScreenKeyboard keyboard = new HandsFreeOnScreenKeyboard();
    
    public MainMenu(HandsFreeApplication application) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());
        
		VBox vBox = (VBox) getContentRoot();
        vBox.setSpacing(Const.V_BOX_SPACING);
        vBox.setPadding(new Insets(Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT, Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT));
        vBox.setMinSize(Const.WIDTH, Const.HEIGHT);
        vBox.setMaxSize(Const.WIDTH, Const.HEIGHT);
        HBox hBoxTitle = new HBox();

        Label title = new Label("HandsFree");
        title.setFont(HandsFreeFont.getFont(30));
        title.setTextFill(Colors.FONT);
        
        hBoxTitle.getChildren().add(title);
        hBoxTitle.setAlignment(Pos.CENTER);
        
        String toggleEyeTrackingText = "Eye Tracking: ";
        String toggleSpeechControlText = "Speech Control: ";
        String toggleOnScreenKeyboardText = "On-Screen Keyboard: ";
        String toggleAutoRunText = "Autorun: ";
        String openShortCutMenuText = "Shortcuts";
        HandsFreeDefaultButton toggleEyeTracking = new HandsFreeDefaultButton(toggleEyeTrackingText + eyeTrackingState);
        HandsFreeDefaultButton toggleSpeechControl = new HandsFreeDefaultButton(toggleSpeechControlText + speechControlState);
        HandsFreeDefaultButton toggleOnScreenKeyboard = new HandsFreeDefaultButton(toggleOnScreenKeyboardText + onScreenKeyboardState);
        HandsFreeDefaultButton toggleAutorun = new HandsFreeDefaultButton(toggleAutoRunText + autorunState);
        HandsFreeDefaultButton openShortCutMenu = new HandsFreeDefaultButton(openShortCutMenuText);
        toggleEyeTracking.setOnAction(event -> {
            eyeTrackingState = changeText(isEyeTrackingEnabled, eyeTrackingState);
            isEyeTrackingEnabled = toggleState(isEyeTrackingEnabled);
            if(isEyeTrackingEnabled) {
                eyeTracking.start(this);
            }
            toggleEyeTracking.setText(toggleEyeTrackingText + eyeTrackingState);
        });
        toggleSpeechControl.setOnAction(event -> {
            speechControlState =  changeText(isSpeechControlEnabled, speechControlState);
            isSpeechControlEnabled = toggleState(isSpeechControlEnabled);
            if(isSpeechControlEnabled) {
                speechControl.start(this);
            }
            toggleSpeechControl.setText(toggleSpeechControlText + speechControlState);
        });
        toggleOnScreenKeyboard.setOnAction(event -> {
            onScreenKeyboardState = changeText(isOnScreenKeyboardEnabled, onScreenKeyboardState);
            isOnScreenKeyboardEnabled = toggleState(isOnScreenKeyboardEnabled);
            toggleOnScreenKeyboard.setText(toggleOnScreenKeyboardText + onScreenKeyboardState);

            if(isOnScreenKeyboardEnabled) {
                keyboard.display();
            } else {
                keyboard.hide();
            }
        });
        toggleAutorun.setOnAction(event -> {
            HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Autorun", "Notice that autorun won't work anymore if you move or rename the application file.");
            dialog.show();
            autorunState = changeText(isAutoRunEnabled, autorunState);
            isAutoRunEnabled = toggleState(isAutoRunEnabled);
            toggleAutorun.setText(toggleAutoRunText + autorunState);
            dialog.closeDialog();
        });
        openShortCutMenu.setOnAction(event -> application.getNavigator().navigateTo(SceneType.SHORTCUT_MENU));
        
        vBox.getChildren().addAll(hBoxTitle, toggleEyeTracking, toggleSpeechControl, toggleOnScreenKeyboard, toggleAutorun, openShortCutMenu);
    }
    
    private String changeText(boolean isEnabled, String state) {
        if(isEnabled) {
            state = "OFF";
        } else {
            state = "ON";
        }
        return state;
    }
    
    private boolean toggleState(boolean isEnabled) {
        isEnabled = !isEnabled;
        return isEnabled;
    }
    
    public boolean isEyeTrackingEnabled() {
        return isEyeTrackingEnabled;
    }
    
    public void setIsEyeTrackingEnabled(boolean isEyeTrackingEnabled) {
        this.isEyeTrackingEnabled = isEyeTrackingEnabled;
    }
    
    public boolean isSpeechControlEnabled() {
        return isSpeechControlEnabled;
    }
    
    public void setIsSpeechControlEnabled(boolean isSpeechControlEnabled) {
        this.isSpeechControlEnabled = isSpeechControlEnabled;
    }
    
    public boolean isOnScreenKeyboardEnabled() {
        return isOnScreenKeyboardEnabled;
    }
    
    public void setIsOnScreenKeyboardEnabled(boolean isOnScreenKeyboardEnabled) {
        this.isOnScreenKeyboardEnabled = isOnScreenKeyboardEnabled;
    }
    
    public boolean isAutoRunEnabled() {
        return isAutoRunEnabled;
    }
    
    public void setIsAutoRunEnabled(boolean isAutoRunEnabled) {
        this.isAutoRunEnabled = isAutoRunEnabled;
    }
}
