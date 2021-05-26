package ch.bbcag.uninstaller.scenes;

import ch.bbcag.handsfree.gui.HandsFreeLabel;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.scenes.Navigator;
import ch.bbcag.uninstaller.UninstallerApplication;
import javafx.application.Platform;

public class Start extends UnInstallerScene {

    public Start(UninstallerApplication application) {
        super(application.getPrimaryStage(), application.getConfiguration());

        initGUI();

        Navigator navigator = application.getNavigator();

        addButton("Cancel", HandsFreeButtonPalette.DEFAULT_PALETTE, Platform::exit);
        addButton("Next >", HandsFreeButtonPalette.PRIMARY_PALETTE, () -> {
            HandsFreeConfirmDialog dialog1 = new HandsFreeConfirmDialog("Uninstalling", "Wait - You clicked, why would you even consider going?!");
            dialog1.show();
            dialog1.setOnConfirmed(() -> {
                HandsFreeConfirmDialog dialog2 = new HandsFreeConfirmDialog("Uninstalling", "You're really serious aren't you?!");
                dialog2.show();
                dialog2.setOnConfirmed(() -> {
                    HandsFreeConfirmDialog dialog3 = new HandsFreeConfirmDialog("Uninstalling", "Okay, seems like we can't stop you if you really " +
                                                                                                "want to go. We will miss you...");
                    dialog3.show();
                    dialog3.setOnConfirmed(() -> navigator.navigateTo(SceneType.END));
                });
            });
        });
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
