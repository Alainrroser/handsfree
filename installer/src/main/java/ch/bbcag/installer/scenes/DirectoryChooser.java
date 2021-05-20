package ch.bbcag.installer.scenes;

import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DirectoryChooser extends Scene {

    private InstallerApplication installerApplication;
    private Label directoryText;

    public DirectoryChooser(InstallerApplication installerApplication) {
        super(new VBox());
        this.installerApplication = installerApplication;
        installerApplication.setSelectedPath(new File("C:\\Program Files\\HandsFree"));
        directoryText = new Label(installerApplication.getSelectedPath().getAbsolutePath());

        VBox root = (VBox) getRoot();
        root.setSpacing(Const.BOX_SPACING);
        root.setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT));

        Label label = new Label("Okay, you have reached the next step. \n" +
                                "Select a path where to install the program down below or leave it at the standard path and continue.");

        javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
        directoryChooser.setInitialDirectory(installerApplication.getSelectedPath());

        Button directorySelector = new Button("Select a directory");
        directorySelector.setOnAction(e -> {
            installerApplication.setSelectedPath(directoryChooser.showDialog(installerApplication.getPrimaryStage()));
            directoryText.setText(installerApplication.getSelectedPath().getAbsolutePath());
        });
        HBox directory = new HBox(Const.BOX_SPACING, directoryText, directorySelector);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> installerApplication.getNavigator().navigateTo(SceneType.START));

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(event -> {
            if(!installerApplication.getSelectedPath().exists()) {
                installerApplication.getSelectedPath().mkdirs();
            }

            File targetJarFile = new File(installerApplication.getSelectedPath().getAbsolutePath() + "/" + Const.FILE_NAME);
            if(targetJarFile.getParentFile() != null) {
                targetJarFile.getParentFile().mkdirs();
            }
            try {
                if(!targetJarFile.exists()) {
                    targetJarFile.createNewFile();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

            File targetIconFile = new File(installerApplication.getSelectedPath().getAbsolutePath() + "/" + Const.ICON_NAME);
            if(targetIconFile.getParentFile() != null) {
                targetIconFile.getParentFile().mkdirs();
            }
            try {
                if(!targetIconFile.exists()) {
                    targetIconFile.createNewFile();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

            InputStream jarInputStream = DirectoryChooser.class.getResourceAsStream("/" + Const.FILE_NAME);
            InputStream iconInputStream = DirectoryChooser.class.getResourceAsStream("/" + Const.ICON_NAME);

            try {
                Files.copy(jarInputStream, targetJarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(iconInputStream, targetIconFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch(Exception e) {
                e.printStackTrace();
            }
            installerApplication.getNavigator().navigateTo(SceneType.SHORTCUT);
        });

        HBox buttonHBox = new HBox(Const.BOX_SPACING, backButton, continueButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        root.getChildren().addAll(label, directory, buttonHBox);
    }
}
