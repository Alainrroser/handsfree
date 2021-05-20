package ch.bbcag.installer.scenes;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeScene;
import ch.bbcag.handsfree.gui.button.HandsFreeDefaultButton;
import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import ch.bbcag.installer.error.ErrorMessages;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DirectoryChooser extends HandsFreeScene {

    private Label directoryText;

    public DirectoryChooser(InstallerApplication application) {
        super(application.getPrimaryStage(), new VBox(), application.getConfiguration());

        getContentRoot().setMinSize(Const.WIDTH, Const.HEIGHT);
        getContentRoot().setMaxSize(Const.WIDTH, Const.HEIGHT);

        initGUI(application);
    }

    private void initGUI(InstallerApplication application) {
        VBox vBox = (VBox) getContentRoot();
        vBox.setSpacing(Const.BOX_SPACING);
        vBox.setPadding(new Insets(Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT, Const.BOX_PADDING_TOP_BOTTOM, Const.BOX_PADDING_RIGHT_LEFT));

        Label label = new Label("Okay, you have reached the next step. \n" +
                                "Select a path where to install the program down below or leave it at the standard path and continue.");

        application.setSelectedPath(new File("C:\\Program Files\\HandsFree"));
        directoryText = new Label(application.getSelectedPath().getAbsolutePath());

        HBox directory = initDirectorySelector(application);

        HandsFreeDefaultButton backButton = new HandsFreeDefaultButton("Back");
        backButton.setOnAction(e -> application.getNavigator().navigateTo(SceneType.START));

        HandsFreeDefaultButton continueButton = new HandsFreeDefaultButton("Continue");
        continueButton.setOnAction(event -> {
            saveFilesToSelectedPath(application);

            application.getNavigator().navigateTo(SceneType.SHORTCUT);
        });

        HBox buttonHBox = new HBox(Const.BOX_SPACING, backButton, continueButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        vBox.getChildren().addAll(label, directory, buttonHBox);
    }

    private HBox initDirectorySelector(InstallerApplication application) {
        javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
        directoryChooser.setInitialDirectory(application.getSelectedPath());

        HandsFreeDefaultButton directorySelector = new HandsFreeDefaultButton("Select a directory");
        directorySelector.setOnAction(e -> {
            File chosenPath = directoryChooser.showDialog(application.getPrimaryStage());
            setSelectedPathAndText(application, chosenPath);
        });
        return new HBox(Const.BOX_SPACING, directoryText, directorySelector);
    }

    private void setSelectedPathAndText(InstallerApplication application, File chosenPath) {
        if(chosenPath != null) {
            application.setSelectedPath(chosenPath);
            directoryText.setText(application.getSelectedPath().getAbsolutePath());
        }
    }

    private void createSelectedPathIfNotCreated(InstallerApplication application) {
        if(!application.getSelectedPath().exists()) {
            application.getSelectedPath().mkdirs();
        }
    }

    private void createFileAndParentFileIfNotCreated(File targetFile) {
        if(targetFile.getParentFile() != null) {
            targetFile.getParentFile().mkdirs();
        }
        try {
            if(!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch(IOException e) {
            Error.reportCritical(ErrorMessages.COPY_FILE, e);
        }
    }

    private void copyFiles(InputStream inputStream, File targetFile) {
        try {
            Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e) {
            Error.reportCritical(ErrorMessages.COPY_FILE, e);
        }
    }

    private void saveFilesToSelectedPath(InstallerApplication application) {
        createSelectedPathIfNotCreated(application);

        File targetJarFile = new File(application.getSelectedPath().getAbsolutePath() + "/" + Const.FILE_NAME);
        createFileAndParentFileIfNotCreated(targetJarFile);

        File targetIconFile = new File(application.getSelectedPath().getAbsolutePath() + "/" + Const.ICON_NAME);
        createFileAndParentFileIfNotCreated(targetIconFile);

        InputStream jarInputStream = DirectoryChooser.class.getResourceAsStream("/" + Const.FILE_NAME);
        InputStream iconInputStream = DirectoryChooser.class.getResourceAsStream("/" + Const.ICON_NAME);

        copyFiles(jarInputStream, targetJarFile);
        copyFiles(iconInputStream, targetIconFile);
    }
}
