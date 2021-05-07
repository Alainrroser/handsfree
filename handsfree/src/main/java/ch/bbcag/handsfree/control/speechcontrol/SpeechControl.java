package ch.bbcag.handsfree.control.speechcontrol;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.shortcuts.Shortcut;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

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
        context.getSpeechRecognizer().addListener("new tab", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_T));
        context.getSpeechRecognizer().addListener("close tab", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_W));
        context.getSpeechRecognizer().addListener("task manager", () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_ESCAPE));
        context.getSpeechRecognizer().addListener("close", () -> robot.pressHotkey(KeyEvent.VK_ALT, KeyEvent.VK_F4));
        context.getSpeechRecognizer().addListener("switch open", () -> {
            robot.keyPressSpecial(KeyEvent.VK_ALT);
            robot.keyPressSpecial(KeyEvent.VK_TAB);
        });
        context.getSpeechRecognizer().addListener("switch close", () -> {
            robot.keyReleaseSpecial(KeyEvent.VK_ALT);
            robot.keyReleaseSpecial(KeyEvent.VK_TAB);
        });
        
        for(Shortcut shortcut : context.getShortcutManager().getShortcuts()) {
            context.getSpeechRecognizer().addListener(shortcut.getName(), () -> context.getShortcutManager().runShortcut(shortcut.getName()));
        }
    }

}
