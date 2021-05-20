package ch.bbcag.installer.scenes;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeScene;
import ch.bbcag.handsfree.gui.button.HandsFreeDefaultButton;
import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import ch.bbcag.installer.error.ErrorMessages;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class End extends HandsFreeScene {


    public End(InstallerApplication application) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);

        initGUI(application);
    }

    private void initGUI(InstallerApplication application) {
        VBox vBox = (VBox) getContentRoot();
        vBox.setSpacing(Const.BOX_SPACING);
        vBox.setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT));

        Label label = new Label("Okay, that was the last step. \n" +
                                "You can select down below if you want to start the app and if it is nothing else, enjoy!");

        CheckBox startAppCheckBox = new CheckBox("Start the app");

        HandsFreeDefaultButton backButton = new HandsFreeDefaultButton("Back");
        backButton.setOnAction(e -> application.getNavigator().navigateTo(SceneType.DIRECTORY_CHOOSER));

        HandsFreeDefaultButton closeButton = new HandsFreeDefaultButton("Close");
        closeButton.setOnAction(event -> {
            if(startAppCheckBox.isSelected()) {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", application.getSelectedPath() + "/" + Const.FILE_NAME);
                    processBuilder.start();
                } catch(Exception e) {
                    Error.reportMinor(ErrorMessages.APPLICATION_START);
                }
            }
            System.exit(0);
        });

        HBox buttonHBox = new HBox(Const.BOX_SPACING, backButton, closeButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);

        vBox.getChildren().addAll(label, startAppCheckBox, buttonHBox);
    }
}
