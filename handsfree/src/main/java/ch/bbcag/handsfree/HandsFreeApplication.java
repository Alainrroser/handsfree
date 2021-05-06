package ch.bbcag.handsfree;

import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.HandsFreeRobotException;
import ch.bbcag.handsfree.gui.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.gui.button.HandsFreeIconButton;
import ch.bbcag.handsfree.gui.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.scenes.MainMenu;
import ch.bbcag.handsfree.scenes.Navigator;
import ch.bbcag.handsfree.scenes.SceneType;
import ch.bbcag.handsfree.scenes.ShortcutMenu;
import ch.bbcag.handsfree.control.shortcuts.ShortcutManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class HandsFreeApplication extends Application {

    private Stage primaryStage;
    private HandsFreeSceneConfiguration configuration;
    private Navigator navigator = new Navigator();
    private HandsFreeContext context;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        try {
            context = new HandsFreeContext();
        } catch(HandsFreeRobotException e) {
            Error.reportAndExit("Input Control", "The application is not allowed to generate mouse and keyboard input. Please check that your system supports input generation.", e);
        }

        configuration = new HandsFreeSceneConfiguration();
        configuration.setTitle("HandsFree");

        MainMenu mainMenu = new MainMenu(this, context);
        ShortcutMenu shortcutMenu = new ShortcutMenu(this, context);

        navigator.registerScene(SceneType.MAIN_MENU, mainMenu);
        navigator.registerScene(SceneType.SHORTCUT_MENU, shortcutMenu);
        navigator.navigateTo(SceneType.MAIN_MENU);

        primaryStage.setOnCloseRequest(event -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Exit", "Do you really want to exit?");
            dialog.setOnConfirmed(() -> {
                context.getEyeTracker().stop();
                Platform.exit();
            });
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
