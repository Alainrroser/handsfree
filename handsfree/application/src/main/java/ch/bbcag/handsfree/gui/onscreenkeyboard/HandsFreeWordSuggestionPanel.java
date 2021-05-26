package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.control.HandsFreeKeyCodes;
import ch.bbcag.handsfree.gui.Colors;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.awt.event.KeyEvent;
import java.util.List;

public class HandsFreeWordSuggestionPanel extends HBox {

    private HBox suggestionHBox;
    private HandsFreeContext context;
    private WordSuggestions wordSuggestions;

    public HandsFreeWordSuggestionPanel(HandsFreeContext context, WordSuggestions wordSuggestions) {
        this.context = context;
        this.wordSuggestions = wordSuggestions;

        setMinHeight(HandsFreeOnScreenKey.SCALE);
        setMaxHeight(HandsFreeOnScreenKey.SCALE);
        setBackground(new Background(new BackgroundFill(Colors.PANEL_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));

        suggestionHBox = new HBox(5);
        suggestionHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(suggestionHBox, Priority.ALWAYS);
        getChildren().add(suggestionHBox);

        wordSuggestions.getCurrentSuggestions().addListener((ListChangeListener<String>) c -> updateSuggestions());
    }

    public void updateSuggestions() {
        suggestionHBox.getChildren().clear();
        for(String suggestion : wordSuggestions.getCurrentSuggestions()) {
            updateSuggestion(suggestion, wordSuggestions.getCurrentlyTypedCharacterCount());
        }
    }

    private void updateSuggestion(String suggestion, int typeCount) {
        HandsFreeTextButton suggestionButton = new HandsFreeTextButton(suggestion);
        suggestionButton.setOnAction(event -> {
            for(char c : suggestion.substring(typeCount).toCharArray()) {
                context.getRobot().keyTypeSpecial(KeyEvent.getExtendedKeyCodeForChar(c));
            }
            context.getRobot().keyTypeSpecial(HandsFreeKeyCodes.SPACE);

            wordSuggestions.resetSuggestions();
        });
        suggestionButton.setMaxWidth(Double.MAX_VALUE);
        suggestionButton.setPadding(new Insets(10, 0, 10, 0));
        HBox.setHgrow(suggestionButton, Priority.ALWAYS);
        suggestionHBox.getChildren().add(suggestionButton);
    }

}
