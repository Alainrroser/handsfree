package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.gui.onscreenkeyboard.HandsFreeOnScreenKeyboard;
import javafx.application.Platform;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Shortcut {

    private List<Click> clicks = new ArrayList<>();

    private String name;

    private boolean keyboardVisible = false;
    private double keyboardX = 0.0;
    private double keyboardY = 0.0;

    private boolean wasKeyboardVisibleBeforeRunning;

    public List<Click> getClicks() {
        return clicks;
    }

    public void setClicks(List<Click> clicks) {
        this.clicks = clicks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isKeyboardVisible() {
        return keyboardVisible;
    }

    public void setKeyboardVisible(boolean keyboardVisible) {
        this.keyboardVisible = keyboardVisible;
    }

    public double getKeyboardX() {
        return keyboardX;
    }

    public void setKeyboardX(double keyboardX) {
        this.keyboardX = keyboardX;
    }

    public double getKeyboardY() {
        return keyboardY;
    }

    public void setKeyboardY(double keyboardY) {
        this.keyboardY = keyboardY;
    }

    public void run(HandsFreeRobot robot, HandsFreeOnScreenKeyboard keyboard) {
        wasKeyboardVisibleBeforeRunning = keyboard.isShowing();

        if(keyboardVisible != keyboard.isShowing()) {
            if(keyboardVisible) {
                keyboard.display();
            } else {
                keyboard.close();
            }
        }

        keyboard.setX(keyboardX);
        keyboard.setY(keyboardY);

        Thread thread = new Thread(() -> {
            int lastClickTime = 0;

            for(Click click : clicks) {
                robot.delay(click.getTime() - lastClickTime);
                robot.mouseMoveSmooth((int) click.getPosition().getX(), (int) click.getPosition().getY());
                robot.delay(100);
                robot.mouseClick(click.getButton());

                lastClickTime = click.getTime();
            }

            Platform.runLater(() -> {
                if(wasKeyboardVisibleBeforeRunning != keyboard.isShowing()) {
                    if(wasKeyboardVisibleBeforeRunning) {
                        keyboard.display();
                    } else {
                        keyboard.close();
                    }
                }
            });
        });
        thread.setDaemon(true);
        thread.start();
    }
}
