package ch.bbcag.handsfree.control.onscreenkeyboard;

import ch.bbcag.handsfree.HandsFreeRobot;
import ch.bbcag.handsfree.control.Colors;
import ch.bbcag.handsfree.control.HandsFreeFont;
import ch.bbcag.handsfree.control.button.HandsFreeButton;
import ch.bbcag.handsfree.control.button.HandsFreeButtonPalette;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.awt.*;

public class HandsFreeOnScreenKey extends StackPane {

    public static final double SCALE = 90;

    private VirtualKey key;
    private boolean keyPressed = false;

    private Robot robot = null;

    public HandsFreeOnScreenKey(VirtualKey key, HandsFreeRobot robot) {
        this.key = key;

        setMinWidth(key.getWidth() * SCALE);
        setMaxWidth(key.getWidth() * SCALE);
        setMinHeight(key.getHeight() * SCALE);
        setMaxHeight(key.getHeight() * SCALE);
        setPadding(new Insets(2));

        HandsFreeButton button = new HandsFreeButton();
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMaxHeight(Double.MAX_VALUE);
        getChildren().add(button);

        int textCounter = 0;
        for(String displayText : key.getDisplayTexts()) {
            if(!displayText.equals("")) {
                textCounter++;
            }
        }
        boolean onlyOneText = textCounter == 1;

        if(!onlyOneText) {
            GridPane gridPane = new GridPane();
            gridPane.setMouseTransparent(true);
            getChildren().add(gridPane);

            int row = 0;
            int column = 0;
            for(String displayText : key.getDisplayTexts()) {
                gridPane.add(createLabel(displayText, true), column, row);
                column++;
                if(column > 1) {
                    column = 0;
                    row++;
                }
            }
        } else {
            getChildren().add(createLabel(key.getDisplayTexts()[0], false));
        }

        button.setMousePressedHandler(event -> {
            if(!keyPressed) {
                robot.keyPress(key.getKeyCode());
                if(!key.isHold()) {
                    robot.keyRelease(key.getKeyCode());
                } else {
                    button.setPalette(HandsFreeButtonPalette.PRIMARY_PALETTE);
                    keyPressed = true;
                }
            } else {
                robot.keyRelease(key.getKeyCode());
                button.setPalette(HandsFreeButtonPalette.DEFAULT_PALETTE);
                keyPressed = false;
            }
        });
    }

    private Label createLabel(String text, boolean center) {
        Label label = new Label(text);
        label.setFont(HandsFreeFont.getFont(22));
        label.setTextFill(Colors.FONT);
        label.setMouseTransparent(true);

        if(center) {
            label.setAlignment(Pos.CENTER);
            label.setMinWidth(SCALE * key.getWidth() * 0.5);
            label.setMinHeight(SCALE * key.getHeight() * 0.5);
        } else {
            label.setPadding(new Insets(5, 0, 0, 10));
            label.setAlignment(Pos.TOP_LEFT);
            label.setMinWidth(SCALE * key.getWidth());
            label.setMinHeight(SCALE * key.getHeight());
        }

        return label;
    }

}
