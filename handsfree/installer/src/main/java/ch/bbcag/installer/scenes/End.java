package ch.bbcag.installer.scenes;

import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class End extends Scene {

    private InstallerApplication installerApplication;

    public End(InstallerApplication installerApplication) {
        super(new VBox());
        this.installerApplication = installerApplication;

        VBox root = (VBox) getRoot();
        root.setSpacing(Const.BOX_SPACING);
        root.setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT));

        Label label = new Label("Okay, that was the last step. \n" +
                                "You can select down below if you want to start the app and if it is nothing else, enjoy!");

        CheckBox startAppCheckBox = new CheckBox("Start the app");


        Button backButton = new Button("Back");
        backButton.setOnAction(e -> installerApplication.getNavigator().navigateTo(SceneType.DIRECTORY_CHOOSER));

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            if(startAppCheckBox.isSelected()) {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", installerApplication.getSelectedPath() + "/" + Const.FILE_NAME);
                    processBuilder.start();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            System.exit(0);
        });

        HBox buttonHBox = new HBox(Const.BOX_SPACING, backButton, closeButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        root.getChildren().addAll(label, startAppCheckBox, buttonHBox);
    }
}
