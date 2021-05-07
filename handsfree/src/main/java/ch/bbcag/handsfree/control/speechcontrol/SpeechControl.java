package ch.bbcag.handsfree.control.speechcontrol;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.shortcuts.Shortcut;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SpeechControl {
    
    public SpeechControl(HandsFreeContext context) {
        HandsFreeRobot robot = context.getRobot();
        
        String press = "press";
        context.getSpeechRecognizer().addListener(press, () -> robot.mousePress(InputEvent.BUTTON1_DOWN_MASK));
        context.getSpeechRecognizer().addCommand(press);
    
        String release = "release";
        context.getSpeechRecognizer().addListener(release, () -> robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK));
        context.getSpeechRecognizer().addCommand(release);
    
        String left = "left";
        context.getSpeechRecognizer().addListener(left, () -> robot.keyTypeSpecial(KeyEvent.VK_LEFT));
        context.getSpeechRecognizer().addCommand(left);
    
        String up = "up";
        context.getSpeechRecognizer().addListener(up, () -> robot.keyTypeSpecial(KeyEvent.VK_UP));
        context.getSpeechRecognizer().addCommand(up);
        
        String right = "right";
        context.getSpeechRecognizer().addListener(right, () -> robot.keyTypeSpecial(KeyEvent.VK_RIGHT));
        context.getSpeechRecognizer().addCommand(right);
        
        String down = "down";
        context.getSpeechRecognizer().addListener(down, () -> robot.keyTypeSpecial(KeyEvent.VK_DOWN));
        context.getSpeechRecognizer().addCommand(down);
        
        String scrollDown = "scroll down";
        context.getSpeechRecognizer().addListener(scrollDown, () -> robot.scrollContinuously(10));
        context.getSpeechRecognizer().addCommand(scrollDown);
        
        String scrollUp = "scroll up";
        context.getSpeechRecognizer().addListener(scrollUp, () -> robot.scrollContinuously(-10));
        context.getSpeechRecognizer().addCommand(scrollUp);
        
        String newTab = "new tab";
        context.getSpeechRecognizer().addListener(newTab, () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_T));
        context.getSpeechRecognizer().addCommand(newTab);
        
        String closeTab = "close tab";
        context.getSpeechRecognizer().addListener(closeTab, () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_W));
        context.getSpeechRecognizer().addCommand(closeTab);
        
        String taskManager = "task manager";
        context.getSpeechRecognizer().addListener(taskManager, () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_ESCAPE));
        context.getSpeechRecognizer().addCommand(taskManager);
        
        String close = "close";
        context.getSpeechRecognizer().addListener(close, () -> robot.pressHotkey(KeyEvent.VK_ALT, KeyEvent.VK_F4));
        context.getSpeechRecognizer().addCommand(close);
        
        String switchOpen = "switch open";
        context.getSpeechRecognizer().addListener(switchOpen, () -> {
            robot.keyPressSpecial(KeyEvent.VK_ALT);
            robot.keyPressSpecial(KeyEvent.VK_TAB);
        });
        context.getSpeechRecognizer().addCommand(switchOpen);
        
        String switchClose = "switch close";
        context.getSpeechRecognizer().addListener(switchClose, () -> {
            robot.keyReleaseSpecial(KeyEvent.VK_ALT);
            robot.keyReleaseSpecial(KeyEvent.VK_TAB);
        });
        context.getSpeechRecognizer().addCommand(switchClose);
        
        for(Shortcut shortcut : context.getShortcutManager().getShortcuts()) {
            context.getSpeechRecognizer().addListener(shortcut.getName(), () -> context.getShortcutManager().runShortcut(shortcut.getName()));
        }
    }

}
