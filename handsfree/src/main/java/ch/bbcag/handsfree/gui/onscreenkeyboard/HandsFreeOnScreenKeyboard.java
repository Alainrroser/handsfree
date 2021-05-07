package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.error.ErrorMessages;
import ch.bbcag.handsfree.error.KeyboardLoadingException;
import ch.bbcag.handsfree.gui.Colors;
import ch.bbcag.handsfree.gui.WindowDragController;
import ch.bbcag.handsfree.error.Error;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// We need this to be a popup so it doesn't steal focus from other windows
// when pressing a button
public class HandsFreeOnScreenKeyboard extends Popup {

    private int nextKeyY = 0;
    private int nextKeyX = 0;
    private Pane pane;

    private Stage stage;

    private List<HandsFreeOnScreenKey> keys = new ArrayList<>();

    public HandsFreeOnScreenKeyboard(HandsFreeContext context) {
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setWidth(0);
        stage.setHeight(0);

        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double width = HandsFreeOnScreenKey.SCALE * 18 + 40;
        double height = HandsFreeOnScreenKey.SCALE * 6 + 40;

        StackPane rootPane = new StackPane();
        rootPane.setPrefWidth(width);
        rootPane.setPrefHeight(height);
        rootPane.setBackground(new Background(new BackgroundFill(Colors.BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
        rootPane.setBorder(new Border(new BorderStroke(Colors.STAGE_BORDER, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        rootPane.setPadding(new Insets(20));

        pane = new Pane();
        pane.setPadding(new Insets(5));
        rootPane.getChildren().add(pane);

        tryLoadKeyboardLayout(context);

        WindowDragController controller = new WindowDragController(this, rootPane);
        controller.enable();

        getContent().add(rootPane);
        setX(screenWidth / 2 - width / 2);
        setY(screenHeight - height);
        setHideOnEscape(false);
    }

    public void display() {
        stage.show();
        show(stage);
    }

    private void tryLoadKeyboardLayout(HandsFreeContext context) {
        try {
            loadKeyboardLayout(context);
        } catch(KeyboardLoadingException e) {
            Error.reportCritical(ErrorMessages.KEYBOARD_LAYOUT, e);
        }
    }

    private void loadKeyboardLayout(HandsFreeContext context) throws KeyboardLoadingException {
        VirtualKeyboardLayout layout = VirtualKeyboardLayoutLoader.loadFromResource("/keyboard_layouts/swiss.txt");

        for(VirtualKeyRow row : layout.getKeyRows()) {
            for(VirtualKey key : row.getKeys()) {
                addKey(new HandsFreeOnScreenKey(key, context, this));
            }

            nextKeyY += HandsFreeOnScreenKey.SCALE;
            nextKeyX = 0;
        }
    }

    private void addKey(HandsFreeOnScreenKey key) {
        key.setLayoutX(nextKeyX);
        key.setLayoutY(nextKeyY);
        pane.getChildren().add(key);
        keys.add(key);
        nextKeyX += key.getMinWidth();
    }

    public void releaseHeldKeys() {
        for(HandsFreeOnScreenKey key : keys) {
            if(key.isKeyPressed() && key.getKey().isHold() && !key.isKeyHeld()) {
                key.release();
            }
        }
    }

}
