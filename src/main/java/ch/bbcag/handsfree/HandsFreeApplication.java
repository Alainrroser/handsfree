package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.control.HandsFreeStageDecoration;
import ch.bbcag.handsfree.scenes.MainMenu;
import ch.bbcag.handsfree.scenes.Navigator;
import ch.bbcag.handsfree.scenes.SceneType;

import ch.bbcag.handsfree.scenes.ShortcutMenu;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HandsFreeApplication extends Application {

    private Stage primaryStage;
    private HandsFreeSceneConfiguration configuration;
    private VBox vBox;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        configuration = new HandsFreeSceneConfiguration();
        String pageTitle = "HandsFree";
        configuration.setTitle(pageTitle);
        
        vBox = new VBox();
        
        Navigator navigator = new Navigator();
        navigator.registerScene(SceneType.MAIN_MENU, new MainMenu(navigator, this));
        navigator.registerScene(SceneType.SHORTCUT_MENU, new ShortcutMenu(navigator, this));
        navigator.navigateTo(SceneType.MAIN_MENU);
        
        primaryStage.show();
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public HandsFreeSceneConfiguration getConfiguration() {
        return configuration;
    }
    
    public VBox getVBox() {
        return vBox;
    }
}
