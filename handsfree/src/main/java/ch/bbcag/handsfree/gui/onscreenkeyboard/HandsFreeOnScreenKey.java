package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeRobot;
import ch.bbcag.handsfree.control.eyetracker.RegionGazeHandler;
import ch.bbcag.handsfree.gui.Colors;
import ch.bbcag.handsfree.gui.HandsFreeFont;
import ch.bbcag.handsfree.gui.button.HandsFreeButton;
import ch.bbcag.handsfree.gui.button.HandsFreeButtonPalette;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.awt.*;

public class HandsFreeOnScreenKey extends StackPane {

    public static final double SCALE = 90;

    private VirtualKey key;
    private boolean keyPressed = false;
    private boolean keyHeld = false;

    private HandsFreeContext context;
    private HandsFreeButton button;
    private HandsFreeOnScreenKeyboard keyboard;

    private RegionGazeHandler gazeHandler;

    public HandsFreeOnScreenKey(VirtualKey key, HandsFreeContext context, HandsFreeOnScreenKeyboard keyboard) {
        this.key = key;
        this.context = context;
        this.keyboard = keyboard;

        setMinWidth(key.getWidth() * SCALE);
        setMaxWidth(key.getWidth() * SCALE);
        setMinHeight(key.getHeight() * SCALE);
        setMaxHeight(key.getHeight() * SCALE);
        setPadding(new Insets(2));

        button = new HandsFreeButton();
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        getChildren().add(button);

        if(!isOnlyOneLabel()) {
            createMultipleLabels();
        } else {
            createSingleLabel();
        }

        initEventHandlers();
    }

    private boolean isOnlyOneLabel() {
        int textCounter = 0;
        for(String displayText : key.getDisplayTexts()) {
            if(!displayText.equals("")) {
                textCounter++;
            }
        }

        return textCounter == 1;
    }

    private void createMultipleLabels() {
        GridPane gridPane = new GridPane();
        gridPane.setMouseTransparent(true);
        getChildren().add(gridPane);

        int row = 0;
        int column = 0;

        for(String displayText : key.getDisplayTexts()) {
            Label label = new Label(displayText);
            label.setFont(HandsFreeFont.getFont(22));
            label.setTextFill(Colors.FONT);
            label.setMouseTransparent(true);
            label.setAlignment(Pos.CENTER);
            label.setMinWidth(SCALE * key.getWidth() * 0.5);
            label.setMinHeight(SCALE * key.getHeight() * 0.5);
            gridPane.add(label, column, row);

            column++;
            if(column > 1) {
                column = 0;
                row++;
            }
        }
    }

    private void createSingleLabel() {
        Label label = new Label(key.getDisplayTexts()[0]);
        label.setFont(HandsFreeFont.getFont(22));
        label.setTextFill(Colors.FONT);
        label.setMouseTransparent(true);
        label.setPadding(new Insets(5, 0, 0, 10));
        label.setAlignment(Pos.TOP_LEFT);
        label.setMinWidth(SCALE * key.getWidth());
        label.setMinHeight(SCALE * key.getHeight());
        getChildren().add(label);
    }

    private void initEventHandlers() {
        button.setMousePressedHandler(event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                press(true);
            } else {
                if(!keyPressed) {
                    press(false);
                } else {
                    release();
                }
            }
        });

        gazeHandler = new RegionGazeHandler(this, 750, (x, y) -> {
            press(false);
        });
        context.getEyeTracker().addRegionGazeHandler(gazeHandler);
    }

    public void press(boolean hold) {
        context.getRobot().keyPressSpecial(key.getKeyCode());

        if(!key.isHold()) {
            context.getRobot().keyReleaseSpecial(key.getKeyCode());
            keyboard.releaseHeldKeys();
        } else {
            keyPressed = true;
            keyHeld = hold;

            if(!hold) {
                button.setPalette(HandsFreeButtonPalette.PRIMARY_PALETTE);
            } else {
                button.setPalette(HandsFreeButtonPalette.ULTRA_PRIMARY_PALETTE);
            }
        }
    }

    public void release() {
        context.getRobot().keyReleaseSpecial(key.getKeyCode());
        button.setPalette(HandsFreeButtonPalette.DEFAULT_PALETTE);
        keyPressed = false;
        keyHeld = false;
    }

    public VirtualKey getKey() {
        return key;
    }

    public boolean isKeyPressed() {
        return keyPressed;
    }

    public boolean isKeyHeld() {
        return keyHeld;
    }

}
