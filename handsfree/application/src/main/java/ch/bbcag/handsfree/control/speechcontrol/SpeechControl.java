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
        
        context.getSpeechRecognizer().addListener("press", () -> robot.mousePress(InputEvent.BUTTON1_DOWN_MASK));
        context.getSpeechRecognizer().addListener("release", () -> robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK));
        
        context.getSpeechRecognizer().addListener("left", () -> robot.keyTypeSpecial(KeyEvent.VK_LEFT));
        context.getSpeechRecognizer().addListener("up", () -> robot.keyTypeSpecial(KeyEvent.VK_UP));
        context.getSpeechRecognizer().addListener("right", () -> robot.keyTypeSpecial(KeyEvent.VK_RIGHT));
        context.getSpeechRecognizer().addListener("down", () -> robot.keyTypeSpecial(KeyEvent.VK_DOWN));
        
        context.getSpeechRecognizer().addListener("scroll down", () -> robot.scrollContinuously(10));
        context.getSpeechRecognizer().addListener("scroll up", () -> robot.scrollContinuously(-10));
        
        context.getSpeechRecognizer().addListener("browser", () -> {
            if(Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("https://www.google.com"));
                } catch(Exception e) {
                    Error.reportMinor("Your standard browser could not be opened!");
                }
            }
        });
        context.getSpeechRecognizer().addListener("new tab", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_T));
        context.getSpeechRecognizer().addListener("close tab", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_W));
    
        context.getSpeechRecognizer().addListener("shut down", () -> Platform.runLater(() -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Shutdown", "Do you really want to shutdown your computer?");
            dialog.setOnConfirmed(() -> {
                try {
                    Runtime.getRuntime().exec("shutdown -s");
                } catch(IOException e) {
                    Error.reportMinor("The system could not be shut down!");
                }
            });
            dialog.show();
        }));
        context.getSpeechRecognizer().addListener("restart", () -> Platform.runLater(() -> {
            HandsFreeConfirmDialog dialog = new HandsFreeConfirmDialog("Restart", "Do you really want to restart your computer?");
            dialog.setOnConfirmed(() -> {
                try {
                    Runtime.getRuntime().exec("shutdown -r");
                } catch(IOException e) {
                    Error.reportMinor("The system could not be restarted!");
                }
            });
            dialog.show();
        }));
        
        context.getSpeechRecognizer().addListener("windows", () -> robot.keyTypeSpecial(91));
        context.getSpeechRecognizer().addListener("notifications", () -> robot.pressHotkey(91, KeyEvent.VK_A));
        context.getSpeechRecognizer().addListener("desktop", () -> robot.pressHotkey(91, KeyEvent.VK_D));
        context.getSpeechRecognizer().addListener("explorer", () -> robot.pressHotkey(91, KeyEvent.VK_E));
        context.getSpeechRecognizer().addListener("settings", () -> robot.pressHotkey(91, KeyEvent.VK_I));
        context.getSpeechRecognizer().addListener("lock", () -> robot.pressHotkey(91, KeyEvent.VK_L));
        context.getSpeechRecognizer().addListener("minimize all", () -> robot.pressHotkey(91, KeyEvent.VK_M));
        context.getSpeechRecognizer().addListener("run", () -> robot.pressHotkey(91, KeyEvent.VK_R));
        context.getSpeechRecognizer().addListener("search", () -> robot.pressHotkey(91, KeyEvent.VK_S));
        context.getSpeechRecognizer().addListener("clipboard", () -> robot.pressHotkey(91, KeyEvent.VK_V));
        context.getSpeechRecognizer().addListener("admin menu", () -> robot.pressHotkey(91, KeyEvent.VK_X));
        context.getSpeechRecognizer().addListener("maximize", () -> robot.pressHotkey(91, KeyEvent.VK_UP));
        context.getSpeechRecognizer().addListener("minimize", () -> robot.pressHotkey(91, KeyEvent.VK_DOWN));
        context.getSpeechRecognizer().addListener("screenshot", () -> robot.pressHotkey(91, 0x2C));
        
        context.getSpeechRecognizer().addListener("select all", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_A));
        context.getSpeechRecognizer().addListener("copy", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_C));
        context.getSpeechRecognizer().addListener("delete", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_D));
        context.getSpeechRecognizer().addListener("search", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_F));
        context.getSpeechRecognizer().addListener("refresh", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_R));
        context.getSpeechRecognizer().addListener("paste", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_V));
        context.getSpeechRecognizer().addListener("close explorer", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_W));
        context.getSpeechRecognizer().addListener("cut", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_X));
        context.getSpeechRecognizer().addListener("redo", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_Y));
        context.getSpeechRecognizer().addListener("undo", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_Z));
        context.getSpeechRecognizer().addListener("new folder", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_N));
        context.getSpeechRecognizer().addListener("task manager", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_ESCAPE));
        context.getSpeechRecognizer().addListener("menu", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_DELETE));
        
        context.getSpeechRecognizer().addListener("close", () -> robot.pressHotkey(KeyEvent.VK_ALT, KeyEvent.VK_F4));
        context.getSpeechRecognizer().addListener("switch open", () -> {
            robot.keyPressSpecial(KeyEvent.VK_ALT);
            robot.keyPressSpecial(KeyEvent.VK_TAB);
        });
        context.getSpeechRecognizer().addListener("switch close", () -> {
            robot.keyReleaseSpecial(KeyEvent.VK_ALT);
            robot.keyReleaseSpecial(KeyEvent.VK_TAB);
        });
    
        context.getSpeechRecognizer().addListener("rename", () -> robot.keyTypeSpecial(KeyEvent.VK_F2));
        context.getSpeechRecognizer().addListener("explorer search", () -> robot.keyTypeSpecial(KeyEvent.VK_F3));
        context.getSpeechRecognizer().addListener("explorer path", () -> robot.keyTypeSpecial(KeyEvent.VK_F4));
        context.getSpeechRecognizer().addListener("update view", () -> robot.keyTypeSpecial(KeyEvent.VK_F5));
        context.getSpeechRecognizer().addListener("full", () -> robot.keyTypeSpecial(KeyEvent.VK_F11));
        context.getSpeechRecognizer().addListener("escape", () -> robot.keyTypeSpecial(KeyEvent.VK_ESCAPE));
    }

    public void addListenerForShortcut(String name) {
        context.getSpeechRecognizer().addListener(name, () -> context.getShortcutManager().runShortcut(name));
    }

}
