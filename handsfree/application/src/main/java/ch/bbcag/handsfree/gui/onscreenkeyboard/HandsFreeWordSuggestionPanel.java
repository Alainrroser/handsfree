package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.HandsFreeContext;
import ch.bbcag.handsfree.gui.Colors;
import ch.bbcag.handsfree.gui.button.HandsFreeTextButton;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class HandsFreeWordSuggestionPanel extends HBox {

    private HBox suggestionHBox;
    private HandsFreeContext context;

    public HandsFreeWordSuggestionPanel(HandsFreeContext context) {
        this.context = context;
        suggestionHBox = new HBox();
        HBox.setHgrow(suggestionHBox, Priority.ALWAYS);
        setMinHeight(HandsFreeOnScreenKey.SCALE);
        setMaxHeight(HandsFreeOnScreenKey.SCALE);
        setBackground(new Background(new BackgroundFill(Colors.PANEL_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void updateSuggestion(String suggestion, int typeCount) {
        HandsFreeTextButton suggestionButton = new HandsFreeTextButton(suggestion);
        suggestionButton.setOnAction(event -> {
            for(char c: suggestion.substring(typeCount).toCharArray()) {
                context.getRobot().keyPressSpecial(KeyEvent.getExtendedKeyCodeForChar(c));
            }
        });
        suggestionButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(suggestionButton, Priority.ALWAYS);
        suggestionHBox.getChildren().add(suggestionButton);
    }

    public void updateSuggestions(String[] suggestions, int typeCount) {
        suggestionHBox.getChildren().clear();
        for(String suggestion: suggestions) {
            updateSuggestion(suggestion, typeCount);
        }
        this.getChildren().add(suggestionHBox);
    }

}
