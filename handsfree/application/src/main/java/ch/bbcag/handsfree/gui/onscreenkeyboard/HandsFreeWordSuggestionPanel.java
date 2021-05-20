package ch.bbcag.handsfree.gui.onscreenkeyboard;

import ch.bbcag.handsfree.gui.Colors;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;

public class HandsFreeWordSuggestionPanel extends HBox {

    public HandsFreeWordSuggestionPanel() {
        setMinHeight(HandsFreeOnScreenKey.SCALE);
        setMaxHeight(HandsFreeOnScreenKey.SCALE);
        setBackground(new Background(new BackgroundFill(Colors.PANEL_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void updateSuggestions() {

    }

}
