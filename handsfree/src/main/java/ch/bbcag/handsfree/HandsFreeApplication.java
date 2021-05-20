package ch.bbcag.handsfree;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
import ch.bbcag.handsfree.error.HandsFreeRobotException;
import ch.bbcag.handsfree.error.NativeException;
import ch.bbcag.handsfree.gui.HandsFreeIconifiedWidget;
import ch.bbcag.handsfree.gui.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.gui.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.scenes.MainMenu;
import ch.bbcag.handsfree.scenes.Navigator;
import ch.bbcag.handsfree.scenes.SceneType;
import ch.bbcag.handsfree.scenes.ShortcutMenu;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

public class HandsFreeApplication extends Application {

    private Stage primaryStage;
    private HandsFreeSceneConfiguration configuration;
    private Navigator navigator = new Navigator();
    private HandsFreeContext context;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Error.initGlobalExceptionHandler();

        try {
            context = new HandsFreeContext();
            context.getShortcutManager().readShortcuts(new File(Const.SHORTCUT_PATH));

            initGUI();
        } catch(HandsFreeRobotException e) {
            Error.reportCritical(ErrorMessages.ROBOT, e);
        } catch(NativeException e) {
            Error.reportCritical(ErrorMessages.LIBRARY, e);
        }
    }

    private void initGUI() {
        configuration = new HandsFreeSceneConfiguration();
        configuration.setTitle("HandsFree");

        MainMenu mainMenu = new MainMenu(this, context);
        ShortcutMenu shortcutMenu = new ShortcutMenu(this, context);

        navigator.registerScene(SceneType.MAIN_MENU, mainMenu);
        navigator.registerScene(SceneType.SHORTCUT_MENU, shortcutMenu);
        navigator.navigateTo(SceneType.MAIN_MENU);

        primaryStage.setOnCloseRequest(event -> {
            showCloseDialog();
            event.consume(); // Prevent stage closing
        });

        primaryStage.show();

        initIconifiedWidget();
        initNothingFocusedCheckLoop();
    }

    private void showCloseDialog() {
        HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Exit", "Do you really want to exit?");
        dialog.setOnConfirmed(() -> {
            context.getEyeTracker().stop();
            context.getSpeechRecognizer().stop();
            context.getRobot().exit();
            Platform.exit();
        });
        dialog.show();
    }

    private void initIconifiedWidget() {
        primaryStage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                primaryStage.hide();
                HandsFreeIconifiedWidget widget = new HandsFreeIconifiedWidget(primaryStage, context);
                widget.show();
            }
        });
    }

    private void initNothingFocusedCheckLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                Window w = Stage.getWindows().stream().filter(Window::isFocused).findFirst().orElse(null);
                if(w == null) { // No window of this application is focused
                    primaryStage.setIconified(true);
                }
            }
        }.start();
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
