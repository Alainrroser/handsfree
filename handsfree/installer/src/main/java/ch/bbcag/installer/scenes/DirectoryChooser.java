package ch.bbcag.installer.scenes;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeLabel;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import ch.bbcag.installer.Const;
import ch.bbcag.installer.InstallerApplication;
import ch.bbcag.installer.error.ErrorMessages;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DirectoryChooser extends InstallerScene {

    private HandsFreeLabel directoryText;

    public DirectoryChooser(InstallerApplication application) {
        super(application.getPrimaryStage(), application.getConfiguration());

        initGUI(application);
    }

    private void initGUI(InstallerApplication application) {
        HandsFreeLabel label = new HandsFreeLabel(
                "Select a path where to install the program down below or leave it at the standard path and continue."
        );
        label.setWrapText(true);
        BorderPane.setMargin(label, Const.LABEL_MARGIN);

        application.setSelectedPath(new File("C:/HandsFree/"));
        directoryText = new HandsFreeLabel(application.getSelectedPath().getAbsolutePath());

        HBox directory = initDirectorySelector(application);

        addButton("Back", HandsFreeButtonPalette.DEFAULT_PALETTE, () -> application.getNavigator().navigateTo(SceneType.START));
        addButton("Next", HandsFreeButtonPalette.PRIMARY_PALETTE, () -> {
            try {
                saveFilesToSelectedPath(application);
                application.getNavigator().navigateTo(SceneType.SHORTCUT);
            } catch(IOException e) {
                Error.reportMinor(ErrorMessages.MISSING_PRIVILEGES);
            }
        });
        addButton("Cancel", HandsFreeButtonPalette.DEFAULT_PALETTE, Platform::exit);

        getBorderPane().setTop(label);
        getBorderPane().setCenter(directory);
    }

    private HBox initDirectorySelector(InstallerApplication application) {
        javafx.stage.DirectoryChooser directoryChooser = new javafx.stage.DirectoryChooser();
        if(application.getSelectedPath().exists()) {
            directoryChooser.setInitialDirectory(application.getSelectedPath());
        }

        HandsFreeTextButton directorySelector = new HandsFreeTextButton("Select...");
        directorySelector.setPrefWidth(Const.BUTTON_WIDTH);
        directorySelector.setPadding(Const.BUTTON_PADDING);
        directorySelector.setOnAction(e -> {
            File chosenPath = directoryChooser.showDialog(application.getPrimaryStage());
            setSelectedPathAndText(application, chosenPath);
        });

        HBox hBox = new HBox(Const.BOX_SPACING, directoryText, directorySelector);
        hBox.maxHeightProperty().bind(directorySelector.heightProperty());
        hBox.setAlignment(Pos.CENTER_LEFT);
        BorderPane.setAlignment(hBox, Pos.TOP_LEFT);

        return hBox;
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

    private void createFileAndParentFileIfNotCreated(File targetFile) throws IOException {
        if(targetFile.getParentFile() != null) {
            targetFile.getParentFile().mkdirs();
        }

        if(!targetFile.exists()) {
            targetFile.createNewFile();
        }
    }

    private void copyFiles(InputStream inputStream, File targetFile) throws IOException {
        Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private void saveFilesToSelectedPath(InstallerApplication application) throws IOException {
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
