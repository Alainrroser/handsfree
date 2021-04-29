package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeFont;
import ch.bbcag.handsfree.control.HandsFreeScene;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;

import ch.bbcag.handsfree.control.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.control.dialog.HandsFreeMessageDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class MainMenu extends HandsFreeScene {
    
    private String eyeTrackingState = "ON";
    private String speechControlState = "ON";
    private String onScreenKeyboardState = "OFF";
    private String autorunState = "ON";
    
    public MainMenu(Navigator navigator, HandsFreeApplication application) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());
    
        VBox vBoxRoot = (VBox) getContentRootPane();
        vBoxRoot.setSpacing(Const.V_BOX_SPACING);
        vBoxRoot.setPadding(new Insets(Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT, Const.V_BOX_PADDING_TOP_BOTTOM, Const.V_BOX_PADDING_RIGHT_LEFT));
        vBoxRoot.setMinSize(Const.WIDTH, Const.HEIGHT);
    
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
            HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Autorun", "Notice that autorun won't work anymore if you move or rename the application file.");
            dialog.show();
            autorunState = toggleState(autorunState);
            toggleAutorun.setText(toggleAutoRunText + autorunState);
            dialog.closeDialog();
        });
        openShortCutMenu.setOnAction(event -> navigator.navigateTo(SceneType.SHORTCUT_MENU));
        
        vBoxRoot.getChildren().addAll(hBoxTitle, toggleEyeTracking, toggleSpeechControl, toggleOnScreenKeyboard, toggleAutorun, openShortCutMenu);
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
