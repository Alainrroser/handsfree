package ch.bbcag.installer;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.gui.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.scenes.Navigator;
import ch.bbcag.installer.scenes.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.File;

public class InstallerApplication extends Application {

    private Stage primaryStage;
    private HandsFreeSceneConfiguration configuration;
    private Navigator navigator = new Navigator();

    private File selectedPath;

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
        navigator.registerScene(SceneType.DIRECTORY_CHOOSER, new DirectoryChooser(this));
        navigator.registerScene(SceneType.SHORTCUT, new Shortcut(this));
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

    public File getSelectedPath() {
        return selectedPath;
    }

    public void setSelectedPath(File selectedPath) {
        this.selectedPath = selectedPath;
    }
}
