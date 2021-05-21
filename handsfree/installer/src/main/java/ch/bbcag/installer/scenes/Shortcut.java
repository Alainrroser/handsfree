package ch.bbcag.installer.scenes;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeCheckBox;
import ch.bbcag.handsfree.gui.HandsFreeLabel;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import ch.bbcag.installer.error.ErrorMessages;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mslinks.ShellLink;

import java.nio.file.AccessDeniedException;

public class Shortcut extends InstallerScene {

    private HandsFreeCheckBox desktopShortcutCheckBox;
    private HandsFreeCheckBox startMenuShortcutCheckBox;

    public Shortcut(InstallerApplication application) {
        super(application.getPrimaryStage(), application.getConfiguration());

        initGUI(application);
    }

    private void initGUI(InstallerApplication application) {
        HandsFreeLabel label = new HandsFreeLabel("Okay, here you just have to select if " +
                                "you want to have a desktop and a start menu shortcut.\n" +
                                "Check them if you want and continue.");
        label.setWrapText(true);
        BorderPane.setMargin(label, Const.LABEL_MARGIN);

        desktopShortcutCheckBox = new HandsFreeCheckBox("Create a desktop shortcut");
        startMenuShortcutCheckBox = new HandsFreeCheckBox("Create a start menu shortcut");
        VBox checkBoxHBox = new VBox(Const.BOX_SPACING, desktopShortcutCheckBox, startMenuShortcutCheckBox);

        addButton("Cancel", HandsFreeButtonPalette.DEFAULT_PALETTE, Platform::exit);
        addButton("< Back", HandsFreeButtonPalette.DEFAULT_PALETTE, () -> application.getNavigator().navigateTo(SceneType.DIRECTORY_CHOOSER));
        addButton("Next >", HandsFreeButtonPalette.PRIMARY_PALETTE, () -> {
            application.getNavigator().navigateTo(SceneType.END);
        });

        getBorderPane().setTop(label);
        getBorderPane().setCenter(checkBoxHBox);
    }

    public void createShortcuts(InstallerApplication application) {
        ShellLink shortcut = ShellLink.createLink(application.getSelectedPath() + "/" + Const.FILE_NAME)
                                      .setIconLocation(application.getSelectedPath().getAbsolutePath() + "/" + Const.ICON_NAME);
        setDesktopShortcut(shortcut);
        setStartMenuShortcut(shortcut);
    }

    private void setDesktopShortcut(ShellLink shortcut) {
        if(desktopShortcutCheckBox.isSelected()) {
            try {
                shortcut.saveTo(System.getProperty("user.home") + "/Desktop/HandsFree.lnk");
            } catch(AccessDeniedException e) {
                Error.reportMinor(ErrorMessages.MISSING_PRIVILEGES);
            } catch(Exception e) {
                Error.reportMinor(ErrorMessages.DESKTOP_SHORTCUT);
            }
        }
    }

    private void setStartMenuShortcut(ShellLink shortcut) {
        if(startMenuShortcutCheckBox.isSelected()) {
            try {
                shortcut.saveTo("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\HandsFree.lnk");
            } catch(AccessDeniedException e) {
                Error.reportMinor(ErrorMessages.MISSING_PRIVILEGES);
            } catch(Exception e) {
                Error.reportMinor(ErrorMessages.START_MENU_SHORTCUT);
            }
        }
    }
}
