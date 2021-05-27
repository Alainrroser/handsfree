package ch.bbcag.uninstaller.scenes;

import ch.bbcag.handsfree.gui.HandsFreeLabel;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.uninstaller.UninstallerConstants;
import ch.bbcag.uninstaller.UninstallerApplication;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;

public class End extends UninstallerScene {

    public End(UninstallerApplication application) {
        super(application.getPrimaryStage(), application.getConfiguration());

        initGUI(application);
    }

    private void initGUI(UninstallerApplication application) {
        HandsFreeLabel label = new HandsFreeLabel("Okay, we're sad you're here.\n" +
                                                  "You can still make the right decision and cancel the uninstallation below... \n But if you " +
                                                  "really want to continue, click finish and all your files get deleted.");
        label.setWrapText(true);
        BorderPane.setMargin(label, UninstallerConstants.LABEL_MARGIN);

        addButton("Cancel", HandsFreeButtonPalette.DEFAULT_PALETTE, Platform::exit);
        addButton("< Back", HandsFreeButtonPalette.DEFAULT_PALETTE, () -> application.getNavigator().navigateTo(SceneType.START));
        addButton("Finish", HandsFreeButtonPalette.PRIMARY_PALETTE, () -> {
            application.execute();
            System.exit(0);
        });

        getBorderPane().setTop(label);
    }
}
