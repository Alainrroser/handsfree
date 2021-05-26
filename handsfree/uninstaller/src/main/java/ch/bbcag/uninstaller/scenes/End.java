package ch.bbcag.uninstaller.scenes;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeLabel;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.uninstaller.Const;
import ch.bbcag.uninstaller.UninstallerApplication;
import ch.bbcag.uninstaller.error.ErrorMessages;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;

import java.io.File;

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
                File jarPath = new File(End.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "ping", "localhost", "-n", "6", ">", "nul", "&&", "rmdir",
                                                                   jarPath.getParentFile().getAbsolutePath(), "/S", "/Q");
                processBuilder.directory(new File(jarPath.getParentFile().getParentFile().getAbsolutePath()));
                processBuilder.start();
            } catch(Exception e) {
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
