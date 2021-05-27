package ch.bbcag.handsfree;

import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ApplicationErrorMessages;
import ch.bbcag.handsfree.error.HandsFreeRobotException;
import ch.bbcag.handsfree.error.NativeException;
import ch.bbcag.handsfree.gui.HandsFreeIconifiedWidget;
import ch.bbcag.handsfree.gui.HandsFreeSceneConfiguration;
import ch.bbcag.handsfree.gui.dialog.HandsFreeConfirmDialog;
import ch.bbcag.handsfree.scenes.*;
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
            context = new HandsFreeContext(primaryStage);
            context.getShortcutManager().readShortcuts(new File(ApplicationConstants.SHORTCUT_FOLDER));

            initGUI();
        } catch(HandsFreeRobotException e) {
            Error.reportCritical(ApplicationErrorMessages.ROBOT, e);
        } catch(NativeException e) {
            Error.reportCritical(ApplicationErrorMessages.LIBRARY, e);
        }
    }

    private void initGUI() {
        configuration = new HandsFreeSceneConfiguration();
        configuration.setTitle("HandsFree");

        MainMenu mainMenu = new MainMenu(this, context);
        ShortcutMenu shortcutMenu = new ShortcutMenu(this, context);
        CommandsList commandsList = new CommandsList(this, context);

        navigator.registerScene(SceneType.MAIN_MENU, mainMenu);
        navigator.registerScene(SceneType.SHORTCUT_MENU, shortcutMenu);
        navigator.registerScene(SceneType.COMMANDS_LIST, commandsList);
        navigator.navigateTo(SceneType.MAIN_MENU);

        primaryStage.setOnCloseRequest(event -> {
            showCloseDialog();
            event.consume(); // Prevent stage closing
        });

        primaryStage.setTitle("HandsFree");
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
                if(primaryStage.isShowing()) {
                    Window w = Stage.getWindows().stream().filter(Window::isFocused).findFirst().orElse(null);
                    if(w == null) { // No window of this application is focused
                        primaryStage.setIconified(true);
                    }
                }
            }
        }.start();
    }

    @Override
    public void stop() {
        // Sometimes there are still threads running that prevent shutdown
        // event though every thread should be a daemon thread.
        // This is why we just exit here so the application doesn't continue
        // running in the background.
        System.exit(0);
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
