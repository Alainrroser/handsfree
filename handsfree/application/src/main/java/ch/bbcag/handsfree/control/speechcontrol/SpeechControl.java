package ch.bbcag.handsfree.control.speechcontrol;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.gui.dialog.HandsFreeConfirmDialog;
import javafx.application.Platform;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;

public class SpeechControl {

    private HandsFreeContext context;

    public SpeechControl(HandsFreeContext context) {
        this.context = context;
        HandsFreeRobot robot = context.getRobot();

        addCommand(new Command("press", "Left Mouse Button Press", () -> robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)));
        addCommand(new Command("release", "Left Mouse Button Release", () -> robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)));

        addCommand(new Command("left", "Left arrow", () -> robot.keyTypeSpecial(KeyEvent.VK_LEFT)));
        addCommand(new Command("up", "Up arrow", () -> robot.keyTypeSpecial(KeyEvent.VK_UP)));
        addCommand(new Command("right", "Right arrow", () -> robot.keyTypeSpecial(KeyEvent.VK_RIGHT)));
        addCommand(new Command("down", "Down arrow", () -> robot.keyTypeSpecial(KeyEvent.VK_DOWN)));

        addCommand(new Command("scroll down", "scrolls down", () -> robot.scrollContinuously(10)));
        addCommand(new Command("scroll down", "scrolls up", () -> robot.scrollContinuously(-10)));

        addCommand(new Command("browser", "starts standard browser", () -> {
            if(Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("https://www.google.com"));
                } catch(Exception e) {
                    Error.reportMinor("Your standard browser could not be opened!");
                }
            }
        }));
        addCommand(new Command("new tab", "Makes new tab", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_T)));
        addCommand(new Command("close tab", "closes tab", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_W)));

        addCommand(new Command("shut down", "Shuts the computer down", () -> Platform.runLater(() -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Shutdown", "Do you really want to shutdown your computer?");
            dialog.setOnConfirmed(() -> {
                try {
                    Runtime.getRuntime().exec("shutdown -s");
                } catch(IOException e) {
                    Error.reportMinor("The system could not be shut down!");
                }
            });
            dialog.show();
        })));
        addCommand(new Command("restart", "Restarts the computer", () -> Platform.runLater(() -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Restart", "Do you really want to restart your computer?");
            dialog.setOnConfirmed(() -> {
                try {
                    Runtime.getRuntime().exec("shutdown -r");
                } catch(IOException e) {
                    Error.reportMinor("The system could not be restarted!");
                }
            });
            dialog.show();
        })));

        addCommand(new Command("windows", "Presses windows key", () -> robot.keyTypeSpecial(91)));
        addCommand(new Command("notifications", "Opens notifications panel", () -> robot.pressHotkey(91, KeyEvent.VK_A)));
        addCommand(new Command("desktop", "Goes to desktop", () -> robot.pressHotkey(91, KeyEvent.VK_D)));
        addCommand(new Command("explorer", "Opens explorer", () -> robot.pressHotkey(91, KeyEvent.VK_E)));
        addCommand(new Command("Settings", "Opens settings", () -> robot.pressHotkey(91, KeyEvent.VK_I)));
        addCommand(new Command("lock", "Presses windows key", () -> robot.pressHotkey(91, KeyEvent.VK_L)));
        addCommand(new Command("minimize all", "Minimizes all apps", () -> robot.pressHotkey(91, KeyEvent.VK_M)));
        addCommand(new Command("run", "Opens run", () -> robot.pressHotkey(91, KeyEvent.VK_R)));
        addCommand(new Command("search", "Opens windows search", () -> robot.pressHotkey(91, KeyEvent.VK_S)));
        addCommand(new Command("clipboard", "Opens clipboard", () -> robot.pressHotkey(91, KeyEvent.VK_V)));
        addCommand(new Command("admin menu", "Opens admin menu", () -> robot.pressHotkey(91, KeyEvent.VK_X)));
        addCommand(new Command("maximize", "Maximizes window", () -> robot.pressHotkey(91, KeyEvent.VK_UP)));
        addCommand(new Command("minimize", "Minimizes window", () -> robot.pressHotkey(91, KeyEvent.VK_DOWN)));
        addCommand(new Command("screenshot", "Makes screenshot", () -> robot.pressHotkey(91, 0x2C)));

        addCommand(new Command("select all", "Selects everything", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_A)));
        addCommand(new Command("copy", "Copies selected", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_C)));
        addCommand(new Command("delete", "Deletes selected", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_D)));
        addCommand(new Command("search", "Opens search", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_F)));
        addCommand(new Command("refresh", "Refreshes page", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_R)));
        addCommand(new Command("paste", "Pastes copied or cut", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_V)));
        addCommand(new Command("close explorer", "Closes explorer", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_W)));
        addCommand(new Command("cut", "Cuts selected", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_X)));
        addCommand(new Command("redo", "Redoes something", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_Y)));
        addCommand(new Command("undo", "Undoes something", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_Z)));
        addCommand(new Command("new folder", "Creates new folder", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_N)));
        addCommand(new Command("task manager", "Opens task manager", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT,
                                                                                             KeyEvent.VK_ESCAPE)));
        addCommand(new Command("menu", "Opens menu", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_DELETE)));

        addCommand(new Command("close", "Closes window", () -> robot.pressHotkey(KeyEvent.VK_ALT, KeyEvent.VK_F4)));
        addCommand(new Command("switch open", "Opens switch", () -> {
            robot.keyPressSpecial(KeyEvent.VK_ALT);
            robot.keyPressSpecial(KeyEvent.VK_TAB);
        }));
        addCommand(new Command("switch close", "Closes switch", () -> {
            robot.keyReleaseSpecial(KeyEvent.VK_ALT);
            robot.keyReleaseSpecial(KeyEvent.VK_TAB);
        }));

        addCommand(new Command("rename", "Renames selected", () -> robot.keyTypeSpecial(KeyEvent.VK_F2)));
        addCommand(new Command("explorer search", "Goes to explorers search", () -> robot.keyTypeSpecial(KeyEvent.VK_F3)));
        addCommand(new Command("explorer path", "Goes to explorers path", () -> robot.keyTypeSpecial(KeyEvent.VK_F4)));
        addCommand(new Command("update view", "Updates view", () -> robot.keyTypeSpecial(KeyEvent.VK_F5)));
        addCommand(new Command("full", "Makes fullscreen", () -> robot.keyTypeSpecial(KeyEvent.VK_F11)));
        addCommand(new Command("escape", "Clicks escape", () -> robot.keyTypeSpecial(KeyEvent.VK_ESCAPE)));
    }

    private void addCommand(Command command) {
        context.getSpeechRecognizer().addListener(command.getName(), command.getSpeechListener());
        context.getSpeechRecognizer().addCommand(command);
    }

    public void addListenerForShortcut(String name) {
        context.getSpeechRecognizer().addListener(name, () -> context.getShortcutManager().runShortcut(name));
    }

}
