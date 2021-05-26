package ch.bbcag.uninstaller.scenes;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeLabel;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.uninstaller.Const;
import ch.bbcag.uninstaller.Error.ErrorMessages;
import ch.bbcag.uninstaller.UninstallerApplication;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;

public class End extends UnInstallerScene {

    public End(UninstallerApplication application) {
        super(application.getPrimaryStage(), application.getConfiguration());

        initGUI(application);
    }

    private void initGUI(UninstallerApplication application) {
        HandsFreeLabel label = new HandsFreeLabel("Okay, we're sad you're here.\n" +
                                                  "You can still make the right decision and cancel the uninstallation below...");
        label.setWrapText(true);
        BorderPane.setMargin(label, Const.LABEL_MARGIN);

        addButton("Cancel", HandsFreeButtonPalette.DEFAULT_PALETTE, Platform::exit);
        addButton("< Back", HandsFreeButtonPalette.DEFAULT_PALETTE, () -> application.getNavigator().navigateTo(SceneType.START));
        addButton("Finish", HandsFreeButtonPalette.PRIMARY_PALETTE, () -> {
            String userHome = System.getProperty("user.home");

            File appData = new File(userHome + "\\AppData\\HandsFree");
            File startMenuShortcut = new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\HandsFree.lnk");
            File desktopShortcut = new File(userHome + "\\Desktop\\HandsFree.lnk");


            deleteFileOrFolderIfExists(appData);
            deleteFileOrFolderIfExists(startMenuShortcut);
            deleteFileOrFolderIfExists(desktopShortcut);
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "ping", "localhost", "-n", "6", ">", "nul", "&&", "rmdir",
                                   "C:\\Program Files\\HandsFree", "/S", "/Q");
                processBuilder.directory(new File("C:/Program Files"));
                processBuilder.start();
            } catch(IOException e) {
                Error.reportMinor(ErrorMessages.DELETE);
            }

            System.exit(0);
        });

        getBorderPane().setTop(label);
    }

    private void deleteFileOrFolderIfExists(File file) {
        if(file.exists()) {
            file.delete();
        }
    }
}
