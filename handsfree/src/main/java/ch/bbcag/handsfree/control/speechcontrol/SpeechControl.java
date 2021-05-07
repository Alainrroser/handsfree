package ch.bbcag.handsfree.control.speechcontrol;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeRobot;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        context.getSpeechRecognizer().addListener("scroll down", () -> scroll(5));
        context.getSpeechRecognizer().addListener("scroll up", () -> scroll(-5));
        context.getSpeechRecognizer().addListener("switch open", () -> {
            robot.keyPressSpecial(KeyEvent.VK_ALT);
            robot.keyPressSpecial(KeyEvent.VK_TAB);
        });
        context.getSpeechRecognizer().addListener("switch close", () -> {
            robot.keyReleaseSpecial(KeyEvent.VK_ALT);
            robot.keyReleaseSpecial(KeyEvent.VK_TAB);
        });
        context.getSpeechRecognizer().addListener("new tab", () -> hotkeyCtrl(KeyEvent.VK_T));
        context.getSpeechRecognizer().addListener("close tab", () -> hotkeyCtrl(KeyEvent.VK_W));
    }

    private void scroll(int ticks) {
        try {
            for(int i = 0; i < Math.abs(ticks); i++) {
                context.getRobot().mouseWheel((int) Math.signum(ticks));
                Thread.sleep(20);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void hotkeyCtrl(int hotkey) {
        HandsFreeRobot robot = context.getRobot();
        robot.keyPressSpecial(KeyEvent.VK_CONTROL);
        robot.keyPressSpecial(hotkey);
        robot.keyReleaseSpecial(hotkey);
        robot.keyReleaseSpecial(KeyEvent.VK_CONTROL);
    }

}
