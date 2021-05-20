package ch.bbcag.installer.scenes;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeScene;
import ch.bbcag.handsfree.gui.button.HandsFreeDefaultButton;
import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import ch.bbcag.installer.error.ErrorMessages;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mslinks.ShellLink;

public class Shortcut extends HandsFreeScene {

    private CheckBox desktopShortcutCheckBox;
    private CheckBox startMenuShortcutCheckBox;

    public Shortcut(InstallerApplication application) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);

        initGUI(application);
    }

    private void initGUI(InstallerApplication application) {
        VBox vBox = (VBox) getContentRoot();
        vBox.setSpacing(Const.BOX_SPACING);
        vBox.setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT));

        Label label = new Label("Okay, here you just have to select if" +
                                "you want to have a desktop and a start menu shortcut" +
                                "Check them if you want and continue.");

        desktopShortcutCheckBox = new CheckBox("Create a desktop shortcut");
        startMenuShortcutCheckBox = new CheckBox("Create a start menu shortcut");
        HBox checkBoxHBox = new HBox(Const.BOX_SPACING, desktopShortcutCheckBox, startMenuShortcutCheckBox);

        HandsFreeDefaultButton backButton = new HandsFreeDefaultButton("Back");
        backButton.setOnAction(e -> application.getNavigator().navigateTo(SceneType.DIRECTORY_CHOOSER));

        HandsFreeDefaultButton continueButton = new HandsFreeDefaultButton("Continue");
        continueButton.setOnAction(e -> {
            ShellLink shortcut = ShellLink.createLink(application.getSelectedPath() + "/" + Const.FILE_NAME)
                                                 .setIconLocation(application.getSelectedPath().getAbsolutePath() + "/" + Const.ICON_NAME);
            setDesktopShortcut(shortcut);
            setStartMenuShortcut(shortcut);

            application.getNavigator().navigateTo(SceneType.END);
        });

        HBox buttonHBox = new HBox(Const.BOX_SPACING, backButton, continueButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        vBox.getChildren().addAll(label, checkBoxHBox, buttonHBox);
    }

    private void setDesktopShortcut(ShellLink shortcut) {
        if(desktopShortcutCheckBox.isSelected()) {
            try {
                shortcut.saveTo(System.getProperty("user.home") + "/Desktop/HandsFree.lnk");
            } catch(Exception e) {
                Error.reportMinor(ErrorMessages.DESKTOP_SHORTCUT);
            }
        }
    }

    private void setStartMenuShortcut(ShellLink shortcut) {
        if(startMenuShortcutCheckBox.isSelected()) {
            try {
                shortcut.saveTo("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\HandsFree.lnk");
            } catch(Exception e) {
                Error.reportMinor(ErrorMessages.START_MENU_SHORTCUT);
            }
        }
    }
}
