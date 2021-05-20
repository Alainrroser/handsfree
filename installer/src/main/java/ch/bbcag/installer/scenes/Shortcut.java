package ch.bbcag.installer.scenes;

import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mslinks.ShellLink;

import java.io.IOException;

public class Shortcut extends Scene {

    private InstallerApplication installerApplication;

    public Shortcut(InstallerApplication installerApplication) {
        super(new VBox());
        this.installerApplication = installerApplication;

        VBox root = (VBox) getRoot();
        root.setSpacing(Const.BOX_SPACING);
        root.setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT));

        Label label = new Label("Okay, here you just have to select if" +
                                "you want to have a desktop and a start menu shortcut" +
                                "Check them if you want and continue.");


        CheckBox desktopShortcutCheckBox = new CheckBox("Create a desktop shortcut");
        CheckBox startMenuShortcutCheckBox = new CheckBox("Create a start menu shortcut");
        HBox checkBoxHBox = new HBox(Const.BOX_SPACING, desktopShortcutCheckBox, startMenuShortcutCheckBox);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> installerApplication.getNavigator().navigateTo(SceneType.DIRECTORY_CHOOSER));

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            ShellLink desktopShortcut = ShellLink.createLink(installerApplication.getSelectedPath() + "/" + Const.FILE_NAME)
                                                 .setIconLocation(installerApplication.getSelectedPath().getAbsolutePath() + "/" + Const.ICON_NAME);
            if(desktopShortcutCheckBox.isSelected()) {
                try {
                    desktopShortcut.saveTo(System.getProperty("user.home") + "/Desktop/HandsFree.lnk");
                } catch(IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if(startMenuShortcutCheckBox.isSelected()) {
                try {
                    desktopShortcut.saveTo("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\HandsFree.lnk");
                } catch(IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            installerApplication.getNavigator().navigateTo(SceneType.END);
        });

        HBox buttonHBox = new HBox(Const.BOX_SPACING, backButton, continueButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        root.getChildren().addAll(label, checkBoxHBox, buttonHBox);
    }
}
