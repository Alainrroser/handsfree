package ch.bbcag.installer;

import ch.bbcag.installer.scenes.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class InstallerApplication extends Application {

    private Stage primaryStage;
    private Navigator navigator;

    private File selectedPath;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        navigator = new Navigator(primaryStage);
        navigator.registerScene(SceneType.START, new Start(this));
        navigator.registerScene(SceneType.DIRECTORY_CHOOSER, new DirectoryChooser(this));
        navigator.registerScene(SceneType.SHORTCUT, new Shortcut(this));
        navigator.registerScene(SceneType.END, new End(this));

        primaryStage.setTitle("HandsFree Installer");
        navigator.navigateTo(SceneType.START);
        primaryStage.show();
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public File getSelectedPath() {
        return selectedPath;
    }

    public void setSelectedPath(File selectedPath) {
        this.selectedPath = selectedPath;
    }
}
