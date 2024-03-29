package ch.bbcag.installer.scenes;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeCheckBox;
import ch.bbcag.handsfree.gui.HandsFreeLabel;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.installer.InstallerApplication;
import ch.bbcag.installer.InstallerConstants;
import ch.bbcag.installer.error.InstallerErrorMessages;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class End extends InstallerScene {

    public End(InstallerApplication application) {
        super(application.getPrimaryStage(), application.getConfiguration());

        initGUI(application);
    }

    private void initGUI(InstallerApplication application) {
        HandsFreeLabel label = new HandsFreeLabel("That was the last step.\n" +
                                                  "You can select down below if you want to start the app and if it is nothing else, enjoy!");
        label.setWrapText(true);
        BorderPane.setMargin(label, InstallerConstants.LABEL_MARGIN);

        HBox checkBoxContainer = new HBox();
        HandsFreeCheckBox startAppCheckBox = new HandsFreeCheckBox("Start the app");
        startAppCheckBox.setSelected(true);
        checkBoxContainer.getChildren().add(startAppCheckBox);
        checkBoxContainer.setAlignment(Pos.TOP_LEFT);

        addButton("Cancel", HandsFreeButtonPalette.DEFAULT_PALETTE, Platform::exit);
        addButton("< Back", HandsFreeButtonPalette.DEFAULT_PALETTE, () -> application.getNavigator().navigateTo(SceneType.SHORTCUT));
        addButton("Finish", HandsFreeButtonPalette.PRIMARY_PALETTE, () -> {
            application.execute();
            if(startAppCheckBox.isSelected()) {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar",
                                                                       application.getSelectedPath() + "/" + InstallerConstants.JAR_FILE_NAME);
                    processBuilder.start();
                } catch(Exception e) {
                    Error.reportMinor(InstallerErrorMessages.APPLICATION_START);
                }
            }
            Platform.exit();
        });

        getBorderPane().setTop(label);
        getBorderPane().setCenter(checkBoxContainer);
    }
}
