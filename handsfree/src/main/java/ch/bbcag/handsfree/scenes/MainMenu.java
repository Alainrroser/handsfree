package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.eyetracker.EyeMouseController;
import ch.bbcag.handsfree.control.eyetracker.EyeTracker;
import ch.bbcag.handsfree.control.speechcontrol.SpeechControl;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
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

    private EyeMouseController eyeMouseController;
    private SpeechControl speechControl;
    private HandsFreeOnScreenKeyboard keyboard;

    public MainMenu(HandsFreeApplication application, HandsFreeContext context) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);
    
        init(context);
        initGUI(application, context);
    }

    private void init(HandsFreeContext context) {
        HandsFreeRobot robot = context.getRobot();
        eyeMouseController = new EyeMouseController(context);
        speechControl = new SpeechControl(robot);
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

        HandsFreeToggleButton toggleEyeTracking = new HandsFreeToggleButton("Eye Tracking");
        toggleEyeTracking.setOnEnabled(() -> context.getEyeTracker().start());
        toggleEyeTracking.setOnDisabled(() -> context.getEyeTracker().stop());
        toggleEyeTracking.setEnabled(false);

        HandsFreeToggleButton toggleSpeechControl = new HandsFreeToggleButton("Speech Control");
        toggleSpeechControl.setOnEnabled(() -> {
            if(context.getSpeechRecognizer().isSupported()) {
                speechControl.start();
            } else {
                Error.reportMinor(ErrorMessages.NO_MICROPHONE);
                toggleSpeechControl.setEnabled(false);
            }
        });
        toggleSpeechControl.setOnDisabled(() -> speechControl.stop());
        toggleSpeechControl.setEnabled(false);

        HandsFreeToggleButton toggleOnScreenKeyboard = new HandsFreeToggleButton("On-Screen Keyboard");
        toggleOnScreenKeyboard.setOnEnabled(() -> keyboard.display());
        toggleOnScreenKeyboard.setOnDisabled(() -> keyboard.hide());
        toggleOnScreenKeyboard.setEnabled(false);

        HandsFreeToggleButton toggleAutorun = new HandsFreeToggleButton("Autorun");
        toggleAutorun.setOnEnabled(() -> {
            HandsFreeMessageDialog dialog = new HandsFreeMessageDialog("Autorun", "Notice that autorun won't work anymore if you move or rename the application file.");
            dialog.show();
        });
        toggleAutorun.setOnDisabled(() -> speechControl.stop());
        toggleAutorun.setEnabled(false);

        HandsFreeDefaultButton openShortCutMenu = new HandsFreeDefaultButton("Shortcuts");
        openShortCutMenu.setOnAction(event -> application.getNavigator().navigateTo(SceneType.SHORTCUT_MENU));

        vBox.getChildren().addAll(hBoxTitle, toggleEyeTracking, toggleSpeechControl, toggleOnScreenKeyboard, toggleAutorun, openShortCutMenu);
    }

}
