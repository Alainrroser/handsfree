package ch.bbcag.handsfree.control.shortcuts;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.gui.onscreenkeyboard.HandsFreeOnScreenKeyboard;
import javafx.application.Platform;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Shortcut {

    private List<Click> clicks = new ArrayList<>();

    private String name = "";

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

    public void run(HandsFreeContext context) {
        context.setShortcutRunning(true);

        wasKeyboardVisibleBeforeRunning = context.getKeyboard().isShowing();

        if(keyboardVisible != context.getKeyboard().isShowing()) {
            if(keyboardVisible) {
                context.getKeyboard().display();
            } else {
                context.getKeyboard().close();
            }
        }

        context.getKeyboard().setX(keyboardX);
        context.getKeyboard().setY(keyboardY);

        Thread thread = new Thread(() -> {
            int lastClickTime = 0;

            for(Click click : clicks) {
                context.getRobot().delay(click.getTime() - lastClickTime);
                context.getRobot().mouseMoveSmooth((int) click.getPosition().getX(), (int) click.getPosition().getY());
                context.getRobot().delay(100);
                context.getRobot().mouseClick(click.getButton());

                lastClickTime = click.getTime();
            }

            Platform.runLater(() -> {
                if(wasKeyboardVisibleBeforeRunning != context.getKeyboard().isShowing()) {
                    if(wasKeyboardVisibleBeforeRunning) {
                        context.getKeyboard().display();
                    } else {
                        context.getKeyboard().close();
                    }
                }
            });

            context.setShortcutRunning(false);
        });
        thread.setDaemon(true);
        thread.start();
    }
}
