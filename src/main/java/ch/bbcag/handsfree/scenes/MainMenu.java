package ch.bbcag.handsfree.scenes;

import ch.bbcag.handsfree.Const;
import ch.bbcag.handsfree.HandsFreeApplication;
import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeFont;
import ch.bbcag.handsfree.control.HandsFreeScene;
import ch.bbcag.handsfree.control.button.HandsFreeDefaultButton;
import ch.bbcag.handsfree.control.button.HandsFreeToggleButton;
import ch.bbcag.handsfree.control.dialog.HandsFreeMessageDialog;
import ch.bbcag.handsfree.control.onscreenkeyboard.HandsFreeOnScreenKeyboard;
import ch.bbcag.handsfree.eyetracking.EyeTracking;
import ch.bbcag.handsfree.speechcontrol.SpeechControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends HandsFreeScene {

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

        HandsFreeToggleButton toggleEyeTracking = new HandsFreeToggleButton("Eye Tracking");
        toggleEyeTracking.setOnEnabled(() -> eyeTracking.start());
        toggleEyeTracking.setOnDisabled(() -> eyeTracking.stop());
        toggleEyeTracking.setEnabled(true);

        HandsFreeToggleButton toggleSpeechControl = new HandsFreeToggleButton("Speech Control");
        toggleSpeechControl.setOnEnabled(() -> speechControl.start());
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
