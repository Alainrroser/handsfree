package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeKeyCodes;
import ch.bbcag.handsfree.gui.Colors;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class HandsFreeWordSuggestionPanel extends HBox {

    private HBox suggestionHBox;
    private HandsFreeContext context;
    private HandsFreeOnScreenKeyboard keyboard;

    public HandsFreeWordSuggestionPanel(HandsFreeContext context, HandsFreeOnScreenKeyboard keyboard) {
        this.context = context;
        this.keyboard = keyboard;

        setMinHeight(HandsFreeOnScreenKey.SCALE);
        setMaxHeight(HandsFreeOnScreenKey.SCALE);
        setBackground(new Background(new BackgroundFill(Colors.PANEL_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));

        suggestionHBox = new HBox(5);
        suggestionHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(suggestionHBox, Priority.ALWAYS);
        getChildren().add(suggestionHBox);
    }

    private void updateSuggestion(String suggestion, int typeCount) {
        HandsFreeTextButton suggestionButton = new HandsFreeTextButton(suggestion);
        suggestionButton.setOnAction(event -> {
            for(char c : suggestion.substring(typeCount).toCharArray()) {
                context.getRobot().keyTypeSpecial(KeyEvent.getExtendedKeyCodeForChar(c));
            }
            context.getRobot().keyTypeSpecial(HandsFreeKeyCodes.SPACE);

            keyboard.resetSuggestions();
        });
        suggestionButton.setMaxWidth(Double.MAX_VALUE);
        suggestionButton.setPadding(new Insets(10, 0, 10, 0));
        HBox.setHgrow(suggestionButton, Priority.ALWAYS);
        suggestionHBox.getChildren().add(suggestionButton);
    }

    public void updateSuggestions(List<String> suggestions, int typeCount) {
        suggestionHBox.getChildren().clear();
        for(String suggestion : suggestions) {
            updateSuggestion(suggestion, typeCount);
        }
    }

}
