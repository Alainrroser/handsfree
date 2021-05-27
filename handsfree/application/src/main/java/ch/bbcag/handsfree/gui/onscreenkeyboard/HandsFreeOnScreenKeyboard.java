package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.error.Error;
import ch.bbcag.handsfree.error.ErrorMessages;
import ch.bbcag.handsfree.error.KeyboardLoadingException;
import ch.bbcag.handsfree.gui.Colors;
import ch.bbcag.handsfree.gui.WindowDragController;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// We need this to be a popup so it doesn't steal focus from other windows
// when pressing a button
public class HandsFreeOnScreenKeyboard extends Popup {

    private int nextKeyY = 0;
    private int nextKeyX = 0;
    private Pane pane;

    private Stage stage; // The fake stage the popup is attached to

    private List<HandsFreeOnScreenKey> keys = new ArrayList<>();

    private WordSuggestions suggestions;
    private HandsFreeWordSuggestionPanel wordSuggestionPanel;

    public HandsFreeOnScreenKeyboard(HandsFreeContext context) {
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setWidth(0);
        stage.setHeight(0);

        VBox rootPane = new VBox(50);
        rootPane.setBackground(new Background(new BackgroundFill(Colors.BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
        rootPane.setBorder(new Border(new BorderStroke(Colors.STAGE_BORDER, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        rootPane.setPadding(new Insets(20));

        suggestions = new WordSuggestions();
        wordSuggestionPanel = new HandsFreeWordSuggestionPanel(context, suggestions);
        rootPane.getChildren().add(wordSuggestionPanel);

        pane = new Pane();
        pane.setPadding(new Insets(5));
        rootPane.getChildren().add(pane);

        tryLoadKeyboardLayout(context);

        WindowDragController controller = new WindowDragController(this, rootPane);
        controller.enable();

        getContent().add(rootPane);
        setHideOnEscape(false);

        addPositioningListeners();

        setOnHiding(event -> suggestions.resetSuggestions());
    }

    private void addPositioningListeners() {
        double environmentWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        double environmentHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        widthProperty().addListener(observable -> setX(environmentWidth / 2 - getWidth() / 2));
        heightProperty().addListener(observable -> setY(environmentHeight - getHeight()));
    }

    public void display() {
        stage.show();
        show(stage);
    }

    public void close() {
        stage.hide();
        hide();
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
                addKey(new HandsFreeOnScreenKey(key, context, this, suggestions));
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
