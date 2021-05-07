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
        
        String press = "press";
        context.getSpeechRecognizer().addListener(press, () -> robot.mousePress(InputEvent.BUTTON1_DOWN_MASK));
        addCommandToList(press);
    
        String release = "release";
        context.getSpeechRecognizer().addListener(release, () -> robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK));
        addCommandToList(release);
    
        String left = "left";
        context.getSpeechRecognizer().addListener(left, () -> robot.keyTypeSpecial(KeyEvent.VK_LEFT));
        addCommandToList(left);
    
        String up = "up";
        context.getSpeechRecognizer().addListener(up, () -> robot.keyTypeSpecial(KeyEvent.VK_UP));
        addCommandToList(up);
        
        String right = "right";
        context.getSpeechRecognizer().addListener(right, () -> robot.keyTypeSpecial(KeyEvent.VK_RIGHT));
        addCommandToList(right);
        
        String down = "down";
        context.getSpeechRecognizer().addListener(down, () -> robot.keyTypeSpecial(KeyEvent.VK_DOWN));
        addCommandToList(down);
        
        String scrollDown = "scroll down";
        context.getSpeechRecognizer().addListener(scrollDown, () -> robot.scrollContinuously(10));
        addCommandToList(scrollDown);
        
        String scrollUp = "scroll up";
        context.getSpeechRecognizer().addListener(scrollUp, () -> robot.scrollContinuously(-10));
        addCommandToList(scrollUp);
        
        String newTab = "new tab";
        context.getSpeechRecognizer().addListener(newTab, () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_T));
        addCommandToList(newTab);
        
        String closeTab = "close tab";
        context.getSpeechRecognizer().addListener(closeTab, () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_W));
        addCommandToList(closeTab);
        
        String taskManager = "task manager";
        context.getSpeechRecognizer().addListener(taskManager, () -> robot.pressHotkey(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_ESCAPE));
        addCommandToList(taskManager);
        
        String close = "close";
        context.getSpeechRecognizer().addListener(close, () -> robot.pressHotkey(KeyEvent.VK_ALT, KeyEvent.VK_F4));
        addCommandToList(close);
        
        String switchOpen = "switch open";
        context.getSpeechRecognizer().addListener(switchOpen, () -> {
            robot.keyPressSpecial(KeyEvent.VK_ALT);
            robot.keyPressSpecial(KeyEvent.VK_TAB);
        });
        addCommandToList(switchOpen);
        
        String switchClose = "switch close";
        context.getSpeechRecognizer().addListener(switchClose, () -> {
            robot.keyReleaseSpecial(KeyEvent.VK_ALT);
            robot.keyReleaseSpecial(KeyEvent.VK_TAB);
        });
        addCommandToList(switchClose);
        
        for(Shortcut shortcut : context.getShortcutManager().getShortcuts()) {
            context.getSpeechRecognizer().addListener(shortcut.getName(), () -> context.getShortcutManager().runShortcut(shortcut.getName()));
        }
    }
    
    private void addCommandToList(String command) {
        context.getSpeechRecognizer().addCommand(command);
    }

}
