package ch.bbcag.installer.scenes;

import ch.bbcag.handsfree.gui.HandsFreeScene;
import ch.bbcag.handsfree.gui.button.HandsFreeDefaultButton;
import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Start extends HandsFreeScene {

    public Start(InstallerApplication application) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);

        initGUI(application);
    }

    private void initGUI(InstallerApplication application) {
        VBox vBox = (VBox) getContentRoot();
        vBox.setSpacing(Const.BOX_SPACING);
        vBox.setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT));

        Label label = new Label("Welcome to the HandsFree Installer. We are here to guide you through the installation process. \n" +
                                "Click \"Continue\" to get to the next part of the installation.");

        HandsFreeDefaultButton cancelButton = new HandsFreeDefaultButton("Cancel");
        cancelButton.setOnAction(e -> System.exit(0));

        HandsFreeDefaultButton continueButton = new HandsFreeDefaultButton("Continue");
        continueButton.setOnAction(event -> application.getNavigator().navigateTo(SceneType.DIRECTORY_CHOOSER));

        HBox buttonHBox = new HBox(Const.BOX_SPACING, cancelButton, continueButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);

        vBox.getChildren().addAll(label, buttonHBox);
    }

}
