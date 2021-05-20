package ch.bbcag.installer.scenes;

import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Start extends Scene {

    InstallerApplication installerApplication;

    public Start(InstallerApplication installerApplication) {
        super(new VBox());
        this.installerApplication = installerApplication;

        VBox root = (VBox) getRoot();
        root.setSpacing(Const.BOX_SPACING);
        root.setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT));

        Label label = new Label("Welcome to the HandsFree Installer. We are here to guide you through the installation process. \n" +
                                "Click \"Continue\" to get to the next part of the installation.");

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> System.exit(0));

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> installerApplication.getNavigator().navigateTo(SceneType.DIRECTORY_CHOOSER));

        HBox buttonHBox = new HBox(Const.BOX_SPACING, cancelButton, continueButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        root.getChildren().addAll(label, buttonHBox);
    }

}
