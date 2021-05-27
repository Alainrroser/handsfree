package ch.bbcag.uninstaller;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.gui.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.scenes.Navigator;
import ch.bbcag.uninstaller.error.ErrorMessages;
import ch.bbcag.uninstaller.scenes.End;
import ch.bbcag.uninstaller.scenes.SceneType;
import ch.bbcag.uninstaller.scenes.Start;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class UninstallerApplication extends Application {

    private Stage primaryStage;
    private HandsFreeSceneConfiguration configuration;
    private Navigator navigator = new Navigator();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Error.initGlobalExceptionHandler();

        initGUI();
    }

    private void initGUI() {
        configuration = new HandsFreeSceneConfiguration();
        configuration.setTitle("HandsFree Installer");

        navigator.registerScene(SceneType.START, new Start(this));
        navigator.registerScene(SceneType.END, new End(this));
        navigator.navigateTo(SceneType.START);

        primaryStage.setOnCloseRequest(event -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Exit", "Do you really want to exit?");
            dialog.setOnConfirmed(Platform::exit);
            dialog.show();
            event.consume(); // Prevent stage closing
        });

        primaryStage.setTitle("HandsFree Installer");
        primaryStage.show();
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public HandsFreeSceneConfiguration getConfiguration() {
        return configuration;
    }

    public void execute() {
        try {
            deleteFileOrFolderIfExists(new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\HandsFree.lnk"));
            deleteFileOrFolderIfExists(new File(System.getProperty("user.home") + "\\Desktop\\HandsFree.lnk"));
            deleteFolder(new File(System.getProperty("user.home") + "/AppData/Local/HandsFree"));
            deleteFolder(new File(End.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile());
        } catch(Exception e) {
            Error.reportMinor(ErrorMessages.DELETE);
        }

    }

    private void deleteFolder(File folder) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "ping", "localhost", "-n", "6", ">", "nul", "&&", "rmdir",
                                                           folder.getAbsolutePath(), "/S", "/Q");
        processBuilder.directory(new File(folder.getParentFile().getAbsolutePath()));
        processBuilder.start();
    }

    private void deleteFileOrFolderIfExists(File file) {
        if(file.exists()) {
            file.delete();
        }
    }
}
