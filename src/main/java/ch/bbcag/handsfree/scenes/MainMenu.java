package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeFont;
import ch.bbcag.handsfree.control.HandsFreeScene;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;

import ch.bbcag.handsfree.control.dialog.HandsFreeConfirmDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MainMenu extends HandsFreeScene {
    
    private String eyeTrackingState = "ON";
    private String speechControlState = "ON";
    private String onScreenKeyboardState = "OFF";
    private String autorunState = "ON";
    
    public MainMenu(Navigator navigator, HandsFreeApplication application) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());
    
        VBox vBox = (VBox) getContentRootPane();
        vBox.setSpacing(Const.V_BOX_SPACING);
        vBox.setPadding(new Insets(Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT, Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT));
        vBox.setMinSize(Const.WIDTH, Const.HEIGHT);
        vBox.setMaxSize(Const.WIDTH, Const.HEIGHT);

        Label title = new Label("HandsFree");
        title.setFont(HandsFreeFont.getFont(30));
        title.setTextFill(Colors.FONT);
    
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
            eyeTrackingState = toggleState(eyeTrackingState);
            toggleEyeTracking.setText(toggleEyeTrackingText + eyeTrackingState);
        });
        toggleSpeechControl.setOnAction(event -> {
            speechControlState =  toggleState(speechControlState);
            toggleSpeechControl.setText(toggleSpeechControlText + speechControlState);
        });
        toggleOnScreenKeyboard.setOnAction(event -> {
            onScreenKeyboardState = toggleState(onScreenKeyboardState);
            toggleOnScreenKeyboard.setText(toggleOnScreenKeyboardText + onScreenKeyboardState);
        });
        toggleAutorun.setOnAction(event -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Autorun", "Notice that autorun won't work anymore if you move or rename the application file.");
            dialog.show();
            dialog.setOnConfirmed(() -> {
                autorunState = toggleState(autorunState);
                toggleAutorun.setText(toggleAutoRunText + autorunState);
            });
        });
        openShortCutMenu.setOnAction(event -> navigator.navigateTo(SceneType.SHORTCUT_MENU));
        
        vBox.getChildren().addAll(title, toggleEyeTracking, toggleSpeechControl, toggleOnScreenKeyboard, toggleAutorun, openShortCutMenu);
        vBox.setAlignment(Pos.CENTER);
    }
    
    private String toggleState(String state) {
        switch(state) {
            case "ON":
                state = "OFF";
                break;
            case "OFF":
                state = "ON";
                break;
            default:
                break;
        }
        return state;
    }
}
