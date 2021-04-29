package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.control.button.HandsFreeButtonPalette;
import ch.bbcag.handsfree.control.button.HandsFreeIconButton;
import ch.bbcag.handsfree.control.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.scenes.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class HandsFreeApplication extends Application {

    private Stage primaryStage;
    private HandsFreeSceneConfiguration configuration;
    private Navigator navigator = new Navigator();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        configuration = new HandsFreeSceneConfiguration();
        String pageTitle = "HandsFree";
        configuration.setTitle(pageTitle);

        navigator.registerScene(SceneType.MAIN_MENU, new MainMenu(this));
        navigator.registerScene(SceneType.SHORTCUT_MENU, new ShortcutMenu(this));
        navigator.navigateTo(SceneType.MAIN_MENU);

        primaryStage.setOnCloseRequest(event -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Exit", "Do you really want to exit?");
            dialog.setOnConfirmed(Platform::exit);
            dialog.show();

            event.consume(); // Prevent stage closing
        });
        
        primaryStage.show();
        
        primaryStage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                primaryStage.hide();
                
                Stage floatingStage = new Stage();
                Group groupRoot = new Group();
                
                HandsFreeIconButton floatingWidget = new HandsFreeIconButton("/images/icon32.png");
                floatingWidget.setPrefSize(64, 64);
                floatingWidget.setOnAction(event -> {
                    primaryStage.show();
                    primaryStage.setIconified(false);
                    navigator.navigateTo(SceneType.MAIN_MENU);
                    floatingStage.close();
                });
                groupRoot.getChildren().add(floatingWidget);
                Scene scene = new Scene(groupRoot);
                
                double taskbarHeight = Toolkit.getDefaultToolkit().getScreenSize().height - GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
                int marginRight = 32;
                int marginBottom = 24;
                
                floatingStage.setScene(scene);
                floatingStage.setTitle("HandsFree");
                floatingStage.initStyle(StageStyle.UNDECORATED);
                floatingStage.setX(Toolkit.getDefaultToolkit().getScreenSize().width - floatingWidget.getPrefWidth() - marginRight);
                floatingStage.setY(Toolkit.getDefaultToolkit().getScreenSize().height - floatingWidget.getPrefHeight() - taskbarHeight - marginBottom);
                floatingStage.show();
            }
        });
        
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public HandsFreeSceneConfiguration getConfiguration() {
        return configuration;
    }
    
    public Navigator getNavigator() {
        return navigator;
    }
}
