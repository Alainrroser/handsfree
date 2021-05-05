package ch.bbcag.handsfree.control.speechcontrol;

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

    private boolean running = true;

    private HandsFreeRobot robot;

    public SpeechControl(HandsFreeRobot robot) {
        this.robot = robot;
    }

    public void start() {
        running = true;

        try {
            // Disable Logging
            Logger rootLogger = Logger.getLogger("default.config");
            rootLogger.setLevel(Level.OFF);
            String configurationFile = System.getProperty("java.util.logging.config.file");
            if(configurationFile == null) {
                System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
            }
    
            Configuration configuration = new Configuration();
            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            configuration.setGrammarPath("resource:/grammars");
            configuration.setGrammarName("dialog");
            configuration.setUseGrammar(true);
    
            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
            recognizer.startRecognition(true);
    
            startListening(recognizer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        running = false;
    }

    private void scroll(int ticks) {
        try {
            for(int i = 0; i < Math.abs(ticks); i++) {
                robot.mouseWheel((int) Math.signum(ticks));
                Thread.sleep(20);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void hotkeyCtrl(Robot robot, int hotkey) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(hotkey);
        robot.keyRelease(hotkey);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    private void startListening(LiveSpeechRecognizer recognizer) throws Exception {
        Robot robot = new Robot();

        new Thread(() -> {
            
            System.out.println("I'm listening");

            while(running) {
                SpeechResult result = recognizer.getResult();
                if(result != null) {
                    String text = result.getHypothesis();
                    System.out.println("You said: " + text);

                    if(text.equals("press")) {
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    } else if(text.equals("release")) {
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    } else if(text.equals("left")) {
                        robot.keyPress(KeyEvent.VK_LEFT);
                        robot.keyRelease(KeyEvent.VK_LEFT);
                    } else if(text.equals("up")) {
                        robot.keyPress(KeyEvent.VK_UP);
                        robot.keyRelease(KeyEvent.VK_UP);
                    } else if(text.equals("right")) {
                        robot.keyPress(KeyEvent.VK_RIGHT);
                        robot.keyRelease(KeyEvent.VK_RIGHT);
                    } else if(text.equals("down")) {
                        robot.keyPress(KeyEvent.VK_DOWN);
                        robot.keyRelease(KeyEvent.VK_DOWN);
                    } else if(text.contains("scroll down")) {
                        scroll(5);
                    } else if(text.contains("scroll up")) {
                        scroll(-5);
                    } else if(text.contains("switch open")) {
                        robot.keyPress(KeyEvent.VK_ALT);
                        robot.keyPress(KeyEvent.VK_TAB);
                    } else if(text.contains("switch close")) {
                        robot.keyRelease(KeyEvent.VK_TAB);
                        robot.keyRelease(KeyEvent.VK_ALT);
                    } else if(text.equals("new tab")) {
                        hotkeyCtrl(robot, KeyEvent.VK_T);
                    } else if(text.equals("close tab")) {
                        hotkeyCtrl(robot, KeyEvent.VK_W);
                    }
                } else {
                    System.out.println("I can't understand you");
                }
            }

            System.out.println("Exiting...");
            recognizer.stopRecognition();
            System.exit(0);
        }).start();
    }
}
