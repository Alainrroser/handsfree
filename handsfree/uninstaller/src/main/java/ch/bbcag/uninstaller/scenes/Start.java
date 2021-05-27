package ch.bbcag.uninstaller.scenes;

import ch.bbcag.handsfree.gui.HandsFreeLabel;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.scenes.Navigator;
import ch.bbcag.uninstaller.UninstallerApplication;
import javafx.application.Platform;

public class Start extends UninstallerScene {

    public Start(UninstallerApplication application) {
        super(application.getPrimaryStage(), application.getConfiguration());

        initGUI();

        Navigator navigator = application.getNavigator();

        addButton("Cancel", HandsFreeButtonPalette.DEFAULT_PALETTE, Platform::exit);
        addButton("Next >", HandsFreeButtonPalette.PRIMARY_PALETTE, () -> navigator.navigateTo(SceneType.END));
    }

    private void initGUI() {
        HandsFreeLabel label = new HandsFreeLabel(
                "Welcome to the - No, wait. Why are you here?!. You shouldn't be here.\n"
                + "Click \"Next\" if you really want to continue..."
        );
        label.setWrapText(true);
        label.setMaxHeight(Double.MAX_VALUE);

        getBorderPane().setTop(label);

        addIconImageBackground();
    }

}
