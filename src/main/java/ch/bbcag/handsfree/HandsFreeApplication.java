package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.control.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.scenes.MainMenu;
import ch.bbcag.handsfree.scenes.Navigator;
import ch.bbcag.handsfree.scenes.SceneType;

import ch.bbcag.handsfree.scenes.ShortcutMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class HandsFreeApplication extends Application {

    private Stage primaryStage;
    private HandsFreeSceneConfiguration configuration;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        configuration = new HandsFreeSceneConfiguration();
        String pageTitle = "HandsFree";
        configuration.setTitle(pageTitle);

        Navigator navigator = new Navigator();
        navigator.registerScene(SceneType.MAIN_MENU, new MainMenu(navigator, this));
        navigator.registerScene(SceneType.SHORTCUT_MENU, new ShortcutMenu(navigator, this));
        navigator.navigateTo(SceneType.MAIN_MENU);

        primaryStage.setOnCloseRequest(event -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Exit", "Do you really want to exit?");
            dialog.setOnConfirmed(Platform::exit);
            dialog.show();

            event.consume(); // Prevent stage closing
        });

        primaryStage.show();
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public HandsFreeSceneConfiguration getConfiguration() {
        return configuration;
    }

}
