package ch.bbcag.installer.scenes;

import ch.bbcag.handsfree.gui.HandsFreeLabel;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.scenes.Navigator;
import ch.bbcag.installer.InstallerApplication;
import javafx.application.Platform;

public class Start extends InstallerScene {

    public Start(InstallerApplication application) {
        super(application.getPrimaryStage(), application.getConfiguration());

        initGUI();

        Navigator navigator = application.getNavigator();
        SceneType nextSceneType = SceneType.DIRECTORY_CHOOSER;

        addButton("Cancel", HandsFreeButtonPalette.DEFAULT_PALETTE, Platform::exit);
        addButton("Next >", HandsFreeButtonPalette.PRIMARY_PALETTE, () -> navigator.navigateTo(nextSceneType));
    }

    private void initGUI() {
        HandsFreeLabel label = new HandsFreeLabel(
                "Welcome to the HandsFree Installer. We are here to guide you through the installation process.\n" +
                "Click \"Next\" to get to the next part of the installation."
        );
        label.setWrapText(true);
        label.setMaxHeight(Double.MAX_VALUE);

        getBorderPane().setTop(label);

        addIconImageBackground();
    }

}
